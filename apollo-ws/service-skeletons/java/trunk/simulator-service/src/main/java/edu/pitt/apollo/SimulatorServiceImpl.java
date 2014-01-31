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
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.simulatorservice.v2_0.SimulatorServiceEI;
import edu.pitt.apollo.types.v2_0.BatchRunResult;
import edu.pitt.apollo.types.v2_0.BatchRunSimulationMessage;
import edu.pitt.apollo.types.v2_0.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.RunStatus;
import edu.pitt.apollo.types.v2_0.RunStatusEnum;
import edu.pitt.apollo.types.v2_0.SupportedPopulationLocation;



@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
class SimulatorServiceImpl implements SimulatorServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RunStatus rs = new RunStatus();
		rs.setMessage("hello from simulatorservice");
		rs.setStatus(RunStatusEnum.COMPLETED);
		return rs;
	}

	@Override
	@WebResult(name = "supportedPopluationLocations", targetNamespace = "")
	@RequestWrapper(localName = "getSupportedLocations", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetSupportedLocations")
	@WebMethod
	@ResponseWrapper(localName = "getSupportedLocationsResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetSupportedLocationsResponse")
	public List<SupportedPopulationLocation> getSupportedLocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runId", targetNamespace = "")
	@RequestWrapper(localName = "run", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.Run")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/run")
	@ResponseWrapper(localName = "runResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.RunResponse")
	public String run(
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") RunSimulationMessage runSimulationMessage) {
		
		return runSimulationMessage.getSimulatorIdentification()
				.getSoftwareDeveloper()
				+ "_"
				+ runSimulationMessage.getSimulatorIdentification()
						.getSoftwareName()
				+ "_"
				+ runSimulationMessage.getSimulatorIdentification().getSoftwareVersion() + "_17";
	}

	@Override
	@WebResult(name = "batchRun", targetNamespace = "")
	@RequestWrapper(localName = "batchRun", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.BatchRun")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/batchRun")
	@ResponseWrapper(localName = "batchRunResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.BatchRunResponse")
	public BatchRunResult batchRun(
			@WebParam(name = "batchRunRunSimulationMessage", targetNamespace = "") BatchRunSimulationMessage batchRunSimulationMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "configurationFile", targetNamespace = "")
	@RequestWrapper(localName = "getConfigurationFileForRun", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetConfigurationFileForRun")
	@WebMethod
	@ResponseWrapper(localName = "getConfigurationFileForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetConfigurationFileForRunResponse")
	public String getConfigurationFileForRun(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "populationAndEnvironmentCensus", targetNamespace = "")
	@RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetPopulationAndEnvironmentCensus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0/getPopulationAndEnvironmentCensus")
	@ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0/", className = "edu.pitt.apollo.service.simulatorservice.v2_0.GetPopulationAndEnvironmentCensusResponse")
	public PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensus(
			@WebParam(name = "location", targetNamespace = "") String location) {
		// TODO Auto-generated method stub
		return null;
	}


}