package edu.pitt.apollo.apolloclient.tutorial;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.VisualizerIdentificationEnum;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;

import java.math.BigInteger;

/**
 * This provides helper methods to send requests to, and process the results of
 * Apollo Broker Service web service calls.
 * 
 * @author jdl50
 * 
 */
public abstract class AbstractRunAndVisualizeSimulationClass {
	public static final Object ID_NOT_SET_DUE_TO_RUN_FAILURE = null;

	protected static void runScenariosAndDisplayResults(String simulation1Label, RunSimulationMessage runSimulationMessage1,
			String simulation2Label, RunSimulationMessage runSimulationMessage2, Authentication authentication) {

		BigInteger simulationRunIdForRun1 = TutorialWebServiceClient.runSimulation(runSimulationMessage1, authentication);
		BigInteger simulationRunIdForRun2 = TutorialWebServiceClient.runSimulation(runSimulationMessage2, authentication);

		if (simulationRunIdForRun1 != null && simulationRunIdForRun2 != null) {

			RunIdentificationAndLabel[] runIdentificationsAndLabels = new RunIdentificationAndLabel[2];

			RunIdentificationAndLabel runIdAndLabel = new RunIdentificationAndLabel();
			runIdAndLabel.setRunIdentification(simulationRunIdForRun1);
			runIdAndLabel.setRunLabel(simulation1Label);
			runIdentificationsAndLabels[0] = runIdAndLabel;

			runIdAndLabel = new RunIdentificationAndLabel();
			runIdAndLabel.setRunIdentification(simulationRunIdForRun2);
			runIdAndLabel.setRunLabel(simulation2Label);
			runIdentificationsAndLabels[1] = runIdAndLabel;

			runVisualizationsAndDisplayResults(runIdentificationsAndLabels, VisualizerIdentificationEnum.TIME_SERIES, authentication);
		} else {
			System.out.println("There was an error running the simulations.");
		}

	}

	protected static BigInteger runScenarioAndDisplayResults(RunSimulationMessage runSimulationMessage, Authentication authentication) {
		BigInteger simulatorRunId = TutorialWebServiceClient.runSimulation(runSimulationMessage, authentication);
		if (simulatorRunId != ID_NOT_SET_DUE_TO_RUN_FAILURE) {
			RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
			runIdentificationAndLabel.setRunLabel("test");
			runIdentificationAndLabel.setRunIdentification(simulatorRunId);
			runVisualizationAndDisplayResults(runIdentificationAndLabel, VisualizerIdentificationEnum.TIME_SERIES, authentication);
		//	runVisualizationAndDisplayResults(runIdentificationAndLabel, VisualizerIdentificationEnum.GAIA);
		} else {
			System.out.println("There was an error running the simulations.");
		}
		return simulatorRunId;
	}

	protected static void runVisualizationAndDisplayResults(RunIdentificationAndLabel runIdentificationAndLabel,
			VisualizerIdentificationEnum visualizerIdentificationEnum, Authentication authentication) {
		RunIdentificationAndLabel[] runIdentificationAndLabels = new RunIdentificationAndLabel[1];
		runIdentificationAndLabels[0] = runIdentificationAndLabel;
		runVisualizationsAndDisplayResults(runIdentificationAndLabels, visualizerIdentificationEnum, authentication);
	}

	protected static void runVisualizationsAndDisplayResults(RunIdentificationAndLabel[] runIdentificationsAndLabels,
                                                             VisualizerIdentificationEnum visualizerIdentificationEnum, Authentication authentication) {
		RunVisualizationMessage runVisualizationMessage = ApolloServiceTypeFactory.getRunVisualizationMessage(
				ApolloServiceTypeFactory.getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum),
				runIdentificationsAndLabels);

		SoftwareIdentification softwareIdentificationForTimeSeriesVisualizer = ApolloServiceTypeFactory
				.getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum);
		runVisualizationMessage.setSoftwareIdentification(softwareIdentificationForTimeSeriesVisualizer);

//		for (RunIdentificationAndLabel runIdentificationAndLabel : runIdentificationsAndLabels)
//			runVisualizationMessage.getSimulationRunIds().add(runIdentificationAndLabel);

		BigInteger visualizationRunId = TutorialWebServiceClient.runVisualization(runVisualizationMessage);

		if (TutorialWebServiceClient.waitForRunToCompleteOrFail(visualizationRunId, authentication) == TutorialWebServiceClient.RUN_WAS_SUCCESSFUL) {
			System.out.printf("The following resources were returned from the %s visualizer:\n",
					softwareIdentificationForTimeSeriesVisualizer.getSoftwareName());
//			TutorialWebServiceClient.getUrlOutputResourcesForVisualization(visualizationRunId);
		} else {
			System.out.println("There was an error running the visualizations.");

		}

	}
}
