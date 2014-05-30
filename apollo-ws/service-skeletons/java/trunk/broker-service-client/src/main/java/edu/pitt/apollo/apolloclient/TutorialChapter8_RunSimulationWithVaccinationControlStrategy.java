package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.runsimulationmessages.ExampleVaccinationControlStrategy;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategy extends AbstractRunAndVisualizeSimulationClass {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
	}

	public void runExample() {
		RunSimulationMessage runSimulationMessageWithoutVaccination = ApolloServiceTypeFactory.getDefaultRunSimulationMessage();

		ExampleVaccinationControlStrategy vaccinationControlStrategy = new ExampleVaccinationControlStrategy();
		RunSimulationMessage runSimulationMessageWithVaccination = vaccinationControlStrategy
				.addVaccinationControlStrategyToRunSimulationMessage(ApolloServiceTypeFactory.getDefaultRunSimulationMessage());

		runScenariosAndDisplayResults("No Vaccination", runSimulationMessageWithoutVaccination, "Vaccination",
				runSimulationMessageWithVaccination);

	}

	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategy();
		tutorialChapter8.runExample();

	}

}
