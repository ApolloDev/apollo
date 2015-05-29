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
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.DataServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.*;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
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
public class DatabaseAccessor implements DataServiceInterface{
    private static final String DATA_SERVICE_PROPERTIES_NAME = "data_service.properties";
    private static final String OUTPUT_DIRECTORY_KEY = "output_directory";
    private static final String OUTPUT_FILE_NAME_KEY = "output_file_name";
    protected static final String OUTPUT_DIRECTORY;
    private static final String APOLLO_DIR;
    protected static final String OUTPUT_FILE_NAME;
    private static final String ZIP_FILE_NAME_KEY = "zip_file_name";
    protected static final String ZIP_FILE_NAME;
    protected static final int DATA_SERVICE_SOFTWARE_KEY;
    private static final String FILE_PREFIX = "run_%d_";
    private static final String RUN_DATA_SEVICE_MESSAGE_FILENAME = "run_data_service_message.json";
    protected static final SoftwareIdentification dataServiceSoftwareId;
    static Logger logger = LoggerFactory.getLogger(DatabaseAccessor.class);
    protected final ApolloServiceQueue queue = new ApolloServiceQueue();

    static{
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

            FileInputStream fis = null;
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
    protected JsonUtils jsonUtils = new JsonUtils();
    protected Md5Utils md5Utils = new Md5Utils();

    protected final ApolloDbUtils dbUtils;

    protected final Authentication authentication;


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
                    "(" + existingHash +")" + runSimulationMessageAssociatedWithRunIdAsJson);

        }return result;

    }

    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws ApolloDatabaseException {
        return dbUtils.getRunIdsForBatch(runId);
    }

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds) throws ApolloDatabaseException,Md5UtilsException {

        dbUtils.addRunIdsToSimulationGroup(simulationGroupId,
                    runIds);

    }

    public  BigInteger getCachedRunIdFromDatabaseOrNull()
            throws ApolloDatabaseException, Md5UtilsException{
        return null;
    }

    protected  String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws ApolloDatabaseException{
        return null;
    }

    public  BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws ApolloDatabaseException, Md5UtilsException{
        return null;
    }

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws ApolloDatabaseException {

        return dbUtils.getSimulationGroupIdForRun(runId);


    }

    /*--DAN'S ADDITIONS--*/
    public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException{
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
                                          ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException{


                try {
                    int contentId = dbUtils.addTextDataContent(content);
            int sourceSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(sourceSoftware);
            int destinationSoftwareIdKey = dbUtils.getSoftwareIdentificationKey(destinationSoftware);
            int runDataDescriptionId = dbUtils.getRunDataDescriptionId(contentDataFormat, contentLabel, contentDataType, sourceSoftwareIdKey, destinationSoftwareIdKey);
            dbUtils.associateContentWithRunId(runId, contentId, runDataDescriptionId);
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        } catch (Md5UtilsException e) {
                    throw new DataServiceException(e.getMessage());
                }

    }
  public void addRunIdsToSimulationGroupWithRunId(BigInteger runId, List<BigInteger> listOfRunIdsToAssociateWithSimulationGroup, Authentication authentication) throws DataServiceException{
        try {
            authenticateUser(authentication);
            BigInteger groupId = getSimulationGroupIdForRun(runId);
            addRunIdsToSimulationGroup(groupId, listOfRunIdsToAssociateWithSimulationGroup);

        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        } catch (Md5UtilsException e) {
            throw new DataServiceException(e.getMessage());
        }

    }

    public void associateContentWithRunIdWithSoftareNameAndVersionParameters(BigInteger runId, String content, String sourceSoftwareName, String sourceSoftwareVersion,
                                                                             String destinationSoftwareName, String destinationSoftwareVersion, String contentLabel,
                                                                            ContentDataFormatEnum contentDataFormatEnum, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException{
        try {
            SoftwareIdentification sourceSoftwareIdentification = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(sourceSoftwareName, sourceSoftwareVersion);
            SoftwareIdentification destinationSoftwareIdentification = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(destinationSoftwareName, destinationSoftwareVersion);
            associateContentWithRunId(runId, content, sourceSoftwareIdentification, destinationSoftwareIdentification, contentLabel, contentDataFormatEnum, contentDataType, authentication);
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }catch (DataServiceException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    
    public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            return dbUtils.getSoftwareIdentificationForRun(runId);
        } catch (ApolloDatabaseKeyNotFoundException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        }
    }

    @Override
    public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException {
        return null;
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

    
    public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
        try{
            authenticateUser(authentication);
            return dbUtils.updateLastServiceToBeCalledForRun(runId, softwareIdentification);
        } catch (ApolloDatabaseException e) {
            e.printStackTrace();
            throw new DataServiceException(e.getMessage());
        }
    }

    public int updateLastServiceToBeCalledForRunWithRunIdSoftwareNameAndSoftwareVersion(BigInteger runId, String softwareName, String softwareVersion, Authentication authentication) throws DataServiceException{
        try {
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(softwareName, softwareVersion);
           return updateLastServiceToBeCalledForRun(runId, si, authentication);
        } catch (ApolloDatabaseException ade) {
            ade.printStackTrace();
            throw new DataServiceException(ade.getMessage());
        }
    }

    
    public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        try{
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getLastServiceToBeCalledForRun(runId);
            return si;
        } catch (ApolloDatabaseKeyNotFoundException adk) {
            adk.printStackTrace();
            throw new DataServiceException(adk.getMessage());
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
            HashMap<BigInteger,FileAndURLDescription> fileIdsToFileDescriptionMap =  dbUtils.getListOfFilesForRunId(runId);
            return fileIdsToFileDescriptionMap;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    
    public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            HashMap<BigInteger,FileAndURLDescription> urlIdsToUrlDescriptionMap =  dbUtils.getListOfURLsForRunId(runId);
            return urlIdsToUrlDescriptionMap;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    
    public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            String fileContent =  dbUtils.getFileContentForFileId(fileId);
            return fileContent;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }

    }

    
    public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {

    }

    
    public String getURLForURLId(BigInteger urlId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            String urlContent =  dbUtils.getFileContentForFileId(urlId);
            return urlContent;
        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    
    public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            String wsdlURL =  dbUtils.getUrlForSoftwareIdentification(softwareId);
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
        try{
            jsonUtils.getObjectFromJson(messageContent,GetAllOutputFilesURLAsZipMessage.class);
            runDataServiceToGetAllOutputFilesURLAsZip(runId,authentication);
            messageTypeFound=true;
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        try{
            if(!messageTypeFound) {
                jsonUtils.getObjectFromJson(messageContent, GetOutputFilesURLAsZipMessage.class);
                runDataServiceToGetOutputFilesURLAsZip(runId, authentication);
                messageTypeFound = true;
            }
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        try{
            if(!messageTypeFound){
                if(!messageTypeFound) {
                    jsonUtils.getObjectFromJson(messageContent, GetOutputFilesURLsMessage.class);
                    runDataServiceToGetOutputFilesURLs(runId, authentication);
                    messageTypeFound = true;
                }
            }
        } catch (JsonUtilsException jue) {
            jue.printStackTrace();
        }
        if(!messageTypeFound){
            logger.error("Error in runDataService method of the DatabaseAccessor, Run ID suppied does not resolve to a data service message type.");
            throw new DataServiceException("Run ID does not resolve to a data service message type.");
        }

    }

    public String getURLForSoftwareIdentificationWithSoftwareNameAndVersion(String softwareName, String softwareVersion, Authentication authentication) throws DataServiceException {
        try {
            authenticateUser(authentication);
            SoftwareIdentification si = dbUtils.getSoftwareIdentificationFromSoftwareNameAndVersion(softwareName,softwareVersion);
            String wsdlURL =  getURLForSoftwareIdentification(si, authentication);
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
        } catch (ApolloDatabaseException ade) {
            throw new DataServiceException(ade.getMessage());
        } catch (JsonUtilsException jue) {
            throw new DataServiceException(jue.getMessage());
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
        } catch (ApolloDatabaseException ade) {
            throw new DataServiceException(ade.getMessage());
        } catch (JsonUtilsException jue) {
            throw new DataServiceException(jue.getMessage());
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
        } catch (ApolloDatabaseException ade) {
            throw new DataServiceException(ade.getMessage());
        } catch (JsonUtilsException jue) {
            throw new DataServiceException(jue.getMessage());
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


}
