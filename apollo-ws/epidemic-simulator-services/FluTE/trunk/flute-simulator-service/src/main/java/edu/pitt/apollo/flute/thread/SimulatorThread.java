package edu.pitt.apollo.flute.thread;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.flute.exception.FluteSimulatorServiceException;
import edu.pitt.apollo.FluteSimulatorServiceImpl;
import edu.pitt.apollo.service.translatorservice._10._28._2013.TranslatorServiceEI;
import edu.pitt.apollo.service.translatorservice._10._28._2013.TranslatorServiceV131;
import edu.pitt.apollo.types._10._28._2013.PopulationDiseaseCensusResult;
import edu.pitt.apollo.types._10._28._2013.RunStatus;
import edu.pitt.apollo.types._10._28._2013.RunStatusEnum;
import edu.pitt.apollo.types._10._28._2013.SimulatorConfiguration;
import edu.pitt.apollo.flute.utils.ApolloDatabaseConnection;
import edu.pitt.apollo.flute.utils.ConfigurationFileUtils;
import edu.pitt.apollo.flute.utils.FluteOutputProcessor;
import edu.pitt.apollo.flute.utils.RunUtils;
import edu.pitt.apollo.flute.utils.SSHConnection;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
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
    private static final String TRANSLATOR_SERVICE_WSDL = CONNECTION_PROPERTIES.getString("translator_service_wsdl");
    private static final String FLUTE_SCRIPT_COMMAND = "cd /home/flute; ./flute_output_util.sh";
    private static final String FLUTE_EXECUTE_SCRIPT_COMMAND = "cd /home/flute; ./flute_run_util.sh";
    private static final String FLUTE_CHECK_EXECUTION_COMMAND = "cd /home/flute; ./check_process.sh";
    private static final String FLUTE_RUNS_DIR = "/home/flute/runs/";
    private String runIdHash;
    private String runDirectory;
    private String simConfigHash;
    private String simConfigJson;
    private String location;
    private int runLength;
    private boolean runSimulator = true;
    private RunSimulationMessage message;
    private int runId;
    private boolean useFile;
    private boolean useDatabase;
    private ApolloDbUtils dbUtils;
    private FluteSimulatorServiceImpl impl;
