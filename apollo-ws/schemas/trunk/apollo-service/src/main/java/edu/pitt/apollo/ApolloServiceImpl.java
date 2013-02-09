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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebParam.Mode;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.VisualizerConfiguration;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", 
portName="ApolloServiceEndpoint",
serviceName="ApolloService", 
endpointInterface="edu.pitt.apollo.service.apolloservice.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

	@Override
	@RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/unRegisterService")
	@ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterServiceResponse")
	public void unRegisterService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord,
			@WebParam(mode = Mode.OUT, name = "unRegistrationSuccessful", targetNamespace = "") Holder<Boolean> unRegistrationSuccessful,
			@WebParam(mode = Mode.OUT, name = "message", targetNamespace = "") Holder<String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulationResponse")
	public String runSimulation(
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
		// TODO Auto-generated method stub
		return "This is not an error!";
	}

	@Override
	@WebResult(name = "serviceRecords", targetNamespace = "")
	@RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServices")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRegisteredServices")
	@ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServicesResponse")
	public List<ServiceRecord> getRegisteredServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		RunStatus rs = new RunStatus();
		rs.setMessage("Hello from apolloservice");
		rs.setStatus(RunStatusEnum.COMPLETED);
		return rs;
	}

	@Override
	@RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterService")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/registerService")
	@ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterServiceResponse")
	public void registerService(
			@WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord,
			@WebParam(mode = Mode.OUT, name = "registrationSuccessful", targetNamespace = "") Holder<Boolean> registrationSuccessful,
			@WebParam(mode = Mode.OUT, name = "message", targetNamespace = "") Holder<String> message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualization")
	@WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runVisualization")
	@ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualizationResponse")
	public String runVisualization(
			@WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}