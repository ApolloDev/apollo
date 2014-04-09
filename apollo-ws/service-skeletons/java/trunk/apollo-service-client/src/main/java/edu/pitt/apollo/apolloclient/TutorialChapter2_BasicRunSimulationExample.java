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
package edu.pitt.apollo.apolloclient;

import edu.pitt.apollo.types.v2_0_1.GetVisualizerOutputResourcesResult;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import edu.pitt.apollo.examples.TutorialChapter2_ExampleConfig;
import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceV201;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;

public class TutorialChapter2_BasicRunSimulationExample {

    //public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice201/services/apolloservice?wsdl";
    public static final String WSDL_LOC = "http://localhost:8080/apolloservice2.0.1/services/apolloservice?wsdl";
    private ApolloServiceEI port;
    private TutorialChapter2_ExampleConfig config;
    private static final QName SERVICE_NAME = new QName("http://service.apollo.pitt.edu/apolloservice/v2_0_1/",
            "ApolloService_v2.0.1");

    public TutorialChapter2_BasicRunSimulationExample() throws MalformedURLException {
        ApolloServiceV201 ss = new ApolloServiceV201(new URL(WSDL_LOC), SERVICE_NAME);
        port = ss.getApolloServiceEndpoint();
        config = new TutorialChapter2_ExampleConfig();
    }

    public ApolloServiceEI getPort() {
        return port;
    }

    public SoftwareIdentification getSoftwareIdentifiationForTimeSeriesVisualizer() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareName("Time Series Visualizer");
        softwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
        softwareId.setSoftwareVersion("1.0");
        softwareId.setSoftwareDeveloper("UPitt");
        return softwareId;
    }

    public SoftwareIdentification getSoftwareIdentifiationForGaia() {
        SoftwareIdentification softwareId = new SoftwareIdentification();
        softwareId.setSoftwareName("GAIA");
        softwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
        softwareId.setSoftwareVersion("1.0");
        softwareId.setSoftwareDeveloper("PSC");
        return softwareId;
    }

    protected MethodCallStatus checkStatusOfWebServiceCall(RunAndSoftwareIdentification runAndSoftwareId) {
        // give the simulator a chance to launch the simulation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // this is acceptable
        }
        while (true) {
            MethodCallStatus status = port.getRunStatus(runAndSoftwareId);
            if (status.getStatus() == null) {
            	System.out.println("Red");
            	
            }
            
            switch (status.getStatus()) {

                case AUTHENTICATION_FAILURE:
                case UNAUTHORIZED:
                    System.out.println("No authorization for this run! Error message is:" + status.getMessage());
                    return status;
                case LOG_FILES_WRITTEN:
                    System.out.println("The simulator finished writing the log files!");
                    return status;
                case COMPLETED:
                    System.out.println("Completed!");
                    return status;
                case FAILED:
                case UNKNOWN_RUNID:
                    System.out.println("Run Failed! Error message is:" + status.getMessage());
                    return status;
                case RUNNING:
                case TRANSLATING:
                case TRANSLATION_COMPLETED:

                case MOVING:
                case QUEUED:
                case HELD:
                case EXITING:
                case WAITING:
                    System.out.println("The " + runAndSoftwareId.getSoftwareId().getSoftwareName() + " run is active ("
                            + status.getStatus().toString() + "). The status message is: " + status.getMessage());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                case INITIALIZING:
                    break;
                case STAGING:
                    break;
                default:
                    break;
            }
        }
    }

    protected void getResourcesFromVisualizer(String simulatorRunId, SoftwareIdentification visualizerSoftwareIdentification) {
        System.out.println("Visualizing runId: " + simulatorRunId + " using the "
                + visualizerSoftwareIdentification.getSoftwareName() + " visualizer...");

        RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();

        runVisualizationMessage.setVisualizerIdentification(visualizerSoftwareIdentification);

        Authentication auth = new Authentication();
        auth.setRequesterId("TutorialUser");
        auth.setRequesterPassword("TutorialPassword");
        runVisualizationMessage.setAuthentication(auth);
        runVisualizationMessage.getSimulationRunIds().add(simulatorRunId);

        //VisualizerResult visualizerResult = port.runVisualization(runVisualizationMessage);
        RunVisualizationResult runVisualizationResult = port.runVisualization(runVisualizationMessage);

        String visualizationRunId = runVisualizationResult.getVisualizationRunId();

        RunAndSoftwareIdentification visualizationRunAndSoftwareId = new RunAndSoftwareIdentification();
        visualizationRunAndSoftwareId.setRunId(visualizationRunId);
        visualizationRunAndSoftwareId.setSoftwareId(visualizerSoftwareIdentification);

        if (checkStatusOfWebServiceCall(visualizationRunAndSoftwareId).getStatus() == MethodCallStatusEnum.COMPLETED) {

            System.out.println("The following resources were returned from the "
                    + visualizerSoftwareIdentification.getSoftwareName() + " visualizer:");
            GetVisualizerOutputResourcesResult results = port.getVisualizerOutputResources(visualizationRunAndSoftwareId);
            if (results.getMethodCallStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
                for (UrlOutputResource r : results.getUrlOutputResources()) {
                    System.out.println("\t" + r.getURL());
                }
            } else {
                System.out.println("Unable to retrieve visualizer output resources. MethodCallStatus was " + results.getMethodCallStatus().getStatus()
                        + " with message: " + results.getMethodCallStatus().getMessage());
            }
        }
    }

    protected RunAndSoftwareIdentification runSimulation(RunSimulationMessage runSimulationMessage) {
        BigInteger simulationRunId = port.runSimulation(runSimulationMessage);
        System.out.println("The simulator returned a runId of " + simulationRunId);

        RunAndSoftwareIdentification runAndSoftwareId = new RunAndSoftwareIdentification();
        runAndSoftwareId.setSoftwareId(runSimulationMessage.getSimulatorIdentification());
        runAndSoftwareId.setRunId(simulationRunId.toString());

        MethodCallStatus status = checkStatusOfWebServiceCall(runAndSoftwareId);
        if (status.getStatus() == MethodCallStatusEnum.COMPLETED
                || status.getStatus() == MethodCallStatusEnum.LOG_FILES_WRITTEN) {
            return runAndSoftwareId;
        } else {
            System.exit(-1);
            return null;
        }
    }

    protected void displayResults(RunAndSoftwareIdentification simulatorRunAndSoftwareId) {
        getResourcesFromVisualizer(simulatorRunAndSoftwareId.getRunId(), getSoftwareIdentifiationForTimeSeriesVisualizer());
//		getResourcesFromVisualizer(simulatorRunAndSoftwareId.getRunId(), getSoftwareIdentifiationForGaia());
    }

    protected RunAndSoftwareIdentification runSimulationAndDisplayResults(RunSimulationMessage runSimulationMessage) {
        RunAndSoftwareIdentification simulatorRunAndSoftwareId = runSimulation(runSimulationMessage);
        displayResults(simulatorRunAndSoftwareId);
        return simulatorRunAndSoftwareId;
    }

    public static void main(String args[]) throws java.lang.Exception {
        TutorialChapter2_BasicRunSimulationExample example = new TutorialChapter2_BasicRunSimulationExample();
        RunSimulationMessage runSimulationMessage = example.config.getRunSimulationMessage();
        example.runSimulationAndDisplayResults(runSimulationMessage);
    }
}