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
package edu.pitt.apollo.libraryclient;

import edu.pitt.apollo.service.libraryservice._10._28._2013.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice._10._28._2013.LibraryServiceV131;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import edu.pitt.apollo.types._10._28._2013.Antiviral;
import edu.pitt.apollo.types._10._28._2013.AntiviralTreatment;
import edu.pitt.apollo.types._10._28._2013.AntiviralTreatmentControlMeasure;
import edu.pitt.apollo.types._10._28._2013.CuratedLibraryItemContainer;
import edu.pitt.apollo.types._10._28._2013.FixedStartTime;
import edu.pitt.apollo.types._10._28._2013.Infection;
import edu.pitt.apollo.types._10._28._2013.InfectionAcquisition;
import edu.pitt.apollo.types._10._28._2013.PathogenTaxonID;
import edu.pitt.apollo.types._10._28._2013.ReactiveStartTime;
import edu.pitt.apollo.types._10._28._2013.ReactiveTriggersDefinition;
import edu.pitt.apollo.types._10._28._2013.SchoolClosureControlMeasure;
import edu.pitt.apollo.types._10._28._2013.SchoolClosureTargetFacilities;
import edu.pitt.apollo.types._10._28._2013.TemporalDiseaseParameter;
import edu.pitt.apollo.types._10._28._2013.TimeStepUnit;
import edu.pitt.apollo.types._10._28._2013.Treatment;
import edu.pitt.apollo.types._10._28._2013.Vaccination;
import edu.pitt.apollo.types._10._28._2013.VaccinationControlMeasure;
import edu.pitt.apollo.types._10._28._2013.VaccinationEfficacyForSimulatorConfiguration;
import edu.pitt.apollo.types._10._28._2013.VaccinationPreventableOutcome;

public class WSClient {

    public static final String WSDL_LOC = "http://localhost:8080/libraryservice1.3.1/services/libraryservice?wsdl";

