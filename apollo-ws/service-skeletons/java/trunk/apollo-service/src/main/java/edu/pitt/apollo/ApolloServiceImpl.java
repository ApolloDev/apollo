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
import edu.pitt.apollo.apolloservice.methods.run.RunSimulationMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunSimulationsMethod;
import edu.pitt.apollo.apolloservice.methods.run.RunVisualizationMethod;
import edu.pitt.apollo.apolloservice.methods.services.GetRegisteredServicesMethod;
import edu.pitt.apollo.apolloservice.methods.services.RegisterServiceMethod;
import edu.pitt.apollo.apolloservice.methods.services.UnregisterServiceMethod;
import java.math.BigInteger;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemUuidsResult;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsResult;
import edu.pitt.apollo.types.v2_0_1.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_1.ServiceRecord;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.SyntheticPopulationGenerationResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService_v2.0.1", endpointInterface = "edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.UnRegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/unRegisterService")
    @ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.UnRegisterServiceResponse")
    public MethodCallStatus unRegisterService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return UnregisterServiceMethod.unregisterService(serviceRegistrationRecord);
    }
    
    @Override
    @WebResult(name = "syntheticPopulationGenerationResult", targetNamespace = "")
    @RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSyntheticPopulationGeneration")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/runSyntheticPopulationGeneration")
    @ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSyntheticPopulationGenerationResponse")
    public SyntheticPopulationGenerationResult runSyntheticPopulationGeneration(
            @WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    @WebResult(name = "runSimulationsResult", targetNamespace = "")
    @RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulations")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/runSimulations")
    @ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulationsResponse")
    public RunSimulationsResult runSimulations(
            @WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
        return RunSimulationsMethod.runSimulations(runSimulationsMessage);
    }
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/registerService")
    @ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RegisterServiceResponse")
    public MethodCallStatus registerService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        return RegisterServiceMethod.registerService(serviceRegistrationRecord);
    }
    
    @Override
    @WebResult(name = "getLibraryItemsResult", targetNamespace = "")
    @RequestWrapper(localName = "getUuidsForLibraryItemsGivenType", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetUuidsForLibraryItemsGivenType")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getUuidsForLibraryItemsGivenType")
    @ResponseWrapper(localName = "getUuidsForLibraryItemsGivenTypeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetUuidsForLibraryItemsGivenTypeResponse")
    public GetLibraryItemUuidsResult getUuidsForLibraryItemsGivenType(
            @WebParam(name = "type", targetNamespace = "") String type) {
        return GetUuidsForLibraryItemsGivenTypeMethod.getUuidsForLibraryItemsGivenType(type);
    }
    
    @Override
    @WebResult(name = "addLibraryItemResult", targetNamespace = "")
    @RequestWrapper(localName = "addLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.AddLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/addLibraryItem")
    @ResponseWrapper(localName = "addLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.AddLibraryItemResponse")
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
    @RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetConfigurationFileForSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getConfigurationFileForSimulation")
    @ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetConfigurationFileForSimulationResponse")
    public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
            @WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {
        return GetConfigurationFileForSimulationMethod.getConfigurationFileForSimulation(runAndSoftwareIdentification);
    }
    
    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(
            @WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {
        return GetRunStatusMethod.getRunStatus(runAndSoftwareIdentification);
    }
    
    @Override
    @WebResult(name = "getLibraryItemResult", targetNamespace = "")
    @RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getLibraryItem")
    @ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetLibraryItemResponse")
    public GetLibraryItemResult getLibraryItem(
            @WebParam(name = "uuid", targetNamespace = "") String uuid) {
        return GetLibraryItemMethod.getLibraryItem(uuid);
    }
    
    @Override
    @WebResult(name = "serviceRecords", targetNamespace = "")
    @RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetRegisteredServices")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getRegisteredServices")
    @ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetRegisteredServicesResponse")
    public List<ServiceRecord> getRegisteredServices() {
        return GetRegisteredServicesMethod.getRegisteredServices();
    }
    
    @Override
    @WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
    @RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetPopulationAndEnvironmentCensus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getPopulationAndEnvironmentCensus")
    @ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetPopulationAndEnvironmentCensusResponse")
    public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification,
            @WebParam(name = "location", targetNamespace = "") String location) {
        return GetPopulationAndEnvironmentCensusMethod.getPopulationAndEnvironmentCensus(simulatorIdentification, location);
    }
    
    @Override
    @WebResult(name = "visualizationResult", targetNamespace = "")
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunVisualizationResponse")
    public RunVisualizationResult runVisualization(
            @WebParam(name = "runVisualizationMessage", targetNamespace = "") RunVisualizationMessage runVisualizationMessage) {
        return RunVisualizationMethod.runVisualization(runVisualizationMessage);
    }
    
    @Override
    @WebResult(name = "methodCallStatus", targetNamespace = "")
    @RequestWrapper(localName = "removeLibraryItem", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RemoveLibraryItem")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/removeLibraryItem")
    @ResponseWrapper(localName = "removeLibraryItemResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RemoveLibraryItemResponse")
    public MethodCallStatus removeLibraryItem(
            @WebParam(name = "authentication", targetNamespace = "") Authentication authentication,
            @WebParam(name = "uuid", targetNamespace = "") String uuid) {
        return RemoveLibraryItemMethod.removeLibraryItem(authentication, uuid);
    }
    
    @Override
    @WebResult(name = "getLibraryItemsResult", targetNamespace = "")
    @RequestWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTime", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetUuidsForLibraryItemsCreatedSinceDateTime")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getUuidsForLibraryItemsCreatedSinceDateTime")
    @ResponseWrapper(localName = "getUuidsForLibraryItemsCreatedSinceDateTimeResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetUuidsForLibraryItemsCreatedSinceDateTimeResponse")
    public GetLibraryItemUuidsResult getUuidsForLibraryItemsCreatedSinceDateTime(
            @WebParam(name = "creationDateTime", targetNamespace = "") XMLGregorianCalendar creationDateTime) {
        return GetUuidsForLibraryItemsCreatedSinceDateTimeMethod.getUuidsForLibraryItemsCreatedSinceDateTime(creationDateTime);
    }
    
    @Override
    @WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
    @RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetScenarioLocationCodesSupportedBySimulator")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/getScenarioLocationCodesSupportedBySimulator")
    @ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResponse")
    public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator(
            @WebParam(name = "simulatorIdentification", targetNamespace = "") SoftwareIdentification simulatorIdentification) {
        return GetScenarioLocationCodesSupportedBySimulatorMethod.getScenarioLocationCodesSupportedBySimulator(simulatorIdentification);
    }
    
    @Override
    @WebResult(name = "simulationRunId", targetNamespace = "")
    @RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/runSimulation")
    @ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.RunSimulationResponse")
    public BigInteger runSimulation(
            @WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
        return RunSimulationMethod.runSimulation(runSimulationMessage);
    }
    
    @Override
    @WebResult(name = "getVisualizerOutputResourcesResult", targetNamespace = "")
    @RequestWrapper(localName = "getVisualizerOutputResources", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetVisualizerOutputResources")
    @WebMethod
    @ResponseWrapper(localName = "getVisualizerOutputResourcesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", className = "edu.pitt.apollo.service.apolloservice.v2_0_1.GetVisualizerOutputResourcesResponse")
    public GetVisualizerOutputResourcesResult getVisualizerOutputResources(
            @WebParam(name = "runId", targetNamespace = "") RunAndSoftwareIdentification runId) {
        return GetVisualizerOutputResourcesMethod.getVisualizerOutputResources(runId);
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        // db4o.close();
    }
}
