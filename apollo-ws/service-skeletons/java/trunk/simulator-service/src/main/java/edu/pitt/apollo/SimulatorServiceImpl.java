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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
class SimulatorServiceImpl implements SimulatorServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatusResponse")
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
	@WebResult(name = "runSimulationsResult", targetNamespace = "")
	@RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulations")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/runSimulations")
	@ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulationsResponse")
	public RunSimulationsResult runSimulations(
			@WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
	@RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetPopulationAndEnvironmentCensus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getPopulationAndEnvironmentCensus")
	@ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetPopulationAndEnvironmentCensusResponse")
	public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
			@WebParam(name = "location", targetNamespace = "") String location) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
	@RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetScenarioLocationCodesSupportedBySimulator")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getScenarioLocationCodesSupportedBySimulator")
	@ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResponse")
	public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulationResponse")
	public void runSimulation(@WebParam(name = "simulationRunId", targetNamespace = "") BigInteger simulationRunId,
			@WebParam(name = "runSimulationMessage", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
		// TODO Auto-generated method stub
		
	}



}