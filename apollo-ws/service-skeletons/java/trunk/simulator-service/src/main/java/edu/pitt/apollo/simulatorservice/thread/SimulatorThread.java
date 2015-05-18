package edu.pitt.apollo.simulatorservice.thread;

import edu.pitt.apollo.*;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.simulatorservice.exception.SimulatorServiceException;
import edu.pitt.apollo.simulatorservice.util.RunUtils;
import edu.pitt.apollo.types.v3_0_0.Location;

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
public abstract class SimulatorThread extends ApolloServiceThread {

	protected RunSimulationMessage message;
	private final boolean useFile;
	private final boolean useDatabase;
	protected final ApolloDbUtils dbUtils;
	private final SoftwareIdentification translatorSoftwareId;
	protected static SoftwareIdentification visualizerSoftwareId;
	private static SoftwareIdentification dataServiceSoftwareId;
	private static int dataServiceSoftwareKey;

	public SimulatorThread(BigInteger runId, ApolloServiceQueue queue, ApolloDbUtils dbUtils, SoftwareIdentification translatorSoftwareId,
			boolean useFile, boolean useDatabase) {
		super(runId, queue);
		this.useFile = useFile;
		this.useDatabase = useDatabase;
		this.dbUtils = dbUtils;
		this.translatorSoftwareId = translatorSoftwareId;

		loadRunSimulationMessage();
	}

	protected abstract void runSimulator() throws IOException;

	protected abstract void storeFileOutputToDatabase(String location) throws SimulatorServiceException;

	protected abstract void storeTimeSeriesOutputToDatabase(String location);

	protected abstract void storeLocalFileOutput();

	@Override
	public void runApolloService() {

		try {

			if (message == null) {
				return;
			}
			setRunStartedStatus();
			runSimulator();
			storeOutput();
			setRunCompletedStatus();
		} catch (IOException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "IOException running simulator: " + ex.getMessage());
		} catch (SimulatorServiceException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "SimulatorServiceException: " + ex.getMessage());
		}

	}

	private void loadRunSimulationMessage() {
		try {
			message = dbUtils.getRunSimulationMessageForRun(runId);
			if (message == null) {
				updateStatus(MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null for run " + runId);
			}
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, ex.getMessage());
		} catch (JsonUtilsException jue) {
			updateStatus(MethodCallStatusEnum.FAILED, jue.getMessage());
		} catch (IOException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, ex.getMessage());
		}
	}

	protected void updateStatus(MethodCallStatusEnum statusEnum, String message) {
		RunUtils.updateStatus(dbUtils, runId, statusEnum, message);
	}

	protected void storeOutput() throws IOException, SimulatorServiceException {
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

	protected final void addTextDataContentForSeries(double[] series, String seriesName, String locationString) throws IOException {

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			for (int i = 0; i < series.length; i++) {
				String line = locationString + " " + series[i] + " " + i + ":1" + "\n";
				stream.write(line.getBytes());
			}
		} catch (IOException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "IOException attempting to write " + seriesName + " series "
					+ "to ByteArrayOutputStream for run " + runId + ": " + ex.getMessage());
		}
		addTextDataContentForSeriesForVisualizer(stream.toString(), seriesName);

	}

//	protected final void addTextDataContentForAllSeries(Map<String, Double[]> seriesNamesAndContent, String location) throws IOException {
//
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		for (String seriesName : seriesNamesAndContent.keySet()) {
//			Double[] series = seriesNamesAndContent.get(seriesName);
//
//			for (int t = 0; t < series.length; t++) {
//				String line = t + "," + location + "," + seriesName + "," + "," + "," + series[t] + "\n";
//				stream.write(line.getBytes());
//			}
//		}
//
//		String seriesName = "run_output";
//		try {
//			int sourceSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
//			addTextDataContentForSeries(stream.toString(), seriesName, sourceSoftwareIdKey, dataServiceSoftwareKey);
//		} catch (ApolloDatabaseException ex) {
//			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to add text data "
//					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
//		}
//
//	}
	private void addTextDataContentForSeriesForVisualizer(String content, String seriesName) throws IOException {

		try {
			int sourceSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
			int visualizerSoftwareKey = dbUtils.getSoftwareIdentificationKey(visualizerSoftwareId);

			addTextDataContentForSeries(content, seriesName, sourceSoftwareIdKey, visualizerSoftwareKey);
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to add text data "
					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
		}
	}

	private void addTextDataContentForSeries(String content, String seriesName, int sourceSoftwareIdKey, int destinationSoftwareIdKey) throws IOException {
		int dataContentKey;
		try {
			dataContentKey = dbUtils.addTextDataContent(content);
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to add text data "
					+ "content for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;

	    } catch (Md5UtilsException md5ex) {
		    updateStatus(MethodCallStatusEnum.FAILED, "Md5ex attempting to add text data "
				+ "content for series " + seriesName + " for run " + runId + ": " + md5ex.getMessage());
	    	return;
	    }

		int runDataDescriptionId;
		try {
			runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, seriesName + ".txt",
					ApolloDbUtils.DbContentDataType.SIMULATOR_LOG_FILE, sourceSoftwareIdKey,
					destinationSoftwareIdKey);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseKeyNotFoundException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to get run data "
					+ "description ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
			return;
		}

		try {
			dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseKeyNotFoundException attempting to associate "
					+ "content with run ID for series " + seriesName + " for run " + runId + ": " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			updateStatus(MethodCallStatusEnum.FAILED, "ApolloDatabaseException attempting to associate "
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

		System.out.println("Loading software identifications");
		try {
			//ApolloDbUtils dbUtils = new ApolloDbUtils(new File(SimulatorServiceImpl.getDatabasePropertiesFilename()));
            ApolloDbUtils dbUtils = new ApolloDbUtils();

			Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
			for (Integer id : softwareIdMap.keySet()) {
				SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
				if (softwareId.getSoftwareName().toLowerCase().equals("time series visualizer")) {
					visualizerSoftwareId = softwareIdMap.get(id).getSoftwareIdentification();
				} else if (softwareId.getSoftwareName().toLowerCase().equals("data service")) {
					dataServiceSoftwareId = softwareIdMap.get(id).getSoftwareIdentification();
				}

			}

			dataServiceSoftwareKey = dbUtils.getSoftwareIdentificationKey(dataServiceSoftwareId);

//		} catch (IOException ex) {
//			throw new ExceptionInInitializerError("IOException attempting to load the time series visualizer software ID: " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("ApolloDatabaseException attempting to load the time series visualizer software ID: " + ex.getMessage());
		}
		if (visualizerSoftwareId == null) {
			throw new ExceptionInInitializerError("Could not find the time series visualizer software id in the list of registered services");
		}
		if (dataServiceSoftwareId == null) {
			throw new ExceptionInInitializerError("Could not find the data service software id in the list of registered services");
		}
	}

	static {
		loadTimeSeriesVisualizerSoftwareIdentification();
	}
}
