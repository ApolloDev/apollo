package edu.pitt.apollo.runmanagerservice.datastore.accessors;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.filestore_service_types.v4_0_2.FileIdentification;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.restfilestoreserviceconnector.RestFilestoreServiceConnector;
import edu.pitt.apollo.runmanagerservice.exception.RunMessageFileNotFoundException;
import edu.pitt.apollo.services_common.v4_0_2.*;
import edu.pitt.apollo.types.v4_0_2.SoftwareIdentification;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.apollo.utilities.FileStoreServiceUtility;
import edu.pitt.apollo.utilities.Md5Utils;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.zip.ZipUtil;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time: 2:26:02 PM Class: DatastoreAccessor IDE: NetBeans 6.9.1
 */
public class DatastoreAccessor implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface, JobRunningServiceInterface {

	private static final String BATCH_REMOTE_FILE_NAME = "run_messages.zip";
	private static final String BATCH_ZIP_FILE_NAME = "batch_run_messages_%d.zip";
	private static final String RUN_MANAGER_SERVICE_PROPERTIES_NAME = "run_manager_service.properties";
	private static final String FILESTORE_SERVICE_URL_PROPERTY = "filestore_service_url";
	private static final String LOCAL_FILE_STORAGE_DIRECTORY_PROPERTY = "local_file_storage_dir";
	private static final String LOCAL_FILE_BASE_URL_PROPERTY = "local_file_base_url";
	protected static final String LOCAL_FILE_BASE_URL;
	protected static final String LOCAL_FILE_STORAGE_DIR;
	private static final String APOLLO_DIR;
	private static FilestoreServiceConnector filestoreServiceConnector;
	private static String filestoreServiceUrl;
	static Logger logger = LoggerFactory.getLogger(DatastoreAccessor.class);

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		System.out.println("apolloDir: " + apolloDir);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			APOLLO_DIR = apolloDir;
			logger.info(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);

