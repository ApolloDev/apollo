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
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.codehaus.jackson.map.ObjectMapper;

import edu.pitt.apollo.seir.utils.RunUtils;
import edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI;
import edu.pitt.apollo.types.BatchRunSimulatorConfiguration;
import edu.pitt.apollo.types.BatchRunSimulatorResult;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SoftwareIdentification;
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
			rs.setMessage("Error getting runStatus from web service, error is: "
					+ e.getMessage());
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

			//check to see if the user would be happy with a cached result
			//if so, check to see if there is a cached run  by:
			//		md5 hashing the sc
			//		querying gthe database for the sc
			//String runId = DbUtils.isRunCached(simulatorConfiguration);	
			//		if run is cached return the old run id for that run and exit
			
			//if the run is not cached, or the user doesn't want a cache, then just run
			
			MessageDigest md = MessageDigest.getInstance("MD5");
			
//			XStream xStream = new XStream(new DomDriver());
//			String xml = xStream.toXML(obj, out)toXML(simulatorConfiguration);

			ObjectMapper mapper = new ObjectMapper();
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, simulatorConfiguration);
			String userDataJSON = strWriter.toString();
			
			
			
			// System.out.println(xStream.toXML(person));
			// String original = args[0];
			// MessageDigest md = MessageDigest.getInstance("MD5");
			// md.update(original.getBytes());
			// byte[] digest = md.digest();
			// StringBuffer sb = new StringBuffer();
			// for (byte b : digest) {
			// sb.append(Integer.toHexString((int) (b & 0xff)));
			// }
			//
			//

//			SimulatorIdentification sid = simulatorConfiguration
//					.getSimulatorIdentification();
//			String runId = sid.getSimulatorDeveloper() + "_"
//					+ sid.getSimulatorName() + "_" + sid.getSimulatorVersion()
//					+ "_" + RunUtils.getNextId();
//			System.out.println("RunId is :" + runId);
//			(new WorkerThread(simulatorConfiguration, runId)).start();

		//	return runId;
			return null;

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

	@Override
	@WebResult(name = "batchRun", targetNamespace = "")
	@RequestWrapper(localName = "batchRun", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.BatchRun")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/batchRun")
	@ResponseWrapper(localName = "batchRunResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", className = "edu.pitt.apollo.service.simulatorservice.BatchRunResponse")
	public BatchRunSimulatorResult batchRun(
			@WebParam(name = "batchRunSimulatorConfiguration", targetNamespace = "") BatchRunSimulatorConfiguration batchRunSimulatorConfiguration) {
		
		SoftwareIdentification sid = batchRunSimulatorConfiguration.getSoftwareIdentification();
		
		String runId = sid.getSoftwareDeveloper() + "_"
				+ sid.getSoftwareName() + "_" + sid.getSoftwareVersion()
				+ "_" + RunUtils.getNextId();
		System.out.println("RunId is :" + runId);
		return null;
	}

}