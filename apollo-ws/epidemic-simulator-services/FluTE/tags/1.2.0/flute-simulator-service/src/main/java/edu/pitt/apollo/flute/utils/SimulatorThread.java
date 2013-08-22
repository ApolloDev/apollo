package edu.pitt.apollo.flute.utils;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import edu.pitt.apollo.FluteSimulatorServiceException;
import edu.pitt.apollo.FluteSimulatorServiceImpl;
import edu.pitt.apollo.types.SimulatorConfiguration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Mar 21, 2013 Time:
 * 1:22:18 PM Class: SimulatorThread IDE: NetBeans 6.9.1
 */
public class SimulatorThread extends Thread {

    static final ResourceBundle SSH_PROPERTIES = ResourceBundle.getBundle("connections");
    
    private static final String FLUTE_USER = SSH_PROPERTIES.getString("ssh_user");
    private static final String FLUTE_PASSWORD = SSH_PROPERTIES.getString("ssh_password");
    private static final String FLUTE_HOST = SSH_PROPERTIES.getString("ssh_host");
    private static final String FLUTE_SCRIPT_COMMAND = "cd /home/flute; ./flute_output_util.sh";
    private static final String FLUTE_EXECUTE_COMMAND = "/home/flute/flute-source-1_15/flute ";
    private static final String FLUTE_RUNS_DIR = "/home/flute/runs/";
    private String runId;
    private String runIdHash;
    private String runDirectory;
    private String simConfigHash;
    private String simConfigJson;
    private boolean runSimulator = true;
    private boolean useFile;
    private boolean useDatabase;
    private SimulatorConfiguration simulatorConfiguration;

    public SimulatorThread(SimulatorConfiguration simulatorConfiguration, String simConfigHash,
            String runId, String runIdHash, String simConfigJson, boolean runSimulator,
            boolean useFile, boolean useDatabase) {
        super();
        this.simulatorConfiguration = simulatorConfiguration;
        this.runId = runId;
        this.runIdHash = runIdHash;
        this.simConfigHash = simConfigHash;
        this.runSimulator = runSimulator;
        this.simConfigJson = simConfigJson;
        this.useFile = useFile;
        this.useDatabase = useDatabase;
        this.runDirectory = FLUTE_RUNS_DIR + runIdHash;
    }

    @Override
    public void run() {

        try {

            String localRunDirectory = RunUtils.setStarted(runIdHash);
            String localResultsFile = localRunDirectory + File.separator + "flute-results.txt";
            if (runSimulator) {
                // first translate the configuration into the file format used by FluTE
                // store in the run directory created by RunUtils 
                FileUtils.createFluteConfigFile(simulatorConfiguration, localRunDirectory);

                // connect to flute server
                try {
                    SSHConnection connection = new SSHConnection(FLUTE_HOST, FLUTE_USER, FLUTE_PASSWORD);
                    connection.executeCommand(FLUTE_SCRIPT_COMMAND + " " + runIdHash); // creates directory with run ID hash
                    // and makes links to the files used by flute so that it can be run in this directory

                    // now scp the configuration file to the directory that was just created
                    String fluteConfigFile = localRunDirectory + File.separator + "nonverbose-text.txt";
                    String newConfigFileName = "flute-config-" + runIdHash;

                    connection.scpUploadFile(fluteConfigFile, newConfigFileName, runDirectory);

                    connection.executeCommand("cd /home/flute/runs/" + runIdHash);
                    connection.executeCommand("cd /home/flute/runs/" + runIdHash + "; "
                            + FLUTE_EXECUTE_COMMAND + newConfigFileName);

                    // now download the output file
                    String remoteResultsFile = "Summary0";

                    connection.scpDownloadFile(runDirectory, remoteResultsFile, localResultsFile);

                    // download the log file (contains num newly infectious and symptomatic by time step and census tract)
                    remoteResultsFile = "Log0";
                    localResultsFile = localRunDirectory + File.separator + "flute-symptomatic-time-series.txt";
                    connection.scpDownloadFile(runDirectory, remoteResultsFile, localResultsFile);

                    // done with SSH connections, so close them
                    connection.closeConnections();

                } catch (JSchException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "There was a JSchException trying to execute commands over SSH: " + ex.getMessage());
                    return; // return without setting finished file
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "InterruptedException waiting for the JSch channel to become closed: " + ex.getMessage());
                    return;
                } catch (SftpException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "SftpException attempting to upload the FluTE config file to the server: " + ex.getMessage());
                    return;
                }

