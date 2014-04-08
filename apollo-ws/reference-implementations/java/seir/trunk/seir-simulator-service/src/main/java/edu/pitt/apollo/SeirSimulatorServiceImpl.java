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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;



import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.seir.utils.QueueThread;
import edu.pitt.apollo.seir.utils.RunUtils;
import edu.pitt.apollo.seir.utils.SimulatorThread;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import edu.pitt.apollo.types.v2_0_1.GetConfigurationFileForSimulationResult;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsMessage;
import edu.pitt.apollo.types.v2_0_1.RunSimulationsResult;
import java.io.File;
import java.util.Map;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", portName = "SimulatorServiceEndpoint", serviceName = "SimulatorService_v2.0.1", endpointInterface = "edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI")
public class SeirSimulatorServiceImpl implements SimulatorServiceEI {

    // queue for simulator threads
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    public static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_201_WORK_DIR";
    private static final String SEIR_OUTPUT_DIRECTORY_NAME = "seir-output";
    private static final int MAX_NUM_SIMULATOR_THREADS = 100;
    private static final int MAX_QUEUE_SIZE = 200;
//    private static int currentRunIdNumber;
    private static int numRunningSimulatorThreads;
//    private static Thread storeThread;
    private static Queue<Thread> simulatorThreadQueue;
    private static List<Integer> queuedThreads = new ArrayList<Integer>();
    private static String APOLLO_DIR = "";
    // executor for the simulator threads
    // private static ExecutorService simulatorExecutor =
    // Executors.newFixedThreadPool(5);
    // private static ExecutorService batchExecutor =
    // Executors.newFixedThreadPool(1);
    // this map will associate the md5 sim config hash with a runID string more
    // easily readable
    // private static Map<String, String> md5RunIdMap;

    static {
        // create and load the runID map

        // try {
        // md5RunIdMap = DbUtils.getStoredRunHashes();
        // } catch (SQLException e) {
        // System.err.println("Fatal Error enountered retrieving stored run hashes. Specific error message was:\n"
        // + e.getMessage());
        // e.printStackTrace();
        // System.exit(-1);
        // } catch (ClassNotFoundException e) {
        // System.err.println("Fatal Error enountered retrieving stored run hashes. Specific error message was:\n"
        // + e.getMessage());
        // e.printStackTrace();
        // System.exit(-1);
        // }
        // initialize simulator thread queue
        simulatorThreadQueue = new LinkedList<Thread>();
        numRunningSimulatorThreads = 0;

//        try {
//            currentRunIdNumber = Integer.parseInt(RunUtils.getNextId());
//            storeThread = new RunIdStoreThread(false);
//            storeThread.start();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//
//            public void run() {
//                // what you want to do
//                try {
//                    RunUtils.writeCurrentId(currentRunIdNumber);
//                } catch (IOException ex) {
//                    System.err.println("IO exception writing current ID to file");
//                }
//            }
//        }));

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

    }

    public String getRunDirectory(int runId) {
        return APOLLO_DIR + File.separator + SEIR_OUTPUT_DIRECTORY_NAME + File.separator + Long.valueOf(runId) + File.separator;
    }

    public static String getDatabasePropertiesFilename() {
        return APOLLO_DIR + DATABASE_PROPERTIES_FILENAME;
    }

    private static synchronized void addRunToQueuedList(int runId) {
        if (!queuedThreads.contains(runId)) { // only need to add it if it is
            // not already in the list
            queuedThreads.add(runId);
        }
    }

    public static synchronized void removeRunFromQueuedList(int runId) {
        queuedThreads.remove(runId);
    }

    public static synchronized boolean isRunQueued(int runId) {
        return queuedThreads.contains(runId);
    }

    public static synchronized void simulatorRunFinished() {
        numRunningSimulatorThreads--;
    }

//    public static synchronized int incrementRunId() {
//
//        currentRunIdNumber++;
//        return currentRunIdNumber;
//    }

//    public static synchronized int getCurrentRunId() {
//        return currentRunIdNumber;
//    }

    public static synchronized boolean addSimulatorThread(Thread runnable) {

        // System.out.println(System.currentTimeMillis() +
        // "     num threads running: " + numRunningSimulatorThreads + "    " +
        // "queue size: " + simulatorThreadQueue.size());

        if (simulatorThreadQueue.size() < MAX_QUEUE_SIZE) {
            simulatorThreadQueue.add(runnable);
            // if (simulatorThreadQueue.size() == MAX_QUEUE_SIZE) {
            // System.out.println("running sim thread");
            runSimulatorThreads(); // try to run a new thread
            // }

            // System.out.println(System.currentTimeMillis() +
            // "     queue size: " + simulatorThreadQueue.size());
            // System.out.flush();
            return true;
        } else {
            // System.out.println(System.currentTimeMillis() +
            // "     queue size: " + simulatorThreadQueue.size());
            return false;
        }
    }

    public static synchronized void runSimulatorThreads() {
        // start as many runs as possible
        while (numRunningSimulatorThreads < MAX_NUM_SIMULATOR_THREADS
                && simulatorThreadQueue.size() > 0) {
            // if (simulatorThreadQueue.size() > 0 && numRunningSimulatorThreads
            // < MAX_NUM_SIMULATOR_THREADS) {
            Thread thread = simulatorThreadQueue.poll();
            numRunningSimulatorThreads++;
            // System.out.println("starting run");
            thread.start();
            // }
        }

        // System.out.println("num threads running: " +
        // numRunningSimulatorThreads + "    " + "queue size: " +
        // simulatorThreadQueue.size());
    }

//    public static String formatJSONString(String jsonString) {
//
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(jsonString);
//        String prettyJsonString = gson.toJson(je);
//        return prettyJsonString;
//    }