//    private boolean useFile;
//    private boolean useDatabase;

    public SimulatorThread(int runId, RunSimulationMessage message, FluteSimulatorServiceImpl impl, ApolloDbUtils dbUtils) {
        super();
        this.message = message;
        this.runId = runId;
        this.runIdHash = runIdHash;
        this.simConfigHash = simConfigHash;
//        this.runSimulator = runSimulator;
        this.simConfigJson = simConfigJson;
//        this.useFile = useFile;
//        this.useDatabase = useDatabase;
        this.runDirectory = FLUTE_RUNS_DIR + runIdHash;
    }

    public String getRunId() {
        return runId;
    }

    public String getRunIdHash() {
        return runIdHash;
    }

    private void finalizeRun() {
        // this function should be run before the run returns
        // whether it was succesful or if there was an error
        FluteSimulatorServiceImpl.simulatorRunFinished();
        FluteSimulatorServiceImpl.runSimulatorThreads();
    }

    @Override
    public void run() {

        try {
            LOGGER.log(Level.INFO, "CREATING STARTED FILE");

            String localRunDirectory = RunUtils.setStarted(runIdHash);

            LOGGER.log(Level.INFO, "CREATED STARTED FILE");
            String localResultsFile = localRunDirectory + File.separator + "flute-results.txt";
            if (runSimulator) {
//                try {
                // first translate the configuration into the file format used by FluTE
                // store in the run directory created by RunUtils 
                LOGGER.log(Level.INFO, "Sending request to translator service...");

                TranslatorServiceV131 service;
                TranslatorServiceEI port;
                try {
                    service = new TranslatorServiceV131(
                            new URL(TRANSLATOR_SERVICE_WSDL));
                    port = service.getTranslatorServiceEndpoint();
                } catch (Exception ex) {
                    RunUtils.setError(runIdHash, "There was an error creating the translator service: "
                            + ex.getMessage());
                    return;
                }
                RunStatus status = port.translateSimulatorConfiguration(simulatorConfiguration);
                if (!status.getStatus().equals(RunStatusEnum.COMPLETED)) {
                    RunUtils.setError(runIdHash, "Error from translation web service: " + status.getMessage());
                    return;
                }

                String fluteConfigFileUrl = status.getMessage() + "/" + ConfigurationFileUtils.FLUTE_CONFIGURATION_FILE_NAME;
                LOGGER.log(Level.INFO, "Downloading config file from translator URL...");
                String fluteConfigFile = ConfigurationFileUtils.downloadFile(fluteConfigFileUrl, runIdHash);
                String fluteVerboseConfigFileUrl = status.getMessage() + "/" + ConfigurationFileUtils.FLUTE_VERBOSE_TRANSLATED_FILE_NAME;
                ConfigurationFileUtils.downloadFile(new URL(fluteVerboseConfigFileUrl), runIdHash, ConfigurationFileUtils.FLUTE_VERBOSE_LOCAL_FILE_NAME);

//                    ConfigurationFileUtils.createFluteConfigFile(simulatorConfiguration, localRunDirectory);

                LOGGER.log(Level.INFO, "CREATED FLUTE CONFIG FILE");
//                } catch (ApolloTranslatorException ex) {
//                    LOGGER.log(Level.INFO, "APOLLO TRANSLATOR EXCEPTION");
//                    RunUtils.setError(runIdHash, "ApolloTranslatorException creating FluTE configuration file: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (FileNotFoundException ex) {
//                    RunUtils.setError(runIdHash, "FileNotFoundException creating FluTE configuration file: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (FluteSimulatorServiceException ex) {
//                    RunUtils.setError(runIdHash, "FluteSimulatorServiceException creating FluTE configuration file: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (IllegalAccessException ex) {
//                    RunUtils.setError(runIdHash, "IllegalAccessException creating FluTE configuration file: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (ClassNotFoundException ex) {
//                    RunUtils.setError(runIdHash, "ClassNotFoundException creating FluTE configuration file: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                }
                // connect to flute server
                try {

                    LOGGER.log(Level.INFO, "Creating connection");
                    SSHConnection connection = new SSHConnection();
                    connection.executeCommand(FLUTE_SCRIPT_COMMAND + " " + runIdHash); // creates directory with run ID hash
                    // and makes links to the files used by flute so that it can be run in this directory

                    // now scp the configuration file to the directory that was just created
//                    String fluteConfigFile = localRunDirectory + File.separator + ConfigurationFileUtils.FLUTE_CONFIGURATION_FILE_NAME;
                    String newConfigFileName = "flute-config-" + runIdHash;

                    LOGGER.log(Level.INFO, "Uploading config file...");
                    connection.scpUploadFile(fluteConfigFile, newConfigFileName, runDirectory);
//                    connection.scpUploadFileFromURL(fluteConfigFile, newConfigFileName, runDirectory);

                    LOGGER.log(Level.INFO, "Executing FluTE...");
                    String flutePid = connection.executeCommand(FLUTE_EXECUTE_SCRIPT_COMMAND + " " + runIdHash + " " + newConfigFileName).trim();

                    // need to wait until FluTE has finished
                    String result = connection.executeCommand(FLUTE_CHECK_EXECUTION_COMMAND + " " + flutePid);
                    while (result.trim().equals("true")) { // this means the process could be sent a message, so it exists
                        LOGGER.log(Level.INFO, "FluTE PID: " + flutePid);
                        result = connection.executeCommand(FLUTE_CHECK_EXECUTION_COMMAND + " " + flutePid);
                        Thread.sleep(2000);
                    }

                    // now download the output file
                    String remoteResultsFile = "Summary0";

                    LOGGER.log(Level.INFO, "Downloading flute-results.txt...");
                    connection.scpDownloadFile(runDirectory, remoteResultsFile, localResultsFile);

                    // download the log file (contains num newly infectious and symptomatic by time step and census tract)
                    remoteResultsFile = "Log0";
                    localResultsFile = localRunDirectory + File.separator + "flute-symptomatic-time-series.txt";
                    LOGGER.log(Level.INFO, "Downloading flute-symptomatic-time-series.txt...");
                    connection.scpDownloadFile(runDirectory, remoteResultsFile, localResultsFile);

                    LOGGER.log(Level.INFO, "Closing connection...");
                    // done with SSH connections, so close them
                    connection.closeConnections();

//                } catch (MalformedURLException ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "There was a MalformedURLException trying to execute commands over SSH: " + ex.getMessage());
//                    return; // return without setting finished file
                } catch (IOException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runIdHash, "There was a IOException trying to execute commands over SSH: " + ex.getMessage());
                    return; // return without setting finished file
                } catch (JSchException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runIdHash, "There was a JSchException trying to execute commands over SSH: " + ex.getMessage());
                    return; // return without setting finished file
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runIdHash, "InterruptedException waiting for the JSch channel to become closed: " + ex.getMessage());
                    return;
                } catch (SftpException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runIdHash, "SftpException attempting to upload the FluTE config file to the server: " + ex.getMessage());
                    return;
                }

                // process the FluTE output to prepare for creating visualizer input files
                // and storing in the database
                FluteOutputProcessor fluteOutputProcessor;
                try {
                    fluteOutputProcessor = new FluteOutputProcessor(localResultsFile, location, runLength);
                    LOGGER.log(Level.INFO, "Storing GAIA input file in database...");
                    fluteOutputProcessor.storeInputFileForGaiaToDatabase();
                    LOGGER.log(Level.INFO, "Storing time-series output in database...");
                    fluteOutputProcessor.storeFluteTimeSeriesDataToDatabase(runId, simConfigHash, simConfigJson);
                } catch (FluteSimulatorServiceException ex) {
                    RunUtils.setError(runIdHash, "FluteSimulatorServiceException attempting to "
                            + "process the FluTE output: " + ex.getMessage());
                    return;
                } catch (SQLException ex) {
                    RunUtils.setError(runIdHash, "SQLException attempting to store data to the database: " + ex.getMessage());
                    return;
                } catch (ClassNotFoundException ex) {
                    RunUtils.setError(runIdHash, "ClassNotFoundException attemtping to store data to the database: " + ex.getMessage());
                    return;
                }

