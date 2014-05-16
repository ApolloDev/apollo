package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;


public class TutorialChapter8_RunSimulationWithVaccinationControlStrategy extends
		TutorialWebServiceClient {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
		// TODO Auto-generated constructor stub
	}
/*
	public TutorialChapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
	}

	protected void createIncidenceVisualizationForMultipleSimulations(Authentication authentication, String... simulatorRunIds) {
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(authentication);
		
		SoftwareIdentification softwareIdentificationForTimeSeriesVisualizer = getSoftwareIdentificationForVisualizer(VisualizerIdentificationEnum.TIME_SERIES);
		runVisualizationMessage.setVisualizerIdentification(softwareIdentificationForTimeSeriesVisualizer);
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
		runAndSoftwareIdentification.setSoftwareId(softwareIdentificationForTimeSeriesVisualizer);
		runAndSoftwareIdentification.setRunId(runVisualizationResult.getVisualizationRunId());

		if (waitForRunToCompleteOrFail(runAndSoftwareIdentification).getStatus() == MethodCallStatusEnum.COMPLETED) {
			System.out.printf("The following resources were returned from the %s visualizer:\n", softwareIdentificationForTimeSeriesVisualizer.getSoftwareName());
			for (UrlOutputResource r : getPort().getVisualizerOutputResources(runAndSoftwareIdentification)
					.getUrlOutputResources()) {
				System.out.println("\t" + r.getURL());
			}
		}

	}

	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter2_RunSimulationWithNoIntervention tutorialChapter2Example = new TutorialChapter2_RunSimulationWithNoIntervention();

		ExampleRunSimulationMessageFactory tutorialChapter2Config = new ExampleRunSimulationMessageFactory();

		RunSimulationMessage runSimulationMessageWithoutVaccination = tutorialChapter2Config.getRunSimulationMessage();
		RunAndSoftwareIdentification noVaccinationRunAndSoftwareId = tutorialChapter2Example
				.runSimulation(runSimulationMessageWithoutVaccination);

		TutorialChapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategy();
		ExampleUseOfVaccinationControlStrategy tutorialChapter8Config = new ExampleUseOfVaccinationControlStrategy();

		RunSimulationMessage runSimulationMessageWithVaccination = tutorialChapter8Config.getRunSimulationMessage();
		RunAndSoftwareIdentification vaccinationRunAndSoftwareId = tutorialChapter8
				.runSimulationAndDisplayResults(runSimulationMessageWithVaccination);

		tutorialChapter8.createIncidenceVisualizationForMultipleSimulations(ExampleRunSimulationMessageFactory.getAuthentication(ExampleRunSimulationMessageFactory.REQUESTER_ID, ExampleRunSimulationMessageFactory.REQUESTER_PASSWORD),
				noVaccinationRunAndSoftwareId.getRunId(), vaccinationRunAndSoftwareId.getRunId());
	}
*/
}
