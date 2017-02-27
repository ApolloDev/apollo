package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.types.v4_0_1.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 19, 2015
 * Time: 11:13:54 AM
 * Class: ExampleAntiviralControlMeasure
 */
public class ExampleAntiviralControlStrategy {

	public static IndividualTreatmentControlMeasure getAntiviralControlMeasure(XMLGregorianCalendar startDate) {

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

		IndividualTreatmentControlMeasure atcm = new IndividualTreatmentControlMeasure();
		atcm.setIndividualTreatment(avt);
		atcm.setDescription("A control strategy in which all sick humans are treated with a course of Tamiflu.");
		ProbabilisticParameter compliance = new ProbabilisticParameter();
		compliance.setProbability(0.9);
		atcm.setCompliance(compliance);

		ControlMeasureTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlMeasureTargetPopulationsAndPrioritization();
		targetPopulationsAndPrioritization.setControlMeasureNamedPrioritizationScheme(NamedPrioritizationSchemeEnum.TREAT_SICK_ONLY);
		atcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);

		FixedDuration stopTime = new FixedDuration();
		stopTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		stopTime.setValue(127);

		TemporalTriggerDefinition stopTrigger = new TemporalTriggerDefinition();
		stopTrigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		stopTrigger.setTimeSinceTimeScaleZero(stopTime);
		atcm.getControlMeasureStopTime().add(stopTrigger);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(4d);
		atcm.setControlMeasureResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(4d);
		atcm.setControlMeasureStandDownDelay(standDownDelay);

		FixedDuration startTime = new FixedDuration();
		startTime.setUnitOfTime(UnitOfTimeEnum.DAY);
		startTime.setValue(0);

		TemporalTriggerDefinition trigger = new TemporalTriggerDefinition();
		trigger.setTimeScale(TimeScaleEnum.SIMULATOR_TIME_SCALE);
		trigger.setTimeSinceTimeScaleZero(startTime);
		atcm.getControlMeasureStartTime().add(trigger);

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Tamiflu");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);

        Calendar cal = startDate.toGregorianCalendar();

        LogisticalSystemNode capacityNode = new LogisticalSystemNode();
        Schedule capacitySchedule = new Schedule();
        capacitySchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);
        capacityNode.setCapacitySchedule(capacitySchedule);

        try {

            for (int i = 0; i < 128; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(cal.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("2000"));
                outputSchedule.getScheduleElements().add(element);


                ScheduleElement capacityElement = new ScheduleElement();
                capacityElement.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                capacityElement.setQuantity(new BigInteger("2000"));
                capacitySchedule.getScheduleElements().add(capacityElement);

                cal.add(Calendar.DATE, 1);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        atcm.getLogisticalSystems().add(logisticalSystem);

        return atcm;
	}

}
