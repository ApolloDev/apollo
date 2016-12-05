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
import java.util.List;

import javax.xml.namespace.QName;

import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.service.apolloservice.v4_0.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v4_0.ApolloServiceV40;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunResult;
import edu.pitt.apollo.services_common.v4_0.RunStatusRequest;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.services_common.v4_0.UrlOutputResource;
import edu.pitt.apollo.simulator_service_types.v4_0.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;

public class TutorialWebServiceClient {

	// public static final String WSDL_LOC =
	// "http://research.rods.pitt.edu/apolloservice2.0.1/services/apolloservice?wsdl";
	public static final String WSDL_LOC = "http://betaweb.rods.pitt.edu/broker-service-war-4.0-SNAPSHOT/services/apolloservice?wsdl";
	// public static final String WSDL_LOC =
	// "http://localhost:8080/apolloservice2.0.1/services/apolloservice?wsdl";
	private static final QName SERVICE_NAME = new QName(
			"http://service.apollo.pitt.edu/apolloservice/v4_0/",
			"ApolloService_v4.0");
	public static final long TWO_SECONDS = 2000;
	public static final boolean RUN_IS_NOT_COMPLETED_OR_FAILED = true;
	public static final boolean RUN_WAS_SUCCESSFUL = true;
	public static final boolean RUN_FAILED = false;
	private static ApolloServiceEI port;
	
	public TutorialWebServiceClient() {
	}

	public static BigInteger runSimulation(
			RunSimulationMessage runSimulationMessage, Authentication authentication) {
		RunResult simulationRunResult = port
				.runSimulation(runSimulationMessage);
		System.out.println("The broker service returned the following status: "
				+ simulationRunResult.getMethodCallStatus().getStatus() + " : "
				+ simulationRunResult.getMethodCallStatus().getMessage());
		System.out.printf("The simulator returned a runId of %s\n",
				simulationRunResult.getRunId());
		
		boolean runWasSuccessful = waitForRunToCompleteOrFail(simulationRunResult
				.getRunId(), authentication);
		
		if (runWasSuccessful) {
			return simulationRunResult.getRunId();
		} else {
			return null;
		}
	}
	
	public static BigInteger runSimulations(
			RunSimulationsMessage runSimulationsMessage, Authentication authentication) {
		RunResult simulationRunResult = port
				.runSimulations(runSimulationsMessage);
		System.out.printf("The simulator returned a runId of %s\n",
				simulationRunResult.getRunId());
		
		boolean runWasSuccessful = waitForRunToCompleteOrFail(simulationRunResult
				.getRunId(), authentication);
		
		if (runWasSuccessful) {
			return simulationRunResult.getRunId();
		} else {
			return null;
		}
	}
	
	protected static BigInteger runVisualization(
			RunVisualizationMessage runVisualizationMessage) {
		RunResult runVisualizationResult = port
				.runVisualization(runVisualizationMessage);
		BigInteger visualizationRunId = runVisualizationResult.getRunId();
		return visualizationRunId;
	}
	
	protected static boolean waitForRunToCompleteOrFail(
			BigInteger runIdentification, Authentication authentication) {
		
		while (RUN_IS_NOT_COMPLETED_OR_FAILED) {
			try {
				Thread.sleep(TWO_SECONDS);
				RunStatusRequest request = new RunStatusRequest();
				request.setRunIdentification(runIdentification);
				MethodCallStatus callStatus = port
						.getRunStatus(request);
				MethodCallStatusEnum status = callStatus.getStatus();
				String message = callStatus.getMessage();
				
				System.out.printf("The status of run %s is: %s\n",
						runIdentification, message);
				
				if (status == null) {
					System.out
							.printf("Fatal Web Service Error: The Web service did not return a status for run: %s\n",
									runIdentification.toString());
					System.exit(-1);
				}
				
				switch (status) {
					case AUTHENTICATION_FAILURE:
					case UNAUTHORIZED:
						System.out
								.printf("No authorization for this run! Error message is: %s\n",
										message);
						return RUN_FAILED;
					case FAILED:
					case UNKNOWN_RUNID:
						System.out.printf(
								"Run Failed. The status message is: %s\n", message);
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
			ApolloServiceV40 ss = new ApolloServiceV40(new URL(WSDL_LOC),
					SERVICE_NAME);
			port = ss.getApolloServiceEndpoint();
		} catch (MalformedURLException ex) {
			throw new ExceptionInInitializerError("MalformedURLException: "
					+ ex.getMessage());
		}
	}


}