                // storing the flute results in the database would be done here
                try {
                    storeOutputInDatabase(localResultsFile);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "FileNotFoundException attempting to "
                            + "store the results in the database: " + ex.getMessage());
                } catch (FluteSimulatorServiceException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "FluteSimulatorServiceException attempting to "
                            + "store the results in the database: " + ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "ClassNotFoundException attemtping to connect to the database: " + ex.getMessage());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    RunUtils.setError(runId, "SQLException attempting to store data to the database: " + ex.getMessage());
                }
            }

            RunUtils.setFinished(runIdHash);
            FluteSimulatorServiceImpl.removeRunFromQueuedList(runIdHash);
            FluteSimulatorServiceImpl.simulatorRunFinished();
            FluteSimulatorServiceImpl.runSimulatorThreads();
        } catch (NullPointerException e) {
            try {
                String formattedJSONString = FluteSimulatorServiceImpl.formatJSONString(simConfigJson);
                RunUtils.setError(runIdHash, "Enountered unexpected null value. Configuration object was: " + formattedJSONString);

                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            try {
                RunUtils.setError(runIdHash,
                        "Error creating run status file.  Specific error was:\n"
                        + e.getMessage());
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void storeOutputInDatabase(String fluteOutputFile) throws FluteSimulatorServiceException, FileNotFoundException,
            SQLException, ClassNotFoundException {

        String[] ageRanges = {"0-4", "5-18", "19-29", "30-64", "65+"};

        System.out.println("Storing results to database:");
        Map<String, String> tractIdMap = new HashMap<String, String>();
        String populationFile = null;

        // get the population location so we know what data file to use
        String popLocation = simulatorConfiguration.getPopulationInitialization().getPopulationLocation();
        if (popLocation.equals("06037")) { // los angeles county
            populationFile = "la-tracts.txt";
        } else {
            populationFile = "one-tracts.txt";
            popLocation = "00000"; // use this as the INCITS code for the "one" population
        }

        InputStream laFile = SimulatorThread.class.getResourceAsStream("/" + populationFile);
        Scanner laScanner = new Scanner(laFile);
        int tractIdInc = 0;

        // state, county, tract, pop, lat, long
        while (laScanner.hasNextLine()) {
            String[] splitLine = laScanner.nextLine().split(",");
            tractIdMap.put(Integer.toString(tractIdInc), splitLine[2]);
            tractIdInc++;
        }

        // load the output data
        File outputFile = new File(fluteOutputFile);
        Scanner scanner;
        try {
            scanner = new Scanner(outputFile);
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("Could not open the FluTE time series output file: " + fluteOutputFile);
        }

        List<Map<String, List<Double>>> tractTimeSeriesAgeRangeMaps = new ArrayList<Map<String, List<Double>>>();

        // used to store the total count per day (summed over the age ranges)
        Map<String, List<Double>> tractTimeSeriesMap = new HashMap<String, List<Double>>();

        // time,TractID,sym0-4,sym5-18,sym19-29,sym30-64,sym65+,cumsym0-4,cumsym5-18,cumsym19-29,cumsym30-64,cumsym65+
        scanner.nextLine(); // skip the header line
        while (scanner.hasNextLine()) {
            String[] splitLine = scanner.nextLine().split(",");
            String tractId = splitLine[1]; // this is the tract ID, which needs to be looked up
            String tract = tractIdMap.get(tractId);
            if (tract == null) {
                throw new FluteSimulatorServiceException("Tract ID " + tractId
                        + " did not have an associated tract code in data file " + populationFile);
            }

            double totalCount = 0.0;
            for (int ageRangeIndex = 0; ageRangeIndex < ageRanges.length; ageRangeIndex++) {
                double countForAgeRange = Double.parseDouble(splitLine[ageRangeIndex + 2]);
                totalCount += countForAgeRange;

                // for storing by age range
//                Map<String, List<Double>> tractTimeSeriesMapForAgeRange;
//                if (tractTimeSeriesAgeRangeMaps.size() < ageRangeIndex + 1) { // if the list does not contain this age range
//                    tractTimeSeriesMapForAgeRange = new HashMap<String, List<Double>>();
//                    tractTimeSeriesAgeRangeMaps.add(tractTimeSeriesMapForAgeRange);
//                } else {
//                    tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
//                }
//
//                // check tract and add count
//                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
//                    tractTimeSeriesMapForAgeRange.get(tract).add(countForAgeRange);
//                } else {
//                    List<Double> list = new ArrayList<Double>();
//                    list.add(countForAgeRange);
//                    tractTimeSeriesMapForAgeRange.put(tract, list);
//                }
            }

            if (tractTimeSeriesMap.containsKey(tract)) {
                tractTimeSeriesMap.get(tract).add(totalCount);
            } else {
                List<Double> tractList = new ArrayList<Double>();
                tractList.add(totalCount);
                tractTimeSeriesMap.put(tract, tractList);
            }
        }

        // now store the data
        int internalRunId = DbUtils.getInternalRunId(runId, simConfigHash, simConfigJson);
        int dsAxisId = DbUtils.getAxisId("disease_state");
        if (dsAxisId == -1) {
            DbUtils.createAxisId("disease_state");
            dsAxisId = DbUtils.getAxisId("disease_state");
        }

        int locAxisId = DbUtils.getAxisId("location");
        if (locAxisId == -1) {
            DbUtils.createAxisId("location");
            locAxisId = DbUtils.getAxisId("location");
        }

        int ageRangeAxisId = DbUtils.getAxisId("age_range");
        if (ageRangeAxisId == -1) {
            DbUtils.createAxisId("age_range");
            ageRangeAxisId = DbUtils.getAxisId("age_range");
        }

        // for testing
//        Map<String, List<Double>> tract = tractTimeSeriesAgeRangeMaps.get(2);
//        List<Double> values = tract.get("920105");
//        for (int i = 0; i < values.size(); i++) {
//            System.out.println("t: " + i + "  count: " + values.get(i));
//        }


        // for each tract listed in the tracts file
        for (String tract : tractIdMap.values()) {

            // for storing by age range
//            for (int ageRangeIndex = 0; ageRangeIndex < ageRanges.length; ageRangeIndex++) {
//
//                int tractPopIdForAgeRange = DbUtils.getOrCreatePopulationId(dsAxisId, locAxisId, ageRangeAxisId,
//                        "newly infectious and symptomatic", popLocation + tract, ageRanges[ageRangeIndex]);
//
//                // see if this tract has a time series
//                Map<String, List<Double>> tractTimeSeriesMapForAgeRange = tractTimeSeriesAgeRangeMaps.get(ageRangeIndex);
//                if (tractTimeSeriesMapForAgeRange.containsKey(tract)) {
//                    List<Double> timeSeriesForTract = tractTimeSeriesMapForAgeRange.get(tract);
//                    DbUtils.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
//                            "newly infectious and symptomatic in " + popLocation + tract + " age " + ageRanges[ageRangeIndex],
//                            timeSeriesForTract);
//                }
//            }

            // for storing by location only
            int tractPopIdForAgeRange = DbUtils.getOrCreatePopulationId(dsAxisId, locAxisId,
                    "newly exposed", popLocation + tract);

            // see if this tract has a time series
            if (tractTimeSeriesMap.containsKey(tract)) {
                List<Double> timeSeriesForTract = tractTimeSeriesMap.get(tract);
                DbUtils.insertTimeSeries(internalRunId, tractPopIdForAgeRange,
                        "newly exposed in " + popLocation + tract,
                        timeSeriesForTract);
            }
        }

    }

    public static void main(String[] args) {

        SimulatorThread thread = new SimulatorThread(null, null, "32", "46161338f5db15622f64e67f4dfd2998", null, true, false, false);
        thread.run();
    }
}