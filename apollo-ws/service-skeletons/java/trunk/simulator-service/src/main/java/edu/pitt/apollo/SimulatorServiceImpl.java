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

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import edu.pitt.apollo.simulatorservice.queue.SimulatorServiceQueue;
import edu.pitt.apollo.simulatorservice.thread.SimulatorThread;
import edu.pitt.apollo.simulatorservice.util.RunUtils;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsResult;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
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

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService", endpointInterface = "edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI")
public abstract class SimulatorServiceImpl implements SimulatorServiceEI {

    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_201_WORK_DIR";

    protected static String APOLLO_DIR = "";
    protected static ApolloDbUtils dbUtils;
    protected static SoftwareIdentification translatorSoftwareId;

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(
            @WebParam(name = "runId", targetNamespace = "") BigInteger runId) {
        try {
            int runIdInt = runId.intValue();
            System.out.println("run directory: " + getRunDirectory(runIdInt));
            return RunUtils.getStatus(getRunDirectory(runIdInt), runIdInt);
        } catch (IOException e) {
            MethodCallStatus status = new MethodCallStatus();
            status.setMessage("Error getting runStatus from web service, error is: "
                    + e.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            return status;
        }
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
        System.out.println("running simulation");
        int runId = simulationRunId.intValue();
        try {
            // set the started file for the run
            System.out.println("creating run directory: " + getRunDirectory(runId));
            RunUtils.createRunDir(getRunDirectory(runId));
            RunUtils.setStatusFile(getRunDirectory(runId), MethodCallStatusEnum.QUEUED);
        } catch (IOException ex) {
            try {
                RunUtils.setError(getRunDirectory(runId), "IOException attempting to create started file for run "
                        + runId + ": " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + runId + ": " + ex1.getMessage());
            }
        }
        // create the run thread
        SimulatorThread worker = createSimulatorThread(runSimulationMessage, simulationRunId);
        System.out.println("Starting an AddSimulatorToQueueThread with run ID " + runId);
        SimulatorServiceQueue.startAddSimulatorToQueueThread(worker);
    }

    public static String getDatabasePropertiesFilename() {
        return APOLLO_DIR + DATABASE_PROPERTIES_FILENAME;
    }

    public static SoftwareIdentification getTranslatorSoftwareId() {
        return translatorSoftwareId;
    }

    protected abstract SimulatorThread createSimulatorThread(RunSimulationMessage message,
            BigInteger runId);

    protected abstract String getRunDirectory(int runId);

    static {

        Map<String, String> env = System.getenv();
        APOLLO_DIR = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
        if (APOLLO_DIR != null) {
            if (!APOLLO_DIR.endsWith(File.separator)) {
                APOLLO_DIR += File.separator;
            }
            System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
        } else {
            System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
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
            throw new RuntimeException("ClassNotFoundException attempting to load the translator software ID: "
                    + ex.getMessage());
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException attempting to load the translator software ID: " + ex.getMessage());
        }

        if (translatorSoftwareId == null) {
            System.out.println("Could not find translator in the list of registered services");
        }
    }

}
