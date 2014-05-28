package edu.pitt.apollo.apolloclient;

import java.net.MalformedURLException;


public class TutorialChapter9_LibraryExample extends TutorialWebServiceClient {

	public TutorialChapter9_LibraryExample() throws MalformedURLException {
		super();
		// TODO Auto-generated constructor stub
	}

    public String infectionUuid = "";
    public String infectiousDiseaseScenarioUuid = "";
    public String vaccinationControlStrategyUuid = "";
    public ExampleUseOfVaccinationControlStrategy chapter8Config;

    public TutorialChapter9_LibraryExample() throws MalformedURLException {
        super();
        chapter8Config = new ExampleUseOfVaccinationControlStrategy();
    }

    protected Authentication getAuthentication() {
        Authentication auth = new Authentication();
        auth.setRequesterId("TutorialUser");
        auth.setRequesterPassword("TutorialPassword");
        return auth;
    }

    public void saveItems() {

        RunSimulationMessage runSimulationMessage = chapter8Config.getRunSimulationMessage();

        InfectiousDiseaseScenario infectiousDiseaseScenario = runSimulationMessage.getInfectiousDiseaseScenario();
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("tutorial");

        Infection infection = infectiousDiseaseScenario.getInfections().get(0);

        AddLibraryItemResult result = getPort().addLibraryItem(getAuthentication(), infection,
                "The Infection instance of the InfectiousDiseaseScenario from the tutorial..", "Tutorial",
                "Infection", labels);
        infectionUuid = result.getUuid();
        System.out.println("Uuid returned for newly saved Infection instance: " + result.getUuid());

        InfectiousDiseaseControlStrategy vaccinationControlStrategy = infectiousDiseaseScenario.getInfectiousDiseaseControlStrategies().get(0);

        result = getPort().addLibraryItem(getAuthentication(), vaccinationControlStrategy,
                "The Vaccination Control Strategy used in the tutorial", "Tutorial",
                "InfectiousDiseaseControlStrategy", labels);

        vaccinationControlStrategyUuid = result.getUuid();
        System.out.println("Uuid returned for newly saved VaccinationControlStrategy instance: "
                + result.getUuid());

        result = getPort().addLibraryItem(getAuthentication(), infectiousDiseaseScenario,
                "The Infectious Disease Scenario used in the tutorial", "Tutorial",
                "InfectiousDiseaseScenario", labels);

        infectiousDiseaseScenarioUuid = result.getUuid();
        System.out.println("Uuid returned for newly saved InfectionDiseaseScenario instance: "
                + result.getUuid());
        System.out.println();

    }

    public void loadInfection() {
        System.out.println("Retrieving Infection instance from Library using Uuid: " + infectionUuid + "\n");
        GetLibraryItemResult infectionItem = getPort().getLibraryItem(infectionUuid);
        Infection infection = (Infection) infectionItem.getCuratedLibraryItemContainer().getApolloIndexableItem();
        System.out.println("Infection instance retrieved from the library!  Here are the attributes:");
        System.out.println("\tHost Taxon ID: " + infection.getHostTaxonId());
        System.out.println("\tPathogen Taxon ID: " + infection.getPathogenTaxonId().getNcbiTaxonId());

        System.out.println("\tInfectionAcquisitionFromInfectiousHost:");
        InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infection.getInfectionAcquisitionsFromInfectiousHosts().get(0);
        System.out.println("\t\tInfectious Host Taxon ID: " + infectionAcquisitionFromInfectiousHost.getInfectiousHostTaxonId());
        FixedDuration infectiousPeriodDuration = (FixedDuration) infectionAcquisitionFromInfectiousHost.getInfectiousPeriodDuration();
        System.out.println("\t\tInfectiousPeriodDuration: " + infectiousPeriodDuration.getValue());

        FixedDuration latentPeriodDuration = (FixedDuration) infectionAcquisitionFromInfectiousHost.getLatentPeriodDuration();
        System.out.println("\t\tLatentPeriodDuration: " + latentPeriodDuration.getValue());

        System.out.println("\t\tBasic Reproduction Number: "
                + infectionAcquisitionFromInfectiousHost.getBasicReproductionNumber());
        System.out.println();
    }

    public void loadVaccinationControlStrategy() {
        GetLibraryItemResult vaccinationControlStrategyItem = getPort().getLibraryItem(
                vaccinationControlStrategyUuid);
        IndividualTreatmentControlStrategy vaccinationControlStrategy = (IndividualTreatmentControlStrategy) vaccinationControlStrategyItem.getCuratedLibraryItemContainer().getApolloIndexableItem();
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
        GetLibraryItemResult infectionDiseaseScenarioLibraryItem = getPort().getLibraryItem(
                infectiousDiseaseScenarioUuid);
        InfectiousDiseaseScenario infectiousDiseaseScenario = (InfectiousDiseaseScenario) infectionDiseaseScenarioLibraryItem.getCuratedLibraryItemContainer().getApolloIndexableItem();

        InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infectiousDiseaseScenario.getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0);
        if (infectionAcquisitionFromInfectiousHost != null) {
            System.out.println("The existing Basic Reproduction Number in the InfectionDiseaseScenario is: "
                    + infectionAcquisitionFromInfectiousHost.getBasicReproductionNumber());
        }
        return infectiousDiseaseScenario;
    }

    private InfectiousDiseaseScenario setR0(InfectiousDiseaseScenario infectiousDiseaseScenario, Double value) {
        System.out.println("Changing the Basic Reproduction Number to 1.7...");
        InfectionAcquisitionFromInfectiousHost infectionAcquisitionFromInfectiousHost = infectiousDiseaseScenario.getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0);
        infectionAcquisitionFromInfectiousHost.setBasicReproductionNumber(1.7);
        return infectiousDiseaseScenario;
    }

    /**
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String[] args) throws MalformedURLException {
        TutorialChapter9_LibraryExample tutorialChapter9 = new TutorialChapter9_LibraryExample();
        tutorialChapter9.saveItems();
        tutorialChapter9.loadInfection();
        tutorialChapter9.loadVaccinationControlStrategy();
        InfectiousDiseaseScenario infectiousDiseaseScenario = tutorialChapter9.loadInfectiousDiseaseScenario();
        InfectiousDiseaseScenario infectiousDiseaseScenarioWithHighR0 = tutorialChapter9.setR0(
                infectiousDiseaseScenario, 1.7);

        TutorialChapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategy();
        RunSimulationMessage runSimulationMessageWithNormalR0 = tutorialChapter9.chapter8Config.getRunSimulationMessage();
        RunAndSoftwareIdentification normalR0runAndSoftwareId = tutorialChapter8.runSimulationAndDisplayResults(runSimulationMessageWithNormalR0);

        RunSimulationMessage runSimulationMessageWithHighR0 = tutorialChapter9.chapter8Config.getRunSimulationMessage();
        runSimulationMessageWithHighR0.setInfectiousDiseaseScenario(infectiousDiseaseScenarioWithHighR0);
        RunAndSoftwareIdentification highR0RunAndSoftwareId = tutorialChapter8.runSimulationAndDisplayResults(runSimulationMessageWithHighR0);

        tutorialChapter8.createIncidenceVisualizationForMultipleSimulations(ExampleRunSimulationMessageFactory.getAuthentication(ExampleRunSimulationMessageFactory.REQUESTER_ID,
                ExampleRunSimulationMessageFactory.REQUESTER_PASSWORD), normalR0runAndSoftwareId.getRunId(), highR0RunAndSoftwareId.getRunId());
    }
    
}
