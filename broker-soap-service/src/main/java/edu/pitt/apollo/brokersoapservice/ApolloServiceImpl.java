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
package edu.pitt.apollo.brokersoapservice;

import edu.pitt.apollo.BrokerServiceImpl;
import edu.pitt.apollo.data_service_types.v4_0_1.GetSoftwareIdentificationForRunMessage;
import edu.pitt.apollo.data_service_types.v4_0_1.GetSoftwareIdentificationForRunResult;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.filestore_service_types.v4_0_1.*;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceEI;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.apollo.utilities.AuthorizationUtility;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

@org.apache.cxf.interceptor.InInterceptors(interceptors = {"edu.pitt.apollo.brokersoapservice.HTTPHeaderInterceptor"})
@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v4.0.1", endpointInterface = "edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceEI")
public class ApolloServiceImpl implements ApolloServiceEI {

    public ApolloServiceImpl() {

    }

    Logger logger = LoggerFactory.getLogger(ApolloServiceImpl.class);
    private static final BrokerServiceImpl brokerService = new BrokerServiceImpl();


    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.UnRegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/unRegisterService")
    @ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.UnRegisterServiceResponse")
    public MethodCallStatus unRegisterService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public GetSoftwareIdentificationForRunResult getSoftwareIdentificationForRun(GetSoftwareIdentificationForRunMessage getSoftwareIdentificationForRunMessage) {
        GetSoftwareIdentificationForRunResult result = new GetSoftwareIdentificationForRunResult();
        try {
            SoftwareIdentification softwareIdentification = brokerService.getSoftwareIdentificationForRun(getSoftwareIdentificationForRunMessage.getRunId(),
                    getAuthentication());
            result.setSoftwareIdentification(softwareIdentification);
            result.setMethodCallStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (RunManagementException | UnsupportedAuthorizationTypeException ex) {
            result.setMethodCallStatus(createStatus("Error calling getSoftwareIdentificationForRun: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetCollectionsResult getCollections(GetCollectionsMessage getCollectionsMessage) {
        GetCollectionsResult result;
        try {
            result = brokerService.getCollections(getCollectionsMessage.getClassName(), getCollectionsMessage.isIncludeUnreleasedItems(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetCollectionsResult();
            result.setStatus(createStatus("Error calling getCollections: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public RunResult runSimulations(
            edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage runSimulationsMessage) {
        try {
            return brokerService.runSimulations(runSimulationsMessage, getAuthentication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
    }

    @Override
    public GetLibraryItemContainersResult getLibraryItemContainers(GetLibraryItemContainersMessage getLibraryItemContainersMessage) {
        GetLibraryItemContainersResult result;
        try {
            result = brokerService.getLibraryItemContainers(getLibraryItemContainersMessage.getClassName(),
                    getLibraryItemContainersMessage.isIncludeUnreleasedItems(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetLibraryItemContainersResult();
            result.setStatus(createStatus("Error calling getLibraryItemContainers: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public UpdateLibraryItemContainerResult updateLibraryItemContainer(UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
        UpdateLibraryItemContainerResult result;
        try {
            result = brokerService.reviseLibraryItem(updateLibraryItemContainerMessage.getUrn(),
                    updateLibraryItemContainerMessage.getLibraryItemContainer(), updateLibraryItemContainerMessage.getComment(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new UpdateLibraryItemContainerResult();
            result.setStatus(createStatus("Error calling updateLibraryItemContainer: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public SetReleaseVersionResult setReleaseVersionForLibraryItem(SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
        SetReleaseVersionResult result;
        try {
            result = brokerService.approveRevisionOfLibraryItem(setReleaseVersionForLibraryItemMessage.getUrn(),
                    setReleaseVersionForLibraryItemMessage.getVersion(), setReleaseVersionForLibraryItemMessage.getComment(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new SetReleaseVersionResult();
            result.setStatus(createStatus("Error calling setReleaseVersionForLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetCacheDataResult getCacheData(GetCacheDataMessage getCacheDataMessage) {
        GetCacheDataResult result;
        try {
            result = brokerService.getCacheData(getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetCacheDataResult();
            result.setStatus(createStatus("Error calling getCacheData: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetCommentsResult getCommentsForLibraryItem(GetCommentsMessage getCommentsForLibraryItemMessage) {
        GetCommentsResult result;
        try {
            result = brokerService.getCommentsForLibraryItem(getCommentsForLibraryItemMessage.getUrn(),
                    getCommentsForLibraryItemMessage.getVersion(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetCommentsResult();
            result.setStatus(createStatus("Error calling getCommentsForLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
        ModifyGroupOwnershipResult result;
        try {
            result = brokerService.grantGroupAccessToLibraryItem(grantGroupAccessToLibraryItemMessage.getUrn(),
                    grantGroupAccessToLibraryItemMessage.getGroup(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new ModifyGroupOwnershipResult();
            result.setStatus(createStatus("Error calling grantGroupAccessToLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
        SetLibraryItemAsNotReleasedResult result;
        try {
            result = brokerService.hideLibraryItem(setLibraryItemAsNotReleasedMessage.getUrn(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new SetLibraryItemAsNotReleasedResult();
            result.setStatus(createStatus("Error calling setLibraryItemAsNotReleased: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
        GetChangeLogForLibraryItemsModifiedSinceDateTimeResult result;
        try {
            result = brokerService.getChangeLogForLibraryItemsModifiedSinceDateTime(
                    getChangeLogForLibraryItemsModifiedSinceDateTimeMessage.getDateTime(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetChangeLogForLibraryItemsModifiedSinceDateTimeResult();
            result.setStatus(createStatus("Error calling getChangeLogForLibraryItemsModifiedSinceDateTime: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetLibraryItemContainerResult getLibraryItemContainer(GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
        GetLibraryItemContainerResult result;
        try {
            result = brokerService.getLibraryItem(getLibraryItemContainerMessage.getUrn(),
                    getLibraryItemContainerMessage.getVersion(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetLibraryItemContainerResult();
            result.setStatus(createStatus("Error calling getLibraryItemContainer: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
        ModifyGroupOwnershipResult result;
        try {
            result = brokerService.removeGroupAccessToLibraryItem(removeGroupAccessToLibraryItemMessage.getUrn(),
                    removeGroupAccessToLibraryItemMessage.getGroup(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new ModifyGroupOwnershipResult();
            result.setStatus(createStatus("Error calling removeGroupAccessToLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public AddLibraryItemContainerResult addLibraryItemContainer(AddLibraryItemContainerMessage addLibraryItemContainerMessage) {
        AddLibraryItemContainerResult result;
        try {
            result = brokerService.addLibraryItem(addLibraryItemContainerMessage.getLibraryItemContainer(),
                    addLibraryItemContainerMessage.getComment(), getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new AddLibraryItemContainerResult();
            result.setStatus(createStatus("Error calling addLibraryItemContainer: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetRevisionsResult getVersionNumbersForLibraryItem(GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
        GetRevisionsResult result;
        try {
            result = brokerService.getAllRevisionsOfLibraryItem(getVersionNumbersForLibraryItemMessage.getUrn(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetRevisionsResult();
            result.setStatus(createStatus("Error calling getVersionNumbersForLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }


    @Override
    @WebResult(name = "serviceRecords", targetNamespace = "")
    @RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRegisteredServices")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/getRegisteredServices")
    @ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRegisteredServicesResponse")
    public GetRegisteredServicesResult getRegisteredServices() {

        GetRegisteredServicesResult result = new GetRegisteredServicesResult();
        try {
            result.getServiceRecords().addAll(brokerService.getListOfRegisteredSoftwareRecords(getAuthentication()));
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (RunManagementException | UnsupportedAuthorizationTypeException ex) {
            result = new GetRegisteredServicesResult();
            result.setStatus(createStatus("Error running getRegisteredServices: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }


    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/registerService")
    @ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RegisterServiceResponse")
    public MethodCallStatus registerService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    @WebResult(name = "simulationRunId", targetNamespace = "")
    @RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/runSimulation")
    @ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunSimulationResponse")
    public RunResult runSimulation(
            @WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
        try {
            return brokerService.runSimulation(runSimulationMessage, getAuthentication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
    }

    @Override
    public AddReviewerCommentResult addReviewerCommentToLibraryItem(AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
        AddReviewerCommentResult result;
        try {
            result = brokerService.addReviewerCommentToLibraryItem(addReviewerCommentToLibraryItemMessage.getUrn(),
                    addReviewerCommentToLibraryItemMessage.getVersion(), addReviewerCommentToLibraryItemMessage.getComment(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new AddReviewerCommentResult();
            result.setStatus(createStatus("Error calling addReviewerCommentToLibraryItem: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetLibraryItemURNsResult getLibraryItemURNs(GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
        GetLibraryItemURNsResult result;
        try {
            result = brokerService.getLibraryItemURNs(getLibraryItemURNsMessage.getItemType(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (UnsupportedAuthorizationTypeException | LibraryServiceException ex) {
            result = new GetLibraryItemURNsResult();
            result.setStatus(createStatus("Error calling getLibraryItemURNs: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    @WebResult(name = "visualizationResult", targetNamespace = "")
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunVisualizationResponse")
    public RunResult runVisualization(
            @WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
        try {
            return brokerService.runVisualization(runVisualizationMessage, getAuthentication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
    }

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(
            @WebParam(name = "runStatusRequest", targetNamespace = "") RunStatusRequest runStatusRequest) {
        try {
            return brokerService.getRunStatus(runStatusRequest.getRunIdentification(), getAuthentication());
        } catch (RunManagementException | UnsupportedAuthorizationTypeException ex) {
            return createStatus("Error running getRunStatus: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED);
        }
    }

    private MethodCallStatus createStatus(String message, MethodCallStatusEnum enumVal) {
        MethodCallStatus status = new MethodCallStatus();
        status.setMessage(message);
        status.setStatus(enumVal);
        return status;
    }

    @Override
    @WebResult(name = "getFileUrlResult", targetNamespace = "")
    @RequestWrapper(localName = "getFileUrl", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetFileUrl")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/listFilesForRun")
    @ResponseWrapper(localName = "getFileUrlResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetFileUrlResponse")

    public GetFileUrlResult getFileUrl(GetFileUrlRequest getFileUrlRequest) {
        GetFileUrlResult result = new GetFileUrlResult();
        MethodCallStatus status = new MethodCallStatus();
        try {
            String url = brokerService.getUrlOfFile(getFileUrlRequest.getRunId(),
                    getFileUrlRequest.getFileIdentification().getLabel(),
                    getFileUrlRequest.getFileIdentification().getFormat(),
                    getFileUrlRequest.getFileIdentification().getType(),
                    getAuthentication());
            result.setUrl(url);
            status.setMessage("Completed");
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (FilestoreException | UnsupportedAuthorizationTypeException ex) {
            status.setMessage("The file URL could not be retrieved: " + ex.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            result.setStatus(status);
            return result;
        }
    }

    @Override
    @WebResult(name = "listFilesForRunResult", targetNamespace = "")
    @RequestWrapper(localName = "listFilesForRun", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.ListFilesForRun")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/listFilesForRun")
    @ResponseWrapper(localName = "listFilesForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0_1/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.ListFilesForRunResponse")

    public ListFilesForRunResult listFilesForRun(@WebParam(name = "listFilesForRunRequest", targetNamespace = "") ListFilesForRunRequest listFilesForRunRequest) {

        ListFilesForRunResult result = new ListFilesForRunResult();
        MethodCallStatus status = new MethodCallStatus();

        try {
            List<FileIdentification> files = brokerService.listFilesForRun(listFilesForRunRequest.getRunId(),
                    getAuthentication());
            result.getFiles().addAll(files);
            status.setMessage("Completed");
            status.setStatus(MethodCallStatusEnum.COMPLETED);
            result.setStatus(status);
            return result;
        } catch (FilestoreException | UnsupportedAuthorizationTypeException ex) {
            status.setMessage("The list of files could not be retrieved: " + ex.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            result.setStatus(status);
            return result;
        }

    }

    @Override
    public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GetMembersOfCollectionResult getMembersOfCollection(GetMembersOfCollectionMessage getMembersOfCollectionMessage) {
        GetMembersOfCollectionResult result;
        try {
            result = brokerService.getMembersOfCollection(getMembersOfCollectionMessage.getCollectionUrn(),
                    getMembersOfCollectionMessage.getCollectionVersion(), getMembersOfCollectionMessage.isIncludeUnreleasedItems(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetMembersOfCollectionResult();
            result.setStatus(createStatus("Error calling getMembersOfCollection: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    @Override
    public GetReleaseVersionResult getLibraryItemReleaseVersion(GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
        GetReleaseVersionResult result;
        try {
            result = brokerService.getApprovedRevisionOfLibraryItem(getLibraryItemReleaseVersionMessage.getUrn(),
                    getAuthentication());
            result.setStatus(createStatus("Success", MethodCallStatusEnum.COMPLETED));
        } catch (LibraryServiceException | UnsupportedAuthorizationTypeException ex) {
            result = new GetReleaseVersionResult();
            result.setStatus(createStatus("Error calling getLibraryItemReleaseVersion: " + ex.getMessage(),
                    MethodCallStatusEnum.FAILED));
        }

        return result;
    }

    private Authentication getAuthentication() throws UnsupportedAuthorizationTypeException {
        String authorization = (String) PhaseInterceptorChain.getCurrentMessage().getExchange().get(HTTPHeaderInterceptor.AUTHORIZATION_EXCHANGE_PROPERTY);
        Authentication authentication = AuthorizationUtility.createAuthenticationFromAuthorizationHeader(authorization);
        return authentication;
    }

}
