package edu.pitt.apollo.dataservice.accessors;

import edu.pitt.apollo.*;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.dataservice.methods.RunJobMethod;
import edu.pitt.apollo.dataservice.methods.RunJobMethodFactory;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseUserPasswordException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.exception.UserNotAuthenticatedException;
import edu.pitt.apollo.exception.UserNotAuthorizedException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.UserManagementInterface;
import edu.pitt.apollo.services_common.v3_0_0.*;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time: 2:26:02 PM Class: DatabaseAccessor IDE: NetBeans 6.9.1
 */
public class DatabaseAccessor implements SoftwareRegistryInterface, RunManagementInterface, UserManagementInterface, ContentManagementInterface, JobRunningServiceInterface {

	protected static final String OUTPUT_DIRECTORY;
	protected static final String OUTPUT_FILE_NAME;
	protected static final String ZIP_FILE_NAME;
	protected static final int DATA_SERVICE_SOFTWARE_KEY;
	protected static final SoftwareIdentification dataServiceSoftwareId;
	private static final String DATA_SERVICE_PROPERTIES_NAME = "data_service.properties";
	private static final String OUTPUT_DIRECTORY_KEY = "output_directory";
	private static final String OUTPUT_FILE_NAME_KEY = "output_file_name";
	private static final String APOLLO_DIR;
	private static final String ZIP_FILE_NAME_KEY = "zip_file_name";
	private static final String FILE_PREFIX = "run_%d_";
	static Logger logger = LoggerFactory.getLogger(DatabaseAccessor.class);

	static {
		dataServiceSoftwareId = new SoftwareIdentification();
		dataServiceSoftwareId.setSoftwareDeveloper("UPitt");
		dataServiceSoftwareId.setSoftwareName("Data Service");
		dataServiceSoftwareId.setSoftwareVersion("1.0");
		dataServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.DATA);
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
				fis = new FileInputStream(APOLLO_DIR + DATA_SERVICE_PROPERTIES_NAME);
			} catch (FileNotFoundException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Can not find file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			Properties properties = new Properties();
			try {
				properties.load(fis);
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to read file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			try {
				fis.close();
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Error initializing Data Service.  Unable to close file \""
						+ DATA_SERVICE_PROPERTIES_NAME + " \" in directory \"" + APOLLO_DIR
						+ "\". Error message is " + e.getMessage());
			}

			String outputDir = properties.getProperty(OUTPUT_DIRECTORY_KEY);
			if (!outputDir.endsWith(File.separator)) {
				outputDir = outputDir + File.separator;
			}
			OUTPUT_DIRECTORY = outputDir;
			OUTPUT_FILE_NAME = properties.getProperty(OUTPUT_FILE_NAME_KEY);

			ZIP_FILE_NAME = properties.getProperty(ZIP_FILE_NAME_KEY);

			try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
				//dbUtils = new ApolloDbUtils(new File(APOLLO_DIR + DATABASE_PROPERTIES_FILENAME));

				try {
					DATA_SERVICE_SOFTWARE_KEY = dbUtils.getSoftwareIdentificationKey(dataServiceSoftwareId);
				} catch (ApolloDatabaseException ex) {
					logger.error(ex.getMessage());
					throw new ExceptionInInitializerError("ApolloDatabaseException getting the key for the data service software ID");
				}
			} catch (ApolloDatabaseException ex) {
				throw new ExceptionInInitializerError("ApolloDatabaseException creating ApolloDbUtils: " + ex.getMessage());
			}
