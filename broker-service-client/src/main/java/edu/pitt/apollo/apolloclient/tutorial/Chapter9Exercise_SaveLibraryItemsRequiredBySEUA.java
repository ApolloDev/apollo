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

import edu.pitt.apollo.service.apolloservice.v3_0_2.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v3_0_2.ApolloServiceV302;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.types.v3_0_2.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2014
 * Time: 3:20:47 PM
 * Class: TutorialChapter9Exercise_SaveLibraryItemsRequiredBySEUA
 * IDE: NetBeans 6.9.1
 */
public class Chapter9Exercise_SaveLibraryItemsRequiredBySEUA {

	public static final String WSDL_LOC = "http://research.rods.pitt.edu/brokerservice-2.0.2a.7-SNAPSHOT/services/apolloservice?wsdl";
//    public static final String WSDL_LOC = "http://localhost:8080/apolloservice2.0.2/services/apolloservice?wsdl";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private static Authentication getAuthentication() {
		Authentication auth = new Authentication();
		auth.setRequesterId("TutorialUser");
		auth.setRequesterPassword("TutorialPassword");
		return auth;
	}

	private static void addAcVcm(ApolloServiceEI port) {
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

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Influenza A (H1N1) 2009 Monovalent Vaccine");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);


        Calendar outputCal = Calendar.getInstance();
        try {
            outputCal.setTime(sdf.parse("20090908"));
        } catch (ParseException ex) {
            throw new RuntimeException("ParseException: " + ex.getMessage());
        }

        outputCal.add(Calendar.DATE, 28);

        LogisticalSystemNode capacityNode = new LogisticalSystemNode();
        Schedule capacitySchedule = new Schedule();
        capacitySchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);
        capacityNode.setCapacitySchedule(capacitySchedule);

        try {
            for (int i = 28; i < 84; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(outputCal.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("3500"));
                outputCal.add(Calendar.DATE, 1);
                outputSchedule.getScheduleElements().add(element);
            }

            for (int i = 84; i < 115; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(outputCal.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("10000"));
                outputCal.add(Calendar.DATE, 1);
                outputSchedule.getScheduleElements().add(element);
            }

            for (int i = 115; i < 127; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(outputCal.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("3500"));
                outputCal.add(Calendar.DATE, 1);
                outputSchedule.getScheduleElements().add(element);
            }

            Calendar capacityCal = Calendar.getInstance();
            try {
                capacityCal.setTime(sdf.parse("20090908"));
            } catch (ParseException ex) {
                throw new RuntimeException("ParseException: " + ex.getMessage());
            }
            capacityCal.add(Calendar.DATE, 28);

            for (int i = 28; i < 127; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(outputCal.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("5000"));
                capacityCal.add(Calendar.DATE, 1);
                capacitySchedule.getScheduleElements().add(element);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        vcm.getLogisticalSystems().add(logisticalSystem);

		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
		itemIndexingLabels.add("IndividualTreatmentControlStrategy");
		itemIndexingLabels.add("Vaccination");
		itemIndexingLabels.add("Vaccine");
		itemIndexingLabels.add("Allegheny County");
		itemIndexingLabels.add("42003");
		itemIndexingLabels.add("Allegheny County Health Department");
		itemIndexingLabels.add("ACHD");
		itemIndexingLabels.add("2009");
		itemIndexingLabels.add("H1N1");
		itemIndexingLabels.add("human");
		itemIndexingLabels.add("non-hypothetical");

//		AddLibraryItemResult result = port.addLibraryItem(getAuthentication(), vcm, vcm.getDescription(),
//				"Discussion with ACHD staff.", "InfectiousDiseaseControlStrategy",
//				itemIndexingLabels);

//		System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with Vaccination: " + result.getUuid());
		System.out.println();
	}

	private static void addAcAvt(ApolloServiceEI port) {

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

        LogisticalSystem logisticalSystem = new LogisticalSystem();
        logisticalSystem.setProduct("Tamiflu");
        LogisticalSystemNode outputNode = new LogisticalSystemNode();
        Schedule outputSchedule = new Schedule();
        outputNode.setOutputSchedule(outputSchedule);
        outputSchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse("20090908"));
        } catch (ParseException ex) {
            throw new RuntimeException("ParseException: " + ex.getMessage());
        }

        LogisticalSystemNode capacityNode = new LogisticalSystemNode();
        Schedule capacitySchedule = new Schedule();
        capacitySchedule.setUnitOfMeasure(UnitOfMeasureEnum.INDIVIDUAL_TREATMENTS);
        capacityNode.setCapacitySchedule(capacitySchedule);

        try {

            for (int i = 0; i < 128; i++) {
                ScheduleElement element = new ScheduleElement();
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTimeInMillis(calendar.getTimeInMillis());
                element.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                element.setQuantity(new BigInteger("2000"));
                outputSchedule.getScheduleElements().add(element);


                ScheduleElement capacityElement = new ScheduleElement();
                capacityElement.setDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
                capacityElement.setQuantity(new BigInteger("2000"));
                capacitySchedule.getScheduleElements().add(capacityElement);

                calendar.add(Calendar.DATE, 1);
            }
        } catch (DatatypeConfigurationException ex) {
            throw new RuntimeException("DatatypeConfigurationException: " + ex.getMessage());
        }

        outputNode.getChildren().add(capacityNode);
        logisticalSystem.getLogisticalSystemNodes().add(outputNode);

        atcm.getLogisticalSystems().add(logisticalSystem);


		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
		itemIndexingLabels.add("IndividualTreatmentControlStrategy");
		itemIndexingLabels.add("Antiviral");
		itemIndexingLabels.add("AntiviralTreatment");
		itemIndexingLabels.add("Allegheny County");
		itemIndexingLabels.add("42003");
		itemIndexingLabels.add("Allegheny County Health Department");
		itemIndexingLabels.add("ACHD");
		itemIndexingLabels.add("2009");
		itemIndexingLabels.add("H1N1");
		itemIndexingLabels.add("human");
		itemIndexingLabels.add("hypothetical");

		Authentication authentication = getAuthentication();
