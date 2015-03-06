package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.types.v3_0_0.AntiviralTreatment;
import edu.pitt.apollo.types.v3_0_0.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v3_0_0.ApolloPathogenCode;
import edu.pitt.apollo.types.v3_0_0.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;
import edu.pitt.apollo.types.v3_0_0.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v3_0_0.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v3_0_0.ProbabilisticParameter;
import edu.pitt.apollo.types.v3_0_0.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v3_0_0.TimeScaleEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v3_0_0.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v3_0_0.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v3_0_0.UnitOfTimeEnum;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 19, 2015
 * Time: 11:13:54 AM
 * Class: ExampleAntiviralControlStrategy
 */
public class ExampleAntiviralControlStrategy {

	public static IndividualTreatmentControlStrategy getAntiviralControlStrategy() {

		ApolloPathogenCode strain = new ApolloPathogenCode();
		strain.setNcbiTaxonId("114727");

		AntiviralTreatment avt = new AntiviralTreatment();
		avt.setDescription("A treatment course of the antiviral Tamiflu");
		avt.setNumDosesInTreatmentCourse(new BigInteger("10"));
		avt.setAntiviralId("Tamiflu");
		avt.setPathogen(strain);
		avt.setSpeciesOfTreatedOrganism("9606");

		FixedDuration durationOfTreatmentCourse = new FixedDuration();
		durationOfTreatmentCourse.setUnitOfTime(UnitOfTimeEnum.DAY);
		durationOfTreatmentCourse.setValue(1);
		avt.setDurationOfTreatmentCourse(durationOfTreatmentCourse);

		AntiviralTreatmentEfficacy avefsc = new AntiviralTreatmentEfficacy();
		avefsc.setEfficacy(0.7);
		avefsc.setForTreatmentPreventableOutcome(TreatmentPreventableOutcomeEnum.SICKNESS_REQUIRING_MEDICAL_ATTENTION);
		avefsc.setHostIdentifier("9606");
		avefsc.setStrainIdentifier(strain);

		avt.getAntiviralTreatmentEfficacy().add(avefsc);

		IndividualTreatmentControlStrategy atcm = new IndividualTreatmentControlStrategy();
		atcm.setIndividualTreatment(avt);
		atcm.setDescription("A control strategy in which all sick humans are treated with a course of Tamiflu.");
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.9);
		atcm.setCompliance(compliance);

		ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlStrategyTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.TREAT_SICK_ONLY);
		atcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);

		FixedDuration stopTime = new FixedDuration();
		stopTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		stopTime.setValue(127);

		TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
		stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		stopTrigger.setTimeSinceTimeScaleZero(stopTime);
		atcm.getControlStrategyStopTime().add(stopTrigger);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(4d);
		atcm.setControlStrategyResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(4d);
		atcm.setControlStrategyStandDownDelay(standDownDelay);

		FixedDuration startTime = new FixedDuration();
		startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		startTime.setValue(0);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		trigger.setTimeSinceTimeScaleZero(startTime);
		atcm.getControlStrategyStartTime().add(trigger);

		TreatmentSystemLogistics logistics = new TreatmentSystemLogistics();
		logistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);
		logistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);

		for (int i = 0; i < 128; i++) {
			logistics.getSupplySchedulePerDay().add(new BigInteger("2000"));
			logistics.getAdministrationCapacityPerDay().add(
					new BigInteger("2000"));
		}

		atcm.getTreatmentSystemLogistics().add(logistics);

		return atcm;
	}

}
