package edu.pitt.apollo.simulatorservice.thread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

import edu.pitt.apollo.SimulatorServiceImpl;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.simulatorservice.queue.SimulatorServiceQueue;
import edu.pitt.apollo.simulatorservice.util.RunUtils;
import edu.pitt.apollo.types.v2_0_2.Location;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jun 20, 2014 Time: 3:27:45 PM Class: SimulatorThread IDE: NetBeans 6.9.1
 */
public abstract class SimulatorThread extends Thread {

    protected RunSimulationMessage message;
    protected final BigInteger runId;
    private final boolean useFile;
    private final boolean useDatabase;
    protected final ApolloDbUtils dbUtils;
    private final SoftwareIdentification translatorSoftwareId;
    protected static SoftwareIdentification visualizerId;
    protected final String runDirectory;

    public SimulatorThread(ApolloDbUtils dbUtils,
            BigInteger runId, String runDirectory, SoftwareIdentification translatorSoftwareId,
            boolean useFile, boolean useDatabase) {
        super();
        this.runId = runId;
        this.useFile = useFile;
        this.useDatabase = useDatabase;
        this.dbUtils = dbUtils;
        this.runDirectory = runDirectory;
        this.translatorSoftwareId = translatorSoftwareId;
        
        message = getRunSimulationMessage();
    }

    public BigInteger getRunId() {
        return runId;
    }

    protected abstract void runSimulator() throws IOException;

    protected abstract void storeFileOutputToDatabase(String location);

    protected abstract void storeTimeSeriesOutputToDatabase(String location);

    protected abstract void storeLocalFileOutput();

    @Override
    public void run() {

        try {
            runSimulator();
            storeOutput();
            setRunFinishedFile();
        } catch (IOException ex) {
            System.err.println("IOException running simulator: " + ex.getMessage());
        } finally {
            finalizeRun();
        }

    }
    
    private RunSimulationMessage getRunSimulationMessage() {
        try {
            return dbUtils.getRunSimulationMessageForRun(runId);
        } catch (ApolloDatabaseException ex) {
            // write error status
        } catch (IOException ex) {
            // write error status
        }
    }

    protected void storeOutput() throws IOException {
        if (useDatabase) {
            // get location
            Location location = message.getInfectiousDiseaseScenario().getLocation();
            String locationString;
            if (location.getApolloLocationCode() != null) {
                locationString = location.getApolloLocationCode();
            } else {
                // else use first location included
                locationString = location.getLocationDefinition().getLocationsIncluded().get(0);
            }
            storeFileOutputToDatabase(locationString);

            try {
                RunUtils.setStatusFile(runDirectory, MethodCallStatusEnum.LOG_FILES_WRITTEN);
            } catch (IOException ex) {
                RunUtils.setError(runDirectory, "IOException attempting to write \"log files written\" "
                        + "file for run " + runId + ": " + ex.getMessage());
            }

            storeTimeSeriesOutputToDatabase(locationString);
        }
        if (useFile) {
            storeLocalFileOutput();
        }
    }

    protected final void setRunFinishedFile() throws IOException {
        try {
            RunUtils.setStatusFile(runDirectory, MethodCallStatusEnum.COMPLETED);
        } catch (IOException ex) {
            RunUtils.setError(runDirectory, "IOException attempting to write finished file for run "
                    + runId + ": " + ex.getMessage());
        }
    }

    private void finalizeRun() {
        SimulatorServiceQueue.simulatorThreadFinished();
        SimulatorServiceQueue.startQueuedSimulatorThreads();
    }

    protected final void addTextDataContentForSeries(String content, String seriesName) throws IOException {
        int dataContentKey;
        try {
            dataContentKey = dbUtils.addTextDataContent(content);
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(runDirectory, "ClassNotFoundException attempting to add text data "
                    + "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (SQLException ex) {
            RunUtils.setError(runDirectory, "SQLException attempting to add text data "
                    + "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (ApolloDatabaseException ex) {
            RunUtils.setError(runDirectory, "ApolloDatabaseException attempting to add text data "
                    + "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        }

        int runDataDescriptionId;
        try {
            runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, seriesName + ".txt",
                    ApolloDbUtils.DbContentDataType.SIMULATOR_LOG_FILE, message.getSimulatorIdentification(),
                    visualizerId);
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            RunUtils.setError(runDirectory, "ApolloDatabaseKeyNotFoundException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(runDirectory, "ClassNotFoundException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (SQLException ex) {
            RunUtils.setError(runDirectory, "SQLException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        } catch (ApolloDatabaseException ex) {
            RunUtils.setError(runDirectory, "ApolloDatabaseException attempting to get run data "
                    + "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
            return;
        }

        try {
            dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
        } catch (ClassNotFoundException ex) {
            RunUtils.setError(runDirectory, "ClassNotFoundException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
        } catch (SQLException ex) {
            RunUtils.setError(runDirectory, "SQLException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            RunUtils.setError(runDirectory, "ApolloDatabaseKeyNotFoundException attempting to associate "
                    + "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
        }
    }

    protected final String getConfigurationFileContent() throws IOException {
        // first get the configuration file from the database
        Map<String, ByteArrayOutputStream> map;
        try {
            int translatorKey = dbUtils.getSoftwareIdentificationKey(translatorSoftwareId);
            int simulatorKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
            map = dbUtils.getDataContentForSoftware(runId,
                    translatorKey, simulatorKey);
            // update this
        } catch (ApolloDatabaseException ex) {
            RunUtils.setError(runDirectory, ex.getMessage());
            return null;
        }

        String configurationFileContent = null;
        for (String label : map.keySet()) {
            if (label.equals("config.txt")) {
                configurationFileContent = map.get(label).toString();
                break;
            }
        }

        if (configurationFileContent == null) {
            RunUtils.setError(runDirectory, "No label \"config.txt\" was found in the data content map"
                    + " for run " + runId);
            return null;
        }

        return configurationFileContent;
    }

    private static void loadTimeSeriesVisualizerSoftwareIdentification() {

        System.out.println("Loading time series visualizer software identification");
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils(new File(SimulatorServiceImpl.getDatabasePropertiesFilename()));

            Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
            for (Integer id : softwareIdMap.keySet()) {
                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
                if (softwareId.getSoftwareName().toLowerCase().equals("time series visualizer")) {
                    visualizerId = softwareIdMap.get(id).getSoftwareIdentification();
                    break;
                }

            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("ClassNotFoundException attempting to load the time series visualizer software ID: "
                    + ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("IOException attempting to load the time series visualizer software ID: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException attempting to load the time series visualizer software ID: " + ex.getMessage());
        }

        if (visualizerId == null) {
            throw new RuntimeException("Could not find the time series visualizer software id in the list of registered services");
        }
    }

    static {
        loadTimeSeriesVisualizerSoftwareIdentification();
    }
}
