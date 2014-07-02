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

package edu.pitt.apollo.apolloclient.tutorial;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import edu.pitt.apollo.service.apolloservice.v2_0_2.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v2_0_2.ApolloServiceV202;
import edu.pitt.apollo.types.v2_0_2.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.ApolloIndexableItem;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_2.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_2.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_2.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_2.UrlOutputResource;

public class TutorialWebServiceClient {

    
	public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice2.0.1/services/apolloservice?wsdl";
    //public static final String WSDL_LOC = "http://localhost:8080/apolloservice2.0.1/services/apolloservice?wsdl";
    private static final QName SERVICE_NAME = new QName(
            "http://service.apollo.pitt.edu/apolloservice/v2_0_2/", "ApolloService_v2.0.1");
    public static final long TWO_SECONDS = 2000;
    public static final boolean RUN_IS_NOT_COMPLETED_OR_FAILED = true;
    public static final boolean RUN_WAS_SUCCESSFUL = true;
    public static final boolean RUN_FAILED = false;
    private static ApolloServiceEI port;

    public TutorialWebServiceClient() {
    }

    public static List<String> getScenarioLocationCodesSupportedBySimulatorOrNull(
            SoftwareIdentification simulatorIdentification) {
        GetScenarioLocationCodesSupportedBySimulatorResult resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall = port.getScenarioLocationCodesSupportedBySimulator(simulatorIdentification);

        MethodCallStatusEnum callStatus = resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall.getMethodCallStatus().getStatus();
        System.out.printf("Call to getScenarioLocationCodesSupportedBySimulator() returned with result: %s\n"
                ,callStatus);
        if (callStatus == MethodCallStatusEnum.COMPLETED) {
            return resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall.getLocationCodes();
        } else {
            return null;
        }

    }

    public static PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensus(SoftwareIdentification simulatorSoftwareIdentification, String apolloLocationCode) {
        GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensusResult = port.getPopulationAndEnvironmentCensus(simulatorSoftwareIdentification, apolloLocationCode);

        return getPopulationAndEnvironmentCensusResult.getPopulationAndEnvironmentCensus();
    }

    protected static List<UrlOutputResource> getUrlOutputResourcesForVisualization(
            BigInteger visualizationRunIdentifier) {
        GetVisualizerOutputResourcesResult results = port.getVisualizerOutputResources(visualizationRunIdentifier);
        if (results.getMethodCallStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
            for (UrlOutputResource r : results.getUrlOutputResources()) {
                System.out.println("\t" + r.getURL());
            }
        } else {
            System.out.printf("Unable to retrieve visualizer output resources. MethodCallStatus was %s with message: %s\n",
                    results.getMethodCallStatus().getStatus(), results.getMethodCallStatus().getMessage());
        }
        return results.getUrlOutputResources();
    }

    public static BigInteger runSimulation(RunSimulationMessage runSimulationMessage) {
        BigInteger simulationRunId = port.runSimulation(runSimulationMessage);
        System.out.printf("The simulator returned a runId of %s\n", simulationRunId);

        boolean runWasSuccessful = waitForRunToCompleteOrFail(simulationRunId);

        if (runWasSuccessful) {
            return simulationRunId;
        } else {
            return null;
        }
    }

    protected static BigInteger runVisualization(RunVisualizationMessage runVisualizationMessage) {
        RunVisualizationResult runVisualizationResult = port.runVisualization(runVisualizationMessage);
        BigInteger visualizationRunId = runVisualizationResult.getVisualizationRunId();
        return visualizationRunId;
    }

    protected static boolean waitForRunToCompleteOrFail(BigInteger runIdentification) {

        while (RUN_IS_NOT_COMPLETED_OR_FAILED) {
            try {
                Thread.sleep(TWO_SECONDS);
                MethodCallStatus callStatus = port.getRunStatus(runIdentification);
                MethodCallStatusEnum status = callStatus.getStatus();
                String message = callStatus.getMessage();

                System.out.printf("The status of run %s is: %s\n", runIdentification, message);

                if (status == null) {
                    System.out.printf("Fatal Web Service Error: The Web service did not return a status for run: %s\n",
                            runIdentification.toString());
                    System.exit(-1);
                }

                switch (status) {
                    case AUTHENTICATION_FAILURE:
                    case UNAUTHORIZED:
                        System.out.printf("No authorization for this run! Error message is: %s\n", message);
                        return RUN_FAILED;
                    case FAILED:
                    case UNKNOWN_RUNID:
                        System.out.printf("Run Failed. The status message is: %s\n", message);
                        return RUN_FAILED;
                    case LOG_FILES_WRITTEN:
                    case COMPLETED:
                        return RUN_WAS_SUCCESSFUL;
                    default:
                        Thread.sleep(TWO_SECONDS);
                        break;
                }
            } catch (InterruptedException nonFatalSleepInterruptedException) {
            }
        }
    }

    static {
        try {
            ApolloServiceV202 ss = new ApolloServiceV202(new URL(WSDL_LOC), SERVICE_NAME);
            port = ss.getApolloServiceEndpoint();
        } catch (MalformedURLException ex) {
            throw new ExceptionInInitializerError("MalformedURLException: " + ex.getMessage());
        }
    }

	public static GetLibraryItemResult getLibraryItem(String uuid) {
		return port.getLibraryItem(uuid);
	}

	public static AddLibraryItemResult addLibraryItem(Authentication authentication, ApolloIndexableItem apolloIndexableItem, String itemDescription,
			String itemSource, String itemType, ArrayList<String> itemIndexingLabels) {
		return port.addLibraryItem(authentication, apolloIndexableItem, itemDescription, itemSource, itemType, itemIndexingLabels);
	}

	
}
