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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.apolloservice.methods.census.GetPopulationAndEnvironmentCensusMethod;
import edu.pitt.apollo.apolloservice.methods.census.GetScenarioLocationCodesSupportedBySimulatorMethod;
import edu.pitt.apollo.apolloservice.methods.content.GetConfigurationFileForSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.content.GetVisualizerOutputResourcesMethod;
import edu.pitt.apollo.apolloservice.methods.library.AddLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetUuidsForLibraryItemsCreatedSinceDateTimeMethod;
import edu.pitt.apollo.apolloservice.methods.library.GetUuidsForLibraryItemsGivenTypeMethod;
import edu.pitt.apollo.apolloservice.methods.library.RemoveLibraryItemMethod;
import edu.pitt.apollo.apolloservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunSimulationsMethod;
import edu.pitt.apollo.apolloservice.methods.services.GetRegisteredServicesMethod;
import edu.pitt.apollo.apolloservice.methods.services.RegisterServiceMethod;
import edu.pitt.apollo.apolloservice.methods.services.UnregisterServiceMethod;
import edu.pitt.apollo.service.apolloservice.v2_0_2.ApolloServiceEI;
import edu.pitt.apollo.types.v2_0_2.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_2.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_2.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_2.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.RunResult;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_2.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_2.RunSimulationsResult;
import edu.pitt.apollo.types.v2_0_2.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_2.ServiceRecord;
import edu.pitt.apollo.types.v2_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_2.SyntheticPopulationGenerationResult;
import edu.pitt.apollo.types.v2_0_2.TerminateRunRequest;
import edu.pitt.apollo.types.v2_0_2.TerminteRunResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v2.0.2", endpointInterface = "edu.pitt.apollo.service.apolloservice.v2_0_2.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.UnRegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/unRegisterService")
    @ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.UnRegisterServiceResponse")
    public MethodCallStatus unRegisterService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return UnregisterServiceMethod.unregisterService(serviceRegistrationRecord);
    }
    
    @Override
    @WebResult(name = "syntheticPopulationGenerationResult", targetNamespace = "")
    @RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSyntheticPopulationGeneration")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/runSyntheticPopulationGeneration")
    @ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSyntheticPopulationGenerationResponse")
    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(
            @WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @WebResult(name = "runSimulationsResult", targetNamespace = "")
    @RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSimulations")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/runSimulations")
    @ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSimulationsResponse")
    public RunSimulationsResult runSimulations(
            @WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
        return RunSimulationsMethod.runSimulations(runSimulationsMessage);
    }
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/registerService")
    @ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RegisterServiceResponse")
    public MethodCallStatus registerService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return RegisterServiceMethod.registerService(serviceRegistrationRecord);
    }
    
    @Override
    @WebResult(name = "getLibraryItemsResult", targetNamespace = "")
    @RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetUuidsForLibraryItemsGivenType")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getUuidsForLibraryItemsGivenType")
    @ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetUuidsForLibraryItemsGivenTypeResponse")
    public GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(
            @WebParam(name = "type", targetNamespace = "") String type) {
        return GetUuidsForLibraryItemsGivenTypeMethod.getUuidsForLibraryItemsGivenType(type);
    }
    
    @Override
    @WebResult(name = "addLibraryItemResult", targetNamespace = "")
    @RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.AddLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/addLibraryItem")
    @ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.AddLibraryItemResponse")
    public AddLibraryItemResult addLibraryItem(
            @WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
            @WebParam(name = "apolloIndexableItem", targetNamespace = "") ApolloIndexableItem apolloIndexableItem,
            @WebParam(name = "itemDescription", targetNamespace = "") String itemDescription,
            @WebParam(name = "itemSource", targetNamespace = "") String itemSource,
            @WebParam(name = "itemType", targetNamespace = "") String itemType,
            @WebParam(name = "itemIndexingLabels", targetNamespace = "") List<String> itemIndexingLabels) {
        return AddLibraryItemMethod.addLibraryItem(authentication, apolloIndexableItem, itemDescription, itemSource, itemType, itemIndexingLabels);
    }
    
    @Override
    @WebResult(name = "getConfigurationFileForSimulationResult", targetNamespace = "")
    @RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetConfigurationFileForSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getConfigurationFileForSimulation")
    @ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetConfigurationFileForSimulationResponse")
    public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        return GetConfigurationFileForSimulationMethod.getConfigurationFileForSimulation(runIdentification);
    }
    
    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        return GetRunStatusMethod.getRunStatus(runIdentification);
    }
    
    @Override
    @WebResult(name = "getLibraryItemResult", targetNamespace = "")
    @RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getLibraryItem")
    @ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetLibraryItemResponse")
    public GetLibraryItemResult getLibraryItem(
            @WebParam(name = "uuid", targetNamespace = "") String uuid) {
        return GetLibraryItemMethod.getLibraryItem(uuid);
    }
    
    @Override
    @WebResult(name = "serviceRecords", targetNamespace = "")
    @RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetRegisteredServices")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getRegisteredServices")
    @ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetRegisteredServicesResponse")
    public List<ServiceRecord> getRegisteredServices() {
        return GetRegisteredServicesMethod.getRegisteredServices();
    }
    
    @Override
    @WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
    @RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetPopulationAndEnvironmentCensus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getPopulationAndEnvironmentCensus")
    @ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetPopulationAndEnvironmentCensusResponse")
    public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification,
            @WebParam(name = "location", targetNamespace = "") String location) {
        return GetPopulationAndEnvironmentCensusMethod.getPopulationAndEnvironmentCensus(simulatorIdentification, location);
    }
    
    @Override
    @WebResult(name = "visualizationResult", targetNamespace = "")
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunVisualizationResponse")
    public RunResult runVisualization(
            @WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
		RunMethod runMethod = new RunMethod(runVisualizationMessage.getAuthentication(), runVisualizationMessage.getVisualizerIdentification(), 
				runVisualizationMessage);
        return runMethod.run();
    }
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "removeLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RemoveLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/removeLibraryItem")
    @ResponseWrapper(localName = "removeLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RemoveLibraryItemResponse")
    public MethodCallStatus removeLibraryItem(
            @WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
            @WebParam(name = "uuid", targetNamespace = "") String uuid) {
        return RemoveLibraryItemMethod.removeLibraryItem(authentication, uuid);
    }
    
    @Override
    @WebResult(name = "getLibraryItemsResult", targetNamespace = "")
    @RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetUuidsForLibraryItemsCreatedSinceDateTime")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getUuidsForLibraryItemsCreatedSinceDateTime")
    @ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
    public GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(
            @WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {
        return GetUuidsForLibraryItemsCreatedSinceDateTimeMethod.getUuidsForLibraryItemsCreatedSinceDateTime(creationDateTime);
    }
    
    @Override
    @WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
    @RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetScenarioLocationCodesSupportedBySimulator")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/getScenarioLocationCodesSupportedBySimulator")
    @ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetScenarioLocationCodesSupportedBySimulatorResponse")
    public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification) {
        return GetScenarioLocationCodesSupportedBySimulatorMethod.getScenarioLocationCodesSupportedBySimulator(simulatorIdentification);
    }
    
    @Override
    @WebResult(name = "simulationRunId", targetNamespace = "")
    @RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/runSimulation")
    @ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.RunSimulationResponse")
    public RunResult runSimulation(
            @WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
		RunMethod runMethod = new RunMethod(runSimulationMessage.getAuthentication(), runSimulationMessage.getSimulatorIdentification(), 
				runSimulationMessage);
		
        return runMethod.run();
    }
    
    @Override
    @WebResult(name = "getVisualizerOutputResourcesResult", targetNamespace = "")
    @RequestWrapper(localName = "getVisualizerOutputResources", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetVisualizerOutputResources")
    @WebMethod
    @ResponseWrapper(localName = "getVisualizerOutputResourcesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", className = "edu.pitt.apollo.service.apolloservice.v2_0_2.GetVisualizerOutputResourcesResponse")
    public GetVisualizerOutputResourcesResult getVisualizerOutputResources(
            @WebParam(name = "runIdentification", targetNamespace = "") BigInteger runIdentification) {
        return GetVisualizerOutputResourcesMethod.getVisualizerOutputResources(runIdentification);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // db4o.close();
    }

	@Override
	public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest) {
		// TODO Auto-generated method stub
		return null;
	}




}
