package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;
import java.util.ArrayList;

import edu.pitt.apollo.types.v2_0.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0.Authentication;
import edu.pitt.apollo.types.v2_0.FixedStartTime;
import edu.pitt.apollo.types.v2_0.GetLibraryItemResult;
import edu.pitt.apollo.types.v2_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0.Infection;
import edu.pitt.apollo.types.v2_0.InfectionAcquisition;
import edu.pitt.apollo.types.v2_0.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v2_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.Vaccination;

public class LibraryClient extends
		RunSimulationWithVaccinationControlStrategyExample {

	public String infectionUuid = "";
	public String infectiousDiseaseScenarioUuid = "";
	public String vaccinationControlStrategyUuid = "";

	public LibraryClient() throws MalformedURLException {
		super();
	}

	protected Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId("TutorialUser");
		auth.setRequesterPassword("TutorialPassword");
		return auth;
	}

	public void save() {
		RunSimulationMessage runSimulationMessage = getRunSimulationMessage();

		InfectiousDiseaseScenario infectiousDiseaseScenario = runSimulationMessage
				.getInfectiousDiseaseScenario();
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("tutorial");

		Infection infection = infectiousDiseaseScenario.getInfections().get(0);

		AddLibraryItemResult result = getPort()
				.addLibraryItem(
						getAuthentication(),
						infection,
						"The Infection instance of the InfectiousDiseaseScenario from the tutorial..",
						"Tutorial", "Infection", labels);
		infectionUuid = result.getUuid(); // here nick example
		System.out.println(result.getUuid());

		InfectiousDiseaseControlStrategy vaccinationControlStrategy = infectiousDiseaseScenario
				.getInfectiousDiseaseControlStrategies().get(0);

		result = getPort().addLibraryItem(getAuthentication(),
				vaccinationControlStrategy,
				"The Vaccination Control Strategy used in the tutorial",
				"Tutorial", "InfectiousDiseaseControlStrategy", labels);

		vaccinationControlStrategyUuid = result.getUuid();
		System.out.println(result.getUuid());

		result = getPort().addLibraryItem(getAuthentication(),
				infectiousDiseaseScenario,
				"The Infectious Disease Scenario used in the tutorial",
				"Tutorial", "InfectiousDiseaseScenario", labels);

		infectiousDiseaseScenarioUuid = result.getUuid();
		System.out.println(result.getUuid());

	}

	public void loadInfection() {
		GetLibraryItemResult infectionItem = getPort().getLibraryItem(
				infectionUuid);
		Infection infection = (Infection) infectionItem
				.getCuratedLibraryItemContainer().getApolloIndexableItem();
		System.out.println("Infection instance retrieved from the library:");
		System.out.println("Host Taxon ID: " + infection.getHostTaxonID());
		System.out
				.println("Pathogen Taxon ID" + infection.getPathogenTaxonID());
		if (infection.getInfectiousness() != null)
			System.out.println("Infectiousness"
					+ infection.getInfectiousness().getValue());
		System.out.println("InfectiousPeriodDuration"
				+ infection.getInfectiousPeriodDuration().getValue());
		System.out.println("LatentPeriodDuration"
				+ infection.getLatentPeriodDuration().getValue());

		InfectionAcquisition infectionAcquisition = infection
				.getInfectionAcquisition().get(0);
		System.out
				.println("InfectionAcquisition instance member of Infection instance retrieved from library:");
		System.out.println("Susceptible Host Taxon ID: "
				+ infectionAcquisition.getSusceptibleHostTaxonID());
		System.out.println("Pathogen Taxon ID: "
				+ infectionAcquisition.getPathogenTaxonID());
		System.out.println("Basic Reproduction Number: "
				+ infectionAcquisition.getBasicReproductionNumber());

	}

	public void loadVaccinationControlStrategy() {
		GetLibraryItemResult vaccinationControlStrategyItem = getPort()
				.getLibraryItem(vaccinationControlStrategyUuid);
		IndividualTreatmentControlStrategy vaccinationControlStrategy = (IndividualTreatmentControlStrategy) vaccinationControlStrategyItem
				.getCuratedLibraryItemContainer().getApolloIndexableItem();
		System.out.println("Infection instance retrieved from the library:");
		System.out.println("Description: "
				+ vaccinationControlStrategy.getDescription());
		System.out.println("Reactive End Point Fraction: "
				+ vaccinationControlStrategy
						.getControlStrategyReactiveEndPointFraction());
		System.out.println("Compliance: "
				+ vaccinationControlStrategy.getControlStrategyCompliance());
		System.out.println("Response Delay: "
				+ vaccinationControlStrategy.getControlStrategyResponseDelay());
		System.out.println("Control Strategy Start Time: "
				+ ((FixedStartTime) vaccinationControlStrategy
						.getControlStrategyStartTime())
						.getStartTimeRelativeToScenarioDate());

		Vaccination vaccination = (Vaccination) vaccinationControlStrategy
				.getIndividualTreatment();
		System.out.println("Num Doses in Treatment Course:"
				+ vaccination.getNumDosesInTreatmentCourse());
	}

	public InfectiousDiseaseScenario loadInfectiousDiseaseScenario() {
		GetLibraryItemResult infectionDiseaseScenarioLibraryItem = getPort()
				.getLibraryItem(infectiousDiseaseScenarioUuid);
		InfectiousDiseaseScenario infectiousDiseaseScenario = (InfectiousDiseaseScenario) infectionDiseaseScenarioLibraryItem
				.getCuratedLibraryItemContainer().getApolloIndexableItem();
		System.out.println("Existing Basic Reproduction Number: "
				+ infectiousDiseaseScenario.getInfections().get(0)
						.getInfectionAcquisition().get(0)
						.getBasicReproductionNumber());

		System.out.println("Setting Basic Reproduction Number to 1.7...");
		infectiousDiseaseScenario.getInfections().get(0)
				.getInfectionAcquisition().get(0)
				.setBasicReproductionNumber(1.7);

		return infectiousDiseaseScenario;
	}

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException {
		LibraryClient client = new LibraryClient();
		client.save();
		client.loadInfection();
		client.loadVaccinationControlStrategy();
		// maybe run fred with orig r0, maybe we don't have to though because
		// it's already run...just show them the URL to the orig r0 run

		// this actually changes r0
		InfectiousDiseaseScenario infectiousDiseaseScenario = client
				.loadInfectiousDiseaseScenario();

		RunSimulationMessage runSimulationMessage = client
				.getRunSimulationMessage();
		runSimulationMessage
				.setInfectiousDiseaseScenario(infectiousDiseaseScenario);
		client.runSimulationAndDisplayResults();

	}

}
