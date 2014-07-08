package edu.pitt.apollo.apolloclient.tutorial;

import java.math.BigInteger;
import java.util.List;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.VisualizerIdentificationEnum;
import edu.pitt.apollo.types.v2_0_2.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;

/**
 * This provides helper methods to send requests to, and process the results of
 * Apollo Broker Service web service calls.
 * 
 * @author jdl50
 * 
 */
public abstract class AbstractRunAndVisualizeSimulationClass {
	public static final Object ID_NOT_SET_DUE_TO_RUN_FAILURE = null;

	private static String getSimulatorRunIdsAsString(List<RunIdentificationAndLabel> simulationRunIdentificationsAndLabels) {
		String simulatorRuns = "";
		for (RunIdentificationAndLabel runIdentificationAndLabel : simulationRunIdentificationsAndLabels) {
			simulatorRuns += runIdentificationAndLabel.getRunIdentification() + ", ";
		}
		simulatorRuns = simulatorRuns.substring(0, simulatorRuns.length() - 3);
		return simulatorRuns;

	}

	protected static void runScenariosAndDisplayResults(String simulation1Label, RunSimulationMessage runSimulationMessage1,
			String simulation2Label, RunSimulationMessage runSimulationMessage2) {

		BigInteger simulationRunIdForRun1 = TutorialWebServiceClient.runSimulation(runSimulationMessage1);
		BigInteger simulationRunIdForRun2 = TutorialWebServiceClient.runSimulation(runSimulationMessage2);

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

			runVisualizationsAndDisplayResults(runIdentificationsAndLabels, VisualizerIdentificationEnum.TIME_SERIES);
		} else {
			System.out.println("There was an error running the simulations.");
		}

	}

	protected static BigInteger runScenarioAndDisplayResults(RunSimulationMessage runSimulationMessage) {
		BigInteger simulatorRunId = TutorialWebServiceClient.runSimulation(runSimulationMessage);
		if (simulatorRunId != ID_NOT_SET_DUE_TO_RUN_FAILURE) {
			RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
			runIdentificationAndLabel.setRunLabel("test");
			runIdentificationAndLabel.setRunIdentification(simulatorRunId);
			runVisualizationAndDisplayResults(runIdentificationAndLabel, VisualizerIdentificationEnum.TIME_SERIES);
			runVisualizationAndDisplayResults(runIdentificationAndLabel, VisualizerIdentificationEnum.GAIA);
		} else {
			System.out.println("There was an error running the simulations.");
		}
		return simulatorRunId;
	}

	protected static void runVisualizationAndDisplayResults(RunIdentificationAndLabel runIdentificationAndLabel,
			VisualizerIdentificationEnum visualizerIdentificationEnum) {
		RunIdentificationAndLabel[] runIdentificationAndLabels = new RunIdentificationAndLabel[1];
		runIdentificationAndLabels[0] = runIdentificationAndLabel;
		runVisualizationsAndDisplayResults(runIdentificationAndLabels, visualizerIdentificationEnum);
	}

	protected static void runVisualizationsAndDisplayResults(RunIdentificationAndLabel[] runIdentificationsAndLabels,
			VisualizerIdentificationEnum visualizerIdentificationEnum) {
		RunVisualizationMessage runVisualizationMessage = ApolloServiceTypeFactory.getRunVisualizationMessage(
				ApolloServiceTypeFactory.getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum),
				runIdentificationsAndLabels);

		SoftwareIdentification softwareIdentificationForTimeSeriesVisualizer = ApolloServiceTypeFactory
				.getSoftwareIdentificationForVisualizer(visualizerIdentificationEnum);
		runVisualizationMessage.setVisualizerIdentification(softwareIdentificationForTimeSeriesVisualizer);

		for (RunIdentificationAndLabel runIdentificationAndLabel : runIdentificationsAndLabels)
			runVisualizationMessage.getSimulationRunIds().add(runIdentificationAndLabel);

		BigInteger visualizationRunId = TutorialWebServiceClient.runVisualization(runVisualizationMessage);

		if (TutorialWebServiceClient.waitForRunToCompleteOrFail(visualizationRunId) == TutorialWebServiceClient.RUN_WAS_SUCCESSFUL) {
			System.out.printf("The following resources were returned from the %s visualizer:\n",
					softwareIdentificationForTimeSeriesVisualizer.getSoftwareName());
			TutorialWebServiceClient.getUrlOutputResourcesForVisualization(visualizationRunId);
		} else {
			System.out.println("There was an error running the visualizations.");

		}

	}
}
