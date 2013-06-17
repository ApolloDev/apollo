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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.service.simulatorservice.SimulatorService;
import edu.pitt.apollo.service.simulatorservice.SimulatorServiceEI;
import edu.pitt.apollo.service.syntheticpopulationservice.SyntheticPopulationService;
import edu.pitt.apollo.service.syntheticpopulationservice.SyntheticPopulationServiceEI;
import edu.pitt.apollo.service.visualizerservice.VisualizerService;
import edu.pitt.apollo.service.visualizerservice.VisualizerServiceEI;
import edu.pitt.apollo.types.ApolloSoftwareType;
import edu.pitt.apollo.types.BatchRunResult;
import edu.pitt.apollo.types.BatchRunSimulatorConfiguration;
import edu.pitt.apollo.types.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.ServiceRegistrationResult;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SyntheticPopulationConfiguration;
import edu.pitt.apollo.types.SyntheticPopulationResult;
import edu.pitt.apollo.types.VisualizerConfiguration;
import edu.pitt.apollo.types.VisualizerResult;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", portName = "ApolloServiceEndpoint", serviceName = "ApolloService", endpointInterface = "edu.pitt.apollo.service.apolloservice.ApolloServiceEI")
class ApolloServiceImpl implements ApolloServiceEI {

    private static final String REGISTRY_FILENAME = "registered_services.xml";
    private static final String ERROR_FILENAME = "run_errors.txt";
    private static final String RUN_ERROR_PREFIX = "ApolloServiceError";
//    private static final String FLUTE_FILE_DIR = "flute_files";
    private static String APOLLO_DIR = "";

    public ByteArrayOutputStream getJSONBytes(Object obj) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            mapper.writeValue(baos, obj);

            return baos;
        } catch (IOException ex) {
            System.err.println("IO Exception JSON encoding and getting bytes from SimulatorConfiguration");
            return null;
        }
    }

    public static String getMd5HashFromString(String string) {

        return DigestUtils.md5Hex(string);
    }

//    public String getMd5HashFromBytes(byte[] bytes) {
//
//        try {
//            MessageDigest md = null;
//
//            md = MessageDigest.getInstance("MD5");
//
//            md.update(bytes);
//            byte[] digest = md.digest();
//            StringBuilder sb = new StringBuilder();
//
//            for (byte b : digest) {
//                sb.append(Integer.toHexString((int) (b & 0xff)));
//            }
//
//            String md5Hash = sb.toString();
//            return md5Hash;
//
//        } catch (NoSuchAlgorithmException ex) {
//            return null;
//        }
//    }
    public synchronized void cacheRunId(String runId, String md5Hash, String filepath) {

        if (checkRunIdCache(md5Hash, filepath) == null) { // make sure that the cache was not updated since this method was called

            try {
                File runCacheFile = new File(filepath);
                PrintStream ps = new PrintStream(new FileOutputStream(runCacheFile, true));

                ps.println(runId + "\t" + md5Hash);
                ps.flush();
                ps.close();
            } catch (FileNotFoundException ex) {
                System.err.println("Could not find run cache file. Cannot cache the current run");
            }

        }
    }

    public synchronized void cacheVisualizerResult(String runId, String md5Hash, VisualizerResult result, String filepath) {

        if (checkVisualizerCache(md5Hash, filepath) == null) { // make sure the cache was not updated since this method was called
            try {

                FileOutputStream fout = new FileOutputStream(new File(
                        filepath), true);

                fout.write((runId + "\t" + md5Hash + "\t").getBytes());

                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
                mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                mapper.writeValue(fout, result);

                fout.write(("\n").getBytes());
                fout.flush();
                fout.close();

            } catch (FileNotFoundException ex) {
                System.err.println("Could not find run cache file. Cannot cache the current run");
            } catch (IOException ex) {
                System.err.println("Could not write the visualizer result to file. Cannot cache the current visualizer run");
            }
        }
    }

