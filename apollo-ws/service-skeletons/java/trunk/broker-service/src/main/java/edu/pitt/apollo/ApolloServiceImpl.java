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

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
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
import edu.pitt.apollo.apolloservice.methods.run.*;
import edu.pitt.apollo.apolloservice.methods.services.GetRegisteredServicesMethod;
import edu.pitt.apollo.apolloservice.methods.services.RegisterServiceMethod;
import edu.pitt.apollo.apolloservice.methods.services.UnregisterServiceMethod;
import edu.pitt.apollo.data_service_types.v3_0_0.*;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
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
import edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceEI;
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

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v3.0.0", endpointInterface = "edu.pitt.apollo.service.apolloservice.v3_0_0.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	private static final BigInteger NO_SIMULATION_GROUP_ID = null;
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
	@WebResult(name = "methodCallStatus", targetNamespace = "")
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RegisterServiceResponse")
	public MethodCallStatus registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
		return RegisterServiceMethod.registerService(serviceRegistrationRecord);
	}

	@Override
	@WebResult(name = "getConfigurationFileForSimulationResult", targetNamespace = "")
	@RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetConfigurationFileForSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getConfigurationFileForSimulation")
	@ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetConfigurationFileForSimulationResponse")
	public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
			@WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
		return GetConfigurationFileForSimulationMethod
				.getConfigurationFileForSimulation(runIdentification);
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(
			@WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
		return GetRunStatusMethod.getRunStatus(runIdentification);
	}

	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetRegisteredServicesResponse")
	public List<ServiceRecord> getRegisteredServices() {
		return GetRegisteredServicesMethod.getRegisteredServices();
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
	@WebResult(name = "visualizationResult", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunVisualizationResponse")
	public RunResult runVisualization(
			@WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {

			RunMethod runMethod = new RunMethodForSimulationAndVisualization(
					runVisualizationMessage.getAuthentication(),
					runVisualizationMessage.getVisualizerIdentification(),
					NO_SIMULATION_GROUP_ID,
					runVisualizationMessage);

			return (RunResult) runMethod.stageAndRun().getObjectToReturnFromBroker();
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
	@WebResult(name = "simulationRunId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.RunSimulationResponse")
	public RunResult runSimulation(
			@WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
		RunMethod runMethod = new RunMethodForSimulationAndVisualization(
				runSimulationMessage.getAuthentication(),
				runSimulationMessage.getSimulatorIdentification(),
				NO_SIMULATION_GROUP_ID,
				runSimulationMessage);

		return (RunResult) runMethod.stageAndRun().getObjectToReturnFromBroker();
	}

	@Override
	@WebResult(name = "getVisualizerOutputResourcesResult", targetNamespace = "")
	@RequestWrapper(localName = "getVisualizerOutputResources", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetVisualizerOutputResources")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/getVisualizerOutputResourcesResponse")
	@ResponseWrapper(localName = "getVisualizerOutputResourcesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v3_0_0/", className = "edu.pitt.apollo.service.apolloservice.v3_0_0.GetVisualizerOutputResourcesResponse")
	public GetVisualizerOutputResourcesResult getVisualizerOutputResources(
			@WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
		return GetVisualizerOutputResourcesMethod
				.getVisualizerOutputResources(runIdentification);
	}

	@Override
	public RunResult runSimulations(
			edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage runSimulationsMessage) {
		RunMethod runMethod = new BatchRunMethod(
				runSimulationsMessage.getAuthentication(),
				runSimulationsMessage.getSimulatorIdentification(),
				NO_SIMULATION_GROUP_ID,
				runSimulationsMessage);

		return (RunResult) runMethod.stageAndRun().getObjectToReturnFromBroker();
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// db4o.close();
	}

	@Override
	public TerminteRunResult terminateRun(
			TerminateRunRequest terminateRunRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetCommentsResult getCommentsForLibraryItem(
			GetCommentsMessage getCommentsForLibraryItemMessage) {
		return GetCommentsForLibraryItemMethod
				.getComments(getCommentsForLibraryItemMessage);
	}

	@Override
	public SetLibraryItemAsNotReleasedResult setLibraryItemAsNotReleased(
			SetLibraryItemAsNotReleasedMessage setLibraryItemAsNotReleasedMessage) {
		return SetLibraryItemAsNotReleasedMethod
				.setLibraryItemAsnotReleased(setLibraryItemAsNotReleasedMessage);
	}

	@Override
	public GetLibraryItemURNsResult getLibraryItemURNs(
			GetLibraryItemURNsMessage getLibraryItemURNsMessage) {
		return GetLibraryItemURNsMethod.getURNs(getLibraryItemURNsMessage);
	}

	@Override
	public ModifyGroupOwnershipResult removeGroupAccessToLibraryItem(
			ModifyGroupOwnershipMessage removeGroupAccessToLibraryItemMessage) {
		return RemoveGroupAccessToLibraryItemMethod
				.removeGroupAccess(removeGroupAccessToLibraryItemMessage);
	}

	@Override
	public AddReviewerCommentResult addReviewerCommentToLibraryItem(
			AddReviewerCommentMessage addReviewerCommentToLibraryItemMessage) {
		return AddReviewerCommentMethod
				.addReviewerComment(addReviewerCommentToLibraryItemMessage);
	}

	@Override
	public ModifyGroupOwnershipResult grantGroupAccessToLibraryItem(
			ModifyGroupOwnershipMessage grantGroupAccessToLibraryItemMessage) {
		return GrantGroupAccessToLibraryItemMethod
				.grantGroupAccess(grantGroupAccessToLibraryItemMessage);
	}

	@Override
	public GetChangeLogForLibraryItemsModifiedSinceDateTimeResult getChangeLogForLibraryItemsModifiedSinceDateTime(
			GetChangeLogForLibraryItemsModifiedSinceDateTimeMessage getChangeLogForLibraryItemsModifiedSinceDateTimeMessage) {
		return GetChangeLogForLibraryItemsModifiedSinceDateTimeMethod
				.getChangeLog(getChangeLogForLibraryItemsModifiedSinceDateTimeMessage);
	}

	@Override
	public GetLibraryItemContainerResult getLibraryItemContainer(
			GetLibraryItemContainerMessage getLibraryItemContainerMessage) {
		return GetLibraryItemContainerMethod
				.getLibraryItemContainer(getLibraryItemContainerMessage);
	}

	@Override
	public UpdateLibraryItemContainerResult updateLibraryItemContainer(
			UpdateLibraryItemContainerMessage updateLibraryItemContainerMessage) {
		return UpdateLibraryItemContainerMethod
				.updateLibraryItemContainer(updateLibraryItemContainerMessage);
	}

	@Override
	public SetReleaseVersionResult setReleaseVersionForLibraryItem(
			SetReleaseVersionMessage setReleaseVersionForLibraryItemMessage) {
		return SetReleaseVersionForLibraryItemMethod
				.setReleaseVersion(setReleaseVersionForLibraryItemMessage);
	}

	@Override
	public GetVersionsResult getVersionNumbersForLibraryItem(
			GetVersionsMessage getVersionNumbersForLibraryItemMessage) {
		return GetVersionNumbersForLibraryItemMethod
				.getVersions(getVersionNumbersForLibraryItemMessage);
	}

	@Override
	public QueryResult query(QueryMessage queryMessage) {
		return QueryMethod.query(queryMessage);
	}

	@Override
	public GetReleaseVersionResult getLibraryItemReleaseVersion(
			GetReleaseVersionMessage getLibraryItemReleaseVersionMessage) {
		return GetLibraryItemReleaseVersionMethod
				.getReleaseVersion(getLibraryItemReleaseVersionMessage);
	}

	@Override
	public ListOutputFilesForSoftwareResult listOutputFilesForSoftware(
			ListOutputFilesForSoftwareMessage listOutputFilesForSoftwareMessage) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public GetOutputFilesURLAsZipResult getOutputFilesURLAsZip(
			GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage) {
		RunMethod method = new RunMethodForDataService(
				getOutputFilesURLAsZipMessage.getAuthentication(),
				getOutputFilesURLAsZipMessage);
		return (GetOutputFilesURLAsZipResult) method.stageAndRun()
				.getObjectToReturnFromBroker();
	}

	@Override
	public GetOutputFilesURLsResult getOutputFilesURLs(
			GetOutputFilesURLsMessage getOutputFilesURLsMessage) {
		RunMethod method = new RunMethodForDataService(
				getOutputFilesURLsMessage.getAuthentication(),
				getOutputFilesURLsMessage);
		return (GetOutputFilesURLsResult) method.stageAndRun().getObjectToReturnFromBroker();
	}

	@Override
	public GetAllOutputFilesURLAsZipResult getAllOutputFilesURLAsZip(
			GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage) {
		RunMethod method = new RunMethodForDataService(
				getAllOutputFilesURLAsZipMessage.getAuthentication(),
				getAllOutputFilesURLAsZipMessage);
		return (GetAllOutputFilesURLAsZipResult) method.stageAndRun()
				.getObjectToReturnFromBroker();
	}

	@Override
	public AddLibraryItemContainerResult addLibraryItemContainer(
			AddLibraryItemContainerMessage addLibraryItemContainerMessage) {
		return AddLibraryItemContainerMethod
				.addLibraryItemContainer(addLibraryItemContainerMessage);

	}

}