//			} catch (IOException ex) {
//				throw new ExceptionInInitializerError("Error creating ApolloDbUtils when initializing the data service: "
//						+ ex.getMessage());
//			}
		} else {
			throw new ExceptionInInitializerError("No Apollo Work Dir evironment variable found when initializing data service!");
		}
	}

	protected final ApolloDbUtils dbUtils;
	protected final Authentication authentication;
	protected JsonUtils jsonUtils = new JsonUtils();
	protected Md5Utils md5Utils = new Md5Utils();

	public DatabaseAccessor(Authentication authentication) throws ApolloDatabaseException {
		this.authentication = authentication;
		this.dbUtils = new ApolloDbUtils();
	}

	private Authentication cloneAndStripAuthentication(Authentication srcAuthentication) {
		Authentication authentication = new Authentication();
		authentication.setRequesterId(srcAuthentication.getRequesterId());
		authentication.setRequesterPassword(srcAuthentication.getRequesterPassword());
		srcAuthentication.setRequesterId("");
		srcAuthentication.setRequesterPassword("");
		return authentication;
	}

	protected Authentication stripAuthentication(Object message) throws RunManagementException {
		Authentication authentication;
		if (message instanceof RunSimulationMessage) {
			authentication = ((RunSimulationMessage) message).getAuthentication();
		} else if (message instanceof RunSimulationsMessage) {
			authentication = ((RunSimulationsMessage) message).getAuthentication();
		} else if (message instanceof RunVisualizationMessage) {
			authentication = ((RunVisualizationMessage) message).getAuthentication();
		} else {
			throw new RunManagementException("Unsupported message type of " + message.getClass().getName() + " passed to the DatabaseAccessor");
		}
		return cloneAndStripAuthentication(authentication);
	}

	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
			BigInteger runId) throws ApolloDatabaseException {
		return null;
	}

	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, String fileNameToMatch, Authentication authentication) throws DataServiceException {
		Map<BigInteger, FileAndURLDescription> contentMap = getListOfFilesForRunId(runId, authentication);

		for (Iterator<BigInteger> itr = contentMap.keySet().iterator(); itr.hasNext();) {
			BigInteger contentId = itr.next();
			FileAndURLDescription desc = contentMap.get(contentId);
			if (!desc.getName().equals(fileNameToMatch)) {
				itr.remove();
			}
		}

		return contentMap;
	}

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			List<BigInteger> listOfRunIds = dbUtils.getRunIdsForBatch(runId);

			return listOfRunIds;
		} catch (UserNotAuthenticatedException | ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}

	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel,
			ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {
		authenticateUser(authentication);
		try {
			int contentId = dbUtils.addTextDataContent(content);
			int sourceSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(sourceSoftware);
			int destinationSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(destinationSoftware);
			int runDataDescriptionId = dbUtils.getRunDataDescriptionId(contentDataFormat, contentLabel, contentDataType, sourceSoftwareIdKey, destinationSoftwareIdKey);
			dbUtils.associateContentWithRunId(runId, contentId, runDataDescriptionId);
		} catch (ApolloDatabaseException | Md5UtilsException e) {
			throw new DataServiceException(e.getMessage());
		}

	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			return dbUtils.getSoftwareIdentificationForRun(runId);
		} catch (UserNotAuthenticatedException | ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public BigInteger insertRun(RunMessage message) throws RunManagementException {
		throw new RunManagementException("insertRun() is not supported in the base DatabaseAccessor.");
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			dbUtils.updateStatusOfRun(runId, statusEnumToSet, messageToSet);
		} catch (UserNotAuthenticatedException | ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			dbUtils.updateLastServiceToBeCalledForRun(runId, softwareIdentification);
		} catch (UserNotAuthenticatedException | ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			SoftwareIdentification si = dbUtils.getLastServiceToBeCalledForRun(runId);
			return si;
		} catch (UserNotAuthenticatedException | ApolloDatabaseException ade) {
			throw new RunManagementException(ade.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			BigInteger simulationGroupId = dbUtils.getSimulationGroupIdForRun(runId);
			dbUtils.addRunIdsToSimulationGroup(simulationGroupId, runIds);
		} catch (UserNotAuthenticatedException | Md5UtilsException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			dbUtils.removeRunData(runId);
		} catch (UserNotAuthenticatedException | ApolloDatabaseException ade) {
			throw new RunManagementException(ade.getMessage());
		}

	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			authenticateUser(authentication);
			return dbUtils.getStatusOfLastServiceToBeCalledForRun(runId);
		} catch (UserNotAuthenticatedException | ApolloDatabaseException e) {
			throw new RunManagementException(e.getMessage());
		}
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		try {
			authenticateUser(authentication);
			Map<BigInteger, FileAndURLDescription> fileIdsToFileDescriptionMap = dbUtils.getListOfFilesForRunId(runId);
			return fileIdsToFileDescriptionMap;
		} catch (ApolloDatabaseException e) {
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		try {
			authenticateUser(authentication);
			HashMap<BigInteger, FileAndURLDescription> urlIdsToUrlDescriptionMap = dbUtils.getListOfURLsForRunId(runId);
			return urlIdsToUrlDescriptionMap;
		} catch (ApolloDatabaseException e) {
			throw new DataServiceException(e.getMessage());
		}
	}

	@Override
	public String getContentForContentId(BigInteger fileId, Authentication authentication) throws DataServiceException {
		try {
			authenticateUser(authentication);
			String fileContent = dbUtils.getFileContentForFileId(fileId);
			return fileContent;
		} catch (ApolloDatabaseException e) {
			throw new DataServiceException(e.getMessage());
		}

	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {
		// DON'T HAVE A NEED FOR THIS METHOD YET, BUT THE METHOD IS DECLARED FOR FUTURE USE
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
		try {
			authenticateUser(authentication);
			String wsdlURL = dbUtils.getUrlForSoftwareIdentification(softwareId);
			return wsdlURL;
		} catch (ApolloDatabaseException e) {
			throw new DataServiceException(e.getMessage());
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
	public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
		try {
			authenticateUser(authentication);
			return dbUtils.getRegisteredSoftware();
		} catch (ApolloDatabaseException ade) {
			throw new DataServiceException(ade.getMessage());
		}
	}

	@Override
	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest,
			String roleDescription, Authentication authentication) throws DataServiceException {
		authenticateUser(authentication);
		try {
			dbUtils.addRole(softwareIdentification, canRunSoftware, allowPrivilegedRequest, roleDescription);
		} catch (ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void deleteUser(String username, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addUserRole(String username, SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DataServiceException {
		authenticateUser(authentication);
		try {
			dbUtils.addUserRole(username, username, softwareIdentification, canRunSoftware, canRequestPrivileged);
		} catch (ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DataServiceException {
		try {
			dbUtils.addUser(userId, userPassword, userEmail);
		} catch (ApolloDatabaseUserPasswordException ex) {
			throw new DataServiceException(ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void authenticateUser(Authentication authentication) throws RunManagementException, UserNotAuthenticatedException {
		try {
			boolean userAuthenticated = dbUtils.authenticateUser(authentication);
			if (!userAuthenticated) {
				throw new UserNotAuthenticatedException("The user could not be authenticated (incorrect username or password)");
			}
		} catch (ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware) throws DataServiceException {

		try {
			boolean userAuthorized = dbUtils.authorizeUser(authentication, softwareIdentification, requestToRunSoftware);
			if (!userAuthorized) {
				throw new UserNotAuthorizedException("The specified user is not authorized for the request");
			}
		} catch (ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}

	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