    // public static final String WSDL_LOC =
    // "http://dbmi-dt-036.univ.pitt.edu:8080/apolloservice1.3/services/apolloservice?wsdl";
    // public static final String WSDL_LOC =
    // "http://localhost:8080/apolloservice1.3/services/apolloservice?wsdl";
    private static Infection addDisease(PathogenTaxonID pathogenTaxonID,
            String hostTaxonID, double infectiousPeriod, double latentPeriod,
            double infectiousnessTemporalProfile,
            double probabilityNeverSymptomatic,
            double probabilitySevereGivenSymptomatic, double reproductionNumber) {
        Infection infection = new Infection();
        infection.setPathogenTaxonID(pathogenTaxonID);
        infection.setHostTaxonID(hostTaxonID);

        TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
        tdp.setTimeStepUnit(TimeStepUnit.DAY);
        tdp.setTimeStepValue(infectiousPeriod);
        infection.setInfectiousPeriod(tdp);

        tdp = new TemporalDiseaseParameter();
        tdp.setTimeStepUnit(TimeStepUnit.DAY);
        tdp.setTimeStepValue(latentPeriod);
        infection.setLatentPeriod(tdp);

        tdp = new TemporalDiseaseParameter();
        tdp.setTimeStepUnit(TimeStepUnit.DAY);
        tdp.setTimeStepValue(infectiousnessTemporalProfile);
        infection.setInfectiousnessTemporalProfile(tdp);

        infection.setProbabilityNeverSymptomatic(probabilityNeverSymptomatic);
        infection.setProbabilitySevereGivenSymptomatic(probabilitySevereGivenSymptomatic);

        InfectionAcquisition ia = new InfectionAcquisition();
        ia.setPathogenTaxonID(pathogenTaxonID);
        ia.setInfectiousHostTaxonID(hostTaxonID);
        ia.setSusceptibleHostTaxonID(hostTaxonID);
        ia.setReproductionNumber(reproductionNumber);
        ia.setTransmissionCoefficient(null);

        infection.getInfectionAcquisition().add(ia);

        return infection;
    }

//	public static String addSARS(ApolloServiceEI port) {
//		addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/5.8d, /*lp*/2.4d, /*itp*/1.4d, /*pns*/0.5d, /*psgs*/0.9d, 1.42);
//		Infection id = new Infection();
//		id.set
//		id.setAsymptomaticInfectionFraction(0.5d);
//		id.setHostOrganismName("human");
//		id.setCausativeOrganismName("SARS coronavirus");
//
//		TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
//		tdp.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp.setTimeStepValue(2.4d);
//		id.setLatentPeriod(tdp);
//
//		TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
//		tdp2.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp2.setTimeStepValue(5.8d);
//		id.setInfectiousPeriod(tdp2);
//
//		id.setReproductionNumber(1.42);
//
//		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("Infection");
//		itemIndexingLabels.add("SARS");
//		itemIndexingLabels.add("Severe acute respiratory syndrome");
//		itemIndexingLabels.add("Hong Kong");
//		itemIndexingLabels.add("2002");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("hypothetical");
//
//		return port.addLibraryItem(
//				id,
//				"The infectious disease of humans caused by Severe acute respiratory syndrome (S.A.R.S.)",
//				"Hypothetical SARS added by John Levander, University of Pittsburgh",
//				"Infection", itemIndexingLabels);
//
//		
//	}
//	public static String addSmallpox(ApolloServiceEI port) {
//		Infection id = new Infection();
//		id.setAsymptomaticInfectionFraction(0.5d);
//		id.setHostOrganismName("human");
//		id.setCausativeOrganismName("variola virus");
//
//		TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
//		tdp.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp.setTimeStepValue(2.7d);
//		id.setLatentPeriod(tdp);
//
//		TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
//		tdp2.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp2.setTimeStepValue(9d);
//		id.setInfectiousPeriod(tdp2);
//
//		id.setReproductionNumber(4.3);
//
//		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("Infection");
//		itemIndexingLabels.add("Smallpox");
//		itemIndexingLabels.add("variola virus");
//		itemIndexingLabels.add("usa");
//		itemIndexingLabels.add("1977");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("hypothetical");
//
//		return port
//				.addLibraryItem(
//						id,
//						"The infectious disease of humans caused by Smallpox",
//						"Hypothetical Smallpox virus added by John Levander, University of Pittsburgh",
//						"Infection", itemIndexingLabels);
//
//	}
//
//	public static String addRubella(ApolloServiceEI port) {
//		Infection id = new Infection();
//		id.setAsymptomaticInfectionFraction(0.2d);
//		id.setHostOrganismName("human");
//		id.setCausativeOrganismName("rubella virus");
//
//		TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
//		tdp.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp.setTimeStepValue(3d);
//		id.setLatentPeriod(tdp);
//
//		TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
//		tdp2.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp2.setTimeStepValue(3d);
//		id.setInfectiousPeriod(tdp2);
//
//		id.setReproductionNumber(5d);
//
//		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("Infection");
//		itemIndexingLabels.add("Rubella");
//		itemIndexingLabels.add("rubella virus");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("hypothetical");
//
//		return port
//				.addLibraryItem(
//						id,
//						"The infectious disease of humans caused by Rubella",
//						"Hypothetical Rubella virus added by John Levander, University of Pittsburgh",
//						"Infection", itemIndexingLabels);
//
//	}
//
//	public static String addDiptheria(ApolloServiceEI port) {
//		
//		Infection id = new Infection();
//		id.setPathogenTaxonID(PathogenTaxonID.)
//		id.setAsymptomaticInfectionFraction(0.25d);
//		id.setHostOrganismName("human");
//		id.setCausativeOrganismName("corynebacterium diphtheriae");
//
//		TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
//		tdp.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp.setTimeStepValue(3.2d);
//		id.setLatentPeriod(tdp);
//
//		TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
//		tdp2.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp2.setTimeStepValue(5.2d);
//		id.setInfectiousPeriod(tdp2);
//
//		id.setReproductionNumber(4.1d);
//
//		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("Infection");
//		itemIndexingLabels.add("Diptheria");
//		itemIndexingLabels.add("corynebacterium diphtheriae");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("hypothetical");
//
//		return port
//				.addLibraryItem(
//						id,
//						"The infectious disease of humans caused by Diptheria",
//						"Hypothetical Diptheria added by John Levander, University of Pittsburgh",
//						"Infection", itemIndexingLabels);
//	}
//
//	public static String addMeningitidis(ApolloServiceEI port) {
//		Infection id = new Infection();
//		id.setAsymptomaticInfectionFraction(0.25d);
//		id.setHostOrganismName("human");
//		id.setCausativeOrganismName("neisseria meningitidis");
//
//		TemporalDiseaseParameter tdp = new TemporalDiseaseParameter();
//		tdp.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp.setTimeStepValue(2.2d);
//		id.setLatentPeriod(tdp);
//
//		TemporalDiseaseParameter tdp2 = new TemporalDiseaseParameter();
//		tdp2.setTimeStepUnit(TimeStepUnit.DAY);
//		tdp2.setTimeStepValue(6.2d);
//		id.setInfectiousPeriod(tdp2);
//
//		id.setReproductionNumber(3.1d);
//
//		ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("Infection");
//		itemIndexingLabels.add("meningitis");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("hypothetical");
//
//		return port
//				.addLibraryItem(
//						id,
//						"The infectious disease of humans caused by Meningitis",
//						"Hypothetical Meningitis added by John Levander, University of Pittsburgh",
//						"Infection", itemIndexingLabels);
//	}
    public static String addH1N1(LibraryServiceEI port) {
        Infection id = addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/ 6d, /*lp*/ 2d, /*itp*/ 1.4d, /*pns*/ 0.5d, /*psgs*/ 0.9d, /*r0*/ 1.3d);

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("Infection");
        itemIndexingLabels.add("Influenza");
        itemIndexingLabels.add("swine");
        itemIndexingLabels.add("usa");
        itemIndexingLabels.add("A/H1N1");
        itemIndexingLabels.add("H1N1");
        itemIndexingLabels.add("2009");
        itemIndexingLabels.add("human");
        itemIndexingLabels.add("hypothetical");

