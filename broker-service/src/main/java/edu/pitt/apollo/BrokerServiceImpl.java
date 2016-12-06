/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo;

import edu.pitt.apollo.apollo_service_types.v4_0_1.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartQueryMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartVisualizationMethod;
import edu.pitt.apollo.connector.FilestoreServiceConnector;
import edu.pitt.apollo.connector.LibraryServiceConnector;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.filestore_service_types.v4_0_1.FileIdentification;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.FilestoreServiceInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.LibraryServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.library_service_types.v4_0_1.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetLibraryItemURNsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v4_0_1.GetRevisionsResult;
import edu.pitt.apollo.library_service_types.v4_0_1.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v4_0_1.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v4_0_1.QueryResult;
import edu.pitt.apollo.library_service_types.v4_0_1.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.library_service_types.v4_0_1.SetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v4_0_1.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.query_service_types.v4_0_1.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.restfilestoreserviceconnector.RestFilestoreServiceConnector;
import edu.pitt.apollo.restlibraryserviceconnector.RestLibraryServiceConnector;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0_1.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0_1.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunMessage;
import edu.pitt.apollo.services_common.v4_0_1.RunResult;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRecord;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.pitt.securitymanager.exception.ApolloSecurityException;
import edu.pitt.securitymanager.utilities.ApolloSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nem41
 */
