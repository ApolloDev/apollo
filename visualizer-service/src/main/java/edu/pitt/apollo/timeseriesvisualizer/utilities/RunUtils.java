package edu.pitt.apollo.timeseriesvisualizer.utilities;

import static edu.pitt.apollo.ApolloServiceConstants.APOLLO_DIR;
import edu.pitt.apollo.connector.BrokerServiceConnector;
import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.query_service_types.v4_0.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.restbrokerserviceconnector.RestBrokerServiceConnector;
import edu.pitt.apollo.restfilestoreserviceconnector.RestFilestoreServiceConnector;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.timeseriesvisualizer.exception.TimeSeriesVisualizerException;
import edu.pitt.apollo.types.v4_0.SimulatorCountOutputSpecification;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.FileStoreServiceUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunUtils {

	static Logger logger = LoggerFactory.getLogger(RunUtils.class);
	private static final String SIMULATOR_SERVICE_PROPERTIES = "visualizer_service.properties";
	private static final String FILESTORE_SERVICE_URL_PROPERTY = "filestore_service_url";
	private static final String BROKER_SERVICE_URL_PROPERTY = "broker_service_url";
	private static final String LOCAL_FILE_STORAGE_DIRECTORY_PROPERTY = "local_file_storage_dir";
	private static final String LOCAL_FILE_BASE_URL_PROPERTY = "local_file_base_url";
	private static final String AUTHENTICATION_USER_PROPERTY = "authentication_user";
	private static final String AUTHENTICATION_PASSWORD_PROPERTY = "authentication_password";
	private static final String IMAGE_FILE_TYPE_PROPERTY = "image_file_type";

	protected static final String LOCAL_FILE_BASE_URL;
	protected static final String LOCAL_FILE_STORAGE_DIR;
	private static final String IMAGE_FILE_TYPE;
	private static String filestoreServiceUrl;
	private static String brokerServiceUrl;
	private static FilestoreServiceConnector filestoreServiceConnector;
	private static BrokerServiceConnector brokerServiceConnector;
	private static Authentication authentication;

	static {
		FileInputStream fis;
		try {
			fis = new FileInputStream(APOLLO_DIR + SIMULATOR_SERVICE_PROPERTIES);
		} catch (FileNotFoundException e) {
			throw new ExceptionInInitializerError("Error initializing Visualizer Service.  Can not find file \""
					+ SIMULATOR_SERVICE_PROPERTIES + " \" in directory \"" + APOLLO_DIR
					+ "\". Error message is " + e.getMessage());
		}

		Properties properties = new Properties();
		try {
			properties.load(fis);
		} catch (IOException e) {
			throw new ExceptionInInitializerError("Error initializing Visualizer Service.  Unable to read file \""
					+ SIMULATOR_SERVICE_PROPERTIES + " \" in directory \"" + APOLLO_DIR
					+ "\". Error message is " + e.getMessage());
		}

		LOCAL_FILE_STORAGE_DIR = properties.getProperty(LOCAL_FILE_STORAGE_DIRECTORY_PROPERTY);
		LOCAL_FILE_BASE_URL = properties.getProperty(LOCAL_FILE_BASE_URL_PROPERTY);
		filestoreServiceUrl = properties.getProperty(FILESTORE_SERVICE_URL_PROPERTY);
		brokerServiceUrl = properties.getProperty(BROKER_SERVICE_URL_PROPERTY);
		filestoreServiceConnector = new RestFilestoreServiceConnector(filestoreServiceUrl);
		brokerServiceConnector = new RestBrokerServiceConnector(brokerServiceUrl);

		authentication = new Authentication();
		authentication.setRequesterId(properties.getProperty(AUTHENTICATION_USER_PROPERTY));
		authentication.setRequesterPassword(properties.getProperty(AUTHENTICATION_PASSWORD_PROPERTY));
		IMAGE_FILE_TYPE = properties.getProperty(IMAGE_FILE_TYPE_PROPERTY);
	}

	public static BrokerServiceConnector getRunManagerServiceConnector() throws FilestoreException {
		return brokerServiceConnector;
	}

	public static String getImageFileType() {
		return IMAGE_FILE_TYPE;
	}

	public static String getLocalFileBaseUrl() {
		return LOCAL_FILE_BASE_URL;
	}

	public static void uploadFile(String url, BigInteger runId, FileIdentification fileIdentification) throws FilestoreException {

		FileStoreServiceUtility.uploadFile(runId, url, fileIdentification,
				authentication, filestoreServiceConnector);
	}

	public static String getContent(String url) throws IOException {
		InputStream in = new URL(url).openStream();

		try {
			return IOUtils.toString(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static void downloadContentToFile(String url, String filePath) throws IOException {
		InputStream in = new URL(url).openStream();

		try {
			File targetFile = new File(filePath);
			OutputStream outStream = new FileOutputStream(targetFile);

			byte[] buffer = new byte[8 * 1024];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static String getUrlOfFile(BigInteger runId, String filename, ContentDataFormatEnum fileFormat,
			ContentDataTypeEnum fileType) throws FilestoreException {
		return filestoreServiceConnector.getUrlOfFile(runId, filename,
				fileFormat, fileType, authentication);
	}

	public static void updateStatus(BigInteger runId, MethodCallStatusEnum statusEnum, String message) throws RunManagementException {
		brokerServiceConnector.updateStatusOfRun(runId, statusEnum, message, authentication);
	}

	public static String getMd5HashFromString(String string) {

		return DigestUtils.md5Hex(string);
	}

	public static SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId) throws RunManagementException {

		return brokerServiceConnector.getSoftwareIdentificationForRun(runId, authentication);
	}

	public static String createRunDirectory(BigInteger runId) {
		String directory = LOCAL_FILE_STORAGE_DIR + File.separator + runId;
		File file = new File(directory);
		file.mkdir();
		return directory;
	}

	public static void runQueryService(RunSimulatorOutputQueryMessage message, BigInteger visualizerRunId) throws RunManagementException, TimeSeriesVisualizerException {

		message.setAuthentication(authentication);
		BigInteger runId = brokerServiceConnector.insertRun(message).getRunId();

		// wait until complete
		MethodCallStatus status = brokerServiceConnector.getRunStatus(runId, authentication);
		while (!status.getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
			status = brokerServiceConnector.getRunStatus(runId, authentication);

			if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
				throw new TimeSeriesVisualizerException("FAILED status running query service: " + status.getMessage());
			}

			System.out.println("Status: " + status.getStatus() + " - " + status.getMessage());
			try {
				Thread.sleep(25000);
			} catch (InterruptedException ex) {
			}
		}

		// now run is complete
		// get the output files
		try {
			for (SimulatorCountOutputSpecification specification : message.getSimulatorCountOutputSpecifications()) {
				String label = specification.getSimulatorCountOutputSpecificationId() + ".csv";
				String url = brokerServiceConnector.getUrlOfFile(runId, label,
						ContentDataFormatEnum.TEXT, ContentDataTypeEnum.QUERY_RESULT, authentication);
				String filePath = LOCAL_FILE_STORAGE_DIR + File.separator + visualizerRunId + File.separator + specification.getSimulatorCountOutputSpecificationId() + ".csv";
				downloadContentToFile(url, filePath);
				
				// upload the content file to the visualizer service run so it is available
				FileIdentification fileIdentification = new FileIdentification();
				fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
				fileIdentification.setLabel(label);
				fileIdentification.setType(ContentDataTypeEnum.QUERY_RESULT);
				uploadFile(url, visualizerRunId, fileIdentification);
				
			}
		} catch (FilestoreException | IOException ex) {
			throw new TimeSeriesVisualizerException("Exception getting output files: " + ex.getMessage());
		}
	}

	public static void uploadFiles(Map<String, String> resourcesMap, BigInteger visualizerRunId)
			throws TimeSeriesVisualizerException {

		try {
			for (String imageName : resourcesMap.keySet()) {

				String url = resourcesMap.get(imageName);

				FileIdentification fileIdentification = new FileIdentification();
				fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
				fileIdentification.setLabel(imageName);
				fileIdentification.setType(ContentDataTypeEnum.IMAGE);
				uploadFile(url, visualizerRunId, fileIdentification);
			}
		} catch (FilestoreException ex) {
			throw new TimeSeriesVisualizerException("Filestore exception uploading image: " + ex.getMessage());
		}
	}

	public static String getLocalFilePath(String fileName, BigInteger visualizerRunId) {
		return LOCAL_FILE_STORAGE_DIR + File.separator + visualizerRunId + File.separator + fileName;
	}
}
