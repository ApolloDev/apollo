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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.simulatorservice.v2_0.SimulatorServiceEI;
import edu.pitt.apollo.types.v2_0.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_0.GetLocationsSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0.RunSimulationsResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
class SimulatorServiceImpl implements SimulatorServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MethodCallStatus rs = new MethodCallStatus();
		rs.setMessage("hello from simulatorservice");
		rs.setStatus(MethodCallStatusEnum.COMPLETED);
		return rs;
	}

	@Override
	@WebResult(name = "configurationFile", targetNamespace = "")
	@RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetConfigurationFileForSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/getConfigurationFileForSimulation")
	@ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetConfigurationFileForSimulationResponse")
	public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runSimulationsResult", targetNamespace = "")
	@RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.RunSimulations")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/runSimulations")
	@ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.RunSimulationsResponse")
	public RunSimulationsResult runSimulations(
			@WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
	@RequestWrapper(localName = "getLocationsSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetLocationsSupportedBySimulator")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/getLocationsSupportedBySimulator")
	@ResponseWrapper(localName = "getLocationsSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetLocationsSupportedBySimulatorResponse")
	public GetLocationsSupportedBySimulatorResult getLocationsSupportedBySimulator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
	@RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetPopulationAndEnvironmentCensus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/getPopulationAndEnvironmentCensus")
	@ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetPopulationAndEnvironmentCensusResponse")
	public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
			@WebParam(name = "location", targetNamespace = "") String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.RunSimulationResponse")
	public String runSimulation(
			@WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
		return runSimulationMessage.getSimulatorIdentification()
				.getSoftwareDeveloper()
				+ "_"
				+ runSimulationMessage.getSimulatorIdentification()
						.getSoftwareName()
				+ "_"
				+ runSimulationMessage.getSimulatorIdentification()
						.getSoftwareVersion() + "_17";
	}

}