package edu.pitt.apollo.dataservice.methods.database;

import edu.pitt.apollo.*;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import edu.pitt.apollo.dataservice.thread.DataServiceAllFilesThread;
import edu.pitt.apollo.dataservice.thread.DataServiceSpecifiedFilesThread;
import edu.pitt.apollo.dataservice.thread.DataServiceThread;
import edu.pitt.apollo.dataservice.types.FileInformation;
import edu.pitt.apollo.dataservice.types.FileInformationCollection;
import edu.pitt.apollo.dataservice.utils.RunUtils;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.DataServiceInterface;
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
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time:
 * 2:26:02 PM Class: DatabaseAccessor IDE: NetBeans 6.9.1
 */
public class DatabaseAccessor implements DataServiceInterface, RunManagementInterface, UserManagementInterface, ContentManagementInterface {

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
    private static final String RUN_DATA_SEVICE_MESSAGE_FILENAME = "run_data_service_message.json";
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

    protected final ApolloServiceQueue queue = new ApolloServiceQueue();
    protected final ApolloDbUtils dbUtils;
    protected final Authentication authentication;
    protected JsonUtils jsonUtils = new JsonUtils();
    protected Md5Utils md5Utils = new Md5Utils();


    private Authentication cloneAndStripAuthentication(Authentication srcAuthentication) {
        Authentication authentication = new Authentication();
        authentication.setRequesterId(srcAuthentication.getRequesterId());
        authentication.setRequesterPassword(srcAuthentication.getRequesterPassword());
        srcAuthentication.setRequesterId("");
        srcAuthentication.setRequesterPassword("");
        return authentication;
    }

    protected Authentication stripAuthentication(Object message) throws DataServiceException {
        Authentication authentication;
        if (message instanceof RunSimulationMessage) {
            authentication = ((RunSimulationMessage) message).getAuthentication();
        } else if (message instanceof RunSimulationsMessage) {
            authentication = ((RunSimulationsMessage) message).getAuthentication();
        } else if (message instanceof RunVisualizationMessage) {
            authentication = ((RunVisualizationMessage) message).getAuthentication();
        } else {
            throw new DataServiceException("Unsupported message type of " + message.getClass().getName() + " passed to the DatabaseAccessor");
        }
        return cloneAndStripAuthentication(authentication);
    }

    public DatabaseAccessor(Authentication authentication, ApolloDbUtils dbUtils) throws ApolloDatabaseException {
        this.authentication = authentication;
        this.dbUtils = dbUtils;
    }


    public void removeAllDataAssociatedWithRunId(BigInteger runId)
            throws ApolloDatabaseException {
        dbUtils.removeRunData(runId);
    }

    public boolean authenticateUser(Authentication authentication)
            throws ApolloDatabaseException {

        return dbUtils.authenticateUser(authentication);

    }

