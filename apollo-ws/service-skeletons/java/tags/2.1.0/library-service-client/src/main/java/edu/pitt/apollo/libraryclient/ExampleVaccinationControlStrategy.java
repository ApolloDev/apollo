package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.types.v2_1_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_1_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_1_0.FixedDuration;
import edu.pitt.apollo.types.v2_1_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_1_0.InfectiousDiseaseControlStrategy;
import edu.pitt.apollo.types.v2_1_0.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v2_1_0.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_1_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v2_1_0.TimeScaleEnum;
import edu.pitt.apollo.types.v2_1_0.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v2_1_0.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v2_1_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v2_1_0.UnitOfTimeEnum;
import edu.pitt.apollo.types.v2_1_0.Vaccination;
import edu.pitt.apollo.types.v2_1_0.VaccinationEfficacyForSimulatorConfiguration;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Dec 9, 2014
 * Time: 5:17:04 PM
 * Class: ExampleVaccinationControlStrategy
 */
public class ExampleVaccinationControlStrategy {

	public static IndividualTreatmentControlStrategy getStrategy() {
		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");

		Vaccination vacc = new Vaccination();
		vacc.setDescription("H1N1 Vaccine");
		vacc.setNumDosesInTreatmentCourse(new BigInteger("1"));
		vacc.setSpeciesOfTreatedOrganism("9606");
		vacc.setPathogen(strain);
		vacc.setVaccineId("Influenza A (H1N1) 2009 Monovalent Vaccine");

		FixedDuration durationOfTreatmentCourse = new FixedDuration();
		durationOfTreatmentCourse.setUnitOfTime(UnitOfTimeEnum.DAY);
		durationOfTreatmentCourse.setValue(1);
		vacc.setDurationOfTreatmentCourse(durationOfTreatmentCourse);

//        Vaccine vaccine = new Vaccine();
//        vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vaccine.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vaccine.getValence().add(new BigInteger("1"));
//        vacc.setVaccine(vaccine);
		VaccinationEfficacyForSimulatorConfiguration vesc = new VaccinationEfficacyForSimulatorConfiguration();

		vesc.setStrainIdentifier(strain);
		vesc.setHostIdentifier("9606");
		vesc.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.INFECTION);
//        vesc.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
		vesc.setAverageVaccinationEfficacy(0.7);
//        vesc.setDescription("The vaccination efficacy for the Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vesc.setTreatment(vacc2);

		vacc.getVaccinationEfficacies().add(vesc);

		IndividualTreatmentControlStrategy vcm = new IndividualTreatmentControlStrategy();
		vcm.setIndividualTreatment(vacc);
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.5);
		vcm.setCompliance(compliance);

		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.ACIP);

		vcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);

		FixedDuration stopTime = new FixedDuration();
		stopTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		stopTime.setValue(127);

		TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
		stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		stopTrigger.setTimeSinceTimeScaleZero(stopTime);
		vcm.getControlStrategyStopTime().add(stopTrigger);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(0d);

		vcm.setControlStrategyResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(0d);

		vcm.setControlStrategyStandDownDelay(standDownDelay);

		FixedDuration startTime = new FixedDuration();
		startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		startTime.setValue(0);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		trigger.setTimeSinceTimeScaleZero(startTime);
		vcm.getControlStrategyStartTime().add(trigger);

		vcm.setDescription("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
		vcm.setPathogen(strain);
		
		TreatmentSystemLogistics logistics = new TreatmentSystemLogistics();
		logistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);
		logistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);
		for (int i = 0; i < 28; i++) {
			logistics.getSupplySchedulePerDay().add(new BigInteger("0"));
		}

		for (int i = 28; i < 84; i++) {
			logistics.getSupplySchedulePerDay().add(new BigInteger("3500"));
		}

		for (int i = 84; i < 115; i++) {
			logistics.getSupplySchedulePerDay().add(new BigInteger("10000"));
		}

		for (int i = 115; i < 127; i++) {
			logistics.getSupplySchedulePerDay().add(new BigInteger("3500"));
		}

		for (int i = 0; i < 28; i++) {
			logistics.getAdministrationCapacityPerDay().add(
					new BigInteger("0"));
		}

		for (int i = 28; i < 127; i++) {
			logistics.getAdministrationCapacityPerDay().add(
					new BigInteger("5000"));
		}
		vcm.getTreatmentSystemLogistics().add(logistics);

		return vcm;
	}

}
