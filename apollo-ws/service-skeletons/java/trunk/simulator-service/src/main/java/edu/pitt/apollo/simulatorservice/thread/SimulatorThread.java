package edu.pitt.apollo.simulatorservice.thread;

import edu.pitt.apollo.SimulatorServiceImpl;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.simulatorservice.queue.SimulatorServiceQueue;
import edu.pitt.apollo.simulatorservice.util.RunUtils;
import edu.pitt.apollo.types.v2_0_2.InfectiousDiseaseScenarioLocation;
import edu.pitt.apollo.types.v2_0_2.Location;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

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

	public SimulatorThread(ApolloDbUtils dbUtils, BigInteger runId, SoftwareIdentification translatorSoftwareId,
			boolean useFile, boolean useDatabase) {
		super();
		this.runId = runId;
		this.useFile = useFile;
		this.useDatabase = useDatabase;
		this.dbUtils = dbUtils;
		this.translatorSoftwareId = translatorSoftwareId;

		loadRunSimulationMessage();
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

			if (message == null) {
				return;
			}
			setRunStartedStatus();
			runSimulator();
			storeOutput();
			setRunCompletedStatus();
		} catch (IOException ex) {
			System.err.println("IOException running simulator: " + ex.getMessage());
		} finally {
			finalizeRun();
		}

	}

	private void loadRunSimulationMessage() {
		try {
			message = dbUtils.getRunSimulationMessageForRun(runId);
			if (message == null) {
				updateStatus(MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
			}

		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, ex.getMessage());
		} catch (IOException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, ex.getMessage());
		}
	}

	protected void updateStatus(MethodCallStatusEnum statusEnum, String message) {
		RunUtils.updateStatus(dbUtils, runId, statusEnum, message);
	}

	protected void storeOutput() throws IOException {
		if (useDatabase) {
			// get location
			InfectiousDiseaseScenarioLocation location = message.getInfectiousDiseaseScenario().getLocation();
			String locationString;
			if (location.getJurisdictionCode() != null) {
				locationString = location.getJurisdictionCode();
			} else {
				// else use first location included
				locationString = location.getJurisdictionCode();
			}
			storeFileOutputToDatabase(locationString);
			updateStatus(MethodCallStatusEnum.LOG_FILES_WRITTEN, "The simulator log files were written");

			storeTimeSeriesOutputToDatabase(locationString);
		}
		if (useFile) {
			storeLocalFileOutput();
		}
	}

	private void setRunCompletedStatus() {
		updateStatus(MethodCallStatusEnum.COMPLETED, "The run is complete");
	}

	private void setRunStartedStatus() {
		updateStatus(MethodCallStatusEnum.RUNNING, "The simulator is running");
	}

	private void finalizeRun() {
		SimulatorServiceQueue.simulatorThreadFinished();
		SimulatorServiceQueue.startQueuedSimulatorThreads();
	}

	protected final void addTextDataContentForSeries(double[] series, String seriesName, String locationString) throws IOException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			for (int i = 0; i < series.length; i++) {
				String line = "US" + locationString + " " + series[i] + " " + i + ":1" + "\n";
				stream.write(line.getBytes());
			}
		} catch (IOException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "IOException attempting to write " + seriesName + " series "
					+ "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
		}
		addTextDataContentForSeries(stream.toString(), seriesName);

	}

	private void addTextDataContentForSeries(String content, String seriesName) throws IOException {
		int dataContentKey;
		try {
			dataContentKey = dbUtils.addTextDataContent(content);
		} catch (ClassNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ClassNotFoundException attempting to add text data "
					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (SQLException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "SQLException attempting to add text data "
					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to add text data "
					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		}

		int runDataDescriptionId;
		try {
			runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, seriesName + ".txt",
					ApolloDbUtils.DbContentDataType.SIMULATOR_LOG_FILE, message.getSimulatorIdentification(),
					visualizerId);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseKeyNotFoundException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (ClassNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ClassNotFoundException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (SQLException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "SQLException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		}

		try {
			dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
		} catch (ClassNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ClassNotFoundException attempting to associate "
					+ "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
		} catch (SQLException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "SQLException attempting to associate "
					+ "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseKeyNotFoundException attempting to associate "
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
			updateStatus(MethodCallStatusEnum.FAILED, ex.getMessage());
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
			updateStatus(MethodCallStatusEnum.FAILED, "No label \"config.txt\" was found in the data content map"
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
			throw new ExceptionInInitializerError("ClassNotFoundException attempting to load the time series visualizer software ID: "
					+ ex.getMessage());
		} catch (IOException ex) {
			throw new ExceptionInInitializerError("IOException attempting to load the time series visualizer software ID: " + ex.getMessage());
		} catch (SQLException ex) {
			throw new ExceptionInInitializerError("SQLException attempting to load the time series visualizer software ID: " + ex.getMessage());
		}

		if (visualizerId == null) {
			throw new ExceptionInInitializerError("Could not find the time series visualizer software id in the list of registered services");
		}
	}

	static {
		loadTimeSeriesVisualizerSoftwareIdentification();
	}
}