    public boolean authorizeUserForSoftwareCacheData(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws ApolloDatabaseException {
        boolean requestRunSimulator = false;
        return dbUtils.authorizeUser(authentication, softwareIdentification,
                requestRunSimulator);
    }

    public boolean authorizeUserForRunningSoftware(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws ApolloDatabaseException {
        boolean requestRunSimulator = true;
        return dbUtils.authorizeUser(authentication, softwareIdentification,
                requestRunSimulator);
    }

    protected final boolean isRunIdAssociatedWithMatchingRunMessage(
            String targetRunMessageAsJson,
            BigInteger runIdAssociatedWithRunMessageHash)
            throws ApolloDatabaseException {

        String runSimulationMessageAssociatedWithRunIdAsJson = getRunMessageAssociatedWithRunIdAsJsonOrNull(runIdAssociatedWithRunMessageHash);

        if (runSimulationMessageAssociatedWithRunIdAsJson == null) {
            throw new ApolloDatabaseException(
                    "There was no run_simulation_message.json content for run ID "
                            + runIdAssociatedWithRunMessageHash);
        }

        boolean result = targetRunMessageAsJson
                .equals(runSimulationMessageAssociatedWithRunIdAsJson);
        if (!result) {
            String targetHash = md5Utils.getMd5FromString(targetRunMessageAsJson);
            String existingHash = md5Utils.getMd5FromString(runSimulationMessageAssociatedWithRunIdAsJson);

            logger.warn("Warning!!! (" + targetHash + ") " + targetRunMessageAsJson +
                    " \n is not equal to \n " +
                    "(" + existingHash + ")" + runSimulationMessageAssociatedWithRunIdAsJson);

        }
        return result;

    }

    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws ApolloDatabaseException {
        return dbUtils.getRunIdsForBatch(runId);
    }

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds) throws ApolloDatabaseException, Md5UtilsException {

        dbUtils.addRunIdsToSimulationGroup(simulationGroupId,
                runIds);

    }

    protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws ApolloDatabaseException {
        return null;
    }

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws ApolloDatabaseException {

        return dbUtils.getSimulationGroupIdForRun(runId);


    }

    public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            BigInteger groupId = getSimulationGroupIdForRun(runId);
            List<BigInteger> listOfRunIds = getRunIdsAssociatedWithRun(groupId);
            return listOfRunIds;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }

    }

    @Override
    public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel,
                                          ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {


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

    public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            return dbUtils.getSoftwareIdentificationForRun(runId);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        }
    }

    @Override
    public BigInteger insertRun(Object message) throws DataServiceException {
        throw new DataServiceException("insertRun() is not supported in the base DatabaseAccessor.");
    }

    public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            dbUtils.updateStatusOfRun(runId, statusEnumToSet, messageToSet);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        }
    }

    public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            dbUtils.updateLastServiceToBeCalledForRun(runId, softwareIdentification);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        }
    }

    public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getLastServiceToBeCalledForRun(runId);
            return si;
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
            throw new DataServiceException(ade.getMessage());
        }
    }


    @Override
    public void addRunIdsToSimulationGroupForRun(BigInteger simulationGroupId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException {

    }

    public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            dbUtils.removeRunData(runId);
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
            throw new DataServiceException(ade.getMessage());
        }

    }

    public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            return dbUtils.getStatusOfLastServiceToBeCalledForRun(runId);
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            HashMap<BigInteger, FileAndURLDescription> fileIdsToFileDescriptionMap = dbUtils.getListOfFilesForRunId(runId);
            return fileIdsToFileDescriptionMap;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

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

    public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {

    }

    public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            String wsdlURL = dbUtils.getUrlForSoftwareIdentification(softwareId);
            return wsdlURL;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    @Override
    public void runDataService(BigInteger runId, Authentication authentication) throws DataServiceException {
        String messageContent;
        try {

            BigInteger dbMessageDescriptionId = dbUtils.getRunDataDescriptionIdFromFileLabel(RUN_DATA_SEVICE_MESSAGE_FILENAME);
            BigInteger messageContentId = dbUtils.getContentIdFromRunIdAndDataDescriptionId(runId, dbMessageDescriptionId);
            messageContent = dbUtils.getFileContentForFileId(messageContentId);
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
            throw new DataServiceException(ade.getMessage());
        }
        boolean messageTypeFound = false;
        try {
            jsonUtils.getObjectFromJson(messageContent, GetAllOutputFilesURLAsZipMessage.class);
            runDataServiceToGetAllOutputFilesURLAsZip(runId, authentication);
            messageTypeFound = true;
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        try {
            if (!messageTypeFound) {
                jsonUtils.getObjectFromJson(messageContent, GetOutputFilesURLAsZipMessage.class);
                runDataServiceToGetOutputFilesURLAsZip(runId, authentication);
                messageTypeFound = true;
            }
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        try {
            if (!messageTypeFound) {
                if (!messageTypeFound) {
                    jsonUtils.getObjectFromJson(messageContent, GetOutputFilesURLsMessage.class);
                    runDataServiceToGetOutputFilesURLs(runId, authentication);
                    messageTypeFound = true;
                }
            }
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        if (!messageTypeFound) {
            logger.error("Error in runDataService method of the DatabaseAccessor, Run ID suppied does not resolve to a data service message type.");
            throw new DataServiceException("Run ID does not resolve to a data service message type.");
        }

    }

    @Override
    public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            return dbUtils.getRegisteredSoftware();
        } catch (ApolloDatabaseException ade) {
            throw new DataServiceException(ade.getMessage());
        }
    }

    public void addRunIdsToSimulationGroupWithRunId(BigInteger runId, List<BigInteger> listOfRunIdsToAssociateWithSimulationGroup, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            BigInteger groupId = getSimulationGroupIdForRun(runId);
            addRunIdsToSimulationGroup(groupId, listOfRunIdsToAssociateWithSimulationGroup);

        } catch (ApolloDatabaseException | Md5UtilsException e) {
            throw new DataServiceException(e.getMessage());
        }

    }

    public void associateContentWithRunIdWithSoftareNameAndVersionParameters(BigInteger runId, String content, String sourceSoftwareName, String sourceSoftwareVersion,
                                                                             String destinationSoftwareName, String destinationSoftwareVersion, String contentLabel,
                                                                             ContentDataFormatEnum contentDataFormatEnum, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {
        try {
            SoftwareIdentification sourceSoftwareIdentification = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(sourceSoftwareName, sourceSoftwareVersion);
            SoftwareIdentification destinationSoftwareIdentification = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(destinationSoftwareName, destinationSoftwareVersion);
            associateContentWithRunId(runId, content, sourceSoftwareIdentification, destinationSoftwareIdentification, contentLabel, contentDataFormatEnum, contentDataType, authentication);
        } catch (ApolloDatabaseException | DataServiceException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    private SoftwareIdentification getSoftwareIdentificationFromMessage(Object message) {
        if (message instanceof RunSimulationMessage) {
            return ((RunSimulationMessage) message).getSimulatorIdentification();
        }
        if (message instanceof RunSimulationsMessage) {
            return ((RunSimulationsMessage) message).getSimulatorIdentification();
        }
        if (message instanceof RunVisualizationMessage) {
            return ((RunVisualizationMessage) message).getVisualizerIdentification();
        }
        return null;

    }

    public void updateLastServiceToBeCalledForRunWithRunIdSoftwareNameAndSoftwareVersion(BigInteger runId, String softwareName, String softwareVersion, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(softwareName, softwareVersion);
            updateLastServiceToBeCalledForRun(runId, si, authentication);
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
            throw new DataServiceException(ade.getMessage());
        }
    }

    public String getURLForSoftwareIdentificationWithSoftwareNameAndVersion(String softwareName, String softwareVersion, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(softwareName, softwareVersion);
            String wsdlURL = getURLForSoftwareIdentification(si, authentication);
            return wsdlURL;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    public void runDataServiceToGetOutputFilesURLs(BigInteger runId, Authentication authentication) throws DataServiceException {
        GetOutputFilesURLsMessage message;
        try {
            authenticateUser(authentication);
            message = dbUtils.getGetOutputFilesURLsMessageForRun(runId);
            if (message == null) {
                RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
            }
        } catch (ApolloDatabaseException | JsonUtilsException ade) {
            throw new DataServiceException(ade.getMessage());
        }

        FileInformationCollection fileInformationCollection = new FileInformationCollection();
        for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
            // create a url for each file in the list
            BigInteger run = runIdAndFiles.getRunId();
            List<FileInformation> fileInformationForRun = new ArrayList<FileInformation>();
            fileInformationCollection.put(run, fileInformationForRun);
            List<String> files = runIdAndFiles.getFiles();
            for (String file : files) {
                String outputFilePath = OUTPUT_DIRECTORY + runId + File.separator + FILE_PREFIX + file;

                FileInformation fileInformation = new FileInformation();
                fileInformation.setFilePath(outputFilePath);
                fileInformation.setRunId(run.intValue());
                fileInformation.setFileName(file);
                fileInformationForRun.add(fileInformation);
            }

        }
        DataServiceSpecifiedFilesThread thread = new DataServiceSpecifiedFilesThread(runId, fileInformationCollection,
                queue, dbUtils);

        MethodCallStatus queueStatus = queue.addThreadToQueueAndRun(thread);
        if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
            RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, queueStatus.getMessage());
        } else {
            RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.RUNNING, "The data service request was successful");
        }
    }

    public void runDataServiceToGetOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {
        GetOutputFilesURLAsZipMessage message;
        try {
            authenticateUser(authentication);
            message = dbUtils.getGetOutputFilesURLAsZipMessageForRun(runId);
            if (message == null) {
                RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
            }
        } catch (ApolloDatabaseException | JsonUtilsException ade) {
            throw new DataServiceException(ade.getMessage());
        }
        FileInformationCollection fileInformationCollection = new FileInformationCollection();
        String outputDirectory = OUTPUT_DIRECTORY + runId + File.separator;
        String zipFileName = String.format(ZIP_FILE_NAME, runId);
        fileInformationCollection.setZipFiles(true);
        fileInformationCollection.setZipFileName(zipFileName);
        fileInformationCollection.setOutputDirectory(outputDirectory);

        for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
            // create a url for each file in the list
            BigInteger run = runIdAndFiles.getRunId();
            List<FileInformation> fileInformationForRun = new ArrayList<FileInformation>();
            fileInformationCollection.put(run, fileInformationForRun);
            List<String> files = runIdAndFiles.getFiles();
            for (String file : files) {
                String outputFilePath = outputDirectory + FILE_PREFIX + file;
                FileInformation fileInformation = new FileInformation();
                fileInformation.setFilePath(outputFilePath);
                fileInformation.setRunId(run.intValue());
                fileInformation.setFileName(file);
                fileInformationForRun.add(fileInformation);
            }
        }

        DataServiceSpecifiedFilesThread thread = new DataServiceSpecifiedFilesThread(runId, fileInformationCollection,
                queue, dbUtils);
        MethodCallStatus queueStatus = queue.addThreadToQueueAndRun(thread);
        if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
            RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, queueStatus.getMessage());
        } else {
            RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.RUNNING, "The data service request was successful");
        }
    }

    public void runDataServiceToGetAllOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            GetAllOutputFilesURLAsZipMessage message = dbUtils.getGetAllOutputFilesURLAsZipMessageForRun(runId);
            if (message == null) {
                RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
            }

            String outputDirectory = OUTPUT_DIRECTORY + runId + File.separator;
            DataServiceThread thread = new DataServiceAllFilesThread(runId, message.getRunId(), queue, dbUtils, outputDirectory, ZIP_FILE_NAME, message.getFileNames());

            MethodCallStatus queueStatus = queue.addThreadToQueueAndRun(thread);
            if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, queueStatus.getMessage());
            } else {
                RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.RUNNING, "The data service request was successful");
            }
        } catch (ApolloDatabaseException | JsonUtilsException e) {
            throw new DataServiceException(e.getMessage());
        }

    }

	@Override
	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void deleteUser(String username, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addUserRole(String username, SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


}
