package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.util.List;

import edu.pitt.apollo.apolloclient.TutorialWebServiceClient.VisualizerIdentificationEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;
/**
 * This provides helper methods to send requests to, and process the results of Apollo Broker Service web service calls.
 * @author jdl50
 *
 */
public abstract class AbstractRunAndVisualizeSimulationClass {
	public static final Object ID_NOT_SET_DUE_TO_RUN_FAILURE = null;
	
	private String getSimulatorRunIdsAsString(List<BigInteger> simulationRunIds) {
		String simulatorRuns = "";
		for (BigInteger simulationId : simulationRunIds) {
			simulatorRuns += simulationId + ", ";
		}
		simulatorRuns = simulatorRuns.substring(0, simulatorRuns.length() - 3);
		return simulatorRuns;

	}
	
	protected BigInteger runSimulationAndDisplayResults(
			RunSimulationMessage runSimulationMessage) {
		BigInteger simulatorRunId = TutorialWebServiceClient.runSimulation(runSimulationMessage);
		if (simulatorRunId != ID_NOT_SET_DUE_TO_RUN_FAILURE) {
			RunVisualizationMessage runVisualizationMessage = ApolloTypeFactory.getRunVisualizationMessage(
					simulatorRunId, VisualizerIdentificationEnum.GAIA);
			runVisualizationAndDisplayResults(runVisualizationMessage);
		}
		return simulatorRunId;
	}
	
	protected void runVisualizationAndDisplayResults(RunVisualizationMessage runVisualizationMessage) {
		String visualizer = runVisualizationMessage.getVisualizerIdentification().getSoftwareName();
		String simulatorRuns = getSimulatorRunIdsAsString(runVisualizationMessage.getSimulationRunIds());

		System.out.printf("Running %s visualizer, visualizing simulator runs: %s\n", visualizer,
				simulatorRuns);

		BigInteger visualizationIdentifier = TutorialWebServiceClient.runVisualization(runVisualizationMessage);

		boolean runWasSuccessful = TutorialWebServiceClient.waitForRunToCompleteOrFail(visualizationIdentifier);

		if (runWasSuccessful) {
			List<UrlOutputResource> visualizerOutputResources = TutorialWebServiceClient.getUrlOutputResourcesForVisualization(visualizationIdentifier);
			if (visualizerOutputResources.size() > 0) {
				System.out.printf("The following resources were returned from the %s visualizer:\n",
						visualizer);
				for (UrlOutputResource r : visualizerOutputResources)
					System.out.println("\t" + r.getURL());
			}
		}
	}
}