//		AddLibraryItemResult result = port.addLibraryItem(authentication,
//				atcm,
//				"A hypothetical control strategy in which all sick humans are treated with a course of Tamiflu.",
//				"Expert opinion regarding a realistic (yet hypothetical) Antiviral Treatment Control Measure.",
//				"InfectiousDiseaseControlStrategy", itemIndexingLabels);
//
//		System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with AntiviralTreatment: " + result.getUuid());
		System.out.println();

	}

	private static void addAllSccm(ApolloServiceEI port) {
		PlaceClosureControlStrategy scm = new PlaceClosureControlStrategy();

		Location location = new Location();
		location.setApolloLocationCode("42003");

		DiseaseSurveillanceTriggerDefinition rt = new DiseaseSurveillanceTriggerDefinition();

		DiseaseSurveillanceCapability capability = new DiseaseSurveillanceCapability();
		capability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
		capability.setLocation(location);

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("114727");
		capability.setPathogen(code);
		capability.setSensitivityOfCaseDetection(0.5);
		capability.setSpeciesOfCase("9606");
		capability.setSpecificityOfCaseDetection(1.0);

		FixedDuration caseDetectionTimeDelay = new FixedDuration();
		caseDetectionTimeDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		caseDetectionTimeDelay.setValue(2d);
		capability.setTimeDelayOfCaseDetection(caseDetectionTimeDelay);

		rt.setReactiveControlStrategyTest("Symptomatic Students");
		rt.setReactiveControlStrategyThreshold(1);
		rt.setReactiveControlStrategyOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlStrategyStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		responseDelay.setValue(2d);

		scm.setControlStrategyResponseDelay(responseDelay);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlStrategyStandDownDelay(standDownDelay);

		scm.setDescription("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");

		FixedDuration closurePeriod = new FixedDuration();
		closurePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		closurePeriod.setValue(56);

		scm.setClosurePeriod(closurePeriod);
		scm.setPlaceClass(PlaceEnum.ALL_SCHOOLS);
		List<String> indexingValues = new ArrayList<String>();
		indexingValues.add("BARDA");
		indexingValues.add("SchoolClosureControlStrategy");
		indexingValues.add("SchoolClosure");
		indexingValues.add("Reactive Start Time");
		indexingValues.add("Allegheny County");
		indexingValues.add("42003");
		indexingValues.add("Allegheny County Health Department");
		indexingValues.add("ACHD");
		indexingValues.add("2009");
		indexingValues.add("H1N1");
		indexingValues.add("human");
		indexingValues.add("hypothetical");

//		AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
//				scm,
//				scm.getDescription(),
//				"A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//				"InfectiousDiseaseControlStrategy", indexingValues);
//
//		System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance : " + result.getUuid());
		System.out.println();
	}

	private static void addIndividualSccm(ApolloServiceEI port) {
		PlaceClosureControlStrategy scm = new PlaceClosureControlStrategy();

		Location location = new Location();
		location.setApolloLocationCode("42003");

		DiseaseSurveillanceTriggerDefinition rt = new DiseaseSurveillanceTriggerDefinition();

		DiseaseSurveillanceCapability capability = new DiseaseSurveillanceCapability();
		capability.setCaseDefinition(DiseaseOutcomeEnum.SYMPTOMATIC);
		capability.setLocation(location);

		ApolloPathogenCode code = new ApolloPathogenCode();
		code.setNcbiTaxonId("114727");
		capability.setPathogen(code);
		capability.setSensitivityOfCaseDetection(0.5);
		capability.setSpeciesOfCase("9606");
		capability.setSpecificityOfCaseDetection(1.0);

		FixedDuration caseDetectionTimeDelay = new FixedDuration();
		caseDetectionTimeDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		caseDetectionTimeDelay.setValue(2d);
		capability.setTimeDelayOfCaseDetection(caseDetectionTimeDelay);

		rt.setReactiveControlStrategyTest("Symptomatic Students");
		rt.setReactiveControlStrategyThreshold(1);
		rt.setReactiveControlStrategyOperator(OperatorEnum.GREATER_THAN_OR_EQUAL);
		rt.setDiseaseSurveillanceCapability(capability);
		rt.setUnitOfMeasureForThreshold(UnitOfMeasureEnum.PERCENT);
		rt.setDiseaseSurveillanceCapability(capability);
		scm.getControlStrategyStartTime().add(rt);

		FixedDuration responseDelay = new FixedDuration();
		responseDelay.setValue(2d);
		responseDelay.setUnitOfTime(UnitOfTimeEnum.DAY);

		FixedDuration standDownDelay = new FixedDuration();
		standDownDelay.setUnitOfTime(UnitOfTimeEnum.DAY);
		standDownDelay.setValue(2d);

		scm.setControlStrategyStandDownDelay(standDownDelay);

		scm.setControlStrategyResponseDelay(responseDelay);
		scm.setDescription("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
		FixedDuration closurePeriod = new FixedDuration();
		closurePeriod.setUnitOfTime(UnitOfTimeEnum.DAY);
		closurePeriod.setValue(56);

		scm.setClosurePeriod(closurePeriod);
		scm.setCloseIndividualPlacesIndependently(true);
		scm.setPlaceClass(PlaceEnum.ALL_SCHOOLS);
		List<String> indexingValues = new ArrayList<String>();
		indexingValues.add("BARDA");
		indexingValues.add("SchoolClosureControlStrategy");
		indexingValues.add("SchoolClosure");
		indexingValues.add("Reactive Start Time");
		indexingValues.add("Allegheny County");
		indexingValues.add("42003");
		indexingValues.add("Allegheny County Health Department");
		indexingValues.add("ACHD");
		indexingValues.add("2009");
		indexingValues.add("H1N1");
		indexingValues.add("human");
		indexingValues.add("hypothetical");

//		AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
//				scm,
//				scm.getDescription(),
//				"A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//				"InfectiousDiseaseControlStrategy", indexingValues);
//
//		System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance: " + result.getUuid());
		System.out.println();
	}

	public static void main(String[] args) throws InterruptedException,
			IOException, DatatypeConfigurationException {

		ApolloServiceV302 service = new ApolloServiceV302(new URL(WSDL_LOC));
		ApolloServiceEI port = service.getApolloServiceEndpoint();

		addAcVcm(port);
		addAcAvt(port);
		addAllSccm(port);
		addIndividualSccm(port);

	}
}
