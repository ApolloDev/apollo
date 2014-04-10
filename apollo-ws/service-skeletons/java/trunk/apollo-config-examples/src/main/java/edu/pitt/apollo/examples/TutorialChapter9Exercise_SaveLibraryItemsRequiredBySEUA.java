package edu.pitt.apollo.examples;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import edu.pitt.apollo.types.v2_0_1.AddLibraryItemResult;
import edu.pitt.apollo.types.v2_0_1.Antiviral;
import edu.pitt.apollo.types.v2_0_1.AntiviralTreatment;
import edu.pitt.apollo.types.v2_0_1.ApolloPathogenCode;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.ControlStrategyTargetPopulationsAndPrioritization;
import edu.pitt.apollo.types.v2_0_1.IndividualTreatmentControlStrategy;
import edu.pitt.apollo.types.v2_0_1.Treatment;
import edu.pitt.apollo.types.v2_0_1.Vaccination;
import edu.pitt.apollo.types.v2_0_1.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types.v2_0_1.Vaccine;

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

//    public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice2.0/services/apolloservice?wsdl";
//
//    private static Authentication getAuthentication() {
//        Authentication auth = new Authentication();
//        auth.setRequesterId("TutorialUser");
//        auth.setRequesterPassword("TutorialPassword");
//        return auth;
//    }
//
//    private static void addAcVcm(ApolloServiceEI port) {
//
//        Vaccination vacc = new Vaccination();
//        vacc.setDescription("H1N1 Vaccine");
//        vacc.setNumDosesInTreatmentCourse(new BigInteger("1"));
//        vacc.setSpeciesOfTreatedOrganisms("9606");
//        vacc.getTreatmentContraindications();
//
//        Treatment vacc2 = new Treatment();
//        vacc2.setDescription("H1N1 Vaccine");
//        vacc2.setNumDosesInTreatmentCourse(new BigInteger("1"));
//        vacc2.setSpeciesOfTreatedOrganisms("9606");
//        vacc2.getTreatmentContraindications();
//
//        Vaccine vaccine = new Vaccine();
//        vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vaccine.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vaccine.getValence().add(new BigInteger("1"));
//        vacc.setVaccine(vaccine);
//
//        VaccinationEfficacyForSimulatorConfiguration vesc = new VaccinationEfficacyForSimulatorConfiguration();
//
//        ApolloPathogenCode strain = new ApolloPathogenCode();
//        strain.setNcbiTaxonId("114727");
//
//        vesc.setStrainIdentifier(strain);
//        vesc.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
//        vesc.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vesc.setAverageVaccinationEfficacy(0.7);
//        vesc.setDescription("The vaccination efficacy for the Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vesc.setTreatment(vacc2);
//
//        vacc.getVaccinationEfficacies().add(vesc);
//
//        IndividualTreatmentControlStrategy vcm = new IndividualTreatmentControlStrategy();
//        vcm.setIndividualTreatment(vacc);
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(0.5);
//        vcm.setControlStrategyCompliance(compliance);
//
//        ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlStrategyTargetPopulationsAndPrioritization();
//        targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme("ACIP");
//
//        vcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);
//        vcm.setControlStrategyReactiveEndPointFraction(1d);
//
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setUnitOfMeasure(UnitOfMeasure.DAYS);
//        responseDelay.setValue(0d);
//
//        vcm.setControlStrategyResponseDelay(responseDelay);
//        FixedStartTime vaccCmFst = new FixedStartTime();
//        vaccCmFst.setStartTimeRelativeToScenarioDate(new BigInteger("0"));
//        vaccCmFst.setStopTimeRelativeToScenarioDate(new BigInteger("127"));
//        vcm.setControlStrategyStartTime(vaccCmFst);
//        vcm.setDescription("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
//
//        for (int i = 0; i < 56; i++) {
//            vcm.getSupplySchedule().add(new BigInteger("3500"));
//        }
//
//        for (int i = 56; i < 87; i++) {
//            vcm.getSupplySchedule().add(new BigInteger("10000"));
//        }
//
//        for (int i = 87; i < 127; i++) {
//            vcm.getSupplySchedule().add(new BigInteger("3500"));
//        }
//
//        for (int i = 0; i < 127; i++) {
//            vcm.getAdministrationCapacity().add(
//                    new BigInteger("5000"));
//        }
//
//        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//        itemIndexingLabels.add("IndividualTreatmentControlStrategy");
//        itemIndexingLabels.add("Vaccination");
//        itemIndexingLabels.add("Vaccine");
//        itemIndexingLabels.add("Allegheny County");
//        itemIndexingLabels.add("42003");
//        itemIndexingLabels.add("Allegheny County Health Department");
//        itemIndexingLabels.add("ACHD");
//        itemIndexingLabels.add("2009");
//        itemIndexingLabels.add("H1N1");
//        itemIndexingLabels.add("human");
//        itemIndexingLabels.add("non-hypothetical");
//
//        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(), vcm, vcm.getDescription(),
//                "Discussion with ACHD staff.", "InfectiousDiseaseControlStrategy",
//                itemIndexingLabels);
//
//        System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with Vaccination: " + result.getUuid());
//        System.out.println();
//    }
//
//    private static void addAcAvt(ApolloServiceEI port) {
//        Antiviral av = new Antiviral();
//        av.setAntiviralId("Tamiflu");
//        av.setDescription("Tamiflu");
//        AntiviralTreatment avt = new AntiviralTreatment();
//        avt.setAntiviral(av);
//        avt.setDescription("A treatment course of the antiviral Tamiflu");
//        avt.setNumDosesInTreatmentCourse(new BigInteger("10"));
//        avt.setSpeciesOfTreatedOrganisms("9606");
//
//        Treatment av2 = new Treatment();
//        av2.setDescription("A treatment course of the antiviral Tamiflu");
//        av2.setNumDosesInTreatmentCourse(new BigInteger("10"));
//        av2.setSpeciesOfTreatedOrganisms("9606");
//        av2.getTreatmentContraindications();
//
//        AntiviralEfficacyForSimulatorConfiguration avefsc = new AntiviralEfficacyForSimulatorConfiguration();
//        avefsc.setAntiviralTreatmentIdentifier("Tamiflu");
//        avefsc.setAverageAntiviralTreatmentEfficacy(0.7);
//        avefsc.setDescription("A treatment of the antiviral Tamiflu");
//        avefsc.setTreatment(av2);
//
//        avt.setAntiviralTreatmentEfficacy(avefsc);
//
//        IndividualTreatmentControlStrategy atcm = new IndividualTreatmentControlStrategy();
//        atcm.setIndividualTreatment(avt);
//        atcm.setDescription("A control strategy in which all sick humans are treated with a course of Tamiflu.");
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(0.9);
//        atcm.setControlStrategyCompliance(compliance);
//
//        ControlStrategyTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlStrategyTargetPopulationsAndPrioritization();
//        targetPopulationsAndPrioritization.setControlStrategyNamedPrioritizationScheme("Treat Sick Only");
//
//        atcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);
//        atcm.setControlStrategyReactiveEndPointFraction(1d);
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setUnitOfMeasure(UnitOfMeasure.DAYS);
//        responseDelay.setValue(4d);
//        atcm.setControlStrategyResponseDelay(responseDelay);
//        FixedStartTime atcmFst = new FixedStartTime();
//        atcmFst.setStartTimeRelativeToScenarioDate(new BigInteger("0"));
//        atcmFst.setStopTimeRelativeToScenarioDate(new BigInteger("127"));
//        atcm.setControlStrategyStartTime(atcmFst);
//        for (int i = 0; i < 128; i++) {
//            atcm.getSupplySchedule().add(new BigInteger("2000"));
//            atcm.getAdministrationCapacity().add(
//                    new BigInteger("2000"));
//        }
//
//        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//        itemIndexingLabels.add("IndividualTreatmentControlStrategy");
//        itemIndexingLabels.add("Antiviral");
//        itemIndexingLabels.add("AntiviralTreatment");
//        itemIndexingLabels.add("Allegheny County");
//        itemIndexingLabels.add("42003");
//        itemIndexingLabels.add("Allegheny County Health Department");
//        itemIndexingLabels.add("ACHD");
//        itemIndexingLabels.add("2009");
//        itemIndexingLabels.add("H1N1");
//        itemIndexingLabels.add("human");
//        itemIndexingLabels.add("hypothetical");
//
//        Authentication authentication = getAuthentication();
//        AddLibraryItemResult result = port.addLibraryItem(authentication,
//                atcm,
//                "A hypothetical control strategy in which all sick humans are treated with a course of Tamiflu.",
//                "Expert opinion regarding a realistic (yet hypothetical) Antiviral Treatment Control Measure.",
//                "InfectiousDiseaseControlStrategy", itemIndexingLabels);
//
//        System.out.println("Uuid returned for newly saved InfectiousDiseaseControlStrategy instance with AntiviralTreatment: " + result.getUuid());
//        System.out.println();
//
//    }
//
//    private static void addAllSccm(ApolloServiceEI port) {
//        SchoolClosureControlStrategy scm = new SchoolClosureControlStrategy();
//        ReactiveStartTime rst = new ReactiveStartTime();
//        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
//        rtd.setReactiveControlStrategyThreshold(0.01);
//        rtd.setReactiveControlStrategyTest("Symptomatic Students");
//        rtd.setAscertainmentDelay(new BigInteger("2"));
//        rtd.setAscertainmentFraction(0.5);
//        rst.setTrigger(rtd);
//        scm.setControlStrategyStartTime(rst);
//
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(1d);
//        scm.setControlStrategyCompliance(compliance);
//
//        scm.setControlStrategyReactiveEndPointFraction(1d);
//
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setValue(2d);
//
//        scm.setControlStrategyResponseDelay(responseDelay);
//        scm.setDescription("A school closure control strategy where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");
//        scm.setSchoolClosureDuration(new BigInteger("56"));
//        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.ALL);
//        List<String> indexingValues = new ArrayList<String>();
//        indexingValues.add("BARDA");
//        indexingValues.add("SchoolClosureControlStrategy");
//        indexingValues.add("SchoolClosure");
//        indexingValues.add("Reactive Start Time");
//        indexingValues.add("Allegheny County");
//        indexingValues.add("42003");
//        indexingValues.add("Allegheny County Health Department");
//        indexingValues.add("ACHD");
//        indexingValues.add("2009");
//        indexingValues.add("H1N1");
//        indexingValues.add("human");
//        indexingValues.add("hypothetical");
//
//        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
//                scm,
//                scm.getDescription(),
//                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//                "InfectiousDiseaseControlStrategy", indexingValues);
//
//        System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance : " + result.getUuid());
//        System.out.println();
//    }
//
//    private static void addIndividualSccm(ApolloServiceEI port) {
//        SchoolClosureControlStrategy scm = new SchoolClosureControlStrategy();
//
//        ReactiveStartTime rst = new ReactiveStartTime();
//        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
//        rtd.setReactiveControlStrategyThreshold(0.01);
//        rtd.setReactiveControlStrategyTest("Symptomatic Students");
//        rtd.setAscertainmentDelay(new BigInteger("2"));
//        rtd.setAscertainmentFraction(0.5);
//        rst.setTrigger(rtd);
//        scm.setControlStrategyStartTime(rst);
//
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(1d);
//
//        scm.setControlStrategyCompliance(compliance);
//        scm.setControlStrategyReactiveEndPointFraction(1d);
//
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setValue(2d);
//
//        scm.setControlStrategyResponseDelay(responseDelay);
//        scm.setDescription("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
//        scm.setSchoolClosureDuration(new BigInteger("56"));
//        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.INDIVIDUAL);
//        List<String> indexingValues = new ArrayList<String>();
//        indexingValues.add("BARDA");
//        indexingValues.add("SchoolClosureControlStrategy");
//        indexingValues.add("SchoolClosure");
//        indexingValues.add("Reactive Start Time");
//        indexingValues.add("Allegheny County");
//        indexingValues.add("42003");
//        indexingValues.add("Allegheny County Health Department");
//        indexingValues.add("ACHD");
//        indexingValues.add("2009");
//        indexingValues.add("H1N1");
//        indexingValues.add("human");
//        indexingValues.add("hypothetical");
//
//        AddLibraryItemResult result = port.addLibraryItem(getAuthentication(),
//                scm,
//                scm.getDescription(),
//                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//                "InfectiousDiseaseControlStrategy", indexingValues);
//
//        System.out.println("Uuid returned for newly saved SchoolClosureControlStrategy instance: " + result.getUuid());
//        System.out.println();
//    }
//
//    public static void main(String[] args) throws InterruptedException,
//            IOException, DatatypeConfigurationException {
//
//        ApolloServiceV20 service = new ApolloServiceV20(new URL(WSDL_LOC));
//        ApolloServiceEI port = service.getApolloServiceEndpoint();
//
//        addAcVcm(port);
//        addAcAvt(port);
//        addAllSccm(port);
//        addIndividualSccm(port);
//
//    }
}
