///* Copyright 2012 University of Pittsburgh
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not
// * use this file except in compliance with the License.  You may obtain a copy of
// * the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
// * License for the specific language governing permissions and limitations under
// * the License.
// */
package edu.pitt.apollo.libraryclient;

public class WSClient {

    public static final String WSDL_LOC = "http://localhost:8080/libraryservice2.0/services/libraryservice?wsdl";

//    private static Authentication getAuthentication() {
//
//        Authentication authentication = new Authentication();
//        return authentication;
//    }
//
//    public static AddLibraryItemResult addAcAvt(LibraryServiceEI port) {
//        Antiviral av = new Antiviral();
//        av.setAntiviralId("Tamiflu");
//        av.setDescription("Tamiflu");
//        AntiviralTreatment avt = new AntiviralTreatment();
//        avt.setAntiviral(av);
//        avt.setDescription("A treatment course of the antiviral Tamiflu");
//        avt.setNumDosesInTreatmentCourse(new BigInteger("10"));
////        NcbiTaxonId speciesOfTreatedOrganism = new NcbiTaxonId();
////        speciesOfTreatedOrganism.setNcbiTaxonId(BigInteger.ZERO);
//        avt.setSpeciesOfTreatedOrganisms("Homo sapiens");
////        avt.getTreatmentContraindications();
//
//        // avt.s
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
////        atcm.setControlMeasureNamedPrioritizationScheme("Treat Sick Only");
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
//
//        return port.addLibraryItem(authentication,
//                atcm,
//                "A hypothetical control strategy in which all sick humans are treated with a course of Tamiflu.",
//                "Expert opinion regarding a realistic (yet hypothetical) Antiviral Treatment Control Measure.",
//                "IndividualTreatmentControlStrategy", itemIndexingLabels);
//
//    }
//
//    public static AddLibraryItemResult addAcVcm(LibraryServiceEI port) {
//        // add Vacc
//        Vaccination vacc = new Vaccination();
//        vacc.setDescription("H1N1 Vaccine");
//        vacc.setNumDosesInTreatmentCourse(new BigInteger("1"));
//        vacc.setSpeciesOfTreatedOrganisms("Homo sapiens");
//        vacc.getTreatmentContraindications();
//
//
//        Vaccine vaccine = new Vaccine();
//        vaccine.setDescription("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vacc.setVaccine(vaccine);
//
//        VaccinationEfficacyForSimulatorConfiguration vesc = new VaccinationEfficacyForSimulatorConfiguration();
//
//        ApolloPathogenCode strain = new ApolloPathogenCode();
//        strain.setNcbiTaxonId("114727");
//
//
//        vesc.setStrainIdentifier(strain);
//        vesc.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
//        vesc.setVaccineIdentifier("Influenza A (H1N1) 2009 Monovalent Vaccine");
//        vesc.setAverageVaccinationEfficacy(0.47);
//        vesc.setDescription("The vaccination efficacy for the Influenza A (H1N1) 2009 Monovalent Vaccine");
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
//        return port.addLibraryItem(getAuthentication(), vcm, vcm.getDescription(),
//                "", "IndividualTreatmentControlStrategy",
//                itemIndexingLabels);
//    }
//
//    public static AddLibraryItemResult addAllSccm(LibraryServiceEI port) {
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
//        return port.addLibraryItem(getAuthentication(),
//                scm,
//                scm.getDescription(),
//                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//                "SchoolClosureControlStrategy", indexingValues);
//    }
//
//    public static AddLibraryItemResult addIndividualSccm(LibraryServiceEI port) {
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
//        return port.addLibraryItem(getAuthentication(),
//                scm,
//                scm.getDescription(),
//                "A hypothetical school closure control strategy specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
//                "SchoolClosureControlStrategy", indexingValues);
//    }
////    
////
////
//    public static void main(String[] args) throws InterruptedException,
//            IOException, DatatypeConfigurationException {
//
//        LibraryServiceV20 service = new LibraryServiceV20(new URL(WSDL_LOC));
//        LibraryServiceEI port = service.getLibraryServiceEndpoint();
//
//        addAcVcm(port);
//        addAcAvt(port);
//		addAllSccm(port);
//		addIndividualSccm(port);
////		addH1N1(port);
////		addH3N2(port);
////		addH5N1(port);
////		addH7N7(port);
////		addH7N9(port);
////		addDiptheria(port);
////		addMeningitidis(port);
////		addRubella(port);
////		addSARS(port);
////		addSmallpox(port);
//
////                Authentication authentication = getAuthentication();
////                MethodCallStatus status = port.removeLibraryItem(authentication, "dbmi-dt-036_1440836e6d307b1fc45 45603463114096640");
////                System.out.println(status.getMessage());
//
////        GetLibraryItemUuidsResult result = port.getUuidsForLibraryItemsGivenType("SchoolClosureControlStrategy");
////        System.out.println(result.getUuids().get(0));
//
//        GregorianCalendar c = new GregorianCalendar();
//        c.add(Calendar.YEAR, -1);
//        XMLGregorianCalendar date;
//
//        date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
//        GetLibraryItemUuidsResult result = port.getUuidsForLibraryItemsCreatedSinceDateTime(date);
//        List<String> l = result.getUuids();
//        for (int i = 0; i < l.size(); i++) {
//            System.out.println("uuid: " + l.get(i));
//            GetLibraryItemResult itemResult = port.getLibraryItem(l.get(i));
//            CuratedLibraryItemContainer item = itemResult.getCuratedLibraryItemContainer();
//            System.out.printf("Item %d:\n\tUUID: %s\n\tType: %s\n\tDescription: %s\n\tSource: %s\n",
//                    i + 1, item.getCuratedLibraryItem().getItemUuid(),
//                    item.getCuratedLibraryItem().getItemType(), item.getCuratedLibraryItem().getItemDescription(), item.getCuratedLibraryItem().getItemSource());
//            System.out.print("\tIndexing labels: ");
//            for (int j = 0; j < item.getCuratedLibraryItem().getItemIndexingLabels().size(); j++) {
//                System.out.print(item.getCuratedLibraryItem().getItemIndexingLabels().get(j));
//                if (j < item.getCuratedLibraryItem().getItemIndexingLabels().size() - 1) {
//                    System.out.print(", ");
//                }
//                if (j > 0 && j % 4 == 0) {
//                    System.out.print("\n                         ");
//                }
//            }
//            System.out.println();
//
//        }
//    }
}
    
