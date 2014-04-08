package edu.pitt.apollo.examples;

import java.math.BigInteger;

import edu.pitt.apollo.types.v2_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0_1.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_0_1.FixedDuration;
import edu.pitt.apollo.types.v2_0_1.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0_1.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v2_0_1.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v2_0_1.Treatment;
import edu.pitt.apollo.types.v2_0_1.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v2_0_1.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v2_0_1.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;
import edu.pitt.apollo.types.v2_0_1.Vaccination;
import edu.pitt.apollo.types.v2_0_1.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v2_0_1.Vaccine;

public class TutorialChapter8_RunSimulationWithVaccinationControlStrategyConfig extends
		TutorialChapter2_ExampleConfig {

	public TutorialChapter8_RunSimulationWithVaccinationControlStrategyConfig() {
		super();
	}

	private Vaccination getVaccination() {
		Vaccination vaccination = new Vaccination();
		vaccination.setDescription("H1N1 Vaccine");
		vaccination.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vaccination.setSpeciesOfTreatedOrganism("9606");
		vaccination.getTreatmentContraindications();

		Vaccine vaccine = new Vaccine();
		vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vaccine.getValence().add(new BigInteger("1"));
		vaccination.setVaccineId("1");

		VaccinationEfficacyForSimulatorConfiguration vaccinationEfficacy = new VaccinationEfficacyForSimulatorConfiguration();

		// this Treatment is required but not read and will not be required in
		// version 2.0.1
		Treatment treatment = new Treatment();
		treatment.setDescription("H1N1 Vaccine");
		treatment.setNumDosesInTreatmentCourse(new BigInteger("1"));
		treatment.setSpeciesOfTreatedOrganism("9606");
		treatment.getTreatmentContraindications();

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");
		vaccinationEfficacy.setStrainIdentifier(strain);
		//vaccinationEfficacy.setTreatment(treatment);
		vaccinationEfficacy.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.INFECTION);
		vaccinationEfficacy.setHostIdentifier("9606"); // homo sapiens
		vaccinationEfficacy.setAverageVaccinationEfficacy(0.47);

		vaccination.getVaccinationEfficacies().add(vaccinationEfficacy);

		return vaccination;
	}

	private ProbabilisticParameter getControlStrategyCompilance() {
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.5);
		return compliance;
	}

	private ControlStrategyTargetPopulationsAndPrioritization getTargetPopulationsAndPrioritizations() {
		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = 
				new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.ACIP);
		return targetPopulationsAndPrioritization;
	}

	private FixedDuration getResponseDelay() {
		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(0d);
		return responseDelay;
	}

	private TemporalTriggerDefinition getFixedStartTime() {
		TemporalTriggerDefinition fixedStartTime = new TemporalTriggerDefinition();
		FixedDuration duration = new FixedDuration();
		duration.setUnitOfTime(UnitOfTimeEnum.DAY);
		duration.setValue(1d);
		fixedStartTime.setTimeSinceTimeScaleZero(duration);
		return fixedStartTime;
	}

	private IndividualTreatmentControlStrategy getVaccinationControlStrategy() {
		IndividualTreatmentControlStrategy vaccinationControlStrategy = new IndividualTreatmentControlStrategy();
		vaccinationControlStrategy.setCompliance(getControlStrategyCompilance());
		vaccinationControlStrategy.setControlStrategyResponseDelay(getResponseDelay());
		vaccinationControlStrategy.getControlStrategyStartTime().add(getFixedStartTime());
		vaccinationControlStrategy.setDescription("An example vaccination control strategy.");
		vaccinationControlStrategy.setIndividualTreatment(getVaccination());
		vaccinationControlStrategy.setTargetPopulationsAndPrioritizations(getTargetPopulationsAndPrioritizations());

		TreatmentSystemLogistics treatmentSystemLogistics = new TreatmentSystemLogistics();
		treatmentSystemLogistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);
		treatmentSystemLogistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);
		
		for (int i = 0; i < 90; i++)
			treatmentSystemLogistics.getSupplySchedulePerDay().add(new BigInteger("3500"));

		for (int i = 0; i < 90; i++)
			treatmentSystemLogistics.getAdministrationCapacityPerDay().add(new BigInteger("3500")); 

		return vaccinationControlStrategy;
	}
        
	
        

	@Override
	public RunSimulationMessage getRunSimulationMessage() {
		RunSimulationMessage message = super.getRunSimulationMessage();
		message.getInfectiousDiseaseScenario().getInfectiousDiseaseControlStrategies()
				.add(getVaccinationControlStrategy());
		return message;
	};

	
}
