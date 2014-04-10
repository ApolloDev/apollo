package edu.pitt.apollo.flute.thread;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.flute.exception.FluteSimulatorServiceException;
import edu.pitt.apollo.FluteSimulatorServiceImpl;
import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.flute.utils.FluteOutputProcessor;
import edu.pitt.apollo.flute.utils.RunUtils;
import edu.pitt.apollo.flute.utils.SSHConnection;
import edu.pitt.apollo.types.v2_0_1.Location;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Mar 21, 2013 Time:
 * 1:22:18 PM Class: SimulatorThread IDE: NetBeans 6.9.1
 */
public class SimulatorThread extends Thread {

    private static final Logger LOGGER = Logger.getLogger(SimulatorThread.class.getName());
    static final ResourceBundle CONNECTION_PROPERTIES = ResourceBundle.getBundle("flute");
    private static final String FLUTE_SCRIPT_COMMAND = "cd /home/flute; ./flute_output_util.sh";
    private static final String FLUTE_EXECUTE_SCRIPT_COMMAND = "cd /home/flute; ./flute_run_util.sh";
    private static final String FLUTE_CHECK_EXECUTION_COMMAND = "cd /home/flute; ./check_process.sh";
    private static final String FLUTE_RUNS_DIR = "/home/flute/runs_2_0_1/";
    private static SoftwareIdentification translatorSoftwareId;
    private RunSimulationMessage message;
    private int runId;
    private ApolloDbUtils dbUtils;
    private String runDirectory;
    private String remoteRunDirectory;

    public SimulatorThread(int runId, RunSimulationMessage message, ApolloDbUtils dbUtils) {
        super();
        this.message = message;
        this.runId = runId;
        this.dbUtils = dbUtils;
        runDirectory = FluteSimulatorServiceImpl.getRunDirectory(runId);
        this.remoteRunDirectory = FLUTE_RUNS_DIR + runId;
    }

    private void finalizeRun() {
        // this function should be run before the run returns
        // whether it was succesful or if there was an error
        FluteSimulatorServiceImpl.simulatorRunFinished();
        FluteSimulatorServiceImpl.runSimulatorThreads();
    }

    public int getRunId() {
        return runId;
    }