//    private String getRunIdCacheFilePath(String simName) {
//        String filePath;
//        if (simName.contains("SEIR")) {
//            filePath = APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "SEIR" + File.separator + "seir_run_cache.txt";
//        } else if (simName.contains("FRED")) {
//            filePath = APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "FRED" + File.separator + "fred_run_cache.txt";
//        } else if (simName.contains("FluTE")) {
//            filePath = APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "FluTE" + File.separator + "flute_run_cache.txt";
//        } else {
//            filePath = null;
//        }
//
//        return filePath;
//    }
//
//    private String getVisualizerCacheFilePath(String visName) {
//        String filePath;
//        if (visName.contains("GAIA")) {
//            filePath = APOLLO_DIR + File.separator + VIS_CACHE_DIR_NAME + File.separator + "GAIA" + File.separator + "gaia_vis_cache.txt";
//        } else {
//            filePath = APOLLO_DIR + File.separator + VIS_CACHE_DIR_NAME + File.separator + "IMAGES" + File.separator + "images_vis_cache.txt";
//        }
//
//        return filePath;
//    }
    public String checkRunIdCache(String md5Hash, String filepath) {

        try {
            File runCache = new File(filepath);
            Scanner scanner = new Scanner(runCache);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("\t")) {
                    String[] array = line.split("\t");
                    if (array.length > 1) {

                        String runId = array[0];
                        String hash = array[1];

                        if (hash.equals(md5Hash)) {
                            return runId;
                        }
                    }
                }
            }

            return null;

        } catch (FileNotFoundException ex) {
            System.err.println("Can not find run cache file.");
            return null;
        }
    }

    public VisualizerResult checkVisualizerCache(String md5Hash, String filepath) {
        try {
            File runCache = new File(filepath);
            Scanner scanner = new Scanner(runCache);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] array = line.split("\t");

                String runId = array[0];
                String hash = array[1];
                String json = array[2];

                if (hash.equals(md5Hash)) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                    Iterator it = mapper.readValues(
                            new JsonFactory().createJsonParser(json),
                            VisualizerResult.class);

                    VisualizerResult vr = (VisualizerResult) it.next();
                    return vr;
                }
            }

            return null;

        } catch (FileNotFoundException ex) {
            System.err.println("Can not find run cache file.");
            return null;
        } catch (IOException ex) {
            System.err.println("Could not create iterator");
            return null;
        }
    }

    @Override
    @WebResult(name = "runId", targetNamespace = "")
    @RequestWrapper(localName = "runSimulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulation")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runSimulation")
    @ResponseWrapper(localName = "runSimulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunSimulationResponse")
    public String runSimulation(
            @WebParam(name = "simulatorConfiguration", targetNamespace = "") SimulatorConfiguration simulatorConfiguration) {
        // this method must return a runId, even if there is an error
        String runId;
        ByteArrayOutputStream baos = getJSONBytes(simulatorConfiguration);
        String simConfigHash = getMd5HashFromString(baos.toString());
        String simulatorName = simulatorConfiguration.getSimulatorIdentification().getSoftwareName();

//        String cacheFilePath = getRunIdCacheFilePath(simulatorName);
//        String cachedRunId;
//
//        if (cacheFilePath != null) {
//            cachedRunId = checkRunIdCache(simConfigHash, cacheFilePath);
//            if (cachedRunId != null) {
//                System.out.println("Run was cached in Apollo Service. Run ID is " + cachedRunId);
//                return cachedRunId;
//            }
//        }

        // check for cached result
        try {
            runId = DbUtils.checkRunCache(simConfigHash);
            if (runId != null) {

                // check the status of the run
                RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
                rasid.setRunId(runId);
                rasid.setSoftwareId(simulatorConfiguration.getSimulatorIdentification());
                RunStatus status = getRunStatus(rasid);
                RunStatusEnum statusEnum = status.getStatus();

                if (statusEnum.equals(RunStatusEnum.FAILED)) {
                    DbUtils.deleteFromRunCache(simConfigHash);
                } else {
                    return runId;
                }
            }
        } catch (ClassNotFoundException ex) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with SimulatorService: ClassNotFoundException: " + ex.getMessage());
            ex.printStackTrace();
            return runId;
        } catch (SQLException ex) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with SimulatorService: SQLException: " + ex.getMessage());
            ex.printStackTrace();
            return runId;
        }


        try {
            URL url = null;
            try {
                // get the webservice WSDL URL for supplied
                // SimulatorIdentification
                url = RegistrationUtils.getUrlForSoftwareIdentification(simulatorConfiguration.getSimulatorIdentification());
                System.out.println("URL resturned: " + url.toString());
                if (url == null) {
                    runId = RunUtils.getErrorRunId();
                    RunUtils.reportError(runId, "Service not registered.");
                    return runId;
                }
            } catch (IOException e) {
                runId = RunUtils.getErrorRunId();
                RunUtils.reportError(runId, "Error reading registry.");
                return runId;
            }
            // run the simulator
            SimulatorService ss = new SimulatorService(url);
            SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();

            // disable chunking for ZSI
            Client client = ClientProxy.getClient(port);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(36000);
            httpClientPolicy.setAllowChunking(false);
            http.setClient(httpClientPolicy);

            System.out.println("Apollo Service is running simulator with URL " + url);
            runId = port.run(simulatorConfiguration);
            System.out.println("Returned run ID is + " + runId);

//            if (cacheFilePath != null) {
//                cacheRunId(runId, simConfigHash, cacheFilePath);
//            } else {
//                System.out.println("Warning: the requested simulator has no associated cache file. This run will not be cached.");
//            }
            // cache the run
            try {
                DbUtils.insertIntoRunCache(runId, simConfigHash);
            } catch (ClassNotFoundException ex) {
                runId = RunUtils.getErrorRunId();
                RunUtils.reportError(runId,
                        "Problem with SimulatorService: ClassNotFoundException: " + ex.getMessage());
                ex.printStackTrace();
            } catch (SQLException ex) {
                runId = RunUtils.getErrorRunId();
                RunUtils.reportError(runId,
                        "Problem with SimulatorService: SQLException: " + ex.getMessage());
                ex.printStackTrace();
                return runId;
            }

            return runId;
        } catch (Exception e) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with SimulatorService:" + e.getMessage());
            e.printStackTrace();
            return runId;
        }


    }

    @Override
    @WebResult(name = "batchRunSimulatorResult", targetNamespace = "")
    @RequestWrapper(localName = "batchRun", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.BatchRun")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/batchRun")
    @ResponseWrapper(localName = "batchRunResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.BatchRunResponse")
    public BatchRunResult batchRunSimulation(
            @WebParam(name = "batchRunSimulatorConfiguration", targetNamespace = "") BatchRunSimulatorConfiguration batchRunSimulatorConfiguration) {
        BatchRunResult bsr = new BatchRunResult();

        String runId;
        try {
            URL url = null;
            try {
                // get the webservice WSDL URL for supplied
                // SimulatorIdentification
                url = RegistrationUtils.getUrlForSoftwareIdentification(batchRunSimulatorConfiguration.getSoftwareIdentification());
                if (url == null) {
                    runId = RunUtils.getErrorRunId();
                    RunUtils.reportError(runId, "Service not registered.");
                    bsr.setRunId(runId);
                    return bsr;
                }
            } catch (IOException e) {
                runId = RunUtils.getErrorRunId();
                RunUtils.reportError(runId, "Error reading registry.");
                bsr.setRunId(runId);
                return bsr;
            }
            // run the simulator
            SimulatorService ss = new SimulatorService(url);
            SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();

            // disable chunking for ZSI
            Client client = ClientProxy.getClient(port);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(36000);
            httpClientPolicy.setAllowChunking(false);
            http.setClient(httpClientPolicy);

            return port.batchRun(batchRunSimulatorConfiguration);
        } catch (Exception e) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with SimulatorService:" + e.getMessage());
            bsr.setRunId(runId);
            return bsr;
        }
    }

    @Override
    @WebResult(name = "visualizerResult", targetNamespace = "")
    @RequestWrapper(localName = "runVisualization", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualization")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/runVisualization")
    @ResponseWrapper(localName = "runVisualizationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RunVisualizationResponse")
    public VisualizerResult runVisualization(
            @WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {


        String runId;
        ByteArrayOutputStream baos = getJSONBytes(visualizerConfiguration);
        String visConfigHash = getMd5HashFromString(baos.toString());
        String visName = visualizerConfiguration.getVisualizerIdentification().getSoftwareName();
        VisualizerResult result;

        // check the cache
        try {
            result = DbUtils.checkVisualizerCache(visConfigHash);
            if (result != null) {

                // check the status of the run
                runId = result.getRunId();
                RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
                rasid.setRunId(runId);
                rasid.setSoftwareId(visualizerConfiguration.getVisualizerIdentification());
                RunStatus status = getRunStatus(rasid);
                RunStatusEnum statusEnum = status.getStatus();

                if (statusEnum.equals(RunStatusEnum.FAILED)) {
                    DbUtils.deleteFromVisualizerCache(visConfigHash);
                } else {
                    return result;
                }

            }
        } catch (ClassNotFoundException ex) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with VisualizerService: ClassNotFoundException" + ex.getMessage());
            result = new VisualizerResult();
            result.setRunId(runId);
            ex.printStackTrace();

            return result;
        } catch (SQLException ex) {
            runId = RunUtils.getErrorRunId();
            RunUtils.reportError(runId,
                    "Problem with VisualizerService: SQLException" + ex.getMessage());
            result = new VisualizerResult();
            result.setRunId(runId);
            ex.printStackTrace();

            return result;
        }

        try {
            result = new VisualizerResult();
            URL url = null;
            try {
                // get the webservice WSDL URL for supplied
                // SimulatorIdentification
                url = RegistrationUtils.getUrlForSoftwareIdentification(visualizerConfiguration.getVisualizerIdentification());
                if (url == null) {
                    result.setRunId(RunUtils.getErrorRunId());
                    RunUtils.reportError(result.getRunId(),
                            "Service not registered.");
                    return result;
                }
            } catch (IOException e) {
                result.setRunId(RunUtils.getErrorRunId());
                RunUtils.reportError(result.getRunId(),
                        "Error reading registry.");
                return result;
            }
            // run the simulator
            VisualizerService ss = new VisualizerService(url);
            VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();

            // disable chunking for ZSI
            Client client = ClientProxy.getClient(port);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(36000);
            httpClientPolicy.setAllowChunking(false);
            http.setClient(httpClientPolicy);

            System.out.println("Running the visualization...");
            VisualizerResult visualizerResult = port.run(visualizerConfiguration);
            runId = visualizerResult.getRunId();

//                cacheVisualizerResult(runId, visConfigHash, visualizerResult, cacheFilePath);

            // cache the visualizer result
            try {
                String cacheId = DbUtils.insertIntoVisualizerCache(runId, visConfigHash);
                DbUtils.insertIntoVisualizerResultsCache(cacheId, visualizerResult.getVisualizerOutputResource());
            } catch (ClassNotFoundException ex) {
                RunUtils.reportError(visualizerResult.getRunId(),
                        "Problem with VisualizerService: ClassNotFoundException" + ex.getMessage());
                ex.printStackTrace();
            } catch (SQLException ex) {
                RunUtils.reportError(visualizerResult.getRunId(),
                        "Problem with VisualizerService: SQLException" + ex.getMessage());
                ex.printStackTrace();
            }

            return visualizerResult;
        } catch (Exception e) {
            VisualizerResult visualizerResult = new VisualizerResult();
            visualizerResult.setRunId(RunUtils.getErrorRunId());

            RunUtils.reportError(visualizerResult.getRunId(),
                    "Problem with VisualizerService:" + e.getMessage());
            e.printStackTrace();
            return visualizerResult;
        }
//        }

    }

    @Override
    @WebResult(name = "syntheticPopulationResult", targetNamespace = "")
    @RequestWrapper(localName = "generateSyntheticPopulation", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GenerateSyntheticPopulation")
    @WebMethod
    @ResponseWrapper(localName = "generateSyntheticPopulationResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GenerateSyntheticPopulationResponse")
    public SyntheticPopulationResult generateSyntheticPopulation(
            @WebParam(name = "syntheticPopulationConfiguration", targetNamespace = "") SyntheticPopulationConfiguration syntheticPopulationConfiguration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRunStatusResponse")
    public RunStatus getRunStatus(
            @WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {

//        if (runAndSoftwareIdentification.getSoftwareId().getSoftwareName().equalsIgnoreCase("flute")) {
//            RunStatus rs = new RunStatus();
//            rs.setMessage("The run is completed");
//            rs.setStatus(RunStatusEnum.COMPLETED);
//            return rs;
//        }


        if (runAndSoftwareIdentification.getRunId().startsWith(RUN_ERROR_PREFIX)) {
            RunStatus rs = new RunStatus();
            rs.setStatus(RunStatusEnum.FAILED);
            rs.setMessage(RunUtils.getError(runAndSoftwareIdentification.getRunId()));
            return rs;
        }

        try {
            ServiceRecord serviceRecord = RegistrationUtils.getServiceRecordForSoftwareId(runAndSoftwareIdentification.getSoftwareId());

            URL url = new URL(serviceRecord.getUrl());

            // get the webservice WSDL URL for supplied
            // SimulatorIdentification
            if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SIMULATOR) {
                SimulatorService ss = new SimulatorService(url);
                SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
                return port.getRunStatus(runAndSoftwareIdentification.getRunId());
            } else if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.VISUALIZER) {
                VisualizerService ss = new VisualizerService(url);
                VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();
                return port.getRunStatus(runAndSoftwareIdentification.getRunId());
            } else if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SYNTHETIC_POPULATION_GENERATOR) {
                SyntheticPopulationService ss = new SyntheticPopulationService(
                        url);
                SyntheticPopulationServiceEI port = ss.getSyntheticPopulationServiceEndpoint();
                return port.getRunStatus(runAndSoftwareIdentification.getRunId());
            } else {
                RunStatus rs = new RunStatus();
                rs.setMessage("Not implemented yet...");
                rs.setStatus(RunStatusEnum.FAILED);
                return rs;
            }
        } catch (IOException e) {
            RunStatus rs = new RunStatus();
            rs.setMessage("Error geting remote runStatus!");
            rs.setStatus(RunStatusEnum.FAILED);
            return rs;
        }

    }

    @Override
    @WebResult(name = "configurationFile", targetNamespace = "")
    @RequestWrapper(localName = "getConfigurationFileForRun", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetConfigurationFileForRun")
    @WebMethod
    @ResponseWrapper(localName = "getConfigurationFileForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetConfigurationFileForRunResponse")
    public String getConfigurationFileForRun(
            @WebParam(name = "runAndSoftwareIdentification", targetNamespace = "") RunAndSoftwareIdentification runAndSoftwareIdentification) {

        String simName = runAndSoftwareIdentification.getSoftwareId().getSoftwareName();
//        if (simName.equalsIgnoreCase("flute")) {
//            String runId = runAndSoftwareIdentification.getRunId();
//            String text = readFluteConfigFile(runId);
//
//            return text;
//        } else {

        try {
            ServiceRecord serviceRecord = RegistrationUtils.getServiceRecordForSoftwareId(runAndSoftwareIdentification.getSoftwareId());

            URL url = new URL(serviceRecord.getUrl());

            // get the webservice WSDL URL for supplied
            // SimulatorIdentification
            if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SIMULATOR) {
                SimulatorService ss = new SimulatorService(url);
                SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
                return port.getConfigurationFileForRun(runAndSoftwareIdentification.getRunId());
            } else if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.VISUALIZER) {
                VisualizerService ss = new VisualizerService(url);
                VisualizerServiceEI port = ss.getVisualizerServiceEndpoint();
                return port.getConfigurationFileForRun(runAndSoftwareIdentification.getRunId());
            } else if (serviceRecord.getSoftwareIdentification().getSoftwareType() == ApolloSoftwareType.SYNTHETIC_POPULATION_GENERATOR) {
                SyntheticPopulationService ss = new SyntheticPopulationService(
                        url);
                SyntheticPopulationServiceEI port = ss.getSyntheticPopulationServiceEndpoint();
                return port.getConfigurationFileForRun(runAndSoftwareIdentification.getRunId());
            } else {
                return "getConfigurationFile() not implemented for this service type.";
            }
        } catch (IOException e) {
            return "General error occurred on server.";
        }
