package edu.pitt.apollo.apolloclient;

import edu.pitt.apollo.types.v2_0_1.GetLibraryItemUuidsResult;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceV201;
import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.AntiviralTreatment;
import edu.pitt.apollo.types.v2_0_1.AntiviralTreatmentEfficacy;
import edu.pitt.apollo.types.v2_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_0_1.DiseaseOutcomeEnum;
import edu.pitt.apollo.types.v2_0_1.DiseaseSurveillanceCapability;
import edu.pitt.apollo.types.v2_0_1.DiseaseSurveillanceTriggerDefinition;
import edu.pitt.apollo.types.v2_0_1.FixedDuration;
import edu.pitt.apollo.types.v2_0_1.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0_1.Location;
import edu.pitt.apollo.types.v2_0_1.NamedPrioritizationSchemeEnum;
import edu.pitt.apollo.types.v2_0_1.OperatorEnum;
import edu.pitt.apollo.types.v2_0_1.PlaceClosureControlStrategy;
import edu.pitt.apollo.types.v2_0_1.PlaceEnum;
import edu.pitt.apollo.types.v2_0_1.ProbabilisticParameter;
import edu.pitt.apollo.types.v2_0_1.TemporalTriggerDefinition;
import edu.pitt.apollo.types.v2_0_1.TimeScaleEnum;
import edu.pitt.apollo.types.v2_0_1.TreatmentPreventableOutcomeEnum;
import edu.pitt.apollo.types.v2_0_1.TreatmentSystemLogistics;
import edu.pitt.apollo.types.v2_0_1.UnitOfMeasureEnum;
import edu.pitt.apollo.types.v2_0_1.UnitOfTimeEnum;
import edu.pitt.apollo.types.v2_0_1.Vaccination;
import edu.pitt.apollo.types.v2_0_1.VaccinationEfficacyForSimulatorConfiguration;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 12, 2014
 * Time: 3:20:47 PM
 * Class: TutorialChapter9Exercise_SaveLibraryItemsRequiredBySEUA
 * IDE: NetBeans 6.9.1
 */
public class TutorialChapter9Exercise_SaveLibraryItemsRequiredBySEUA {

    public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice2.0.1/services/apolloservice?wsdl";
//    public static final String WSDL_LOC = "http://localhost:8080/apolloservice2.0.1/services/apolloservice?wsdl";

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

        TreatmentSystemLogistics logistics = new TreatmentSystemLogistics();
        logistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);
        logistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);
        for (int i = 0; i < 56; i++) {
            logistics.getSupplySchedulePerDay().add(new BigInteger("3500"));
        }

        for (int i = 56; i < 87; i++) {
            logistics.getSupplySchedulePerDay().add(new BigInteger("10000"));
        }

        for (int i = 87; i < 127; i++) {
            logistics.getSupplySchedulePerDay().add(new BigInteger("3500"));
        }

        for (int i = 0; i < 127; i++) {
            logistics.getAdministrationCapacityPerDay().add(
                    new BigInteger("5000"));
        }
        vcm.setTreatmentSystemLogistics(logistics);

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

        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(), vcm, vcm.getDescription(),
                "Discussion with ACHD staff.", "InfectiousDiseaseControlStrategy",
                itemIndexingLabels);

        System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with Vaccination: " + result.getUuid());
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

        TreatmentSystemLogistics logistics = new TreatmentSystemLogistics();
        logistics.setAdministrationCapacityUnits(UnitOfMeasureEnum.DAILY_DOSE);
        logistics.setSupplyScheduleUnits(UnitOfMeasureEnum.DAILY_DOSE);

        for (int i = 0; i < 128; i++) {
            logistics.getSupplySchedulePerDay().add(new BigInteger("2000"));
            logistics.getAdministrationCapacityPerDay().add(
                    new BigInteger("2000"));
        }

        atcm.setTreatmentSystemLogistics(logistics);

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
        AddLibraryItemResult result = port.addLibraryItem(authentication,
                atcm,
                "A hypothetical control strategy in which all sick humans are treated with a course of Tamiflu.",
                "Expert opinion regarding a realistic (yet hypothetical) Antiviral Treatment Control Measure.",
                "InfectiousDiseaseControlStrategy", itemIndexingLabels);

        System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with AntiviralTreatment: " + result.getUuid());
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

        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
                scm,
                scm.getDescription(),
                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
                "InfectiousDiseaseControlStrategy", indexingValues);

        System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance : " + result.getUuid());
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

        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
                scm,
                scm.getDescription(),
                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
                "InfectiousDiseaseControlStrategy", indexingValues);

        System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance: " + result.getUuid());
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException,
            IOException, DatatypeConfigurationException {

        ApolloServiceV201 service = new ApolloServiceV201(new URL(WSDL_LOC));
        ApolloServiceEI port = service.getApolloServiceEndpoint();

        addAcVcm(port);
        addAcAvt(port);
        addAllSccm(port);
        addIndividualSccm(port);

    }
}
