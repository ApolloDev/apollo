package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apolloclient.TutorialWebServiceClient.VisualizerIdentificationEnum;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleRunSimulationMessageFactory;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleUseOfVaccinationControlStrategy;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategy extends
		AbstractRunAndVisualizeSimulationClass {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
	}

	protected void createIncidenceVisualizationForMultipleSimulations(Authentication authentication,
			List<RunIdentificationAndLabel> runIdentificationsAndLabels) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(authentication);

		SoftwareIdentification softwareIdentificationForTimeSeriesVisualizer = ApolloTypeFactory
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

	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter2_RunSimulationWithNoIntervention tutorialChapter2Example = new TutorialChapter2_RunSimulationWithNoIntervention();



		RunSimulationMessage runSimulationMessageWithoutVaccination = ExampleRunSimulationMessageFactory
				.getRunSimulationMessage();
		BigInteger noInterventionSimulationId = TutorialWebServiceClient
				.runSimulation(runSimulationMessageWithoutVaccination);

		TutorialChapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategy();
		ExampleUseOfVaccinationControlStrategy tutorialChapter8Config = new ExampleUseOfVaccinationControlStrategy();

		RunSimulationMessage runSimulationMessageWithVaccination = tutorialChapter8Config
				.getRunSimulationMessage();
		BigInteger vaccinationSimulationId = tutorialChapter8
				.runSimulationAndDisplayResults(runSimulationMessageWithVaccination);

		List<RunIdentificationAndLabel> runIdentificationAndLabels = new ArrayList<RunIdentificationAndLabel>();
		RunIdentificationAndLabel noInterventionIdentificationAndLabel = new RunIdentificationAndLabel();
		noInterventionIdentificationAndLabel.setRunIdentification(noInterventionSimulationId);
		noInterventionIdentificationAndLabel.setRunLabel("No Intervention");
		
		RunIdentificationAndLabel vaccinationIdentificationAndLabel = new RunIdentificationAndLabel();
		vaccinationIdentificationAndLabel.setRunIdentification(vaccinationSimulationId);
		vaccinationIdentificationAndLabel.setRunLabel("Vaccination");
		
		runIdentificationAndLabels.add(noInterventionIdentificationAndLabel);
		runIdentificationAndLabels.add(vaccinationIdentificationAndLabel);
		
		tutorialChapter8.createIncidenceVisualizationForMultipleSimulations(
				ExampleRunSimulationMessageFactory.getAuthentication(
						ExampleRunSimulationMessageFactory.REQUESTER_ID,
						ExampleRunSimulationMessageFactory.REQUESTER_PASSWORD), runIdentificationAndLabels);
	}

}
