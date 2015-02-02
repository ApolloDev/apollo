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

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.simulator_service_types.v3_0_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulatorservice.thread.SimulatorThread;
import edu.pitt.apollo.simulatorservice.util.RunUtils;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
public abstract class SimulatorServiceImpl implements SimulatorServiceEI {

	static Logger logger = LoggerFactory.getLogger(SimulatorServiceImpl.class);

	private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";

	protected static String APOLLO_DIR = "";
	protected static ApolloDbUtils dbUtils;
	protected static SoftwareIdentification translatorSoftwareId;
	protected static final ApolloServiceQueue serviceQueue = new ApolloServiceQueue();

	@Override
	@RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.RunSimulation")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/runSimulation")
	@ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.RunSimulationResponse")
	public MethodCallStatus runSimulation(@WebParam(name = "simulationRunId", targetNamespace = "") BigInteger simulationRunId) {
		System.out.println("running simulation");
		int runId = simulationRunId.intValue();
//        try {
		// set the started file for the run
//            System.out.println("creating run directory: " + getRunDirectory(runId));
		RunUtils.updateStatus(dbUtils, simulationRunId, MethodCallStatusEnum.QUEUED, "The simulator run is queued");
//        } catch (IOException ex) {
//            try {
//                RunUtils.updateStatus(dbUtils, MethodCallStatusEnum.FAILED, "IOException attempting to create started file for run "
//                        + runId + ": " + ex.getMessage());
//            } catch (IOException ex1) {
//                System.err.println("IOException attempting to create error file for run " + runId + ": " + ex1.getMessage());
//            }
//        }
		// create the run thread
		SimulatorThread worker = createSimulatorThread(simulationRunId);
		System.out.println("Starting an AddSimulatorToQueueThread with run ID " + runId);
//        SimulatorServiceQueue.startAddSimulatorToQueueThread(worker);
		MethodCallStatus status = serviceQueue.addThreadToQueueAndRun(worker);
		if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			RunUtils.updateStatus(dbUtils, simulationRunId, status.getStatus(), status.getMessage());
		}
		return null;
	}

	public static String getDatabasePropertiesFilename() {
		return APOLLO_DIR + DATABASE_PROPERTIES_FILENAME;
	}

	@Override
	@WebResult(name = "getPopulationAndEnvironmentCensusResult", targetNamespace = "")
	@RequestWrapper(localName = "getPopulationAndEnvironmentCensus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.GetPopulationAndEnvironmentCensus")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/getPopulationAndEnvironmentCensus")
	@ResponseWrapper(localName = "getPopulationAndEnvironmentCensusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.GetPopulationAndEnvironmentCensusResponse")
	public GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensus(
			@WebParam(name = "location", targetNamespace = "") String location) {
		// TODO Auto-generated method stub
		return null;
	}

	public static SoftwareIdentification getTranslatorSoftwareId() {
		return translatorSoftwareId;
	}

	protected abstract SimulatorThread createSimulatorThread(BigInteger runId);

	@Override
	@WebResult(name = "getLocationsSupportedBySimulatorResult", targetNamespace = "")
	@RequestWrapper(localName = "getScenarioLocationCodesSupportedBySimulator", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.GetScenarioLocationCodesSupportedBySimulator")
	@WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/getScenarioLocationCodesSupportedBySimulator")
	@ResponseWrapper(localName = "getScenarioLocationCodesSupportedBySimulatorResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v3_0_0/", className = "edu.pitt.apollo.service.simulatorservice.v3_0_0.GetScenarioLocationCodesSupportedBySimulatorResponse")
	public GetScenarioLocationCodesSupportedBySimulatorResult getScenarioLocationCodesSupportedBySimulator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TerminteRunResult terminateRun(TerminateRunRequest terminateRunRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	static {

	// protected abstract String getRunDirectory(int runId);
		Map<String, String> env = System.getenv();
		APOLLO_DIR = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (APOLLO_DIR != null) {
			if (!APOLLO_DIR.endsWith(File.separator)) {
				APOLLO_DIR += File.separator;
			}
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
		} else {
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
			APOLLO_DIR = "";
		}
		try {
			dbUtils = new ApolloDbUtils(new File(getDatabasePropertiesFilename()));
		} catch (IOException ex) {
			System.out.println("Error creating ApoloDbUtils when initializing SEIR web service: " + ex.getMessage());
		}

		try {
			Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
			for (Integer id : softwareIdMap.keySet()) {
				SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
				if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
					translatorSoftwareId = softwareIdMap.get(id).getSoftwareIdentification();
					break;
				}
			}

		} catch (ClassNotFoundException ex) {
			throw new ExceptionInInitializerError("ClassNotFoundException attempting to load the translator software ID: "
					+ ex.getMessage());
		} catch (SQLException ex) {
			throw new ExceptionInInitializerError("SQLException attempting to load the translator software ID: " + ex.getMessage());
		}

		if (translatorSoftwareId == null) {
			System.out.println("Could not find translator in the list of registered services");
		}
	}

}