        return port.addLibraryItem(
                id,
                "The infectious disease of humans caused by Influenza A virus subtype H1N1",
                "Hypothetical H1N1 virus added by John Levander, University of Pittsburgh",
                "Infection", itemIndexingLabels);

    }

    public static String addH5N1(LibraryServiceEI port) {
        Infection id = addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/ 6.3d, /*lp*/ 2.1d, /*itp*/ 1.4d, /*pns*/ 0.5d, /*psgs*/ 0.9d, /*r0*/ 1.4d);

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("Infection");
        itemIndexingLabels.add("Influenza");
        itemIndexingLabels.add("china");
        itemIndexingLabels.add("avian");
        itemIndexingLabels.add("A/H5N1");
        itemIndexingLabels.add("H5N1");
        itemIndexingLabels.add("2008");
        itemIndexingLabels.add("human");
        itemIndexingLabels.add("hypothetical");

        return port.addLibraryItem(
                id,
                "The infectious disease of humans caused by Influenza A virus subtype H5N1",
                "Hypothetical H5N1 virus added by John Levander, University of Pittsburgh",
                "Infection", itemIndexingLabels);

    }

    public static String addH7N9(LibraryServiceEI port) {
        Infection id = addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/ 5.3d, /*lp*/ 1.9d, /*itp*/ 1.4d, /*pns*/ 0.6d, /*psgs*/ 0.9d, /*r0*/ 1.5d);

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("Infection");
        itemIndexingLabels.add("Influenza");
        itemIndexingLabels.add("taiwan");
        itemIndexingLabels.add("avian");
        itemIndexingLabels.add("A/H7N9");
        itemIndexingLabels.add("H7N9");
        itemIndexingLabels.add("2013");
        itemIndexingLabels.add("human");
        itemIndexingLabels.add("hypothetical");

        return port.addLibraryItem(
                id,
                "The infectious disease of humans caused by Influenza A virus subtype H7N9",
                "Hypothetical H7N9 virus added by John Levander, University of Pittsburgh",
                "Infection", itemIndexingLabels);

    }

    public static String addH3N2(LibraryServiceEI port) {
        Infection id = addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/ 6.3d, /*lp*/ 2.9d, /*itp*/ 1.4d, /*pns*/ 0.6d, /*psgs*/ 0.9d, /*r0*/ 1.2d);

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("Infection");
        itemIndexingLabels.add("Influenza");
        itemIndexingLabels.add("california");
        itemIndexingLabels.add("A/H3N2");
        itemIndexingLabels.add("H3N2");
        itemIndexingLabels.add("2009");
        itemIndexingLabels.add("human");
        itemIndexingLabels.add("hypothetical");

        return port.addLibraryItem(
                id,
                "The infectious disease of humans caused by Influenza A virus subtype H3N2",
                "Hypothetical H3N2 virus added by John Levander, University of Pittsburgh",
                "Infection", itemIndexingLabels);

    }

    public static String addH7N7(LibraryServiceEI port) {
        Infection id = addDisease(PathogenTaxonID.INFLUENZA, "human", /*ip*/ 6.1d, /*lp*/ 2.6d, /*itp*/ 1.4d, /*pns*/ 0.3d, /*psgs*/ 0.9d, /*r0*/ 7.44d);

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("Infection");
        itemIndexingLabels.add("Influenza");
        itemIndexingLabels.add("netherlands");
        itemIndexingLabels.add("A/H7N7");
        itemIndexingLabels.add("H7N7");
        itemIndexingLabels.add("2003");
        itemIndexingLabels.add("human");
        itemIndexingLabels.add("hypothetical");

        return port.addLibraryItem(
                id,
                "The infectious disease of humans caused by Influenza A virus subtype H7N7"
                + "",
                "Hypothetical H7N7 virus aded by John Levander, University of Pittsburgh",
                "Infection", itemIndexingLabels);

    }

    public static String addAcAvt(LibraryServiceEI port) {
        Antiviral av = new Antiviral();
        av.setAntiviralId("Tamiflu");
        av.setDescription("Tamiflu");
        AntiviralTreatment avt = new AntiviralTreatment();
        avt.setDescription("A treatment course of the antiviral Tamiflu");
        avt.setNumDosesInTreatmentCourse(new BigInteger("10"));
        avt.setSpeciesOfTreatedOrganisms("human");
        avt.getTreatmentContraindications();

        // avt.s
        AntiviralTreatmentControlMeasure atcm = new AntiviralTreatmentControlMeasure();
        atcm.setAntiviralTreatment(avt);
        atcm.setDescription("A control measure in which all sick humans are treated with a course of Tamiflu.");
        atcm.setControlMeasureCompliance(0.9);
        atcm.setControlMeasureNamedPrioritizationScheme("Treat Sick Only");
        atcm.setControlMeasureReactiveEndPointFraction(1d);
        atcm.setControlMeasureResponseDelay(4d);
        FixedStartTime atcmFst = new FixedStartTime();
        atcmFst.setStartTime(new BigInteger("0"));
        atcmFst.setStopTime(new BigInteger("127"));
        atcm.setControlMeasureStartTime(atcmFst);
        for (int i = 0; i < 128; i++) {
            atcm.getAntiviralSupplySchedule().add(2000);
            atcm.getAntiviralTreatmentAdministrationCapacity().add(
                    new BigInteger("2000"));
        }

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
        itemIndexingLabels.add("AntiviralTreatmentControlMeasure");
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

        return port.addLibraryItem(
                atcm,
                "A hypothetical control measure in which all sick humans are treated with a course of Tamiflu.",
                "Expert opinion regarding a realistic (yet hypothetical) Antiviral Treatment Control Measure.",
                "AntiviralTreatmentControlMeasure", itemIndexingLabels);

    }

    public static String addAcVcm(LibraryServiceEI port) {
        // add Vacc
        Vaccination vacc = new Vaccination();
        vacc.setDescription("A/(H3N2) Victoria/361//2011-like");
        vacc.setNumDosesInTreatmentCourse(new BigInteger("1"));
        vacc.setSpeciesOfTreatedOrganisms("human");
        vacc.getTreatmentContraindications();

        Treatment t = new Treatment();
        t.setDescription("dummy treatment");
        t.setNumDosesInTreatmentCourse(new BigInteger("1"));
        t.setSpeciesOfTreatedOrganisms("human");
//		t.getTreatmentContraindications();

        VaccinationEfficacyForSimulatorConfiguration vesc = new VaccinationEfficacyForSimulatorConfiguration();
        vesc.setStrainIdentifier("A/(H3N2) Victoria/361//2011-like");
        vesc.setForVaccinationPreventableOutcome(VaccinationPreventableOutcome.INFECTION);
        // vesc.setTreatment(vacc);
        vesc.setTreatment(t);
        vesc.setVaccineIdentifier("A/(H3N2) Victoria/361//2011-like");
        vesc.setAverageVaccinationEfficacy(0.47);
        vesc.setDescription("A/(H3N2) Victoria/361//2011-like");

        vacc.getVaccinationEfficacies().add(vesc);

        VaccinationControlMeasure vcm = new VaccinationControlMeasure();
        vcm.setVaccination(vacc);
        vcm.setControlMeasureCompliance(0.5);
        vcm.setControlMeasureNamedPrioritizationScheme("ACIP");
        vcm.setControlMeasureReactiveEndPointFraction(1d);
        vcm.setControlMeasureResponseDelay(0d);
        FixedStartTime vaccCmFst = new FixedStartTime();
        vaccCmFst.setStartTime(new BigInteger("0"));
        vaccCmFst.setStopTime(new BigInteger("127"));
        vcm.setControlMeasureStartTime(vaccCmFst);
        vcm.setDescription("dummy vaccination control measure to hold efficacy");

        for (int i = 0; i < 56; i++) {
            vcm.getVaccineSupplySchedule().add(0);
        }

        for (int i = 56; i < 87; i++) {
            vcm.getVaccineSupplySchedule().add(0);
        }

        for (int i = 87; i < 127; i++) {
            vcm.getVaccineSupplySchedule().add(0);
        }

        for (int i = 0; i < 127; i++) {
            vcm.getVaccinationAdministrationCapacity().add(
                    new BigInteger("0"));
        }

        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add("VaccinationControlMeasure");
//		itemIndexingLabels.add("Vaccination");
//		itemIndexingLabels.add("Vaccine");
//		itemIndexingLabels.add("Allegheny County");
//		itemIndexingLabels.add("42003");
//		itemIndexingLabels.add("Allegheny County Health Department");
//		itemIndexingLabels.add("ACHD");
//		itemIndexingLabels.add("2009");
//		itemIndexingLabels.add("H1N1");
//		itemIndexingLabels.add("human");
//		itemIndexingLabels.add("non-hypothetical");

        return port.addLibraryItem(vcm, vcm.getDescription(),
                "", "VaccinationControlMeasure",
                itemIndexingLabels);
    }

    public static String addAllSccm(LibraryServiceEI port) {
        SchoolClosureControlMeasure scm = new SchoolClosureControlMeasure();
        ReactiveStartTime rst = new ReactiveStartTime();
        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
        rtd.setReactiveControlMeasureThreshold(0.01);
        rtd.setReactiveControlMeasureTest("Symptomatic Students");
        rtd.setAscertainmentDelay(new BigInteger("2"));
        rtd.setAscertainmentFraction(0.5);
        rst.setTrigger(rtd);
        scm.setControlMeasureStartTime(rst);
        scm.setControlMeasureCompliance(1d);
        scm.setControlMeasureNamedPrioritizationScheme("Immediately Close All Schools");
        scm.setControlMeasureReactiveEndPointFraction(1d);
        scm.setControlMeasureResponseDelay(2d);
        scm.setDescription("A school closure control measure where all schools in the jurisdiction are closed to mitigate the spread of an infectious disease.");
        scm.setSchoolClosureDuration(new BigInteger("56"));
        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.ALL);
        List<String> indexingValues = new ArrayList<String>();
        indexingValues.add("BARDA");
        indexingValues.add("SchoolClosureControlMeasure");
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

        return port.addLibraryItem(
                scm,
                scm.getDescription(),
                "A hypothetical school closure control measure specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
                "SchoolClosureControlMeasure", indexingValues);
    }

    public static String addIndividualSccm(LibraryServiceEI port) {
        SchoolClosureControlMeasure scm = new SchoolClosureControlMeasure();

        ReactiveStartTime rst = new ReactiveStartTime();
        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
        rtd.setReactiveControlMeasureThreshold(0.01);
        rtd.setReactiveControlMeasureTest("Symptomatic Students");
        rtd.setAscertainmentDelay(new BigInteger("2"));
        rtd.setAscertainmentFraction(0.5);
        rst.setTrigger(rtd);
        scm.setControlMeasureStartTime(rst);
        scm.setControlMeasureCompliance(1d);
        scm.setControlMeasureNamedPrioritizationScheme("Close Schools Individually");
        scm.setControlMeasureReactiveEndPointFraction(1d);
        scm.setControlMeasureResponseDelay(2d);
        scm.setDescription("A school closure control measure where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
        scm.setSchoolClosureDuration(new BigInteger("56"));
        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.INDIVIDUAL);
        List<String> indexingValues = new ArrayList<String>();
        indexingValues.add("BARDA");
        indexingValues.add("SchoolClosureControlMeasure");
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

        return port.addLibraryItem(
                scm,
                scm.getDescription(),
                "A hypothetical school closure control measure specified by BARDA in 2009 to Dr. Shawn Brown when running simulations of the FRED simulator to determine the effect of this control measure on the spread of disease.",
                "SchoolClosureControlMeasure", indexingValues);
    }

    public static void main(String[] args) throws InterruptedException,
            IOException, DatatypeConfigurationException {

        LibraryServiceV131 service = new LibraryServiceV131(new URL(WSDL_LOC));
        LibraryServiceEI port = service.getLibraryServiceEndpoint();

        addAcVcm(port);
//		addAcAvt(port);
//		addAllSccm(port);
//		addIndividualSccm(port);
//		addH1N1(port);
//		addH3N2(port);
//		addH5N1(port);
//		addH7N7(port);
//		addH7N9(port);
//		addDiptheria(port);
//		addMeningitidis(port);
//		addRubella(port);
//		addSARS(port);
//		addSmallpox(port);

        GregorianCalendar c = new GregorianCalendar();
        c.add(Calendar.YEAR, -1);
        XMLGregorianCalendar date;

        date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        List<String> l = port.getUuidsForLibraryItemsCreatedSinceDateTime(date);
        for (int i = 0; i < l.size(); i++) {
            CuratedLibraryItemContainer item = port.getLibraryItem(l.get(i));
            System.out.printf("Item %d:\n\tUUID: %s\n\tType: %s\n\tDescription: %s\n\tSource: %s\n",
                    i + 1, item.getCuratedLibraryItem().getItemUuid(),
                    item.getCuratedLibraryItem().getItemType(), item.getCuratedLibraryItem().getItemDescription(), item.getCuratedLibraryItem().getItemSource());
            System.out.print("\tIndexing labels: ");
            for (int j = 0; j < item.getCuratedLibraryItem().getItemIndexingLabels().size(); j++) {
                System.out.print(item.getCuratedLibraryItem().getItemIndexingLabels().get(j));
                if (j < item.getCuratedLibraryItem().getItemIndexingLabels().size() - 1) {
                    System.out.print(", ");
                }
                if (j > 0 && j % 4 == 0) {
                    System.out.print("\n                         ");
                }
            }
            System.out.println();

        }

        // // add
        // System.out.println(uuid);
        //
        // // grab
        //
        // CuratedLibraryItemContainer clic = port.getLibraryItem(uuid);
        // System.out.println("The item is of type: "
        // + clic.getCuratedLibraryItem().getItemType());
        // System.out.println("The item is tagged as follows: ");
        // for (int i = 0; i < clic.getCuratedLibraryItem()
        // .getItemIndexingLabels().size(); i++) {
        // System.out.println("\t"
        // + clic.getCuratedLibraryItem().getItemIndexingLabels()
        // .get(i));
        // }
        // SchoolClosureControlMeasure sscm = (SchoolClosureControlMeasure) clic
        // .getApolloIndexableItem();
        // System.out.println("The school will remain closed for "
        // + sscm.getSchoolClosureDuration() + " days.");
        //
        // // query
        // GregorianCalendar c = new GregorianCalendar();
        // c.add(Calendar.YEAR, -1);
        // XMLGregorianCalendar date;
        //
        // date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        // List<String> l =
        // port.getUuidsForLibraryItemsCreatedSinceDateTime(date);
        // System.out.println(l);
        //
        // // query
        // l =
        // port.getUuidsForLibraryItemsGivenType("SchoolClosureControlMeasure");
        // System.out.println(l);

    }
}
//
//
// @SuppressWarnings("unused")
// ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
//
//
// Authentication auth = new Authentication();
// auth.setRequesterId("fake_user");
// auth.setRequesterPassword("fake_password");
// srr.setAuthentication(auth);
// //
// // ServiceRecord sr = new ServiceRecord();
// SoftwareIdentification si = new SoftwareIdentification();
// si.setSoftwareDeveloper("UPitt");
// si.setSoftwareName("SEIR");
// si.setSoftwareVersion("1.2");
// si.setSoftwareType(ApolloSoftwareType.SIMULATOR);
// srr.setSoftwareIdentification(si);
//
// srr.setUrl("http://localhost:8080/seirsimulatorservice/services/seirsimulatorservice?wsdl");
//
// port.registerService(srr);
// // sr.setSimulatorIdentification(si);
// //
// // srr.setServiceRecord(sr);
// // srr.setUrl("http://youtube.com");
// // //srr.setUrl("http://localhost:8087/fred?wsdl");
// //
// // Holder<Boolean> success = new Holder<Boolean>();
// // Holder<String> msg = new Holder<String>();
// // port.registerService(srr, success, msg);
// // System.out.println(msg.value);
//
// // List<ServiceRecord> l = port.getRegisteredServices();
// // System.out.println("Found " + l.size() + " registered services!");
// // for (ServiceRecord r : l) {
// //
// System.out.println(r.getSimulatorIdentification().getSimulatorDeveloper());
// // System.out.println(r.getSimulatorIdentification().getSimulatorName());
// // System.out.println(r.getSimulatorIdentification().getSimulatorVersion() +
// "\n\n");
// // }
//
// SimulatorConfiguration simulatorConfiguration = new SimulatorConfiguration();
//
// simulatorConfiguration.setAuthentication(auth);
// // simulatorConfiguration
// // .setAntiviralControlMeasure(new AntiviralTreatmentControlMeasure());
// //// AntiviralTreatmentControlMeasure acm = simulatorConfiguration
// // .getAntiviralControlMeasure();
// // acm.setAntiviralCmCompliance(0d);
// // acm.setAntiviralEfficacy(0d);
// // acm.setAntiviralEfficacyDelay(0d);
// // acm.getAntiviralAdminSchedule().add(1d);
// // acm.getAntiviralSupplySchedule().add(1d);
//
// simulatorConfiguration.setSimulatorIdentification(si);
//
// simulatorConfiguration.setDisease(new Disease());
// Disease disease = simulatorConfiguration.getDisease();
// disease.setAsymptomaticInfectionFraction(0.5);
// disease.setDiseaseName("Influenza");
// disease.setInfectiousPeriod(3.2);
// disease.setLatentPeriod(2.0);
// disease.setReproductionNumber(1.7);
//
// simulatorConfiguration
// .setPopulationInitialization(new SimulatedPopulation());
// SimulatedPopulation sp = simulatorConfiguration
// .getPopulationInitialization();
// sp.setPopulationLocation("42003");
//
// List<PopulationDiseaseState> ds = sp.getPopulationDiseaseState();
// PopulationDiseaseState pds = new PopulationDiseaseState();
// pds.setDiseaseState("susceptible");
// pds.setFractionOfPopulation(0.95);
// ds.add(pds);
// pds = new PopulationDiseaseState();
// pds.setDiseaseState("exposed");
// pds.setFractionOfPopulation(0.0);
// ds.add(pds);
// pds = new PopulationDiseaseState();
// pds.setDiseaseState("infectious");
// pds.setFractionOfPopulation(0.01);
// ds.add(pds);
// pds = new PopulationDiseaseState();
// pds.setDiseaseState("recovered");
// pds.setFractionOfPopulation(0.04);
// ds.add(pds);
//
// simulatorConfiguration
// .setSimulatorTimeSpecification(new SimulatorTimeSpecification());
// SimulatorTimeSpecification stc = simulatorConfiguration
// .getSimulatorTimeSpecification();
// stc.setRunLength(new BigInteger("30"));
// stc.setTimeStepUnit(TimeStepUnit.DAY);
// stc.setTimeStepValue(1d);
//
// // simulatorConfiguration
// // .setVaccinationControlMeasure(new VaccinationControlMeasure());
// // VaccinationControlMeasure vcm = simulatorConfiguration
// // .getVaccinationControlMeasure();
// // vcm.setVaccineCmCompliance(0d);
// // vcm.setVaccineEfficacy(0d);
// // vcm.setVaccineEfficacyDelay(0d);
// // for (int i = 0; i < 30; i++) {
// // vcm.getVaccinationAdminSchedule().add(0d);
// // vcm.getVaccineSupplySchedule().add(0d);
// // }
//
// //MessageDigest md = MessageDigest.getInstance("MD5");
//
// // XStream xStream = new XStream(new DomDriver());
// // FileWriter fw = new FileWriter(new File("simulatorConfiguration.xml"));
// // xStream.toXML(simulatorConfiguration, fw);
// // fw.close();
//
// // OBJECTMAPPER MAPPER = NEW OBJECTMAPPER();
// // WRITER STRWRITER = NEW FILEWRITER(NEW
// FILE("SIMULATORCONFIGURATION.JSON"));
// // MAPPER.WRITEVALUE(STRWRITER, SIMULATORCONFIGURATION);
// // @SUPPRESSWARNINGS("UNUSED")
// // STRING USERDATAJSON = STRWRITER.TOSTRING();
// // STRWRITER.CLOSE();
// //
//
// // String runId = port.runSimulation(simulatorConfiguration);
// // System.out.println("Simulator returned runId: " + runId );
// // // String runId = "Pitt,PSC,CMU_FRED_2.0.1_231162";
// // RunStatus rs = port.getRunStatus(runId, sr);
// // while (rs.getStatus() != RunStatusEnum.COMPLETED) {
// // System.out.println("Status is " + rs.getStatus());
// // System.out.println("Message is " + rs.getMessage());
// // System.out.println("\n");
// // Thread.sleep(500);
// // rs = port.getRunStatus(runId, sr);
// // }
// // System.out.println("Status is " + rs.getStatus());
// // System.out.println("Message is " + rs.getMessage());
//
// // port.unRegisterService(srr, success, msg);
// // System.out.println(msg.value);
//
// }