    @Override
    public void run() {

        try {

            // first get the configuration file from the database
            Map<String, ByteArrayOutputStream> map;
            try {
                int translatorKey = dbUtils.getSoftwareIdentificationKey(translatorSoftwareId);
                int simulatorKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
                map = dbUtils.getDataContentForSoftware(runId,
                        translatorKey, simulatorKey);
                // update this
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                RunUtils.setError(runDirectory, "ApolloDatabaseKeyNotFoundException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (ClassNotFoundException ex) {
                RunUtils.setError(runDirectory, "ClassNotFoundException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (IOException ex) {
                RunUtils.setError(runDirectory, "IOException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            } catch (SQLException ex) {
                RunUtils.setError(runDirectory, "SQLException attempting to get data content map"
                        + " for run " + runId + ": " + ex.getMessage());
                return;
            }

            ByteArrayOutputStream configurationFileContent = null;
            for (String label : map.keySet()) {
                if (label.equals("config.txt")) {
                    configurationFileContent = map.get(label);
                    break;
                }
            }

            if (configurationFileContent == null) {
                RunUtils.setError(runDirectory, "No label \"config.txt\" was found in the data content map"
                        + " for run " + runId);
                return;
            }

            String localResultsFileName = runDirectory + File.separator + "flute-results.txt";
            // write the translated file to the run directory
            String fluteConfigFileName = runDirectory + File.separator + "config.txt";
            OutputStream configOutputStream;
            try {
                configOutputStream = new FileOutputStream(fluteConfigFileName);
                configurationFileContent.writeTo(configOutputStream);

            } catch (FileNotFoundException ex) {
                RunUtils.setError(runDirectory, "FileNotFoundException attmepting to create file " + fluteConfigFileName
                        + " for run " + runId + ": " + ex.getMessage());
            } catch (IOException ex) {
                RunUtils.setError(runDirectory, "IOEXception attmepting to write to file " + fluteConfigFileName
                        + " for run " + runId + ": " + ex.getMessage());
            }

            // connect to flute server
            try {

                LOGGER.log(Level.INFO, "Creating connection to FluTE server...");
                SSHConnection connection = new SSHConnection();
                connection.executeCommand(FLUTE_SCRIPT_COMMAND + " " + runId); // creates directory with run ID
                // and makes links to the files used by flute so that it can be run in this directory

                // now scp the configuration file to the directory that was just created
                String newConfigFileName = "flute-config-" + runId;

                LOGGER.log(Level.INFO, "Uploading config file...");
                connection.scpUploadFile(fluteConfigFileName, newConfigFileName, remoteRunDirectory);

                LOGGER.log(Level.INFO, "Executing FluTE...");
                String flutePid = connection.executeCommand(FLUTE_EXECUTE_SCRIPT_COMMAND + " " + runId + " " + newConfigFileName).trim();

                LOGGER.log(Level.INFO, "FluTE PID: " + flutePid);
                // need to wait until FluTE has finished
                String result = connection.executeCommand(FLUTE_CHECK_EXECUTION_COMMAND + " " + flutePid);
                while (result.trim().equals("true")) { // this means the process could be sent a message, so it exists
                    result = connection.executeCommand(FLUTE_CHECK_EXECUTION_COMMAND + " " + flutePid);
                    Thread.sleep(2000);
                }

                // now download the output file
                String remoteResultsFile = "Summary0";

                LOGGER.log(Level.INFO, "Downloading flute-results.txt...");
                connection.scpDownloadFile(remoteRunDirectory, remoteResultsFile, localResultsFileName);

                // download the log file (contains num newly infected by time step and census tract)
                remoteResultsFile = "Log0";
                localResultsFileName = runDirectory + File.separator + "flute-newly-infected-time-series.txt";
                LOGGER.log(Level.INFO, "Downloading flute-newly-infected-time-series.txt...");
                connection.scpDownloadFile(remoteRunDirectory, remoteResultsFile, localResultsFileName);

                LOGGER.log(Level.INFO, "Closing connection...");
                // done with SSH connections, so close them
                connection.closeConnections();

            } catch (IOException ex) {
                RunUtils.setError(runDirectory, "There was a IOException trying to execute commands over SSH for run " + runId + ": " + ex.getMessage());
                return; // return without setting finished file
            } catch (JSchException ex) {
                RunUtils.setError(runDirectory, "There was a JSchException trying to execute commands over SSH " + runId + ": " + ex.getMessage());
                return; // return without setting finished file
            } catch (InterruptedException ex) {
                RunUtils.setError(runDirectory, "InterruptedException waiting for the JSch channel to become closed for run " + runId + ": " + ex.getMessage());
                return;
            } catch (SftpException ex) {
                RunUtils.setError(runDirectory, "SftpException attempting to upload the FluTE config file to the server for run " + runId + ": " + ex.getMessage());
                return;
            }

            // process the FluTE output to prepare for creating visualizer input files
            // and storing in the database
            FluteOutputProcessor fluteOutputProcessor;
            try {

                Location location = message.getInfectiousDiseaseScenario().getLocation();
                String locationString;
                if (location.getApolloLocationCode() != null) {
                    locationString = location.getApolloLocationCode();
                } else {
                    // else use first location included
                    locationString = location.getLocationDefinition().getLocationsIncluded().get(0);
                }

                int runLength = message.getSimulatorTimeSpecification().getRunLength().intValue();

                fluteOutputProcessor = new FluteOutputProcessor(localResultsFileName, message.getSimulatorIdentification(),
                        locationString, runLength, runId, runDirectory, dbUtils);
                LOGGER.log(Level.INFO, "Storing GAIA input file in database...");
                fluteOutputProcessor.storeInputFileForGaiaToDatabase();

                try {
                    RunUtils.setStatusFile(runDirectory, MethodCallStatusEnum.LOG_FILES_WRITTEN);
                } catch (IOException ex) {
                    RunUtils.setError(runDirectory, "IOException attempting to write \"log files written\" "
                            + "file for run " + runId + ": " + ex.getMessage());
                }

                LOGGER.log(Level.INFO, "Storing time-series output in database...");
                fluteOutputProcessor.storeFluteTimeSeriesDataToDatabase(runId);
            } catch (FluteSimulatorServiceException ex) {
                RunUtils.setError(runDirectory, "FluteSimulatorServiceException attempting to "
                        + "process the FluTE output: " + ex.getMessage());
                return;
            } catch (SQLException ex) {
                RunUtils.setError(runDirectory, "SQLException attempting to store data to the database: " + ex.getMessage());
                return;
            } catch (ClassNotFoundException ex) {
                RunUtils.setError(runDirectory, "ClassNotFoundException attemtping to store data to the database: " + ex.getMessage());
                return;
            }


            try {
                RunUtils.setStatusFile(runDirectory, MethodCallStatusEnum.COMPLETED);
            } catch (IOException ex) {
                RunUtils.setError(runDirectory, "IOException attempting to write finished file for run "
                        + runId + ": " + ex.getMessage());
            }
        } catch (IOException ex) {
            System.err.println("IOException attempting to write error file for run " + runId
                    + ": " + ex.getMessage());

        } finally {
            finalizeRun();
        }
    }

    static {
        loadTranslatorSoftwareIdentification();
    }

    private static void loadTranslatorSoftwareIdentification() {

        System.out.println("Loading translator software identification");
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils(new File(FluteSimulatorServiceImpl.getDatabasePropertiesFilename()));

            Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
            for (Integer id : softwareIdMap.keySet()) {
                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
                if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
                    translatorSoftwareId = softwareIdMap.get(id).getSoftwareIdentification();
                    break;
                }

            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("ClassNotFoundException attempting to load the translator software ID: "
                    + ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("IOException attempting to load the translator software ID: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException attempting to load the translator software ID: " + ex.getMessage());
        }

        if (translatorSoftwareId == null) {
            throw new RuntimeException("Could not find translator in the list of registered services");
        }
    }
}