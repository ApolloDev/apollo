package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apolloclient.ApolloServiceTypeFactory.VisualizerIdentificationEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;

/**
 * This provides helper methods to send requests to, and process the results of
 * Apollo Broker Service web service calls.
 * 
 * @author jdl50
 * 
 */
public abstract class AbstractRunAndVisualizeSimulationClass {
	public static final Object ID_NOT_SET_DUE_TO_RUN_FAILURE = null;

	protected static void createIncidenceVisualizationForMultipleSimulations(Authentication authentication,
			List<RunIdentificationAndLabel> runIdentificationsAndLabels) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(authentication);

		SoftwareIdentification softwareIdentificationForTimeSeriesVisualizer = ApolloServiceTypeFactory
				.getSoftwareIdentificationForVisualizer(VisualizerIdentificationEnum.TIME_SERIES);
		runVisualizationMessage.setVisualizerIdentification(softwareIdentificationForTimeSeriesVisualizer);

		runVisualizationMessage.getSimulationRunIds().addAll(runIdentificationsAndLabels);

		BigInteger visualizationRunId = TutorialWebServiceClient.runVisualization(runVisualizationMessage);

		if (TutorialWebServiceClient.waitForRunToCompleteOrFail(visualizationRunId) == TutorialWebServiceClient.RUN_WAS_SUCCESSFUL) {
			System.out.printf("The following resources were returned from the %s visualizer:\n",
					softwareIdentificationForTimeSeriesVisualizer.getSoftwareName());
			TutorialWebServiceClient.getUrlOutputResourcesForVisualization(visualizationRunId);
		}

	}

	public static void runScenariosAndDisplayResults(String simulation1Label, RunSimulationMessage runSimulationMessage1,
			String simulation2Label, RunSimulationMessage runSimulationMessage2) {

		BigInteger simulationRunIdForRun1 = TutorialWebServiceClient.runSimulation(runSimulationMessage1);
		BigInteger simulationRunIdForRun2 = TutorialWebServiceClient.runSimulation(runSimulationMessage2);

		if (simulationRunIdForRun1 != null && simulationRunIdForRun2 != null) {
			List<RunIdentificationAndLabel> runIdentificationsAndLabels = new ArrayList<RunIdentificationAndLabel>();

			RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
			runIdAndLabel.setRunIdentification(simulationRunIdForRun1);
			runIdAndLabel.setRunLabel(simulation1Label);
			runIdentificationsAndLabels.add(runIdAndLabel);

			runIdAndLabel = new RunIdentificationAndLabel();
			runIdAndLabel.setRunIdentification(simulationRunIdForRun2);
			runIdAndLabel.setRunLabel(simulation2Label);
			runIdentificationsAndLabels.add(runIdAndLabel);

			createIncidenceVisualizationForMultipleSimulations(ApolloServiceTypeFactory.getAuthentication(),
					runIdentificationsAndLabels);
		} else {
			System.out.println("There was an error running the simulations.");
		}

	}

	private String getSimulatorRunIdsAsString(List<RunIdentificationAndLabel> simulationRunIdentificationsAndLabels) {
		String simulatorRuns = "";
		for (RunIdentificationAndLabel runIdentificationAndLabel : simulationRunIdentificationsAndLabels) {
			simulatorRuns += runIdentificationAndLabel.getRunIdentification() + ", ";
		}
		simulatorRuns = simulatorRuns.substring(0, simulatorRuns.length() - 3);
		return simulatorRuns;

	}

	protected BigInteger runSimulationAndDisplayResults(RunSimulationMessage runSimulationMessage) {
		BigInteger simulatorRunId = TutorialWebServiceClient.runSimulation(runSimulationMessage);
		if (simulatorRunId != ID_NOT_SET_DUE_TO_RUN_FAILURE) {
			RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
			runIdentificationAndLabel.setRunLabel("test");
			runIdentificationAndLabel.setRunIdentification(simulatorRunId);
			RunVisualizationMessage runVisualizationMessage = ApolloServiceTypeFactory.getRunVisualizationMessage(
					runIdentificationAndLabel, VisualizerIdentificationEnum.TIME_SERIES);
			runVisualizationAndDisplayResults(runVisualizationMessage);
		}
		return simulatorRunId;
	}

	protected void runVisualizationAndDisplayResults(RunVisualizationMessage runVisualizationMessage) {
		String visualizer = runVisualizationMessage.getVisualizerIdentification().getSoftwareName();
		String simulatorRuns = getSimulatorRunIdsAsString(runVisualizationMessage.getSimulationRunIds());

		System.out.printf("Running %s visualizer, visualizing simulator runs: %s\n", visualizer, simulatorRuns);

		BigInteger visualizationIdentifier = TutorialWebServiceClient.runVisualization(runVisualizationMessage);

		boolean runWasSuccessful = TutorialWebServiceClient.waitForRunToCompleteOrFail(visualizationIdentifier);

		if (runWasSuccessful) {
			List<UrlOutputResource> visualizerOutputResources = TutorialWebServiceClient
					.getUrlOutputResourcesForVisualization(visualizationIdentifier);
			if (visualizerOutputResources.size() > 0) {
				System.out.printf("The following resources were returned from the %s visualizer:\n", visualizer);
				for (UrlOutputResource r : visualizerOutputResources)
					System.out.println("\t" + r.getURL());
			}
		}
	}
}