    // public static ByteArrayOutputStream getJSONBytes(
    // SimulatorConfiguration simConfig) {
    // try {
    //
    // ObjectMapper mapper = new ObjectMapper();
    // mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
    // mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // mapper.writeValue(baos, simConfig);
    //
    // return baos;
    // } catch (IOException ex) {
    // System.err.println("IO Exception JSON encoding and getting string from SimulatorConfiguration");
    // return null;
    // }
    // }
//    public static String getJSONString(SimulatorConfiguration simConfig) {
//        try {
//
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
//            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            mapper.writeValue(baos, simConfig);
//
//            return baos.toString();
//        } catch (IOException ex) {
//            System.err.println("IO Exception JSON encoding and getting bytes from SimulatorConfiguration");
//            return null;
//        }
//    }

//    public static synchronized RunIdProperties getOrAddRunId(
//            String simConfigHash, SoftwareIdentification sid) {
//
//        // no longer using cache since it is implemented in apollo service
//
//        RunIdProperties runIdProps = new RunIdProperties();
//
//        String newRunId = sid.getSoftwareDeveloper() + "_"
//                + sid.getSoftwareName() + "_" + sid.getSoftwareVersion() + "_"
//                + incrementRunId();
//        runIdProps.setRunId(newRunId);
//        runIdProps.setRunIdInDatabase(false);
//        System.out.println("New run ID created: " + runIdProps.getRunId());
//        return runIdProps;
//
//    }

    public static void main(String[] args) {
    }

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetRunStatusResponse")
    public MethodCallStatus getRunStatus(@WebParam(name = "runId", targetNamespace = "") String runId) {
        try {
            int runIdInt = Integer.parseInt(runId);
            return RunUtils.getStatus(getRunDirectory(runIdInt), runIdInt);
        } catch (IOException e) {
            MethodCallStatus status = new MethodCallStatus();
            status.setMessage("Error getting runStatus from web service, error is: "
                    + e.getMessage());
            status.setStatus(MethodCallStatusEnum.FAILED);
            return status;
        }
    }

//    @Override
//    @WebResult(name = "configurationFile", targetNamespace = "")
//    @RequestWrapper(localName = "getConfigurationFileForSimulation", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetConfigurationFileForSimulation")
//    @WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/getConfigurationFileForSimulation")
//    @ResponseWrapper(localName = "getConfigurationFileForSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.GetConfigurationFileForSimulationResponse")
//    public GetConfigurationFileForSimulationResult getConfigurationFileForSimulation(
//            @WebParam(name = "runId", targetNamespace = "") String runId) {
//        // TODO Auto-generated method stub
//        return null;
//    }

    @Override
    @WebResult(name = "runSimulationsResult", targetNamespace = "")
    @RequestWrapper(localName = "runSimulations", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulations")
    @WebMethod(action = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/runSimulations")
    @ResponseWrapper(localName = "runSimulationsResponse", targetNamespace = "http://service.apollo.pitt.edu/simulatorservice/v2_0_1/", className = "edu.pitt.apollo.service.simulatorservice.v2_0_1.RunSimulationsResponse")
    public RunSimulationsResult runSimulations(
            @WebParam(name = "runSimulationsMessage", targetNamespace = "") RunSimulationsMessage runSimulationsMessage) {
//        SoftwareIdentification sid = batchRunSimulatorConfiguration.getSoftwareIdentification();
//
//        String runId = "-1";
//        // try {
//        runId = sid.getSoftwareDeveloper() + "_" + sid.getSoftwareName() + "_"
//                + sid.getSoftwareVersion() + "_" + incrementRunId();
//
//        String md5RunIdHash = RunUtils.getMd5HashFromString(runId);
//
//        BatchRunResult result = new BatchRunResult();
//
//        result.setCompletedFile("https://betaweb.rods.pitt.edu/jdl50_web_dav-source/"
//                + md5RunIdHash + "_results.txt");
//        // result.setErrorFile(md5RunIdHash + "_errors.txt");
//        result.setRunId(runId);
//
//        BatchThread bt = new BatchThread(
//                batchRunSimulatorConfiguration.getBatchConfigurationFile(),
//                result, runId, md5RunIdHash);
//        bt.start();
//        // bt.run();
//
//        System.out.println("Batch run id is: " + runId);
//
//        return result;
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

        int runId = simulationRunId.intValue();
        try {
            // set the started file for the run
            RunUtils.setStarted(getRunDirectory(runId));
        } catch (IOException ex) {
            try {
                RunUtils.setError(getRunDirectory(runId), "IOException attempting to create started file for run "
                        + runId + ": " + ex.getMessage());
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + runId + ": " + ex1.getMessage());
            }
        }
        ApolloDbUtils dbUtils;
        try {
            dbUtils = new ApolloDbUtils(new File(getDatabasePropertiesFilename()));
        } catch (IOException ex) {
            try {
                RunUtils.setError(getRunDirectory(runId), "IOException attempting to create ApolloDbUtils for run "
                        + runId + ": " + ex.getMessage());
                return;
            } catch (IOException ex1) {
                System.err.println("IOException attempting to create error file for run " + runId + ": " + ex1.getMessage());
                return;
            }
        }
        // create the run thread
        SimulatorThread worker = new SimulatorThread(runSimulationMessage, dbUtils, this, runId, false, true);

        queuedThreads.add(runId);
        QueueThread queueThread = new QueueThread(worker);
        System.out.println("Starting a queued thread with run ID " + runId);
        queueThread.start();
    }
}