//                try {
//                    LOGGER.log(Level.INFO, "Storing output in database...");
//                    storeOutputInDatabase(localResultsFile);
//                } catch (FileNotFoundException ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "FileNotFoundException attempting to "
//                            + "store the results in the database: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (FluteSimulatorServiceException ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "FluteSimulatorServiceException attempting to "
//                            + "store the results in the database: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (ClassNotFoundException ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "ClassNotFoundException attemtping to connect to the database: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "SQLException attempting to store data to the database: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    RunUtils.setError(runIdHash, "Exception attempting to store data to the database: " + ex.getMessage());
//                    finalizeRun();
//                    return;
//                }
            }

            RunUtils.setFinished(runIdHash);
        } catch (NullPointerException e) {
            try {
                String formattedJSONString = FluteSimulatorServiceImpl.formatJSONString(simConfigJson);
                RunUtils.setError(runIdHash, "Enountered unexpected null value. Configuration object was: " + formattedJSONString);
                return;
            } catch (IOException e1) {
                return;
            }
        } catch (IOException e) {
            try {
                RunUtils.setError(runIdHash,
                        "Error creating run status file.  Specific error was:\n"
                        + e.getMessage());
                return;
            } catch (IOException e1) {
                LOGGER.log(Level.SEVERE, "IOException creating a file in the run directory: {0}", e.getMessage());
                return;
            }
        } finally {
            finalizeRun();
        }
    }

