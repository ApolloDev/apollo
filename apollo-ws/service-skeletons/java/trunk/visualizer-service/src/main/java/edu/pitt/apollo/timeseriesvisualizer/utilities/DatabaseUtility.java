package edu.pitt.apollo.timeseriesvisualizer.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceV300;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceV300;

import edu.pitt.apollo.services_common.v3_0_0.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.GlobalConstants;

//import edu.pitt.apollo.db.ApolloDbUtils;
//import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataFormatEnum;
//import edu.pitt.apollo.db.ApolloDbUtils.DbContentDataType;
//import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
//import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;


import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesCurveTypeEnum;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainer;
import edu.pitt.apollo.timeseriesvisualizer.types.TimeSeriesContainerList;

import javax.xml.namespace.QName;

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
//	protected static ApolloDbUtils dbUtils;
	static DataServiceV300 serviceDataService = null;
	private static DataServiceEI dataServicePort = null;
	static ApolloServiceV300 serviceBroker = null;
	private static ApolloServiceEI apolloServicePort = null;
	public static final String WSDL_LOC_BROKER = "http://localhost:8080/broker-service-war-3.0.0-SNAPSHOT/services/apolloservice?wsdl";
	public static final String WSDL_LOC_DATASERVICE = "http://localhost:8080/data-service-war-3.0.0-SNAPSHOT/services/dataservice?wsdl";
	public static final QName SERVICE_DATA_SERVICE = new QName(
			"http://service.apollo.pitt.edu/dataservice/v3_0_0/",
			"DataService_v3.0.0");

	public static final QName APOLLO_SERVICE = new QName(
			"http://service.apollo.pitt.edu/apolloservice/v3_0_0/",
			"ApolloService_v3.0.0");
	private static Authentication authentication;
	static {

			try {
				serviceDataService = new DataServiceV300(new URL(WSDL_LOC_DATASERVICE), SERVICE_DATA_SERVICE);

				dataServicePort = serviceDataService.getDataServiceEndpoint();
			authentication.setRequesterId("apollolib");
			authentication.setRequesterPassword("4p0110lib");

				serviceBroker = new ApolloServiceV300(new URL(WSDL_LOC_BROKER), APOLLO_SERVICE);
				apolloServicePort = serviceBroker.getApolloServiceEndpoint();

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
//
//		InputStream input;
//		Map<String, String> env = System.getenv();
//		APOLLO_DIR = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
//		if (APOLLO_DIR != null) {
//			if (!APOLLO_DIR.endsWith(File.separator)) {
//				APOLLO_DIR += File.separator;
//			}
//			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
//		} else {
//			logger.error(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
//			APOLLO_DIR = "";
//		}
//
//		String fn = getDatabasePropertiesFilename();
//		try {
//			input = new FileInputStream(fn);
//			properties.load(input);
//			logger.info("Successfully loaded " + fn + " file.");
//		} catch (Exception e) {
//			logger.error("\n\n\nError loading " + fn + " file\n\n\n");
//		}
////		try {
////			dbUtils = new ApolloDbUtils(new File(getDatabasePropertiesFilename()));
////		} catch (IOException ex) {
////			logger.error("Error creating ApolloDbUtils when initializing the Visualizer database utility: "
////					+ ex.getMessage());
////		}
//		try {
//			dbUtils = new ApolloDbUtils();
//		} catch (ApolloDatabaseException ex) {
//			throw new ExceptionInInitializerError("Could not create ApolloDbUtils: " + ex.getMessage());
//		}
	}

//	public static ApolloDbUtils getDbUtils() {
//		return dbUtils;
//	}

//	public static String getDatabasePropertiesFilename() {
//		return APOLLO_DIR + DATABASE_PROPERTIES_FILENAME;
//	}

	public DatabaseUtility(SoftwareIdentification visualizerSoftwareId) throws TimeSeriesVisualizerException {
		this.visuazlierSoftwareId = visualizerSoftwareId;

	}

	public String getSimulatorSoftwareNameForRun(BigInteger runId) throws TimeSeriesVisualizerException {
		GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
		message.setRunId(runId);

		GetSoftwareIdentificationForRunResult result = dataServicePort.getSoftwareIdentificationForRun(message);

		return result.getSoftwareIdentification().getSoftwareName();
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

	/*public TimeSeriesContainerList retrieveTimeSeriesFromDatabaseFiles(List<BigInteger> runIds,
			List<TimeSeriesCurveTypeEnum> infectionSatesToRetrieve) throws TimeSeriesVisualizerException {

		TimeSeriesContainerList container = new TimeSeriesContainerList();


		GetSoftwareIdentificationKeyMessage message = new GetSoftwareIdentificationKeyMessage();
		message.setSoftwareIdentification(visuazlierSoftwareId);

		GetSoftwareIdentificationKeyResult visualizerResult = dataServicePort.getSoftwareIdentificationKey(message);
		int visualizerKey;
		if(visualizerResult.getMethodCallStatus().getStatus()!= MethodCallStatusEnum.FAILED)
		{
			visualizerKey = visualizerResult.getSoftwareIdentificationKey().intValue();
		}
		else
		{
			throw new TimeSeriesVisualizerException("Failed to obtain visualizer software identfication key.");
		}
//		try {
//			visualizerKey = dbUtils.getSoftwareIdentificationKey(visuazlierSoftwareId);
//		} catch (ApolloDatabaseException ex) {
//			throw new TimeSeriesVisualizerException(ex.getMessage());
//		}

		for (BigInteger runId : runIds) {
//			try {
			GetSoftwareIdentificationForRunMessage softwareIdentificationForRunMessage = new GetSoftwareIdentificationForRunMessage();
//				GetSoftwareIdentificationKeyForRunMessage simulatorMessage = new GetSoftwareIdentificationKeyForRunMessage();
//				simulatorMessage.setRunId(runId);
			softwareIdentificationForRunMessage.setRunId(runId);

			GetSoftwareIdentificationForRunResult simulatorResult = dataServicePort.getSoftwareIdentificationForRun(softwareIdentificationForRunMessage);
				SoftwareIdentification simulatorIndentification;
				if(simulatorResult.getMethodCallStatus().getStatus()!= MethodCallStatusEnum.FAILED)
				{
					simulatorIndentification = simulatorResult.getSoftwareIdentification();
				}
				else
				{
					throw new TimeSeriesVisualizerException("Failed to obtain visualizer software identfication for simulator run id " + runId +".");
				}
				//int simulatorKey = dbUtils.getSoftwareIdentificationKeyForRun(runId);

			*//*--CHANGE THIS TO TAKE THE SIMULATOR IDENTIFICATION--*//*
				Map<String, ByteArrayOutputStream> map =  getDataContentForSoftware(runId,
						simulatorIndentification, visuazlierSoftwareId);

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

//			} catch (ApolloDatabaseKeyNotFoundException ex) {
//				throw new TimeSeriesVisualizerException("ApolloDatabaseKeyNotFoundException for run " + runId
//						+ " when attempting to retrieve the time series from database files: "
//						+ ex.getMessage());
//
//			} catch (NumberFormatException ex) {
//				throw new TimeSeriesVisualizerException("NumberFormatException for run " + runId
//						+ " when attempting to retrieve the time series from database files "
//						+ ex.getMessage());
//
//			} catch (ApolloDatabaseException ex) {
//				throw new TimeSeriesVisualizerException("ApolloDatabaseException for run " + runId
//						+ " when attempting to retrieve the time series from database files "
//						+ ex.getMessage());
//			}
		}

		return container;
	}*/

	private Map<String,ByteArrayOutputStream> getDataContentForSoftware(BigInteger runId, SoftwareIdentification simulator, SoftwareIdentification visualizer) throws TimeSeriesVisualizerException {
		HashMap<String, ByteArrayOutputStream> fileNametoByteArrayOutputStreamMap = new HashMap<String,ByteArrayOutputStream>();


		GetAllOutputFilesURLAsZipMessage message = new GetAllOutputFilesURLAsZipMessage();
		message.setRunId(runId);
		message.setAuthentication(authentication);
		message.setSoftwareIdentification(visualizer);


		GetAllOutputFilesURLAsZipResult messageResult = apolloServicePort.getAllOutputFilesURLAsZip(message);

		RunStatus ras = new RunStatus();
		ras.setRunId(messageResult.getRequestIdentification());

		MethodCallStatus status = new MethodCallStatus();
		status.setMessage("Initial status for zipped simulator files...");
		status.setStatus(MethodCallStatusEnum.WAITING);

		while(status.getStatus() != MethodCallStatusEnum.COMPLETED)
		{
			try{
				MethodCallStatus cStatus = apolloServicePort.getRunStatus(ras.getRunId());

				if(cStatus!=status)
				{
					status = cStatus;
				}
				try{
					Thread.sleep(10000);
				}catch(InterruptedException ie)
				{
					ie.printStackTrace();
				}
			}catch(Exception ex){
				throw new TimeSeriesVisualizerException("Failed to check status for zipped files using run ID " + runId +": " + ex.getMessage());
			}
		}

		String zipFolderForRunUrl = messageResult.getUrl();

		fileNametoByteArrayOutputStreamMap = getFileNameToByteArrayOutputMap(zipFolderForRunUrl);

		return fileNametoByteArrayOutputStreamMap;
	}
	private HashMap<String,ByteArrayOutputStream> getFileNameToByteArrayOutputMap(String urlForZippedData) throws TimeSeriesVisualizerException {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		ZipInputStream zipInputStream = null;
		HashMap<String, ByteArrayOutputStream> fileNameToByteArrayOutputStreamMap = new HashMap<String, ByteArrayOutputStream>();

		try{
			URL zipFileUrl = new URL(urlForZippedData);
			InputStream inputStream = zipFileUrl.openStream();
			zipInputStream = new ZipInputStream(inputStream);
			ZipEntry zipEntry = null;

			zipEntry = zipInputStream.getNextEntry();

			while (zipEntry != null) {
				String fileName = zipEntry.getName();
				String[] fileNameSplitOnSlash = fileName.split("/");
				String keyForNameToFileMap = fileNameSplitOnSlash[1];

				byte[] bytes = IOUtils.toByteArray(zipInputStream);
				ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
				baos.write(bytes,0,bytes.length);
				fileNameToByteArrayOutputStreamMap.put(keyForNameToFileMap, baos);
				zipEntry = zipInputStream.getNextEntry();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new TimeSeriesVisualizerException("Failed to connect to URL " + urlForZippedData +": " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new TimeSeriesVisualizerException("Failed to retrieve and read files from URL " + urlForZippedData+": " + e.getMessage());
		}

		return fileNameToByteArrayOutputStreamMap;
	}


		public String getSoftwareNameForRunId(BigInteger runId) throws TimeSeriesVisualizerException {

//		try {
			GetSoftwareIdentificationForRunMessage message = new GetSoftwareIdentificationForRunMessage();
			message.setRunId(runId);

			GetSoftwareIdentificationForRunResult result = dataServicePort.getSoftwareIdentificationForRun(message);
			if(result.getMethodCallStatus().getStatus()!=MethodCallStatusEnum.FAILED) {
				SoftwareIdentification softwareId = result.getSoftwareIdentification();
				return softwareId.getSoftwareName();
			}
			else
			{
				throw new TimeSeriesVisualizerException(result.getMethodCallStatus().getMessage());
			}
//		} catch (ApolloDatabaseKeyNotFoundException ex) {
//			throw new TimeSeriesVisualizerException("ApolloDatabaseKeyNotFoundException for run " + runId
//					+ " attempting to get software identification from database: " + ex.getMessage());
//		} catch (NumberFormatException ex) {
//			throw new TimeSeriesVisualizerException("NumberFormatException for run " + runId
//					+ " attempting to get software identification from database: " + ex.getMessage());
//		} catch (ApolloDatabaseException ex) {
//			throw new TimeSeriesVisualizerException("ApolloDatabaseException for run " + runId
//					+ " attempting to get software identification from database: " + ex.getMessage());
//		}
	}

	private void addTextDataContentForUrl(String url, String imageName, BigInteger visualizerRunId)
			throws TimeSeriesVisualizerException {

		int dataContentKey;
//		try {
//			dataContentKey = dbUtils.addTextDataContent(url);
			AddTextDataContentMessage addTextDataContentMessage = new AddTextDataContentMessage();
			addTextDataContentMessage.setUrl(url);
			AddTextDataContentResult addTextDataContentResult  = dataServicePort.addTextDataContent(addTextDataContentMessage);

			if(addTextDataContentResult.getMethodCallStatus().getStatus()!=MethodCallStatusEnum.FAILED)
			{
				dataContentKey = addTextDataContentResult.getDataContentKey().intValue();

				int runDataDescriptionId;
			}
			else
			{
				throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to add text data "
						+ "content for image " + imageName + " for run " + visualizerRunId + ": "
						+ addTextDataContentResult.getMethodCallStatus().getMessage());
			}


//		} catch (ApolloDatabaseException ex) {
//			throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to add text data "
//					+ "content for image " + imageName + " for run " + visualizerRunId + ": "
//					+ ex.getMessage());
//		}

		int runDataDescriptionId;
//		try {
			GetSoftwareIdentificationKeyMessage softwareIdMessage = new GetSoftwareIdentificationKeyMessage();
			GetSoftwareIdentificationKeyResult softwareIdResult = dataServicePort.getSoftwareIdentificationKeyFromSoftwareIdentification(softwareIdMessage);

			if(softwareIdResult.getMethodCallStatus().getStatus()!=MethodCallStatusEnum.FAILED)
			{

				GetRunDataDescriptionIdMessage runDataDescriptionIdMessage = new GetRunDataDescriptionIdMessage();
				runDataDescriptionIdMessage.setDataFormat(DbContentDataFormatEnum.URL);
				runDataDescriptionIdMessage.setDataLabel(imageName);
				runDataDescriptionIdMessage.setDataType(DbContentDataType.IMAGE);
				runDataDescriptionIdMessage.setDataSourceSoftwareIdKey(softwareIdResult.getSoftwareIdentificationKey());
				runDataDescriptionIdMessage.setDataDestinationSoftwareIdKey(BigInteger.valueOf(0));

				GetRunDataDescriptionIdResult getRunDataDescriptionIdResult = dataServicePort.getRunDataDescriptionId(runDataDescriptionIdMessage);

				if(getRunDataDescriptionIdResult.getMethodCallStatus().getStatus()==MethodCallStatusEnum.COMPLETED)
				{
					runDataDescriptionId = getRunDataDescriptionIdResult.getRunDescriptionId().intValue();

					AssociateContentWithRunIdMessage associateContentWithRunMessage = new AssociateContentWithRunIdMessage();
					associateContentWithRunMessage.setDataContentKey(BigInteger.valueOf(dataContentKey));
					associateContentWithRunMessage.setRunDataDescriptionId(BigInteger.valueOf(runDataDescriptionId));
					associateContentWithRunMessage.setRunId(visualizerRunId);

					AssociateContentWithRunIdResult associateContentWithRunIdResult = dataServicePort.associateContentWithRunId(associateContentWithRunMessage);
					//dbUtils.associateContentWithRunId(visualizerRunId, dataContentKey, runDataDescriptionId);

					if(associateContentWithRunIdResult.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
					{
						throw new TimeSeriesVisualizerException(
								"ApolloDatabaseKeyNotFoundException attempting to associate "
										+ "content with run ID for image " + imageName + " for run " + visualizerRunId
										+ ": " + associateContentWithRunIdResult.getMethodCallStatus().getMessage());
					}
				}
				else
				{
					throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to get run data "
							+ "description ID for image " + imageName + " for run " + visualizerRunId + ": "
							+ getRunDataDescriptionIdResult.getMethodCallStatus().getMessage());
				}
//				runDataDescriptionId = dbUtils.getRunDataDescriptionId(DbContentDataFormatEnum.URL, imageName,
//						DbContentDataType.IMAGE, dbUtils.getSoftwareIdentificationKey(visuazlierSoftwareId), 0);
			}
			else
			{
				throw new TimeSeriesVisualizerException(
						"ApolloDatabaseKeyNotFoundException attempting to get software identification for visualizer with  "
								+ "software ID of" + visuazlierSoftwareId + ":" + visualizerRunId +": "+ softwareIdResult.getMethodCallStatus().getMessage());
			}


//		} catch (ApolloDatabaseKeyNotFoundException ex) {
//			throw new TimeSeriesVisualizerException(
//					"ApolloDatabaseKeyNotFoundException attempting to get run data "
//					+ "description ID for image " + imageName + " for run " + visualizerRunId + ": "
//					+ ex.getMessage());
//		} catch (ApolloDatabaseException ex) {
//			throw new TimeSeriesVisualizerException("ApolloDatabaseException attempting to get run data "
//					+ "description ID for image " + imageName + " for run " + visualizerRunId + ": "
//					+ ex.getMessage());
//		}

//		try {
//			AssociateContentWithRunIdMessage associateContentWithRunIdMessage = new AssociateContentWithRunIdMessage();
//			associateContentWithRunIdMessage.setDataContentKey(BigInteger.valueOf(dataContentKey));
//			associateContentWithRunIdMessage.setRunDescritionId(BigInteger.valueOf(runDataDescriptionId));
//			associateContentWithRunIdMessage.setVisualizerRunId(visualizerRunId);
//
//			AssociateContentWithRunIdResult associateContentWithRunIdResult = port.associateContentWithRunId(associateContentWithRunIdMessage);
//			//dbUtils.associateContentWithRunId(visualizerRunId, dataContentKey, runDataDescriptionId);
//
//			if(associateContentWithRunIdResult.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
//			{
//				throw new TimeSeriesVisualizerException(
//						"ApolloDatabaseKeyNotFoundException attempting to associate "
//								+ "content with run ID for image " + imageName + " for run " + visualizerRunId
//								+ ": " + associateContentWithRunIdResult.getMethodCallStatus().getMessage());
//			}
//		} catch (ApolloDatabaseKeyNotFoundException ex) {
//			throw new TimeSeriesVisualizerException(
//					"ApolloDatabaseKeyNotFoundException attempting to associate "
//					+ "content with run ID for image " + imageName + " for run " + visualizerRunId
//					+ ": " + ex.getMessage());
//		} catch (ApolloDatabaseException ex) {
//			throw new TimeSeriesVisualizerException(
//					"ApolloDatabaseException attempting to associate "
//					+ "content with run ID for image " + imageName + " for run " + visualizerRunId
//					+ ": " + ex.getMessage());
//		}
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

	public static void updateStatusOfVisualizerRun(BigInteger visualizerRunId,MethodCallStatusEnum statusEnum, String message) throws TimeSeriesVisualizerException{
//		dbUtils.updateStatusOfRun(visualizerRunId, MethodCallStatusEnum.RUNNING,
//				"Started at " + sdf.format(new Date(System.currentTimeMillis())));

		UpdateStatusOfRunMessage updateStatusOfRunMessage = new UpdateStatusOfRunMessage();
		updateStatusOfRunMessage.setStatusEnum(statusEnum);
		updateStatusOfRunMessage.setStatusMessage(message);
		updateStatusOfRunMessage.setRunId(visualizerRunId);

		UpdateStatusOfRunResult result = dataServicePort.updateStatusOfRun(updateStatusOfRunMessage);

		if(result.getMethodCallStatus().getStatus()==MethodCallStatusEnum.FAILED)
		{
			throw new TimeSeriesVisualizerException(result.getMethodCallStatus().getMessage());
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
