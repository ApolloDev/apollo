package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.TutorialChapter2_ExampleConfig;
import edu.pitt.apollo.examples.TutorialChapter8_RunSimulationWithVaccinationControlStrategyConfig;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample extends
		TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample() throws MalformedURLException {
		super();
	}

	protected void createIncidenceVisualizationForMultipleSimulations(Authentication authentication, String... simulatorRunIds) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(authentication);
		runVisualizationMessage.setVisualizerIdentification(getSoftwareIdentifiationForTimeSeriesVisualizer());
		for (String runId : simulatorRunIds) {
			runVisualizationMessage.getSimulationRunIds().add(runId);
		}

		String runIdString = "";
		for (String simulatorRunId : simulatorRunIds) {
			runIdString += simulatorRunId + ":";
		}
		runIdString = runIdString.substring(0, runIdString.length() - 1);

		RunVisualizationResult runVisualizationResult = getPort().runVisualization(runVisualizationMessage);

		RunAndSoftwareIdentification runAndSoftwareIdentification = new RunAndSoftwareIdentification();
		runAndSoftwareIdentification.setSoftwareId(getSoftwareIdentifiationForTimeSeriesVisualizer());
		runAndSoftwareIdentification.setRunId(runVisualizationResult.getVisualizationRunId());

		if (checkStatusOfWebServiceCall(runAndSoftwareIdentification).getStatus() == MethodCallStatusEnum.COMPLETED) {
			System.out.println("The following resources were returned from the "
					+ getSoftwareIdentifiationForTimeSeriesVisualizer().getSoftwareName() + " visualizer:");
			for (UrlOutputResource r : getPort().getVisualizerOutputResources(runAndSoftwareIdentification)
					.getUrlOutputResources()) {
				System.out.println("\t" + r.getURL());
			}
		}

	}

	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter2_BasicRunSimulationExample tutorialChapter2Example = new TutorialChapter2_BasicRunSimulationExample();

		TutorialChapter2_ExampleConfig tutorialChapter2Config = new TutorialChapter2_ExampleConfig();

		RunSimulationMessage runSimulationMessageWithoutVaccination = tutorialChapter2Config.getRunSimulationMessage();
		RunAndSoftwareIdentification noVaccinationRunAndSoftwareId = tutorialChapter2Example
				.runSimulation(runSimulationMessageWithoutVaccination);

		TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample();
		TutorialChapter8_RunSimulationWithVaccinationControlStrategyConfig tutorialChapter8Config = new TutorialChapter8_RunSimulationWithVaccinationControlStrategyConfig();

		RunSimulationMessage runSimulationMessageWithVaccination = tutorialChapter8Config.getRunSimulationMessage();
		RunAndSoftwareIdentification vaccinationRunAndSoftwareId = tutorialChapter8
				.runSimulationAndDisplayResults(runSimulationMessageWithVaccination);

		tutorialChapter8.createIncidenceVisualizationForMultipleSimulations(TutorialChapter2_ExampleConfig.getAuthentication(),
				noVaccinationRunAndSoftwareId.getRunId(), vaccinationRunAndSoftwareId.getRunId());
	}

}
