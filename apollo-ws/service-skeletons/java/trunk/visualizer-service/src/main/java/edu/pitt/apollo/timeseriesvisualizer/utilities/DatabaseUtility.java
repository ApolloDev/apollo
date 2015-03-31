package edu.pitt.apollo.timeseriesvisualizer.utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 12, 2013 Time:
 * 4:33:35 PM Class: DatabaseUtility IDE: NetBeans 6.9.1
 */
public class DatabaseUtility {

	static Logger logger = LoggerFactory.getLogger(DatabaseUtility.class);
	private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
	// private static final String[] seirTimeSeriesNames = {"susceptible",
	// "exposed", "infectious", "recovered"};
	private Connection connect = null;
	private ResultSet resultSet = null;
	private PreparedStatement statement = null;
	private SoftwareIdentification visuazlierSoftwareId;
	static final Properties properties = new Properties();
	private static String APOLLO_DIR;
	protected static ApolloDbUtils dbUtils;

	static {
		InputStream input;
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
		} else {
			logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
			APOLLO_DIR = "";
		}

		String fn = getDatabasePropertiesFilename();
		try {
			input = new FileInputStream(fn);
			properties.load(input);
			logger.info("Successfully loaded " + fn + " file.");
		} catch (Exception e) {
			logger.error("\n\n\nError loading " + fn + " file\n\n\n");
		}
		try {
			dbUtils = new ApolloDbUtils(new File(getDatabasePropertiesFilename()));
		} catch (IOException ex) {
			logger.error("Error creating ApolloDbUtils when initializing the Visualizer database utility: "
					+ ex.getMessage());
		}
	}

	public static ApolloDbUtils getDbUtils() {
		return dbUtils;
	}

	public static String getDatabasePropertiesFilename() {
		return APOLLO_DIR + DATABASE_PROPERTIES_FILENAME;
	}

	public DatabaseUtility(SoftwareIdentification visualizerSoftwareId) throws TimeSeriesVisualizerException {
		this.visuazlierSoftwareId = visualizerSoftwareId;

	}

	public String getSimulatorSoftwareNameForRun(BigInteger runId) throws TimeSeriesVisualizerException {
		try {
			SoftwareIdentification id = dbUtils.getSoftwareIdentificationForRun(runId);
			return id.getSoftwareName();
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw new TimeSeriesVisualizerException(
					"ApolloDatabaseKeyNotFoundException attempting to get software identification for run "
					+ runId + ": " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException(
					"ApolloDatabaseException attempting to get software identification for run "
					+ runId + ": " + ex.getMessage());
		}
	}

	// private DatabaseTimeSeriesProcessor getDiseaseStateMapFromResultSet()
	// throws SQLException {
	//
	// DatabaseTimeSeriesProcessor container = new
	// DatabaseTimeSeriesProcessor();
	// // disease state -> location code -> time step -> pop count
	// Map<String, Map<String, Map<Integer, Double>>> map = new HashMap<String,
	// Map<String, Map<Integer, Double>>>();
	//
	// int maxLength = Integer.MIN_VALUE;
	// while (resultSet.next()) {
	// String diseaseState = resultSet.getString("disease_state");
	// String location = resultSet.getString("apollo_location_code");
	// int timeStep = resultSet.getInt("time_step");
	// double populationCount = resultSet.getInt("pop_count");
	//
	// if (timeStep > maxLength) {
	// maxLength = timeStep;
	// }
	//
	// Map<String, Map<Integer, Double>> locationMap;
	// if (map.containsKey(diseaseState)) {
	// locationMap = map.get(diseaseState);
	// } else {
	// // use a tree map so that the locations are ordered
	// locationMap = new TreeMap<String, Map<Integer, Double>>();
	// }
	//
	// Map<Integer, Double> timeStepMap;
	// if (locationMap.containsKey(location)) {
	// timeStepMap = locationMap.get(location);
	// } else {
	// // use tree map so that the time steps are ordered
	// timeStepMap = new TreeMap<Integer, Double>();
	// }
	//
	// // store the population count for this time step
	// timeStepMap.put(timeStep, populationCount);
	// }
	//
	// container.setMap(map);
	// container.setLength(maxLength);
	// return container;
	// }
	public TimeSeriesContainerList retrieveTimeSeriesFromDatabaseTimeSeriesTable(List<BigInteger> runIds,
			List<TimeSeriesCurveTypeEnum> infectionStatesToUse) throws TimeSeriesVisualizerException {

		TimeSeriesContainerList container = new TimeSeriesContainerList();

		String dbClass = properties.getProperty("class");
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		StringBuilder timeSeriesQuery = new StringBuilder();
		InputStream queryInput = DatabaseUtility.class.getResourceAsStream("/time_series_query.sql");

		Scanner scanner = new Scanner(queryInput);

		while (scanner.hasNextLine()) {
			timeSeriesQuery.append(" ").append(scanner.nextLine());
		}
		timeSeriesQuery.deleteCharAt(0); // remove first space
		scanner.close();

		try {
			queryInput.close();
		} catch (IOException ex) {
			System.err.println("IO exception closing input stream");
		}

		String query = timeSeriesQuery.toString();

		for (BigInteger runId : runIds) {
			DatabaseTimeSeriesProcessor processor = new DatabaseTimeSeriesProcessor();
			TimeSeriesContainer timeSeriesContainer;
			try {
				Class.forName(dbClass);
				connect = DriverManager.getConnection(url, user, password);

				logger.info("Retrieving data for run from the database...");
				statement = connect.prepareStatement(query);
				statement.setString(1, runId.toString());
				statement.setString(2, runId.toString());
				resultSet = statement.executeQuery();

				processor.storeTimeSeriesFromResultSet(resultSet);

				//jdl: removing the second condition as this is no longer possible..
//				if (getDiseaseStatesData /* && !runId.toLowerCase().contains("flute")*/) { // can't
				// make
				// a
				// disease
				// states
				// chart
				// for
				// flute
				timeSeriesContainer = processor.getTimeSeriesForInfectionStates(infectionStatesToUse);
//				} else {
//					timeSeriesContainer = processor.getTimeSeriesForInfectionStates(false, getIncidenceData);
//				}

				timeSeriesContainer.setRunId(runId);

				container.add(timeSeriesContainer);

				resultSet.close();
			} catch (ClassNotFoundException ex) {
				throw new TimeSeriesVisualizerException(
						"ClassNotFoundException attempting to get data from database: " + ex.getMessage());
			} catch (SQLException ex) {
				throw new TimeSeriesVisualizerException("SQLException attempting to get data from database: "
						+ ex.getMessage());
			} finally {
				try {
					close();
				} catch (SQLException ex) {
					throw new TimeSeriesVisualizerException(
							"SQLException attempting to close database connection, statement, "
							+ "and resultset: " + ex.getMessage());
				}
			}

		}

		return container;

	}

	public TimeSeriesContainerList retrieveTimeSeriesFromDatabaseFiles(List<BigInteger> runIds,
			List<TimeSeriesCurveTypeEnum> infectionSatesToRetrieve) throws TimeSeriesVisualizerException {

		TimeSeriesContainerList container = new TimeSeriesContainerList();

		int visualizerKey;
		try {
			visualizerKey = dbUtils.getSoftwareIdentificationKey(visuazlierSoftwareId);
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException(ex.getMessage());
		}

		for (BigInteger runId : runIds) {
			try {
				int simulatorKey = dbUtils.getSoftwareIdentificationKeyForRun(runId);
				Map<String, ByteArrayOutputStream> map = dbUtils.getDataContentForSoftware(runId,
						simulatorKey, visualizerKey);

				DatabaseTimeSeriesProcessor processor = new DatabaseTimeSeriesProcessor();
				TimeSeriesContainer timeSeriesContainer;

				processor.storeTimeSeriesFromDatabaseFiles(map);

//				if (getDiseaseStatesData) { // can't make a disease states chart
				// for flute
				timeSeriesContainer = processor.getTimeSeriesForInfectionStates(infectionSatesToRetrieve);
//				} else {
//					timeSeriesContainer = processor.getTimeSeriesForInfectionStates(false, getIncidenceData);
//				}

				timeSeriesContainer.setRunId(runId);

				container.add(timeSeriesContainer);

			} catch (ApolloDatabaseKeyNotFoundException ex) {
				throw new TimeSeriesVisualizerException("ApolloDatabaseKeyNotFoundException for run " + runId
						+ " when attempting to retrieve the time series from database files: "
						+ ex.getMessage());

			} catch (NumberFormatException ex) {
				throw new TimeSeriesVisualizerException("NumberFormatException for run " + runId
						+ " when attempting to retrieve the time series from database files "
						+ ex.getMessage());

			} catch (ApolloDatabaseException ex) {
				throw new TimeSeriesVisualizerException("ApolloDatabaseException for run " + runId
						+ " when attempting to retrieve the time series from database files "
						+ ex.getMessage());
			}
		}

		return container;
	}

	public String getSoftwareNameForRunId(BigInteger runId) throws TimeSeriesVisualizerException {

		try {

			SoftwareIdentification softwareId = dbUtils.getSoftwareIdentificationForRun(runId);
			return softwareId.getSoftwareName();
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw new TimeSeriesVisualizerException("ApolloDatabaseKeyNotFoundException for run " + runId
					+ " attempting to get software identification from database: " + ex.getMessage());
		} catch (NumberFormatException ex) {
			throw new TimeSeriesVisualizerException("NumberFormatException for run " + runId
					+ " attempting to get software identification from database: " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException("ApolloDatabaseException for run " + runId
					+ " attempting to get software identification from database: " + ex.getMessage());
		}
	}

	private void addTextDataContentForUrl(String url, String imageName, BigInteger visualizerRunId)
			throws TimeSeriesVisualizerException {

		int dataContentKey;
		try {
			dataContentKey = dbUtils.addTextDataContent(url);
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to add text data "
					+ "content for image " + imageName + " for run " + visualizerRunId + ": "
					+ ex.getMessage());
		}

		int runDataDescriptionId;
		try {
			runDataDescriptionId = dbUtils.getRunDataDescriptionId(DbContentDataFormatEnum.URL, imageName,
					DbContentDataType.IMAGE, dbUtils.getSoftwareIdentificationKey(visuazlierSoftwareId), 0);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw new TimeSeriesVisualizerException(
					"ApolloDatabaseKeyNotFoundException attempting to get run data "
					+ "description ID for image " + imageName + " for run " + visualizerRunId + ": "
					+ ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to get run data "
					+ "description ID for image " + imageName + " for run " + visualizerRunId + ": "
					+ ex.getMessage());
		}

		try {
			dbUtils.associateContentWithRunId(visualizerRunId, dataContentKey, runDataDescriptionId);
		} catch (ApolloDatabaseKeyNotFoundException ex) {
			throw new TimeSeriesVisualizerException(
					"ApolloDatabaseKeyNotFoundException attempting to associate "
					+ "content with run ID for image " + imageName + " for run " + visualizerRunId
					+ ": " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new TimeSeriesVisualizerException(
					"ApolloDatabaseException attempting to associate "
					+ "content with run ID for image " + imageName + " for run " + visualizerRunId
					+ ": " + ex.getMessage());
		}
	}

	public void insertURLsIntoDatabase(Map<String, String> resourcesMap, BigInteger visualizerRunId)
			throws TimeSeriesVisualizerException {

		for (String imageName : resourcesMap.keySet()) {

			String url = resourcesMap.get(imageName);
			addTextDataContentForUrl(url, imageName, visualizerRunId);
		}
	}

	private void close() throws SQLException {

		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (connect != null) {
				connect.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException ex) {
			throw ex;
		}

	}
	// public static void main(String[] args) {
	//
	// String runId = "UPitt,PSC,CMU_FRED_2.0.1_230616";
	//
	// DatabaseUtility dbConnect = new DatabaseUtility(runId);
	// try {
	// dbConnect.retrieveTimeSeriesFromDatabaseTimeSeriesTable(true, true);
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	//
	// }
}