//        }

    }

    @Override
    @WebResult(name = "serviceRegistrationResult", targetNamespace = "")
    @RequestWrapper(localName = "registerService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/registerService")
    @ResponseWrapper(localName = "registerServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.RegisterServiceResponse")
    public ServiceRegistrationResult registerService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        ServiceRegistrationResult result = new ServiceRegistrationResult();

        List<ServiceRegistrationRecord> records;
        try {

            records = RegistrationUtils.getServiceRegistrationRecords();

            for (ServiceRegistrationRecord record : records) {
                if (RegistrationUtils.softwareIdentificationEqual(
                        record.getSoftwareIdentification(),
                        serviceRegistrationRecord.getSoftwareIdentification())) {
                    result.setMessage("Service is already registered.  Please unRegisterService to make changes to the existing ServiceRecord.");
                    result.setActionSuccessful(false);
                    return result;
                }

                if (RegistrationUtils.serviceUrlEqual(record,
                        serviceRegistrationRecord)) {
                    result.setMessage("URL is already registered.");
                    result.setActionSuccessful(false);
                    return result;
                }
            }

            // if we are here, it looks like a valid registration
            try {
                RegistrationUtils.addServiceRegistrationRecord(serviceRegistrationRecord);
                result.setMessage("Service Registration Successful!");
                result.setActionSuccessful(true);
            } catch (IOException e) {
                result.setMessage("Error registering service: "
                        + e.getMessage());
                result.setActionSuccessful(false);
            }

        } catch (IOException e) {
            result.setMessage("Error reading registry!");
            result.setActionSuccessful(false);
        }
        return result;
    }

    @Override
    @WebResult(name = "serviceRegistrationResult", targetNamespace = "")
    @RequestWrapper(localName = "unRegisterService", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterService")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/unRegisterService")
    @ResponseWrapper(localName = "unRegisterServiceResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.UnRegisterServiceResponse")
    public ServiceRegistrationResult unRegisterService(
            @WebParam(name = "serviceRegistrationRecord", targetNamespace = "") ServiceRegistrationRecord serviceRegistrationRecord) {
        ServiceRegistrationResult result = new ServiceRegistrationResult();

        List<ServiceRegistrationRecord> records;
        try {
            // get the entire list of current service registration records
            records = RegistrationUtils.getServiceRegistrationRecords();

            for (ServiceRegistrationRecord record : records) {
                // for each record currently in the registry, see if we can find
                // a record with a ServiceIdentification that is equal to one
                // that the user is trying to unregister
                if (RegistrationUtils.softwareIdentificationEqual(
                        record.getSoftwareIdentification(),
                        serviceRegistrationRecord.getSoftwareIdentification())) {
                    // found the service the user wants to unregister, now check
                    // that the username and password supplied with this request
                    // match the username and password sent with the
                    // registration request
                    if (RegistrationUtils.authenticationEqual(
                            record.getAuthentication(),
                            serviceRegistrationRecord.getAuthentication())) {
                        try {
                            // the username/password match, so remove the record
                            // from the registry
                            RegistrationUtils.removeServiceRegistrationRecord(serviceRegistrationRecord);
                        } catch (IOException e) {
                            // there was en error removing the record, report
                            // this error to the caller
                            result.setMessage("Error Unregistering Service: "
                                    + e.getMessage());
                            result.setActionSuccessful(false);
                            return result;
                        }
                        // removal succeeded
                        result.setMessage("unregistration Successful!");
                        result.setActionSuccessful(true);
                        return result;
                    } else {
                        // username/passwords do not match
                        result.setMessage("Error Unregistering Service: Username/Password does not match orignial ServiceRegistrationRecord!");
                        result.setActionSuccessful(false);
                        return result;
                    }
                }
            }
            // couldn't find matching ServiceRecords
            result.setMessage("Error Unregistering Service: Service not registered at this registry.");
            result.setActionSuccessful(false);
            return result;
        } catch (IOException e) {
            result.setMessage("Error Unregistering Service: Error reading registry!");
            result.setActionSuccessful(false);
            return result;
        }
    }

    @Override
    @WebResult(name = "serviceRecords", targetNamespace = "")
    @RequestWrapper(localName = "getRegisteredServices", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServices")
    @WebMethod(action = "http://service.apollo.pitt.edu/apolloservice/getRegisteredServices")
    @ResponseWrapper(localName = "getRegisteredServicesResponse", targetNamespace = "http://service.apollo.pitt.edu/apolloservice/", className = "edu.pitt.apollo.service.apolloservice.GetRegisteredServicesResponse")
    public List<ServiceRecord> getRegisteredServices() {
        try {
            return RegistrationUtils.getRegisteredSoftware();
        } catch (IOException e) {
            return null;
        }
    }

//    private static void createRunCacheFiles() throws SQLException, ClassNotFoundException, IOException {
//
//        System.out.println("Creating run cache files...");
//        Map<String, String> storedRunIds = DbUtils.getStoredRuns();
//
//        File seirRunCacheFile = new File(APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "SEIR" + File.separator + "seir_run_cache.txt");
//        if (!seirRunCacheFile.exists()) {
//            seirRunCacheFile.getParentFile().mkdirs();
//            seirRunCacheFile.createNewFile();
//        }
//
//        File fluteRunCacheFile = new File(APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "FLUTE" + File.separator + "flute_run_cache.txt");
//        if (!fluteRunCacheFile.exists()) {
//            fluteRunCacheFile.getParentFile().mkdirs();
//            fluteRunCacheFile.createNewFile();
//        }
//
//        File fredRunCacheFile = new File(APOLLO_DIR + File.separator + RUN_CACHE_DIR_NAME + File.separator + "FRED" + File.separator + "fred_run_cache.txt");
//        if (!fredRunCacheFile.exists()) {
//            fredRunCacheFile.getParentFile().mkdirs();
//            fredRunCacheFile.createNewFile();
//        }
//
//
//        PrintStream seirPs = new PrintStream(seirRunCacheFile);
//        PrintStream flutePs = new PrintStream(fluteRunCacheFile);
//        PrintStream fredPs = new PrintStream(fredRunCacheFile);
//        for (String runId : storedRunIds.keySet()) {
//
//            String hash = storedRunIds.get(runId);
//            if (hash == null | hash.trim().equals("")) {
//                continue; // skip the runs which do not have a sim config hash since we can't get to them from the sim config
//            }
//
//            if (runId.contains("SEIR")) {
//                seirPs.println(runId + "\t" + hash);
//                seirPs.flush();
//            } else if (runId.contains("FRED")) {
//                fredPs.println(runId + "\t" + hash);
//                fredPs.flush();
//            } else if (runId.contains("FluTE")) {
//                flutePs.println(runId + "\t" + hash);
//                flutePs.flush();
//            }
//
//        }
//
//        seirPs.close();
//        flutePs.close();
//        fredPs.close();
//    }
//    private static void createVisualizerCacheFiles() {
//
//        System.out.println("Creating visualization cache files...");
//        File gaiaVisCacheFile = new File(APOLLO_DIR + File.separator + VIS_CACHE_DIR_NAME + File.separator + "GAIA" + File.separator + "gaia_vis_cache.txt");
//        if (!gaiaVisCacheFile.exists()) {
//            gaiaVisCacheFile.getParentFile().mkdirs();;
//            try {
//                gaiaVisCacheFile.createNewFile();
//            } catch (IOException ex) {
//                System.err.println("Could not create vis cache file");
//            }
//        }
//
//        File imageVisualizationsFile = new File(APOLLO_DIR + File.separator + VIS_CACHE_DIR_NAME + File.separator + "IMAGES" + File.separator + "images_vis_cache.txt");
//        if (!imageVisualizationsFile.exists()) {
//            imageVisualizationsFile.getParentFile().mkdirs();
//            try {
//                imageVisualizationsFile.createNewFile();
//            } catch (IOException ex) {
//                System.err.println("Could not create vis cache file");
//            }
//        }
//    }
    public static String getRegistryFilename() {
        return APOLLO_DIR + REGISTRY_FILENAME;
    }

    public static String getErrorFilename() {
        return APOLLO_DIR + ERROR_FILENAME;
    }

    public static String getRunErrorPrefix() {
        return RUN_ERROR_PREFIX;
    }

    static {
        Map<String, String> env = System.getenv();
        APOLLO_DIR = env.get("APOLLO_WORK_DIR");
        if (APOLLO_DIR != null) {
            if (!APOLLO_DIR.endsWith(File.separator)) {
                APOLLO_DIR += File.separator;
            }
            System.out.println("APOLLO_DIR is now:" + APOLLO_DIR);
        } else {
            System.out.println("APOLLO_DIR environment variable not found!");
            APOLLO_DIR = "";
        }


//        try {
//            createRunCacheFiles();
//        } catch (ClassNotFoundException ex) {
//            System.err.println("Could not create run cache file: ClassNotFoundException - " + ex.getMessage());
//        } catch (SQLException ex) {
//            System.err.println("Could not create run cache file: SQLException - " + ex.getMessage());
//        } catch (IOException ex) {
//            System.err.println("Could not create run cache file: IOException - " + ex.getMessage());
//        }
//
//        createVisualizerCacheFiles();

//        File fluteFileDir = new File(APOLLO_DIR + File.separator + FLUTE_FILE_DIR);
//        if (!fluteFileDir.exists()) {
//            fluteFileDir.mkdirs();
//        }

    }
 
}
