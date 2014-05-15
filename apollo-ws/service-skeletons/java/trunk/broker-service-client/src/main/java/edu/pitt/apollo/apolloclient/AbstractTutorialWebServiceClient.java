package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceV201;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;

public class AbstractTutorialWebServiceClient {
	public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice2.0.1/services/apolloservice?wsdl";
	private ApolloServiceEI port;
	private static final QName SERVICE_NAME = new QName(
			"http://service.apollo.pitt.edu/apolloservice/v2_0_1/", "ApolloService_v2.0.1");
	public static final long TWO_SECONDS = 2000;
	public static final boolean SIMULATION_IS_RUNNING = true;

	public static final String REQUESTER_ID = "TutorialUser";

	public static final String REQUESTER_PASSWORD = "TutorialPassword";

	protected static Authentication getAuthentication(String requesterId, String requesterPassword) {
		Authentication auth = new Authentication();
		auth.setRequesterId(requesterId);
		auth.setRequesterPassword(requesterPassword);
		return auth;
	}

	protected static RunAndSoftwareIdentification getRunAndSoftwareIdentification(String runId,
			SoftwareIdentification softwareIdentification) {
		RunAndSoftwareIdentification runAndSoftwareIdentification = new RunAndSoftwareIdentification();
		runAndSoftwareIdentification.setRunId(runId);
		runAndSoftwareIdentification.setSoftwareId(softwareIdentification);
		return runAndSoftwareIdentification;
	}

	public AbstractTutorialWebServiceClient() throws MalformedURLException {
		ApolloServiceV201 ss = new ApolloServiceV201(new URL(WSDL_LOC), SERVICE_NAME);
		port = ss.getApolloServiceEndpoint();
	}

	public ApolloServiceEI getPort() {
		return port;
	}

