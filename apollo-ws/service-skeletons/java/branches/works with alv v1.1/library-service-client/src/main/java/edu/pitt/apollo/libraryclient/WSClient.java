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

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.AddOrUpdateLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v2_1_0.CatalogEntry;
import edu.pitt.apollo.library_service_types.v2_1_0.GetDiffResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemContainerMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemContainerResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetLibraryItemURIsResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetReleaseVersionResult;
import edu.pitt.apollo.library_service_types.v2_1_0.GetVersionsMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.GetVersionsResult;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import edu.pitt.apollo.library_service_types.v2_1_0.LibraryItemContainer;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.QueryResult;
import edu.pitt.apollo.library_service_types.v2_1_0.SetReleaseVersionMessage;
import edu.pitt.apollo.library_service_types.v2_1_0.SetReleaseVersionResult;
import edu.pitt.apollo.libraryservice.methods.GetVersionsMethod;
import edu.pitt.apollo.service.libraryservice.v2_1_0.GetVersionsResponse;
import edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v2_1_0.LibraryServiceV210;
import edu.pitt.apollo.services_common.v2_1_0.Authentication;
import edu.pitt.apollo.types.v2_1_0.Census;
import edu.pitt.apollo.types.v2_1_0.ContactDefinitionEnum;
import edu.pitt.apollo.types.v2_1_0.TransmissionProbability;
import edu.pitt.apollo.types.v2_1_0.Treatment;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class WSClient {

	public static final String WSDL_LOC = "http://betaweb.rods.pitt.edu/library-service-war-2.1.0-SNAPSHOT/services/libraryservice?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/libraryservice/v2_1_0/", "LibraryService_v2.1.0");

	public static void main(String[] args) throws MalformedURLException {
		LibraryServiceV210 ls = new LibraryServiceV210(new URL(WSDL_LOC), SERVICE);
		LibraryServiceEI port = ls.getLibraryServiceEndpoint();

		Authentication a = new Authentication();
		a.setRequesterId("library_demo");
		a.setRequesterPassword("password");

//		QueryResult result = query(a, port);
//		System.out.println("ran query");
//		
//		AddOrUpdateLibraryItemContainerResult result = addLibraryItemContainer(a, port);
//		System.out.println(result.getStatus().getStatus());
//		System.out.println(result.getStatus().getMessage());
//		System.out.println(result.getVersion());
//		AddOrUpdateLibraryItemContainerResult updateResult = updateLibraryItemComtainer(a, port);
////
//		System.out.println(updateResult.getStatus().getStatus());
//		System.out.println(updateResult.getStatus().getMessage());
//		System.out.println(updateResult.getVersion());
//		GetVersionsResult result = getVersions(a, port);
//		List<Integer> versions = result.getVersions();
//		for (int i = 0; i < versions.size(); i++) {
//			System.out.println("version " + versions.get(i));
//		}
//		GetLibraryItemContainerResult result = getLibraryItem(a, port);
//		Census c = (Census) result.getLibraryItemContainer().getLibraryItem();
//		System.out.println(c.getDescription());
//		System.out.println(result.getLibraryItemContainer().getCatalogEntry().getItemDescription());
//		GetLibraryItemURIsResult result = getUris(a, port);
//		System.out.println(result.getURIs().size());
//		SetReleaseVersionResult setReleaseVersionResult = setReleaseVersion(a, port);
//		System.out.println(setReleaseVersionResult.getStatus().getStatus());
		
		
		GetReleaseVersionResult result = getReleaseVersion(a, port);
		System.out.println(result.getStatus().getStatus());
		System.out.println(result.getVersion());
	}

	private static GetReleaseVersionResult getReleaseVersion(Authentication auth, LibraryServiceEI port) {
		
		GetReleaseVersionMessage message = new GetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setUri("/epidemic/ebola/ZR/1976");
		
		return port.getReleaseVersion(message);
		
	}
	
	private static SetReleaseVersionResult setReleaseVersion(Authentication auth, LibraryServiceEI port) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("test");
		message.setUri("http://testtreatment");
		message.setVersion(2);

		return port.setReleaseVersion(message);
	}

	private static QueryResult query(Authentication auth, LibraryServiceEI port) {

		QueryMessage message = new QueryMessage();

		message.setAuthentication(auth);

		String query = "SELECT id,json_of_library_object->1->'catalogEntry'->1->'itemDescription' AS description FROM library_objects";
		message.setQuery(query);

		QueryResult result = port.query(message);
		return result;
	}

	private static GetLibraryItemURIsResult getUris(Authentication auth, LibraryServiceEI port) {

		GetLibraryItemURIsMessage message = new GetLibraryItemURIsMessage();
		message.setAuthentication(auth);
		message.setItemType("Treatment");
		return port.getLibraryItemURIs(message);
	}

	private static AddOrUpdateLibraryItemContainerResult addLibraryItemContainer(Authentication auth, LibraryServiceEI port) {

//		Census c = new Census();
//		c.setDescription("test description1");
//		c.setSimulatorTime(0);
//		try {
//			GregorianCalendar cal = new GregorianCalendar();
//			cal.setTime(new Date());
//			c.setReferenceDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
//		} catch (DatatypeConfigurationException ex) {
//			ex.printStackTrace();
//		}
		Treatment t = new Treatment();
		t.setDescription("the treatment");
		t.setNumDosesInTreatmentCourse(BigInteger.TEN);
		t.setSpeciesOfTreatedOrganism("people");

		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(t);

//		GetVersionsMessage test =  new GetVersionsMessage();
//		test.setAuthentication(auth);
//		test.setUri("http://test");
//		lic.setLibraryItem(test);
		CatalogEntry entry = new CatalogEntry();
		entry.setItemDescription("test treatment");
		lic.setCatalogEntry(entry);

		AddOrUpdateLibraryItemContainerMessage message = new AddOrUpdateLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setUri("http://testtreatment");
		message.setComment("initial commit");

		return port.addLibraryItemContainer(message);
	}

	private static AddOrUpdateLibraryItemContainerResult updateLibraryItemComtainer(Authentication auth, LibraryServiceEI port) {

		Census c = new Census();
		c.setDescription("test description 3rd time");
		c.setSimulatorTime(17);
//		try {
//			c.setReferenceDate(DatatypeFactory.newInstance().newXMLGregorianCalendar());
//		} catch (DatatypeConfigurationException ex) {
//			ex.printStackTrace();
//		}
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(c);

//		GetVersionsMessage test =  new GetVersionsMessage();
//		test.setAuthentication(auth);
//		test.setUri("http://test");
//		lic.setLibraryItem(test);
		CatalogEntry entry = new CatalogEntry();
		entry.setItemDescription("test item update");
		lic.setCatalogEntry(entry);

		AddOrUpdateLibraryItemContainerMessage message = new AddOrUpdateLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setUri("http://testtreatment");
		message.setComment("update 1");

		return port.updateLibraryItemContainer(message);
	}

	private static GetVersionsResult getVersions(Authentication auth, LibraryServiceEI port) {

		GetVersionsMessage message = new GetVersionsMessage();
		message.setAuthentication(auth);
		message.setUri("http://testitem");

		return port.getVersions(message);

	}

	private static GetLibraryItemContainerResult getLibraryItem(Authentication auth, LibraryServiceEI port) {

		GetLibraryItemContainerMessage message = new GetLibraryItemContainerMessage();
		message.setAuthentication(auth);
		message.setUri("http://testitem");
		message.setVersion(1);

		return port.getLibraryItemContainer(message);
	}

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
