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

import java.io.IOException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.seir.utils.RunUtils;
import edu.pitt.apollo.seir.utils.WorkerThread;
import edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.SupportedPopulationLocation;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
class SeirSimulatorServiceImpl implements SimulatorServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		try {
			return RunUtils.getStatus(runId);
		} catch (IOException e) {
			RunStatus rs = new RunStatus();
			rs.setMessage("Error getting runStatus from web service, error is: " + e.getMessage());
			rs.setStatus(RunStatusEnum.FAILED);
			return rs;
		}
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
			@WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
		try {
		

			SimulatorIdentification sid = simulatorConfiguration
					.getSimulatorIdentification();
			String runId = sid.getSimulatorDeveloper() + "_" + sid.getSimulatorName()
					+ "_" + sid.getSimulatorVersion() + "_" + RunUtils.getNextId();
			System.out.println("RunId is :" + runId);
			(new WorkerThread(simulatorConfiguration, runId)).start();

			return runId;
					

		} catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "blah.";

	}
	
	static {
		try {
			System.loadLibrary("seir2jni");
			System.out.println("Loaded seirjni in Apollo!");
		} catch (Exception e) {
			System.out.println("Error loading SEIR JNI: " + e.getMessage());
		}
	}

}