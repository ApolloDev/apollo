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

import edu.pitt.apollo.service.visualizerservice._07._03._2013.VisualizerServiceEI;
import edu.pitt.apollo.types._07._03._2013.RunStatus;
import edu.pitt.apollo.types._07._03._2013.RunStatusEnum;
import edu.pitt.apollo.types._07._03._2013.UrlOutputResource;
import edu.pitt.apollo.types._07._03._2013.VisualizerConfiguration;
import edu.pitt.apollo.types._07._03._2013.VisualizerResult;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/",
portName = "VisualizerServiceEndpoint",
serviceName = "VisualizerService",
endpointInterface = "edu.pitt.apollo.service.visualizerservice.VisualizerServiceEI")
class VisualizerServiceImpl implements VisualizerServiceEI {

    @Override
    @WebResult(name = "runStatus", targetNamespace = "")
    @RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetRunStatus")
    @WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/getRunStatus")
    @ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetRunStatusResponse")
    public RunStatus getRunStatus(
            @WebParam(name = "runId", targetNamespace = "") String runId) {
        RunStatus rs = new RunStatus();

        String runIdString = runId;
        List<String> runIds;
        runIds = new ArrayList<String>();
        boolean multiSimulatorChart;
        boolean multiVaccChart;
        if (runIdString.contains(":")) {
            // multi simulator
            multiSimulatorChart = true;
            if (runIdString.contains(";")) {
                // vacc and no vacc runs
                multiVaccChart = true;
                String[] simRunIds = runIdString.split(":");
                for (String simRunId : simRunIds) {
                    String[] vaccStratRunIds = simRunId.split(";");
                    for (int i = 0; i < 2; i++) {
                        String id = vaccStratRunIds[i];
                        runIds.add(id);
                    }
                }
            } else {
                // only no vacc run
                multiVaccChart = false;
                String[] simRunIds = runIdString.split(":");
                for (String simRunId : simRunIds) {
                    runIds.add(simRunId);
                }
            }
        } else {
            // single simulator 
            multiSimulatorChart = false;
            if (runIdString.contains(";")) {
                // vacc and no vacc runs
                multiVaccChart = true;
                String[] vaccStratRunIds = runIdString.split(";");
                for (int i = 0; i < 2; i++) {
                    runIds.add(vaccStratRunIds[i]);
                }
            } else {
                // only no vacc run
                multiVaccChart = false;
                runIds.add(runIdString);

            }
        }
//        rs.setMessage("it worked");
//        rs.setStatus(RunStatusEnum.COMPLETED);

//        // find the run directory
        ImageGenerator ig = null;
        try {
            ig = new ImageGenerator(runIds, multiVaccChart, multiSimulatorChart);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Exception creatingImageGenerator: " + ex.getMessage());
        }

        String runDirectory = ig.getRunDirectory();
        String finishedFilePath = runDirectory + File.separator + ImageGeneratorRunnable.FINISHED_FILE;
        File finishedFile = new File(finishedFilePath);
        if (finishedFile.exists()) {
            rs.setMessage("Run with ID " + runId + " is completed");
            rs.setStatus(RunStatusEnum.COMPLETED);
        } else {
            // check started file
            String startedFilePath = runDirectory + File.separator + ImageGeneratorRunnable.STARTED_FILE;
            File startedFile = new File(startedFilePath);
            if (startedFile.exists()) {
                rs.setMessage("Still running with run ID " + runId);
                rs.setStatus(RunStatusEnum.RUNNING);
            } else {
                System.out.println("finished file path: " + finishedFile.getAbsolutePath());
                rs.setMessage("Run with ID " + runId + " has not been requested yet");
                rs.setStatus(RunStatusEnum.FAILED);
            }
        }
        return rs;
    }

    @Override
    @WebResult(name = "visualizerResult", targetNamespace = "")
    @RequestWrapper(localName = "run", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.Run")
    @WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/run")
    @ResponseWrapper(localName = "runResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.RunResponse")
    public VisualizerResult run(
            @WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {
        String runIdString = visualizerConfiguration.getVisualizationOptions().getRunId();
        List<String> runIds;
        Map<String, String> runIdSeriesLabel = new HashMap<String, String>();
        boolean multiSimulatorChart;
        boolean multiVaccChart;
        runIds = new ArrayList<String>();
        if (runIdString.contains(":")) {
            // multi simulator
            multiSimulatorChart = true;
            if (runIdString.contains(";")) {
                // vacc and no vacc runs
                multiVaccChart = true;
                String[] simRunIds = runIdString.split(":");
                for (String simRunId : simRunIds) {
                    String[] vaccStratRunIds = simRunId.split(";");
                    for (int i = 0; i < 2; i++) {
                        String id = vaccStratRunIds[i];
                        runIds.add(id);
                        switch (i) {
                            case 0:
                                runIdSeriesLabel.put(id, id + "_no_control_measure");
                                break;
                            case 1:
                                runIdSeriesLabel.put(id, id + "_control_measure");
                                break;
                        }
                    }
                }
            } else {
                // only no vacc run
                multiVaccChart = false;
                String[] simRunIds = runIdString.split(":");
                for (String simRunId : simRunIds) {
                    runIds.add(simRunId);
                    runIdSeriesLabel.put(simRunId, simRunId);
                }
            }
        } else {
            // single simulator 
            multiSimulatorChart = false;
            if (runIdString.contains(";")) {
                // vacc and no vacc runs
                multiVaccChart = true;
                String[] vaccStratRunIds = runIdString.split(";");
                for (int i = 0; i < 2; i++) {
                    runIds.add(vaccStratRunIds[i]);
                    switch (i) {
                        case 0:
                            runIdSeriesLabel.put(vaccStratRunIds[i], "No Vaccination");
                            break;
                        case 1:
                            runIdSeriesLabel.put(vaccStratRunIds[i], "Vaccination");
                            break;
                    }
                }
            } else {
                // only no vacc run
                multiVaccChart = false;
                runIds.add(runIdString);
                runIdSeriesLabel.put(runIdString, "No control measure");

            }
        }

//        System.out.println("multi vacc chart: " + multiVaccChart + "   multi sim chart: " + multiSimulatorChart);
//        System.out.println("run ids size: " + runIds.size());
//        System.out.println("run ids 0: " + runIds.get(0));

        VisualizerResult result = new VisualizerResult();
        result.setRunId(visualizerConfiguration.getVisualizationOptions().getRunId()); // use the same runId as in the request

        ImageGenerator ig = null;

        List<UrlOutputResource> outputResource = result.getVisualizerOutputResource();

        try {
            ig = new ImageGenerator(runIds, outputResource, runIdSeriesLabel, multiVaccChart, multiSimulatorChart);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("Exception: " + ex.getMessage());
        }

        // now the image URLs have been set, so start a thread to generate the images
        ImageGeneratorRunnable igRunnable = new ImageGeneratorRunnable();
        igRunnable.setImageGenerator(ig);
        Thread thread = new Thread(igRunnable);
        thread.start();

        return result;
    }

    @Override
    @WebResult(name = "configurationFile", targetNamespace = "")
    @RequestWrapper(localName = "getConfigurationFileForRun", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetConfigurationFileForRun")
    @WebMethod
    @ResponseWrapper(localName = "getConfigurationFileForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetConfigurationFileForRunResponse")
    public String getConfigurationFileForRun(
            @WebParam(name = "runIdentification", targetNamespace = "") String runIdentification) {
        // TODO Auto-generated method stub
        return null;
    }
}