			FileInputStream fis;
			try {
				fis = new FileInputStream(APOLLO_DIR + RUN_MANAGER_SERVICE_PROPERTIES_NAME);
			} catch (FileNotFoundException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Can not find file \""
						+ RUN_MANAGER_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			Properties properties = new Properties();
			try {
				properties.load(fis);
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to read file \""
						+ RUN_MANAGER_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			try {
				fis.close();
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to close file \""
						+ RUN_MANAGER_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			LOCAL_FILE_STORAGE_DIR = properties.getProperty(LOCAL_FILE_STORAGE_DIRECTORY_PROPERTY);
			LOCAL_FILE_BASE_URL = properties.getProperty(LOCAL_FILE_BASE_URL_PROPERTY);
		} else {
			throw new ExceptionInInitializerError("No Apollo Work Dir evironment variable found when initializing data service!");
		}
	}

	protected static FilestoreServiceConnector getFilestoreServiceConnector() throws FilestoreException {
		try {
			if (filestoreServiceConnector == null) {
				filestoreServiceConnector = new RestFilestoreServiceConnector(getFilestoreServiceUrl());
			}

			return filestoreServiceConnector;
		} catch (IOException ex) {
			throw new FilestoreException("IOException loading run manager service connector: " + ex.getMessage());
		}
	}

	protected static String getFilestoreServiceUrl() throws IOException {
		if (filestoreServiceUrl == null) {
			String apolloDir = ApolloServiceConstants.APOLLO_DIR;

			File configurationFile = new File(apolloDir + File.separator + RUN_MANAGER_SERVICE_PROPERTIES_NAME);
			Properties brokerServiceProperties = new Properties();

			try (InputStream input = new FileInputStream(configurationFile)) {
				// load a properties file
				brokerServiceProperties.load(input);
				filestoreServiceUrl = brokerServiceProperties.getProperty(FILESTORE_SERVICE_URL_PROPERTY);
			}
		}
		return filestoreServiceUrl;
	}

	protected final ApolloDbUtils dbUtils;
//	protected final Authentication authentication;
	protected JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
	protected Md5Utils md5Utils = new Md5Utils();

	public DatastoreAccessor() throws DatastoreException {
		try {
//			this.authentication = authentication;
			this.dbUtils = new ApolloDbUtils();
		} catch (ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
			BigInteger runId) throws ApolloDatabaseException {
		return null;
	}

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			List<BigInteger> listOfRunIds = dbUtils.getRunIdsForBatch(runId);
			return listOfRunIds;
		} catch (ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}

	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			return dbUtils.getSoftwareIdentificationForRun(runId);
		} catch (ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public InsertRunResult insertRun(RunMessage message, Authentication authentication) throws RunManagementException {
		throw new RunManagementException("insertRun() is not supported in the base DatabaseAccessor.");
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		try {
			dbUtils.updateStatusOfRun(runId, statusEnumToSet, messageToSet);
		} catch (ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		try {
			dbUtils.updateLastServiceToBeCalledForRun(runId, softwareIdentification);
		} catch (ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			SoftwareIdentification si = dbUtils.getLastServiceToBeCalledForRun(runId);
			return si;
		} catch (ApolloDatabaseException ade) {
			throw new RunManagementException(ade.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		try {
			BigInteger simulationGroupId = dbUtils.getSimulationGroupIdForRun(runId);
			dbUtils.addRunIdsToSimulationGroup(simulationGroupId, runIds);
		} catch (Md5UtilsException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			dbUtils.removeRunData(runId);
		} catch (ApolloDatabaseException ade) {
			throw new RunManagementException(ade.getMessage());
		}

	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			return dbUtils.getStatusOfLastServiceToBeCalledForRun(runId);
		} catch (ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
		try {
			String wsdlURL = dbUtils.getUrlForSoftwareIdentification(softwareId);
			return wsdlURL;
		} catch (ApolloDatabaseException e) {
			throw new DatastoreException(e.getMessage());
		}
	}

//	@Override
//	public void runDataService(BigInteger runId, Authentication authentication) throws DataServiceException {
//
//		authenticateUser(authentication);
//
//		String messageContent;
//		try {
//			BigInteger dbMessageDescriptionId = dbUtils.getRunDataDescriptionIdFromFileLabel(RUN_DATA_SEVICE_MESSAGE_FILENAME);
//			BigInteger messageContentId = dbUtils.getContentIdFromRunIdAndDataDescriptionId(runId, dbMessageDescriptionId);
//			messageContent = dbUtils.getFileContentForFileId(messageContentId);
//		} catch (ApolloDatabaseException ade) {
//			throw new DataServiceException(ade.getMessage());
//		}
//
//		RunJobMethod dataServiceMethod = RunJobMethodFactory.getDataServiceMethod(messageContent, runId);
//		dataServiceMethod.runDataService();
//
//	}
	@Override
	public List<ServiceRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
		try {
			return new ArrayList(dbUtils.getRegisteredSoftware().values());
		} catch (ApolloDatabaseException ade) {
			throw new DatastoreException(ade.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId, Authentication authentication, String runMessageFilename) throws DatastoreException, FilestoreException {

		List<FileIdentification> fileIds = getFilestoreServiceConnector().listFilesForRun(runId, authentication);
		// this is inadequate!! need to filter by source and dest software id too!
		for (FileIdentification fileIdentification : fileIds) {
			String filename = fileIdentification.getLabel();
			if (filename.equals(runMessageFilename)) {
				String url = getFilestoreServiceConnector().getUrlOfFile(runId, filename,
						fileIdentification.getFormat(), fileIdentification.getType(), authentication);
				try {
					return getContent(url);
				} catch (IOException ex) {
					throw new FilestoreException("IOException: " + ex.getMessage());
				}
			}
		}
		throw new RunMessageFileNotFoundException("Couldn't find " + runMessageFilename + " in database for run " + runId);
	}

	public List<FileIdentification> getListOfFilesForRunId(BigInteger runId, String fileNameToMatch, Authentication authentication) throws DatastoreException, FilestoreException {
		List<FileIdentification> fileIds = getFilestoreServiceConnector().listFilesForRun(runId, authentication);

		for (Iterator<FileIdentification> itr = fileIds.iterator(); itr.hasNext();) {
			FileIdentification contentId = itr.next();
			if (!contentId.getLabel().equals(fileNameToMatch)) {
				itr.remove();
			}
		}

		return fileIds;
	}

	public String getFileContent(BigInteger runId, FileIdentification fileIdentification, Authentication authentication) throws FilestoreException {

		String url = getFilestoreServiceConnector().getUrlOfFile(runId, fileIdentification.getLabel(),
				fileIdentification.getFormat(), fileIdentification.getType(), authentication);

		try {
			return getContent(url);
		} catch (IOException ex) {
			throw new FilestoreException("IOException: " + ex.getMessage());
		}
	}

	public <T> T getRunMessageAssociatedWithRunIdAsTypeOrNull(BigInteger runId, Authentication authentication, String runMessageFilename, Class<T> clazz) throws DatastoreException, JsonUtilsException, FilestoreException {
		String json = getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, runMessageFilename);
		JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
		return (T) jsonUtils.getObjectFromJson(json, clazz);
	}

	private static String getContent(String url) throws IOException {
		InputStream in = new URL(url).openStream();

		try {
			return IOUtils.toString(in);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	public static void uploadTextFileContent(String content, BigInteger runId, FileIdentification fileIdentification, Authentication authentication) throws FilestoreException {

		FileStoreServiceUtility.uploadTextFileContentSynchronous(content, runId, fileIdentification,
				authentication, LOCAL_FILE_STORAGE_DIR, LOCAL_FILE_BASE_URL, getFilestoreServiceConnector());
	}

	public static void addRunSimulationMessageFileToBatchDirectory(BigInteger batchRunId, BigInteger messageRunId,
			RunMessage runMessage) throws Md5UtilsException, FileNotFoundException {

		String content = new Md5Utils().getMd5(runMessage);
		String directory = LOCAL_FILE_STORAGE_DIR + File.separator + batchRunId + File.separator;
		String filePath = directory + messageRunId + ".json";
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		PrintStream ps = new PrintStream(file);
		ps.print(content);
		ps.close();
	}

	public static void zipAndUploadBatchRunDir(BigInteger batchRunId) throws FilestoreException {

		String directory = LOCAL_FILE_STORAGE_DIR + File.separator + batchRunId + File.separator;
		String zipFile = LOCAL_FILE_STORAGE_DIR + File.separator + String.format(BATCH_ZIP_FILE_NAME, batchRunId);

		ZipUtil.pack(new File(directory), new File(zipFile));
		
		FileIdentification fileIdentification = new FileIdentification();
		fileIdentification.setFormat(ContentDataFormatEnum.ZIP);
		fileIdentification.setType(ContentDataTypeEnum.RUN_MESSAGE);
		fileIdentification.setLabel(BATCH_REMOTE_FILE_NAME);
		
		FileStoreServiceUtility.uploadFile(batchRunId, zipFile, fileIdentification, null, filestoreServiceConnector);
	}

}