	protected RunVisualizationMessage getRunVisualizationMessage(Authentication visualizerAuthentication,
			SoftwareIdentification visualizerSoftwareIdentification, String... simulatorRunIds) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(visualizerAuthentication);
		runVisualizationMessage.setVisualizerIdentification(visualizerSoftwareIdentification);
		for (String simulatorRunId : simulatorRunIds) {
			runVisualizationMessage.getSimulationRunIds().add(simulatorRunId);
		}
		return runVisualizationMessage;
	}

	public SoftwareIdentification getSoftwareIdentifiationForGaia() {
		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareName("GAIA");
		softwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
		softwareId.setSoftwareVersion("1.0");
		softwareId.setSoftwareDeveloper("PSC");
		return softwareId;
	}

	public SoftwareIdentification getSoftwareIdentifiationForTimeSeriesVisualizer() {
		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareName("Time Series Visualizer");
		softwareId.setSoftwareType(ApolloSoftwareTypeEnum.VISUALIZER);
		softwareId.setSoftwareVersion("1.0");
		softwareId.setSoftwareDeveloper("UPitt");
		return softwareId;
	}

	protected MethodCallStatus getStatusOfWebServiceCall(RunAndSoftwareIdentification runAndSoftwareId) {
		String simulator = runAndSoftwareId.getSoftwareId().getSoftwareName();
		String runId = runAndSoftwareId.getRunId();
		while (SIMULATION_IS_RUNNING) {
			try {
				Thread.sleep(TWO_SECONDS);
				MethodCallStatus callStatus = port.getRunStatus(runAndSoftwareId);
				MethodCallStatusEnum status = callStatus.getStatus();
				String message = callStatus.getMessage();

				System.out.printf("The status of the %s simulaton is %s. The status message is: %s\n",
						simulator, status, message);

				if (status == null) {
					System.out
							.printf("Fatal Simulator Web Service Error: The %s simulator did not return a status for run: %s\n",
									simulator, runId);
					System.exit(-1);
				}

				switch (status) {
				case AUTHENTICATION_FAILURE:
				case UNAUTHORIZED:
					System.out.printf("No authorization for this run! Error message is: %s\n", message);
					return callStatus;
				case FAILED:
				case UNKNOWN_RUNID:
					System.out.printf("Run Failed. The status message is: %s\n");
					return callStatus;
				case LOG_FILES_WRITTEN:
				case COMPLETED:
					return callStatus;
				default:
					Thread.sleep(TWO_SECONDS);
					break;
				}
			} catch (InterruptedException sleepInterrupted) {
				// this is acceptable
			}
		}
	}

	protected RunAndSoftwareIdentification runSimulation(RunSimulationMessage runSimulationMessage) {
		BigInteger simulationRunId = port.runSimulation(runSimulationMessage);
		System.out.printf("The simulator returned a runId of %s\n", simulationRunId);

		RunAndSoftwareIdentification simulationRunAndSoftwareId = getRunAndSoftwareIdentification(
				simulationRunId.toString(), runSimulationMessage.getSimulatorIdentification());

		MethodCallStatus status = getStatusOfWebServiceCall(simulationRunAndSoftwareId);
		if (status.getStatus() == MethodCallStatusEnum.COMPLETED
				|| status.getStatus() == MethodCallStatusEnum.LOG_FILES_WRITTEN) {
			return simulationRunAndSoftwareId;
		} else {
			System.out.printf("The status of the simulator was %s with message: %s\n",
					status.getStatus(), status.getMessage());
			return null;
		}
	}

	protected RunAndSoftwareIdentification runSimulationAndDisplayResults(
			RunSimulationMessage runSimulationMessage) {
		RunAndSoftwareIdentification simulatorRunAndSoftwareId = runSimulation(runSimulationMessage);
		if (simulatorRunAndSoftwareId != null) {
			runVisualizerAndDisplayResults(simulatorRunAndSoftwareId);
		}
		return simulatorRunAndSoftwareId;
	}

	protected RunAndSoftwareIdentification runVisualization(RunVisualizationMessage runVisualizationMessage) {
		RunVisualizationResult runVisualizationResult = port.runVisualization(runVisualizationMessage);
		String visualizationRunId = runVisualizationResult.getVisualizationRunId();
		RunAndSoftwareIdentification visualizationRunAndSoftwareId = getRunAndSoftwareIdentification(
				visualizationRunId, runVisualizationMessage.getVisualizerIdentification());
		return visualizationRunAndSoftwareId;
	}

	protected void runVisualizerAndDisplayResults(RunAndSoftwareIdentification simulatorAndSoftwareIdentification) {
		String visualizer = simulatorAndSoftwareIdentification.getSoftwareId().getSoftwareName();
		String simulatorRunId = simulatorAndSoftwareIdentification.getRunId();
		
		System.out.printf("Visualizing runId: %s using the %s visualizer...\n", simulatorRunId, visualizer);

		Authentication visualizerAuthentication = getAuthentication(REQUESTER_ID, REQUESTER_PASSWORD);

		RunVisualizationMessage runVisualizationMessage = getRunVisualizationMessage(
				visualizerAuthentication, getSoftwareIdentifiationForGaia(), simulatorRunId);

		RunAndSoftwareIdentification visualizationRunAndSoftwareId = runVisualization(runVisualizationMessage);

		if (getStatusOfWebServiceCall(visualizationRunAndSoftwareId).getStatus() == MethodCallStatusEnum.COMPLETED) {
			System.out.printf("The following resources were returned from the %s visualizer:\n", visualizer);
			GetVisualizerOutputResourcesResult results = port
					.getVisualizerOutputResources(visualizationRunAndSoftwareId);
			if (results.getMethodCallStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
				for (UrlOutputResource r : results.getUrlOutputResources())
					System.out.println("\t" + r.getURL());

			} else {
				System.out
						.printf("Unable to retrieve visualizer output resources. MethodCallStatus was %s with message: %s\n",
								results.getMethodCallStatus().getStatus(), results.getMethodCallStatus()
										.getMessage());
			}
		}
	}

}
