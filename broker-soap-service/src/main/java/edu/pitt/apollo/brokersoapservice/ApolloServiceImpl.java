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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.data_service_types.v4_0_1.*;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.LibraryServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.filestore_service_types.v4_0_1.FileIdentification;
import edu.pitt.apollo.filestore_service_types.v4_0_1.GetFileUrlRequest;
import edu.pitt.apollo.filestore_service_types.v4_0_1.GetFileUrlResult;
import edu.pitt.apollo.filestore_service_types.v4_0_1.ListFilesForRunRequest;
import edu.pitt.apollo.filestore_service_types.v4_0_1.ListFilesForRunResult;
import edu.pitt.apollo.library_service_types.v4_0_1.*;
import edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceEI;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.GetRegisteredServicesResult;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunResult;
import edu.pitt.apollo.services_common.v4_0_1.RunStatusRequest;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v4_0_1.TerminateRunRequest;
import edu.pitt.apollo.services_common.v4_0_1.TerminteRunResult;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.utilities.AuthorizationUtility;
import edu.pitt.apollo.exception.UnsupportedAuthorizationTypeException;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;
import java.util.List;

import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.apache.cxf.interceptor.InInterceptors (interceptors = {"edu.pitt.apollo.brokersoapservice.HTTPHeaderInterceptor" })
@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v4.0", endpointInterface = "edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceEI")
public class ApolloServiceImpl implements ApolloServiceEI {

	//TODO: Implement library methods?
	public ApolloServiceImpl() {

	}

	Logger logger = LoggerFactory.getLogger(ApolloServiceImpl.class);
	private static final BrokerServiceImpl brokerService = new BrokerServiceImpl();



	@Override
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.UnRegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/unRegisterService")
	@ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.UnRegisterServiceResponse")
	public MethodCallStatus unRegisterService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public GetSoftwareIdentificationForRunResult getSoftwareIdentificationForRun(GetSoftwareIdentificationForRunMessage getSoftwareIdentificationForRunMessage) {
		return null;
	}


	@Override
	public RunResult runSimulations(
			edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage runSimulationsMessage) {
        try {
            return brokerService.runSimulations(runSimulationsMessage, getAuthetication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
	}

	@Override
	public UpdateLibraryItemContainerResult updateLibraryItemContainer(UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
		return null;
	}

	@Override
	public SetReleaseVersionResult setReleaseVersionForLibraryItem(SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
		return null;
	}

	@Override
	public GetCommentsResult getCommentsForLibraryItem(GetCommentsMessage getCommentsForLibraryItemMessage) {
		return null;
	}

	@Override
	public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
		return null;
	}

	@Override
	public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
		return null;
	}

	@Override
	public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
		return null;
	}

	@Override
	public QueryResult query(QueryMessage queryMessage) {
		return null;
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItemContainer(GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		return null;
	}

	@Override
	public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
		return null;
	}

	@Override
	public AddLibraryItemContainerResult addLibraryItemContainer(AddLibraryItemContainerMessage addLibraryItemContainerMessage) {
		return null;
	}

	@Override
	public GetRevisionsResult getVersionNumbersForLibraryItem(GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
		return null;
	}

	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRegisteredServicesResponse")
	public GetRegisteredServicesResult getRegisteredServices() {

        GetRegisteredServicesResult result = new GetRegisteredServicesResult();
		try {
			result.getServiceRecords().addAll(brokerService.getListOfRegisteredSoftwareRecords(getAuthetication()));
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
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RegisterServiceResponse")
	public MethodCallStatus registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}

//	@Override
//	public TerminteRunResult terminateRun(
//			TerminateRunRequest terminateRunRequest) {
//		// TODO Auto-generated method stub
//		return null;
//	}



	@Override
	@WebResult(name = "simulationRunId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunSimulationResponse")
	public RunResult runSimulation(
			@WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
        try {
            return brokerService.runSimulation(runSimulationMessage, getAuthetication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
	}

	@Override
	public AddReviewerCommentResult addReviewerCommentToLibraryItem(AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
		return null;
	}


	@Override
	public GetLibraryItemURNsResult getLibraryItemURNs(GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
		return null;
	}

	@Override
	@WebResult(name = "visualizationResult", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.RunVisualizationResponse")
	public RunResult runVisualization(
			@WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
        try {
            return brokerService.runVisualization(runVisualizationMessage, getAuthetication());
        } catch (UnsupportedAuthorizationTypeException ex) {
            RunResult result = new RunResult();
            result.setMethodCallStatus(createStatus("UnsupportedAuthorizationTypeException: " + ex.getMessage(), MethodCallStatusEnum.FAILED));
            return result;
        }
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(
			@WebParam(name = "runStatusRequest", targetNamespace = "") RunStatusRequest runStatusRequest) {
		try {
			return brokerService.getRunStatus(runStatusRequest.getRunIdentification(), getAuthetication());
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
	@RequestWrapper(localName = "getFileUrl", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetFileUrl")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/listFilesForRun")
	@ResponseWrapper(localName = "getFileUrlResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.GetFileUrlResponse")

	public GetFileUrlResult getFileUrl(GetFileUrlRequest getFileUrlRequest) {
		GetFileUrlResult result = new GetFileUrlResult();
		MethodCallStatus status = new MethodCallStatus();
		try {
			String url = brokerService.getUrlOfFile(getFileUrlRequest.getRunId(),
					getFileUrlRequest.getFileIdentification().getLabel(),
					getFileUrlRequest.getFileIdentification().getFormat(),
					getFileUrlRequest.getFileIdentification().getType(),
					getAuthetication());
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
	@RequestWrapper(localName = "listFilesForRun", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.ListFilesForRun")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v4_0/listFilesForRun")
	@ResponseWrapper(localName = "listFilesForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v4_0/", className = "edu.pitt.apollo.service.apolloservice.v4_0_1.ListFilesForRunResponse")

	public ListFilesForRunResult listFilesForRun(@WebParam(name = "listFilesForRunRequest", targetNamespace = "")ListFilesForRunRequest listFilesForRunRequest) {

		ListFilesForRunResult result = new ListFilesForRunResult();
		MethodCallStatus status = new MethodCallStatus();

		try {
			List<FileIdentification> files = brokerService.listFilesForRun(listFilesForRunRequest.getRunId(),
					getAuthetication());
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
	public GetReleaseVersionResult getLibraryItemReleaseVersion(GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
		return null;
	}

	private Authentication getAuthetication() throws UnsupportedAuthorizationTypeException {
        String authorization = (String) PhaseInterceptorChain.getCurrentMessage().getExchange().get(HTTPHeaderInterceptor.AUTHORIZATION_EXCHANGE_PROPERTY);
        Authentication authentication = AuthorizationUtility.createAuthenticationFromAuthorizationHeader(authorization);
        return authentication;
    }

}
