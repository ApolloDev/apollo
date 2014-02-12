package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;

import edu.pitt.apollo.types.v2_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_0.FixedStartTime;
import edu.pitt.apollo.types.v2_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0.NumericParameterValue;
import edu.pitt.apollo.types.v2_0.ProbabilisticParameterValue;
import edu.pitt.apollo.types.v2_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0.Treatment;
import edu.pitt.apollo.types.v2_0.UnitOfMeasure;
import edu.pitt.apollo.types.v2_0.Vaccination;
import edu.pitt.apollo.types.v2_0.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v2_0.VaccinationPreventableOutcome;
import edu.pitt.apollo.types.v2_0.Vaccine;

public class RunSimulationWithVaccinationControlStrategyExample extends
		BasicApolloBrokerClient {

	public RunSimulationWithVaccinationControlStrategyExample()
			throws MalformedURLException {
		super();
	}

	private Vaccination getVaccination() {
		Vaccination vacc = new Vaccination();
		vacc.setDescription("H1N1 Vaccine");
		vacc.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vacc.setSpeciesOfTreatedOrganisms("9606");
		vacc.getTreatmentContraindications();

		Vaccine vaccine = new Vaccine();
		vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.getValence().add(new BigInteger("1"));
		vacc.setVaccine(vaccine);

		VaccinationEfficacyForSimulatorConfiguration vesc = new VaccinationEfficacyForSimulatorConfiguration();
		
		Treatment vacc2 = new Treatment();
		vacc2.setDescription("H1N1 Vaccine");
		vacc2.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vacc2.setSpeciesOfTreatedOrganisms("9606");
		vacc2.getTreatmentContraindications();

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");
		vesc.setStrainIdentifier(strain);
		vesc.setTreatment(vacc2);
		vesc.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
		vesc.setHostIdentifier("9606"); //homo sapiens
		vesc.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vesc.setAverageVaccinationEfficacy(0.47);
		vesc.setDescription("The vaccination efficacy for the Influenza A (H1N1) 2009 Monovalent Vaccine");

		vacc.getVaccinationEfficacies().add(vesc);

		return vacc;
	}

	private ProbabilisticParameterValue getControlStrategyCompilance() {
		ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
		compliance.setValue(0.5);
		return compliance;

	}

	private ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = 
				new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization
				.setControlStrategyNamedPrioritizationScheme("ACIP");
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
	
	@Override
	public RunSimulationMessage getRunSimulationMessage() {
		RunSimulationMessage message = super.getRunSimulationMessage();
		message.getInfectiousDiseaseScenario()
				.getInfectiousDiseaseControlStrategies()
				.add(getVaccinationControlStrategy());
		return message;
	};

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException {
		new RunSimulationWithVaccinationControlStrategyExample()
				.runSimulationAndDisplayResults();

	}

}
