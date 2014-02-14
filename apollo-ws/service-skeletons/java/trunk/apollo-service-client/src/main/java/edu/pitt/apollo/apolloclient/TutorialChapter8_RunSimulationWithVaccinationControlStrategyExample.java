package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;

import edu.pitt.apollo.types.v2_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_0.FixedStartTime;
import edu.pitt.apollo.types.v2_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0.NumericParameterValue;
import edu.pitt.apollo.types.v2_0.ProbabilisticParameterValue;
import edu.pitt.apollo.types.v2_0.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0.Treatment;
import edu.pitt.apollo.types.v2_0.UnitOfMeasure;
import edu.pitt.apollo.types.v2_0.UrlOutputResource;
import edu.pitt.apollo.types.v2_0.Vaccination;
import edu.pitt.apollo.types.v2_0.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v2_0.VaccinationPreventableOutcome;
import edu.pitt.apollo.types.v2_0.Vaccine;
import edu.pitt.apollo.types.v2_0.VisualizationOptions;
import edu.pitt.apollo.types.v2_0.VisualizerResult;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample extends
		TutorialChapter2_BasicRunSimulationExample {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample() throws MalformedURLException {
		super();
	}

	private Vaccination getVaccination() {
		Vaccination vaccination = new Vaccination();
		vaccination.setDescription("H1N1 Vaccine");
		vaccination.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vaccination.setSpeciesOfTreatedOrganisms("9606");
		vaccination.getTreatmentContraindications();

		Vaccine vaccine = new Vaccine();
		vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.getValence().add(new BigInteger("1"));
		vaccination.setVaccine(vaccine);

		VaccinationEfficacyForSimulatorConfiguration vaccinationEfficacy = new VaccinationEfficacyForSimulatorConfiguration();

		// this Treatment is required but not read and will not be required in
		// version 2.0.1
		Treatment treatment = new Treatment();
		treatment.setDescription("H1N1 Vaccine");
		treatment.setNumDosesInTreatmentCourse(new BigInteger("1"));
		treatment.setSpeciesOfTreatedOrganisms("9606");
		treatment.getTreatmentContraindications();

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");
		vaccinationEfficacy.setStrainIdentifier(strain);
		vaccinationEfficacy.setTreatment(treatment);
		vaccinationEfficacy.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
		vaccinationEfficacy.setHostIdentifier("9606"); // homo sapiens
		vaccinationEfficacy.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccinationEfficacy.setAverageVaccinationEfficacy(0.47);
		vaccinationEfficacy
				.setDescription("The vaccination efficacy for the Influenza A (H1N1) 2009 Monovalent Vaccine");

		vaccination.getVaccinationEfficacies().add(vaccinationEfficacy);

		return vaccination;
	}

	private ProbabilisticParameterValue getControlStrategyCompilance() {
		ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
		compliance.setValue(0.5);
		return compliance;
	}

	private ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = 
				new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme("ACIP");
		return targetPopulationsAndPrioritization;
	}

	private NumericParameterValue getResponseDelay() {
		NumericParameterValue responseDelay = new NumericParameterValue();
		responseDelay.setUnitOfMeasure(UnitOfMeasure.DAYS);
		responseDelay.setValue(0d);
		return responseDelay;
	}

	private FixedStartTime getFixedStartTime() {
		FixedStartTime fixedStartTime = new FixedStartTime();
		fixedStartTime.setStartTimeRelativeToScenarioDate(new BigInteger("0"));
		return fixedStartTime;
	}

	private IndividualTreatmentControlStrategy getVaccinationControlStrategy() {
		IndividualTreatmentControlStrategy vaccinationControlStrategy = new IndividualTreatmentControlStrategy();
		vaccinationControlStrategy.setControlStrategyCompliance(getControlStrategyCompilance());
		vaccinationControlStrategy.setControlStrategyReactiveEndPointFraction(1.0);
		vaccinationControlStrategy.setControlStrategyResponseDelay(getResponseDelay());
		vaccinationControlStrategy.setControlStrategyStartTime(getFixedStartTime());
		vaccinationControlStrategy.setDescription("An example vaccination control strategy.");
		vaccinationControlStrategy.setIndividualTreatment(getVaccination());
		vaccinationControlStrategy.setTargetPopulationsAndPrioritizations(getTargetPopulationsAndPrioritizations());

		for (int i = 0; i < 90; i++)
			vaccinationControlStrategy.getSupplySchedule().add(new BigInteger("3500"));

		for (int i = 0; i < 90; i++)
			vaccinationControlStrategy.getAdministrationCapacity().add(new BigInteger("3500")); 

		return vaccinationControlStrategy;
	}
        
        protected void createIncidenceVisualizationForMultipleSimulations(String ... simulatorRunIds) {
		
		RunVisualizationMessage runVisualizationMessage = new RunVisualizationMessage();
		runVisualizationMessage.setAuthentication(getAuthentication());
		runVisualizationMessage.setVisualizerIdentification(getSoftwareIdentifiationForTimeSeriesVisualizer());
		VisualizationOptions options = new VisualizationOptions();
		
		//pass all runId's to "setRunId()" delimited by the ";" character
		//let's hope there are no semicolons in the runId!
		String runIdString = "";
		for (String simulatorRunId : simulatorRunIds) {
			runIdString += simulatorRunId + ":";
		}
		runIdString = runIdString.substring(0, runIdString.length()-1);
		System.out.println(runIdString);
		options.setRunId(runIdString);
		options.setLocation("42003");
		options.setOutputFormat("default");
		runVisualizationMessage.setVisualizationOptions(options);

		VisualizerResult visualizerResult = getPort().runVisualization(runVisualizationMessage);
				
		
		RunAndSoftwareIdentification runAndSoftwareIdentification = new RunAndSoftwareIdentification();
		runAndSoftwareIdentification.setSoftwareId(getSoftwareIdentifiationForTimeSeriesVisualizer());
		runAndSoftwareIdentification.setRunId(visualizerResult.getRunId());

		if (checkStatusOfWebServiceCall(runAndSoftwareIdentification).getStatus() == MethodCallStatusEnum.COMPLETED) {
			System.out.println("The following resources were returned from the " + getSoftwareIdentifiationForTimeSeriesVisualizer().getSoftwareName() +
					" visualizer:");
			for (UrlOutputResource r : visualizerResult.getVisualizerOutputResource()) {
				System.out.println("\t" + r.getURL());
			}
		}
		
	}
        

	@Override
	public RunSimulationMessage getRunSimulationMessage() {
		RunSimulationMessage message = super.getRunSimulationMessage();
		message.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies()
				.add(getVaccinationControlStrategy());
		return message;
	};

	public static void main(String[] args) throws MalformedURLException {
            TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample tutorialChapter8 = new TutorialChapter8_RunSimulationWithVaccinationControlStrategyExample();
		RunAndSoftwareIdentification vaccinationRunAndSoftwareId = tutorialChapter8.runSimulationAndDisplayResults();
		//run no vacc, save id
                TutorialChapter2_BasicRunSimulationExample tutorialChapter2 = new TutorialChapter2_BasicRunSimulationExample();
		RunAndSoftwareIdentification noVaccinationRunAndSoftwareId = tutorialChapter2.runSimulation();
                //run vacc, save id,
                //create combined incidence..
		//it already runs for VACC but we need to re-run Chapter 2, get the runId's dynamically and create a combined incidence curve

                tutorialChapter8.createIncidenceVisualizationForMultipleSimulations(noVaccinationRunAndSoftwareId.getRunId(), vaccinationRunAndSoftwareId.getRunId());
                
                
	}

}