public class BrokerServiceImpl implements ContentManagementInterface, FilestoreServiceInterface,
        RunManagementInterface, JobRunningServiceInterface, SoftwareRegistryInterface, LibraryServiceInterface {

    private static final String BROKER_SERVICE_PROPERTIES = "broker_service.properties";
    private static final String RUN_MANAGER_SERVICE_URL_PROPERTY = "run_manager_service_url";
    private static final String FILESTORE_SERVICE_URL_PROPERTY = "filestore_service_url";
    private static final String LIBRARY_SERVICE_URL_PROPERTY = "filestore_service_url";
    private static String runManagerServiceUrl;
    private static String filestoreServiceUrl;
    private static String libraryServiceUrl;
    private static RunManagerServiceConnector runManagerServiceConnector;
    private static FilestoreServiceConnector filestoreServiceConnector;
    private static LibraryServiceConnector libraryServiceConnector;
    protected static final ApolloServiceQueue apolloServiceQueue;
    Logger logger = LoggerFactory.getLogger(BrokerServiceImpl.class);

    static {
        apolloServiceQueue = new ApolloServiceQueue();
    }

    private static RunManagerServiceConnector getRunManagerServiceConnector() throws DatastoreException {
        try {
            if (runManagerServiceConnector == null) {
                runManagerServiceConnector = new RestRunManagerServiceConnector(getRunManagerServiceUrl());
            }

            return runManagerServiceConnector;
        } catch (IOException ex) {
            throw new DatastoreException("IOException loading run manager service connector: " + ex.getMessage());
        }
    }

    private static FilestoreServiceConnector getFilestoreServiceConnector() throws FilestoreException {
        try {
            if (filestoreServiceConnector == null) {
                filestoreServiceConnector = new RestFilestoreServiceConnector(getFilestoreServiceUrl());
            }

            return filestoreServiceConnector;
        } catch (IOException ex) {
            throw new FilestoreException("IOException loading file store service connector: " + ex.getMessage());
        }
    }

    private static LibraryServiceConnector getLibraryServiceConnector() throws LibraryServiceException {
        try {
            if (libraryServiceConnector == null) {
                libraryServiceConnector = new RestLibraryServiceConnector(getLibraryServiceUrl());
            }

            return libraryServiceConnector;
        } catch (IOException ex) {
            throw new LibraryServiceException("IOException loading library service connector: " + ex.getMessage());
        }
    }

    protected static String getFilestoreServiceUrl() throws IOException {
        if (filestoreServiceUrl == null) {
            String apolloDir = ApolloServiceConstants.APOLLO_DIR;

            File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
            Properties brokerServiceProperties = new Properties();

            try (InputStream input = new FileInputStream(configurationFile)) {
                // load a properties file
                brokerServiceProperties.load(input);
                filestoreServiceUrl = brokerServiceProperties.getProperty(FILESTORE_SERVICE_URL_PROPERTY);
            }
        }
        return filestoreServiceUrl;
    }

    protected static String getLibraryServiceUrl() throws IOException {
        if (libraryServiceUrl == null) {
            String apolloDir = ApolloServiceConstants.APOLLO_DIR;

            File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
            Properties brokerServiceProperties = new Properties();

            try (InputStream input = new FileInputStream(configurationFile)) {
                // load a properties file
                brokerServiceProperties.load(input);
                libraryServiceUrl = brokerServiceProperties.getProperty(LIBRARY_SERVICE_URL_PROPERTY);
            }
        }
        return libraryServiceUrl;
    }

    protected static String getRunManagerServiceUrl() throws IOException {
        if (runManagerServiceUrl == null) {
            String apolloDir = ApolloServiceConstants.APOLLO_DIR;

            File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
            Properties brokerServiceProperties = new Properties();

            try (InputStream input = new FileInputStream(configurationFile)) {
                // load a properties file
                brokerServiceProperties.load(input);
                runManagerServiceUrl = brokerServiceProperties.getProperty(RUN_MANAGER_SERVICE_URL_PROPERTY);
            }
        }
        return runManagerServiceUrl;
    }

    @Override
    public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
        try {
            ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication, softwareId);
            return getRunManagerServiceConnector().getURLForSoftwareIdentification(softwareId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeServiceOrUserForRunData(authentication, runId);
            return getRunManagerServiceConnector().getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            return getRunManagerServiceConnector().getSoftwareIdentificationForRun(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public InsertRunResult insertRun(RunMessage message, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication, message.getSoftwareIdentification());
            return getRunManagerServiceConnector().insertRun(message, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            getRunManagerServiceConnector().updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            getRunManagerServiceConnector().updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            return getRunManagerServiceConnector().getLastServiceToBeCalledForRun(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            getRunManagerServiceConnector().addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeServiceOrUserForRunData(authentication, runId);
            getRunManagerServiceConnector().removeRunData(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
        try {
            ApolloSecurityManager.authorizeServiceOrUserForRunData(authentication, runId);
            return getRunManagerServiceConnector().getRunStatus(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
        try {
            ApolloSecurityManager.authorizeUserForRunData(authentication, runId);
            getRunManagerServiceConnector().run(runId, authentication);
        } catch (RunManagementException ex) {
            throw new JobRunningServiceException(ex.getMessage());
        } catch (ApolloSecurityException ex) {
            throw new JobRunningServiceException("ApolloSecurityException: " + ex.getMessage());
        }

    }

    @Override
    public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
        try {
            ApolloSecurityManager.authorizeUserForRunData(authentication, runId);
            getRunManagerServiceConnector().terminate(runId, authentication);
        } catch (RunManagementException ex) {
            throw new JobRunningServiceException(ex.getMessage());
        } catch (ApolloSecurityException ex) {
            throw new JobRunningServiceException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public List<ServiceRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
        try {
            // this method uses the original authorization after getting the registered software
            Authentication userAuthentication = ApolloSecurityManager.getUsernameAuthentication(authentication);
            List<ServiceRecord> records = getRunManagerServiceConnector().getListOfRegisteredSoftwareRecords(userAuthentication);
            ApolloSecurityManager.filterSoftwareListForServiceOrUser(authentication, records);
            return records;
        } catch (ApolloSecurityException ex) {
            throw new DatastoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    public RunResult runSimulation(RunSimulationMessage runSimulationMessage, Authentication authentication) {
        try {
            String userId = ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication,
                    runSimulationMessage.getSoftwareIdentification());
            runSimulationMessage.setUserId(userId);
            InsertAndStartSimulationMethod method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runSimulationMessage, authentication);
        } catch (ApolloSecurityException | IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public RunResult runVisualization(RunVisualizationMessage runVisualizationMessage, Authentication authentication) {
        try {
            String userId = ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication,
                    runVisualizationMessage.getSoftwareIdentification());
            runVisualizationMessage.setUserId(userId);
            InsertAndStartVisualizationMethod method = new InsertAndStartVisualizationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runVisualizationMessage, authentication);
        } catch (ApolloSecurityException | IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public RunResult runSimulations(
            edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage runSimulationsMessage, Authentication authentication) {
        InsertAndStartSimulationMethod method = null;
        try {
            String userId = ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication,
                    runSimulationsMessage.getSoftwareIdentification());
            runSimulationsMessage.setUserId(userId);
            method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runSimulationsMessage, authentication);
        } catch (ApolloSecurityException | IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public RunResult runSimulatorOutputQuery(RunSimulatorOutputQueryMessage message, Authentication authentication) {
        InsertAndStartQueryMethod method = null;
        try {
            ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication,
                    message.getSoftwareIdentification());
            method = new InsertAndStartQueryMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(message, authentication);
        } catch (ApolloSecurityException | IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    public RunResult runInfectiousDiseaseTransmissionExperiment(RunInfectiousDiseaseTransmissionExperimentMessage runInfectiousDiseaseTransmissionExperimentMessage,
                                                                Authentication authentication) {
        InsertAndStartSimulationMethod method = null;
        try {
            ApolloSecurityManager.authorizeUserForSpecifiedSoftware(authentication,
                    runInfectiousDiseaseTransmissionExperimentMessage.getSoftwareIdentification());
            method = new InsertAndStartSimulationMethod(BrokerServiceImpl.getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runInfectiousDiseaseTransmissionExperimentMessage, authentication);
        } catch (ApolloSecurityException | IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void uploadFile(BigInteger runId, String urlToFile, String filename,
                           ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            getFilestoreServiceConnector().uploadFile(runId, urlToFile, filename, fileFormat, fileType, authentication);
        } catch (ApolloSecurityException ex) {
            throw new FilestoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public String getUrlOfFile(BigInteger runId, String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
        try {
            ApolloSecurityManager.authorizeServiceOrUserForRunData(authentication, runId);
            return getFilestoreServiceConnector().getUrlOfFile(runId, filename, fileFormat, fileType, authentication);
        } catch (ApolloSecurityException ex) {
            throw new FilestoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public MethodCallStatus getStatusOfFileUpload(BigInteger runId, String filename, ContentDataFormatEnum fileFormat, ContentDataTypeEnum fileType, Authentication authentication) throws FilestoreException {
        try {
            ApolloSecurityManager.authorizeService(authentication);
            return getFilestoreServiceConnector().getStatusOfFileUpload(runId, filename, fileFormat, fileType, authentication);
        } catch (ApolloSecurityException ex) {
            throw new FilestoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public List<FileIdentification> listFilesForRun(BigInteger runId, Authentication authentication) throws FilestoreException {
        try {
            ApolloSecurityManager.authorizeServiceOrUserForRunData(authentication, runId);
            return getFilestoreServiceConnector().listFilesForRun(runId, authentication);
        } catch (ApolloSecurityException ex) {
            throw new FilestoreException("ApolloSecurityException: " + ex.getMessage());
        }
    }

    @Override
    public QueryResult query(String query, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().query(query, authentication);
    }

    @Override
    public GetLibraryItemContainerResult getLibraryItem(int urn, Integer revision, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().getLibraryItem(urn, revision, authentication);
    }

    @Override
    public UpdateLibraryItemContainerResult reviseLibraryItem(int urn, LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().reviseLibraryItem(urn, libraryItemContainer, comment, authentication);
    }

    @Override
    public AddLibraryItemContainerResult addLibraryItem(LibraryItemContainer libraryItemContainer, String comment, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().addLibraryItem(libraryItemContainer, comment, authentication);
    }

    @Override
    public GetCommentsResult getCommentsForLibraryItem(int urn, int revision, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().getCommentsForLibraryItem(urn, revision, authentication);
    }

    @Override
    public GetRevisionsResult getAllRevisionsOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().getAllRevisionsOfLibraryItem(urn, authentication);
    }

    @Override
    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().removeGroupAccessToLibraryItem(urn, group, authentication);
    }

    @Override
    public GetLibraryItemURNsResult getLibraryItemURNs(String itemType, Authentication authentication) throws LibraryServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SetReleaseVersionResult approveRevisionOfLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().approveRevisionOfLibraryItem(urn, revision, comment, authentication);
    }

    @Override
    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(int urn, String group, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().grantGroupAccessToLibraryItem(urn, group, authentication);
    }

    @Override
    public AddReviewerCommentResult addReviewerCommentToLibraryItem(int urn, int revision, String comment, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().addReviewerCommentToLibraryItem(urn, revision, comment, authentication);
    }

    @Override
    public SetLibraryItemAsNotReleasedResult hideLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().hideLibraryItem(urn, authentication);
    }

    @Override
    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(XMLGregorianCalendar dateTime, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().getChangeLogForLibraryItemsModifiedSinceDateTime(dateTime, authentication);
    }

    @Override
    public GetReleaseVersionResult getApprovedRevisionOfLibraryItem(int urn, Authentication authentication) throws LibraryServiceException {
        return getLibraryServiceConnector().getApprovedRevisionOfLibraryItem(urn, authentication);
    }
}
