/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.pitt.apollo.apolloclient.tutorial;

import java.net.MalformedURLException;
import java.util.ArrayList;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.SimulatorIdentificationEnum;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleVaccinationControlStrategy;
import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v2_0_1.FixedDuration;
import edu.pitt.apollo.types.v2_0_1.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0_1.Infection;
import edu.pitt.apollo.types.v2_0_1.InfectionAcquisitionFromInfectiousHost;
import edu.pitt.apollo.types.v2_0_1.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v2_0_1.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.TriggerDefinition;
import edu.pitt.apollo.types.v2_0_1.Vaccination;

public class Chapter9_LibraryExample extends AbstractRunAndVisualizeSimulationClass {

	public String infectionUuid = "";
	public String infectiousDiseaseScenarioUuid = "";
	public String vaccinationControlStrategyUuid = "";
	public ExampleVaccinationControlStrategy vaccinationControlStrategy;

	public Chapter9_LibraryExample() throws MalformedURLException {
		super();
		vaccinationControlStrategy = new ExampleVaccinationControlStrategy();
	}

	protected Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId("TutorialUser");
		auth.setRequesterPassword("TutorialPassword");
		return auth;
	}

	public void savePredefinedItemsToApolloLibrary() {

		RunSimulationMessage runSimulationMessage = vaccinationControlStrategy
				.addVaccinationControlStrategyToRunSimulationMessage(ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR));

		InfectiousDiseaseScenario infectiousDiseaseScenario = runSimulationMessage.getInfectiousDiseaseScenario();
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("tutorial");

		Infection infection = infectiousDiseaseScenario.getInfections().get(0);

		AddLibraryItemResult result = TutorialWebServiceClient.addLibraryItem(getAuthentication(), infection,
				"The Infection instance of the InfectiousDiseaseScenario from the tutorial..", "Tutorial", "Infection", labels);
		infectionUuid = result.getUuid();
		System.out.println("Uuid returned for newly saved Infection instance: " + result.getUuid());

		InfectiousDiseaseControlStrategy vaccinationControlStrategy = infectiousDiseaseScenario
				.getInfectiousDiseaseControlStrategies().get(0);

		result = TutorialWebServiceClient
				.addLibraryItem(getAuthentication(), vaccinationControlStrategy,
						"The Vaccination Control Strategy used in the tutorial", "Tutorial",
						"InfectiousDiseaseControlStrategy", labels);

		vaccinationControlStrategyUuid = result.getUuid();
		System.out.println("Uuid returned for newly saved VaccinationControlStrategy instance: " + result.getUuid());

		result = TutorialWebServiceClient.addLibraryItem(getAuthentication(), infectiousDiseaseScenario,
				"The Infectious Disease Scenario used in the tutorial", "Tutorial", "InfectiousDiseaseScenario", labels);

		infectiousDiseaseScenarioUuid = result.getUuid();
		System.out.println("Uuid returned for newly saved InfectionDiseaseScenario instance: " + result.getUuid());
		System.out.println();

	}

	public void printInfectionToConsole() {
		System.out.println("Retrieving Infection instance from Library using Uuid: " + infectionUuid + "\n");
		GetLibraryItemResult infectionItem = TutorialWebServiceClient.getLibraryItem(infectionUuid);
		Infection infection = (Infection) infectionItem.getCuratedLibraryItemContainer().getApolloIndexableItem();
		System.out.println("Infection instance retrieved from the library!  Here are the attributes:");
		System.out.println("\tHost Taxon ID: " + infection.getHostTaxonId());
		System.out.println("\tPathogen Taxon ID: " + infection.getPathogenTaxonId().getNcbiTaxonId());

		System.out.println("\tInfectionAcquisitionFromInfectiousHost:");
		InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infection
				.getInfectionAcquisitionsFromInfectiousHosts().get(0);
		System.out
				.println("\t\tInfectious Host Taxon ID: " + infectionAcquisitionFromInfectiousHost.getInfectiousHostTaxonId());
		FixedDuration infectiousPeriodDuration = (FixedDuration) infectionAcquisitionFromInfectiousHost
				.getInfectiousPeriodDuration();
		System.out.println("\t\tInfectiousPeriodDuration: " + infectiousPeriodDuration.getValue());

		FixedDuration latentPeriodDuration = (FixedDuration) infectionAcquisitionFromInfectiousHost.getLatentPeriodDuration();
		System.out.println("\t\tLatentPeriodDuration: " + latentPeriodDuration.getValue());

		System.out.println("\t\tBasic Reproduction Number: "
				+ infectionAcquisitionFromInfectiousHost.getBasicReproductionNumber());
		System.out.println();
	}

	public void printVaccinationControlStrategyToConsole() {
		GetLibraryItemResult vaccinationControlStrategyItem = TutorialWebServiceClient
				.getLibraryItem(vaccinationControlStrategyUuid);
		IndividualTreatmentControlStrategy vaccinationControlStrategy = (IndividualTreatmentControlStrategy) vaccinationControlStrategyItem
				.getCuratedLibraryItemContainer().getApolloIndexableItem();
		System.out.println("VaccinationControlStrategy instance retrieved from the library:");
		System.out.println("\tDescription: " + vaccinationControlStrategy.getDescription());
		System.out.println("\tCompliance: " + vaccinationControlStrategy.getCompliance().getProbability());
		if (vaccinationControlStrategy.getControlStrategyResponseDelay() instanceof FixedDuration) {
			System.out.println("\tResponse Delay: "
					+ ((FixedDuration) vaccinationControlStrategy.getControlStrategyResponseDelay()).getValue());
		}
		for (TriggerDefinition trigger : vaccinationControlStrategy.getControlStrategyStartTime()) {
			DiseaseSurveillanceTriggerDefinition triggerDefinition = (DiseaseSurveillanceTriggerDefinition) trigger;
			System.out.println("\tControl Strategy Starts when : "
					+ triggerDefinition.getReactiveControlStrategyOperator().toString() + " "
					+ triggerDefinition.getReactiveControlStrategyThreshold() + " "
					+ triggerDefinition.getReactiveControlStrategyTest() + " "
					+ triggerDefinition.getUnitOfMeasureForThreshold().toString());

		}

		Vaccination vaccination = (Vaccination) vaccinationControlStrategy.getIndividualTreatment();
		System.out.println("\tNum Doses in Treatment Course:" + vaccination.getNumDosesInTreatmentCourse());
		System.out.println("\t...\n");
	}

	public InfectiousDiseaseScenario loadInfectiousDiseaseScenario() {
		System.out.println("Retrieving InfectiousDiseaseScenario instance from Library using Uuid: "
				+ infectiousDiseaseScenarioUuid + "\n");
		GetLibraryItemResult infectionDiseaseScenarioLibraryItem = TutorialWebServiceClient
				.getLibraryItem(infectiousDiseaseScenarioUuid);
		InfectiousDiseaseScenario infectiousDiseaseScenario = (InfectiousDiseaseScenario) infectionDiseaseScenarioLibraryItem
				.getCuratedLibraryItemContainer().getApolloIndexableItem();

		InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infectiousDiseaseScenario
				.getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0);
		if (infectionAcquisitionFromInfectiousHost != null) {
			System.out.println("The existing Basic Reproduction Number in the InfectionDiseaseScenario is: "
					+ infectionAcquisitionFromInfectiousHost.getBasicReproductionNumber());
		}
		return infectiousDiseaseScenario;
	}

	private InfectiousDiseaseScenario setR0(InfectiousDiseaseScenario infectiousDiseaseScenario, Double value) {
		System.out.println("Changing the Basic Reproduction Number to " +  value + "...");
		InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infectiousDiseaseScenario
				.getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0);
		infectionAcquisitionFromInfectiousHost.setBasicReproductionNumber(1.7);
		return infectiousDiseaseScenario;
	}

	private Double getR0(InfectiousDiseaseScenario infectiousDiseaseScenario) {
		return infectiousDiseaseScenario.getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0)
				.getBasicReproductionNumber();
	}

	

	public void runExample() {

		savePredefinedItemsToApolloLibrary();
		printInfectionToConsole();
		printVaccinationControlStrategyToConsole();

		InfectiousDiseaseScenario infectiousDiseaseScenario = loadInfectiousDiseaseScenario();
		InfectiousDiseaseScenario infectiousDiseaseScenarioWithIncreasedR0 = setR0(loadInfectiousDiseaseScenario(),
				getR0(infectiousDiseaseScenario) + 0.5);

		RunSimulationMessage runSimulationMessageWithStoredR0 = ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR);
		runSimulationMessageWithStoredR0.setInfectiousDiseaseScenario(infectiousDiseaseScenario);

		RunSimulationMessage runSimulationMessageWithIncreasedR0 = ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR);
		runSimulationMessageWithIncreasedR0.setInfectiousDiseaseScenario(infectiousDiseaseScenarioWithIncreasedR0);

		runScenariosAndDisplayResults("Baseline R0", runSimulationMessageWithStoredR0, "Baseline R0 + 0.5",
				runSimulationMessageWithIncreasedR0);
	}

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		Chapter9_LibraryExample tutorialChapter9 = new Chapter9_LibraryExample();
		tutorialChapter9.runExample();

	}

}
