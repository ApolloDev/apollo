package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apolloclient.ApolloServiceTypeFactory.VisualizerIdentificationEnum;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleInfectiousDiseaseScenario;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleVaccinationControlStrategy;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.RunIdentificationAndLabel;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategy extends AbstractRunAndVisualizeSimulationClass {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
	}
	
	public void runExample() {
		RunSimulationMessage runSimulationMessageWithoutVaccination = ApolloServiceTypeFactory.getDefaultRunSimulationMessage();
		
		ExampleVaccinationControlStrategy vaccinationControlStrategy = new ExampleVaccinationControlStrategy();
		RunSimulationMessage runSimulationMessageWithVaccination = vaccinationControlStrategy
				.addVaccinationControlStrategyToRunSimulationMessage(ApolloServiceTypeFactory.getDefaultRunSimulationMessage());


		runScenariosAndDisplayResults("No Vaccination", runSimulationMessageWithoutVaccination, "Vaccination", runSimulationMessageWithVaccination);
		
	}

	public static void main(String[] args) throws MalformedURLException {
		TutorialChapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategy();
		tutorialChapter8.runExample();

	}

}
