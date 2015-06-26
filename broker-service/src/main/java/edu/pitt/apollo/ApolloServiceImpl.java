/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.apolloservice.methods.census.GetPopulationAndEnvironmentCensusMethod;
import edu.pitt.apollo.apolloservice.methods.census.GetScenarioLocationCodesSupportedBySimulatorMethod;
import edu.pitt.apollo.apolloservice.methods.content.GetConfigurationFileForSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.content.GetVisualizerOutputResourcesMethod;
import edu.pitt.apollo.apolloservice.methods.library.AddLibraryItemContainerMethod;
import edu.pitt.apollo.apolloservice.methods.library.AddReviewerCommentMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetCommentsForLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetLibraryItemContainerMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetLibraryItemReleaseVersionMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetLibraryItemURNsMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetVersionNumbersForLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.GrantGroupAccessToLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.QueryMethod;
import edu.pitt.apollo.apolloservice.methods.library.RemoveGroupAccessToLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.SetLibraryItemAsNotReleasedMethod;
import edu.pitt.apollo.apolloservice.methods.library.SetReleaseVersionForLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.UpdateLibraryItemContainerMethod;
import edu.pitt.apollo.apolloservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartDataServiceJobMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.run.InsertAndStartVisualizationMethod;
import edu.pitt.apollo.apolloservice.methods.services.GetRegisteredServicesMethod;
import edu.pitt.apollo.apolloservice.methods.services.RegisterServiceMethod;
import edu.pitt.apollo.apolloservice.methods.services.UnregisterServiceMethod;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.AddReviewerCommentMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.AddReviewerCommentResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetChangeLogForLibraryItemsModifiedSinceDateTimeResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetCommentsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetLibraryItemURNsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_0.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.GetVersionsResult;
import edu.pitt.apollo.library_service_types.v3_0_0.ModifyGroupOwnershipMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.ModifyGroupOwnershipResult;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.QueryResult;
import edu.pitt.apollo.library_service_types.v3_0_0.SetLibraryItemAsNotReleasedMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.SetLibraryItemAsNotReleasedResult;
import edu.pitt.apollo.library_service_types.v3_0_0.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.SetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v3_0_0.UpdateLibraryItemContainerResult;
import edu.pitt.apollo.restdataserviceconnector.RestDataServiceConnector;
import edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceEI;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRecord;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v3.0.0", endpointInterface = "edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

    private static final BigInteger NO_SIMULATION_GROUP_ID = null;
    private static final String BROKER_SERVICE_PROPERTIES = "broker_service.properties";
    private static final String DATA_SERVICE_URL_PROPERTY_TOKEN = "dataServiceUrl";
    private static final String AUTHENTICATION_USER = "authenticationUser";
    private static final String AUTHENTICATION_PASSWORD = "authenticationPassword";
    private static Authentication dataServiceAuthentication;
    private static String runManagerServiceUrl;
    private static String dataServiceUrl;
    private static ApolloServiceQueue apolloServiceQueue;

    static {
        apolloServiceQueue = new ApolloServiceQueue();
    }

    Logger logger = LoggerFactory.getLogger(ApolloServiceImpl.class);

    private static String getDataServiceUrl() throws IOException {
        if (dataServiceUrl == null) {
            String apolloDir = ApolloServiceConstants.APOLLO_DIR;

            File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
            Properties brokerServiceProperties = new Properties();

            try (InputStream input = new FileInputStream(configurationFile)) {
                // load a properties file
                brokerServiceProperties.load(input);
                dataServiceUrl = brokerServiceProperties.getProperty(DATA_SERVICE_URL_PROPERTY_TOKEN);
            }
        }
        return dataServiceUrl;
    }

    private static Authentication getDataServiceAuthentication() throws IOException {
        if (dataServiceAuthentication == null) {
            String apolloDir = ApolloServiceConstants.APOLLO_DIR;

            File configurationFile = new File(apolloDir + File.separator + BROKER_SERVICE_PROPERTIES);
            Properties brokerServiceProperties = new Properties();

            try (InputStream input = new FileInputStream(configurationFile)) {
                // load a properties file
                brokerServiceProperties.load(input);

                String authenticationUser = brokerServiceProperties.getProperty(AUTHENTICATION_USER);
                String authenticationPassword = brokerServiceProperties.getProperty(AUTHENTICATION_PASSWORD);

                dataServiceAuthentication = new Authentication();
                dataServiceAuthentication.setRequesterId(authenticationUser);
                dataServiceAuthentication.setRequesterPassword(authenticationPassword);
            }
        }
        return dataServiceAuthentication;
    }

    private static String getRunManagerServiceUrl() throws IOException {
        if (runManagerServiceUrl == null) {
            DataServiceConnector dataServiceConnector = new RestDataServiceConnector(getDataServiceUrl());
            String initRunManagerServiceUrl = null;
            Authentication authentication = getDataServiceAuthentication();
            try {
                List<ServiceRegistrationRecord> softwareRecords = dataServiceConnector.getListOfRegisteredSoftwareRecords(authentication);

                for (ServiceRegistrationRecord record : softwareRecords) {
                    SoftwareIdentification softwareId = record.getSoftwareIdentification();
                    if (softwareId.getSoftwareType().equals(ApolloSoftwareTypeEnum.RUN_MANAGER)) {
                        initRunManagerServiceUrl = dataServiceConnector.getURLForSoftwareIdentification(softwareId, authentication);
                    }
                }

                if (initRunManagerServiceUrl == null) {
                    throw new RuntimeException("No registered software with type RUN_MANAGER was found");
                } else {
                    runManagerServiceUrl = initRunManagerServiceUrl;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        return runManagerServiceUrl;
    }

    @Override
    public GetVersionsResult getVersionNumbersForLibraryItem(
            GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
        return GetVersionNumbersForLibraryItemMethod
                .getVersions(getVersionNumbersForLibraryItemMessage);
    }

    @Override
    @WebResult(name = "syntheticPopulationGenerationResult", targetNamespace = "")
    @RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSyntheticPopulationGeneration")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/runSyntheticPopulationGeneration")
    @ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSyntheticPopulationGenerationResponse")
    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(
            @WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GetOutputFilesURLAsZipResult getOutputFilesURLAsZip(GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GetLibraryItemContainerResult getLibraryItemContainer(
            GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
        return GetLibraryItemContainerMethod
                .getLibraryItemContainer(getLibraryItemContainerMessage);
    }

    @Override
    public QueryResult query(QueryMessage queryMessage) {
        return QueryMethod.query(queryMessage);
    }

    @Override
    public AddLibraryItemContainerResult addLibraryItemContainer(
            AddLibraryItemContainerMessage addLibraryItemContainerMessage) {
        return AddLibraryItemContainerMethod
                .addLibraryItemContainer(addLibraryItemContainerMessage);

    }

    @Override
    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(
            ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
        return RemoveGroupAccessToLibraryItemMethod
                .removeGroupAccess(removeGroupAccessToLibraryItemMessage);
    }

    @Override
    @WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
    @RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetScenarioLocationCodesSupportedBySimulator")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getScenarioLocationCodesSupportedBySimulator")
    @ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetScenarioLocationCodesSupportedBySimulatorResponse")
    public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification) {
        return GetScenarioLocationCodesSupportedBySimulatorMethod
                .getScenarioLocationCodesSupportedBySimulator(simulatorIdentification);
    }

    @Override
    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(
            GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
        return GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod
                .getChangeLog(getChangeLogForLibraryItemsModifiedSinceDateTimeMessage);
    }

    @Override
    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(
            SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
        return SetLibraryItemAsNotReleasedMethod
                .setLibraryItemAsnotReleased(setLibraryItemAsNotReleasedMessage);
    }

    @Override
    public GetAllOutputFilesURLAsZipResult getAllOutputFilesURLAsZip(
            GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage) {

        DataRetrievalRequestMessage dataRetrievalRequestMessage = new DataRetrievalRequestMessage();
        dataRetrievalRequestMessage.getOptionalFileNamesToMatch().addAll(getAllOutputFilesURLAsZipMessage.getFileNames());
        dataRetrievalRequestMessage.setAuthentication(getAllOutputFilesURLAsZipMessage.getAuthentication());
        dataRetrievalRequestMessage.setSoftwareIdentification(getAllOutputFilesURLAsZipMessage.getSoftwareIdentification());
        dataRetrievalRequestMessage.setRunId(getAllOutputFilesURLAsZipMessage.getRunId());

        InsertAndStartDataServiceJobMethod method = null;
        try {
            method = new InsertAndStartDataServiceJobMethod(getRunManagerServiceUrl(), apolloServiceQueue);
            RunResult runResult = method.insertAndStartRun(dataRetrievalRequestMessage, getDataServiceAuthentication());
            GetAllOutputFilesURLAsZipResult result = new GetAllOutputFilesURLAsZipResult();
            result.setMethodCallStatus(runResult.getMethodCallStatus());
            result.setRequestIdentification(runResult.getRunId());
            return result;
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }

    }

    // LIBRARY METHODS
    @Override
    public GetCommentsResult getCommentsForLibraryItem(
            GetCommentsMessage getCommentsForLibraryItemMessage) {
        return GetCommentsForLibraryItemMethod
                .getComments(getCommentsForLibraryItemMessage);
    }

    @Override
    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(
            ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
        return GrantGroupAccessToLibraryItemMethod
                .grantGroupAccess(grantGroupAccessToLibraryItemMessage);
    }

    @Override
    public SetReleaseVersionResult setReleaseVersionForLibraryItem(
            SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
        return SetReleaseVersionForLibraryItemMethod
                .setReleaseVersion(setReleaseVersionForLibraryItemMessage);
    }

    @Override
    public GetOutputFilesURLsResult getOutputFilesURLs(GetOutputFilesURLsMessage getOutputFilesURLsMessage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.UnRegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/unRegisterService")
    @ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.UnRegisterServiceResponse")
    public MethodCallStatus unRegisterService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return UnregisterServiceMethod
                .unregisterService(serviceRegistrationRecord);
    }

    @Override
    public GetSoftwareIdentificationForRunResult getSoftwareIdentificationForRun(GetSoftwareIdentificationForRunMessage getSoftwareIdentificationForRunMessage) {
        return null;
    }

    @Override
    public UpdateLibraryItemContainerResult updateLibraryItemContainer(
            UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
        return UpdateLibraryItemContainerMethod
                .updateLibraryItemContainer(updateLibraryItemContainerMessage);
    }

    @Override
    public RunResult runSimulations(
            edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage runSimulationsMessage) {
        InsertAndStartSimulationMethod method = null;
        try {
            method = new InsertAndStartSimulationMethod(getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runSimulationsMessage, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    @WebResult(name = "serviceRecords", targetNamespace = "")
    @RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRegisteredServices")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getRegisteredServices")
    @ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRegisteredServicesResponse")
    public List<ServiceRecord> getRegisteredServices() {
        try {
            return new GetRegisteredServicesMethod(getDataServiceUrl()).getRegisteredServices(getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public GetReleaseVersionResult getLibraryItemReleaseVersion(
            GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
        return GetLibraryItemReleaseVersionMethod
                .getReleaseVersion(getLibraryItemReleaseVersionMessage);
    }

    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/registerService")
    @ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RegisterServiceResponse")
    public MethodCallStatus registerService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return RegisterServiceMethod.registerService(serviceRegistrationRecord);
    }

    @Override
    public TerminteRunResult terminateRun(
            TerminateRunRequest terminateRunRequest) {
        // TODO Auto-generated method stub
        return null;
    }

    // END LIBRARY METHODS
    @Override
    public ListOutputFilesForSoftwareResult listOutputFilesForSoftware(
            ListOutputFilesForSoftwareMessage listOutputFilesForSoftwareMessage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @WebResult(name = "getConfigurationFileForSimulationResult", targetNamespace = "")
    @RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetConfigurationFileForSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getConfigurationFileForSimulation")
    @ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetConfigurationFileForSimulationResponse")
    public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        try {
            return new GetConfigurationFileForSimulationMethod(getDataServiceUrl()).getConfigurationFile(runIdentification, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public AddReviewerCommentResult addReviewerCommentToLibraryItem(
            AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
        return AddReviewerCommentMethod
                .addReviewerComment(addReviewerCommentToLibraryItemMessage);
    }

    @Override
    @WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
    @RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetPopulationAndEnvironmentCensus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getPopulationAndEnvironmentCensus")
    @ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetPopulationAndEnvironmentCensusResponse")
    public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification,
            @WebParam(name = "location", targetNamespace = "") String location) {
        return GetPopulationAndEnvironmentCensusMethod
                .getPopulationAndEnvironmentCensus(simulatorIdentification,
                        location);
    }

    @Override
    @WebResult(name = "simulationRunId", targetNamespace = "")
    @RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/runSimulation")
    @ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSimulationResponse")
    public RunResult runSimulation(
            @WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
        try {
            InsertAndStartSimulationMethod method = new InsertAndStartSimulationMethod(getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runSimulationMessage, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public GetLibraryItemURNsResult getLibraryItemURNs(
            GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
        return GetLibraryItemURNsMethod.getURNs(getLibraryItemURNsMessage);
    }

    @Override
    @WebResult(name = "visualizationResult", targetNamespace = "")
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunVisualizationResponse")
    public RunResult runVisualization(
            @WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
        try {
            InsertAndStartVisualizationMethod method = new InsertAndStartVisualizationMethod(getRunManagerServiceUrl(), apolloServiceQueue);
            return method.insertAndStartRun(runVisualizationMessage, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        try {
            return new GetRunStatusMethod(getRunManagerServiceUrl()).getRunStatus(runIdentification, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    @WebResult(name = "getVisualizerOutputResourcesResult", targetNamespace = "")
    @RequestWrapper(localName = "getVisualizerOutputResources", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetVisualizerOutputResources")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getVisualizerOutputResourcesResponse")
    @ResponseWrapper(localName = "getVisualizerOutputResourcesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetVisualizerOutputResourcesResponse")
    public GetVisualizerOutputResourcesResult getVisualizerOutputResources(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        try {
            return new GetVisualizerOutputResourcesMethod(getDataServiceUrl()).getVisualizerOutputResources(runIdentification, getDataServiceAuthentication());
        } catch (IOException e) {
            logger.error(e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

}