//    private void storeOutputInDatabase(String fluteOutputFile) throws FluteSimulatorServiceException, FileNotFoundException,
//            SQLException, ClassNotFoundException {
//
////        String[] ageRanges = {"0-4", "5-18", "19-29", "30-64", "65+"};
////
////        LOGGER.log(Level.INFO, "Storing results to database:");
////        Map<String, String> tractIdMap = new HashMap<String, String>();
////        String populationFile = null;
////
////        // get the population location so we know what data file to use
////        List<PopulationDiseaseCensusResult> populationDiseaseCensusResults = simulatorConfiguration.getPopulationInitialization().getPopulationStates();
////        if (populationDiseaseCensusResults.size() != 1) {
////            throw new FluteSimulatorServiceException("FluTE only supports a single population location a this time");
////        }
////        List<String> locationsIncluded = populationDiseaseCensusResults.get(0).getLocation().getLocationsIncluded();
////        if (locationsIncluded.size() != 1) {
////            throw new FluteSimulatorServiceException("FluTE only supports a single population location at this time");
////        }
////        String popLocation = locationsIncluded.get(0);
////        if (popLocation.equals("06037")) { // los angeles county
////            populationFile = "la-tracts.txt";
////        } else {
////            populationFile = "one-tracts.txt";
////            popLocation = "00000"; // use this as the INCITS code for the "one" population
////        }
////
////        // get the run length
////        int runLength = simulatorConfiguration.getSimulatorTimeSpecification().getRunLength().intValue();
////
////        InputStream laFile = SimulatorThread.class.getResourceAsStream("/" + populationFile);
////        Scanner laScanner = new Scanner(laFile);
////        int tractIdInc = 0;
////
////        // state, county, tract, pop, lat, long
////        while (laScanner.hasNextLine()) {
////            String[] splitLine = laScanner.nextLine().split(",");
////            tractIdMap.put(Integer.toString(tractIdInc), splitLine[2]);
////            tractIdInc++;
////        }
////
////        // load the output data
////        File outputFile = new File(fluteOutputFile);
////        Scanner scanner;
////        try {
////            scanner = new Scanner(outputFile);
////        } catch (FileNotFoundException ex) {
////            throw new FileNotFoundException("Could not open the FluTE time series output file: " + fluteOutputFile);
////        }
////
////        List<Map<String, List<Double>>> tractTimeSeriesAgeRangeMaps = new ArrayList<Map<String, List<Double>>>();
////
////        // used to store the total count per day (summed over the age ranges)
////        Map<String, List<Double>> tractTimeSeriesMap = new HashMap<String, List<Double>>();
////
////        // time,TractID,sym0-4,sym5-18,sym19-29,sym30-64,sym65+,cumsym0-4,cumsym5-18,cumsym19-29,cumsym30-64,cumsym65+
////        scanner.nextLine(); // skip the header line
////        while (scanner.hasNextLine()) {
////            String[] splitLine = scanner.nextLine().split(",");
////            String tractId = splitLine[1]; // this is the tract ID, which needs to be looked up
////            String tract = tractIdMap.get(tractId);
////            if (tract == null) {
////                throw new FluteSimulatorServiceException("Tract ID " + tractId
////                        + " did not have an associated tract code in data file " + populationFile);
////            }
////
////            double totalCount = 0.0;
////            for (int ageRangeIndex = 0; ageRangeIndex < ageRanges.length; ageRangeIndex++) {
////                double countForAgeRange = Double.parseDouble(splitLine[ageRangeIndex + 2]);
////                totalCount += countForAgeRange;
////
////                // for storing by age range
//////                Map<String, List<Double>> tractTimeSeriesMapForAgeRange;
//////                if (tractTimeSeriesAgeRangeMaps.size() < ageRangeIndex + 1) { // if the list does not contain this age range
//////                    tractTimeSeriesMapForAgeRange = new HashMap<String, List<Double>>();
//////                    tractTimeSeriesAgeRangeMaps.add(tractTimeSeriesMapForAgeRange);
//////                } else {
//////                    tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
//////                }
//////
//////                // check tract and add count
//////                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
//////                    tractTimeSeriesMapForAgeRange.get(tract).add(countForAgeRange);
//////                } else {
//////                    List<Double> list = new ArrayList<Double>();
//////                    list.add(countForAgeRange);
//////                    tractTimeSeriesMapForAgeRange.put(tract, list);
//////                }
////            }
////
////            if (tractTimeSeriesMap.containsKey(tract)) {
////                tractTimeSeriesMap.get(tract).add(totalCount);
////            } else {
////                List<Double> tractList = new ArrayList<Double>();
////                tractList.add(totalCount);
////                tractTimeSeriesMap.put(tract, tractList);
////            }
////        }
////
////        // create time-series dataset for the sum of all the tracts
////        System.err.println("creating tract sum time series");
////        List<Double> tractSumTimeSeries = new ArrayList<Double>(runLength); // no tract will have a time series longer than runLength
////        for (int i = 0; i < runLength; i++) {
////            tractSumTimeSeries.add(0.0);
////        }
////        for (String tract : tractIdMap.values()) {
////
////            if (tractTimeSeriesMap.containsKey(tract)) {
////                List<Double> tractTimeSeries = tractTimeSeriesMap.get(tract);
////                for (int i = 0; i < tractTimeSeries.size(); i++) {
////                    tractSumTimeSeries.set(i, tractSumTimeSeries.get(i) + tractTimeSeries.get(i));
////                }
////            }
////        }
//
//        // now store the data
//        int internalRunId = ApolloDatabaseConnection.getInternalRunId(runId, simConfigHash, simConfigJson);
//        int dsAxisId = ApolloDatabaseConnection.getAxisId("disease_state");
//        if (dsAxisId == -1) {
//            ApolloDatabaseConnection.createAxisId("disease_state");
//            dsAxisId = ApolloDatabaseConnection.getAxisId("disease_state");
//        }
//
//        int locAxisId = ApolloDatabaseConnection.getAxisId("location");
//        if (locAxisId == -1) {
//            ApolloDatabaseConnection.createAxisId("location");
//            locAxisId = ApolloDatabaseConnection.getAxisId("location");
//        }
//
//        int ageRangeAxisId = ApolloDatabaseConnection.getAxisId("age_range");
//        if (ageRangeAxisId == -1) {
//            ApolloDatabaseConnection.createAxisId("age_range");
//            ageRangeAxisId = ApolloDatabaseConnection.getAxisId("age_range");
//        }
//
//        // for testing
////        Map<String, List<Double>> tract = tractTimeSeriesAgeRangeMaps.get(2);
////        List<Double> values = tract.get("920105");
////        for (int i = 0; i < values.size(); i++) {
////            LOGGER.log.println("t: " + i + "  count: " + values.get(i));
////        }
//
//
//        // for each tract listed in the tracts file
//        for (String tract : tractIdMap.values()) {
//
//            // for storing by age range
////            for (int ageRangeIndex = 0; ageRangeIndex < ageRanges.length; ageRangeIndex++) {
////
////                int tractPopIdForAgeRange = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId, ageRangeAxisId,
////                        "newly infectious and symptomatic", popLocation + tract, ageRanges[ageRangeIndex]);
////
////                // see if this tract has a time series
////                Map<String, List<Double>> tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
////                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
////                    List<Double> timeSeriesForTract = tractTimeSeriesMapForAgeRange.get(tract);
////                    ApolloDatabaseConnection.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
////                            "newly infectious and symptomatic in " + popLocation + tract + " age " + ageRanges[ageRangeIndex],
////                            timeSeriesForTract);
////                }
////            }
//
//            // for storing by location only
//            int tractPopIdForAgeRange = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId,
//                    "newly exposed", popLocation + tract);
//
//            // see if this tract has a time series
//            if (tractTimeSeriesMap.containsKey(tract)) {
//                List<Double> timeSeriesForTract = tractTimeSeriesMap.get(tract);
//                ApolloDatabaseConnection.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
//                        "newly exposed in " + popLocation + tract,
//                        timeSeriesForTract);
//            }
//        }
//
//        // store the tract-sum dataset
//        int popId = ApolloDatabaseConnection.getOrCreatePopulationId(dsAxisId, locAxisId, "newly exposed", popLocation);
//        ApolloDatabaseConnection.insertTimeSeries(internalRunId, popId, "newly exposed in " + popLocation, tractSumTimeSeries);
//
//    }
    public static void main(String[] args) {

        SimulatorThread thread = new SimulatorThread(null, null, "32", "46161338f5db15622f64e67f4dfd2998", null);
        thread.run();
    }
}