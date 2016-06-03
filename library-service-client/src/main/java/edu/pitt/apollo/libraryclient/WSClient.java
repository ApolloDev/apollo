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

import edu.pitt.apollo.GlobalConstants;
import edu.pitt.apollo.library_service_types.v3_1_0.*;
import edu.pitt.apollo.service.libraryservice.v3_1_0.LibraryServiceEI;
import edu.pitt.apollo.service.libraryservice.v3_1_0.LibraryServiceV310;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.types.v3_1_0.Census;
import edu.pitt.apollo.types.v3_1_0.IndividualTreatmentControlMeasure;
import edu.pitt.apollo.types.v3_1_0.InfectiousDiseaseScenario;
import edu.pitt.apollo.types.v3_1_0.PlaceClosureControlMeasure;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import java.text.ParseException;
import java.util.Map;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

public class WSClient {

	public static final String WSDL_LOC = "http://betaweb.rods.pitt.edu/library-service-war-3.1.0-SNAPSHOT/services/libraryservice?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/libraryservice/v3_1_0/", "LibraryService_v3.1.0");
	private static final String LIBRARY_CONNECTION_PROPERTIES_FILE = "library_service_connection.properties";

	public static final String APOLLO_DIR;

	static {
		Map<String, String> env = System.getenv();
		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
		if (apolloDir != null) {
			if (!apolloDir.endsWith(File.separator)) {
				apolloDir += File.separator;
			}
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + apolloDir);
		} else {
			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + "environment variable not found!");
		}

		APOLLO_DIR = apolloDir;
	}

	public static void main(String[] args) throws MalformedURLException, FileNotFoundException, IOException, JAXBException, DatatypeConfigurationException, ParseException {
		LibraryServiceV310 ls = new LibraryServiceV310(new URL(WSDL_LOC), SERVICE);
		LibraryServiceEI port = ls.getLibraryServiceEndpoint();

		Authentication a = getAuthentication();
		AddLibraryItemContainerResult scenarioResult = addInfectiousDiseaseScenarioToLibrary(a, port);
//        UpdateLibraryItemContainerResult result = updateInfectiousDiseaseScenarioInLibrary(a, 1, port);

		//setReleaseVersionForInfectiousDiseaseScenario(a, port, scenarioResult.getVersion(), scenarioResult.getUrn());
//		AddLibraryItemContainerResult vaccResult = addVaccinationControlMeasureToLibrary(a, port);
//		setReleaseVersionForVaccinationControlStratgy(a, port, vaccResult.getVersion(), vaccResult.getUrn());
////
//		AddLibraryItemContainerResult antiviralResult = addAntiviralControlMeasureToLibrary(a, port);
//		setReleaseVersionForAntiviralControlStratgy(a, port, antiviralResult.getVersion(), antiviralResult.getUrn());
////
//		AddLibraryItemContainerResult allSchoolsResult = addAllSchoolClosureControlMeasureToLibrary(a, port);
//		setReleaseVersionForAllSchoolsControlStratgy(a, port, allSchoolsResult.getVersion(), allSchoolsResult.getUrn());
////
//		AddLibraryItemContainerResult individualSchoolsResult = addIndividualSchoolClosureControlMeasureToLibrary(a, port);
//		setReleaseVersionForIndividualSchoolsControlStratgy(a, port, individualSchoolsResult.getVersion(), individualSchoolsResult.getUrn());
//		AddOrUpdateLibraryItemContainerResult result = updateVaccinationControlMeasureInLibrary(a, port);
//		GetLibraryItemContainerResult result = getLibraryItem(a, port);
		System.out.println("finished");
	}

	private static Authentication getAuthentication() throws FileNotFoundException, IOException {
		File props = new File(APOLLO_DIR + LIBRARY_CONNECTION_PROPERTIES_FILE);
		InputStream fis = new FileInputStream(props);
		Properties properties = new Properties();
		properties.load(fis);
		fis.close();

		Authentication auth = new Authentication();
		auth.setRequesterId(properties.getProperty("user"));
		auth.setRequesterPassword(properties.getProperty("password"));
		return auth;
	}

	private static GetLibraryItemURNsResult getURNs(Authentication auth, LibraryServiceEI port) {

		GetLibraryItemURNsMessage message = new GetLibraryItemURNsMessage();
		message.setAuthentication(auth);
		message.setItemType("Epidemic");

		return port.getLibraryItemURNs(message);
	}

	private static SetReleaseVersionResult setReleaseVersionForInfectiousDiseaseScenario(Authentication auth, LibraryServiceEI port, int version, int urn) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("Setting release version for influenza scenario");
		message.setUrn(urn);
		message.setVersion(version);

		return port.setReleaseVersionForLibraryItem(message);
	}

	private static SetReleaseVersionResult setReleaseVersionForVaccinationControlStratgy(Authentication auth, LibraryServiceEI port, int version, int urn) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("Setting release version for 2009 Allegheny County vaccination control strategy");
		message.setUrn(urn);
		message.setVersion(version);

		return port.setReleaseVersionForLibraryItem(message);
	}

	private static SetReleaseVersionResult setReleaseVersionForAntiviralControlStratgy(Authentication auth, LibraryServiceEI port, int version, int urn) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("Setting release version for 2009 Allegheny County antiviral control strategy");
		message.setUrn(urn);
		message.setVersion(version);

		return port.setReleaseVersionForLibraryItem(message);
	}

	private static SetReleaseVersionResult setReleaseVersionForAllSchoolsControlStratgy(Authentication auth, LibraryServiceEI port, int version, int urn) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("Setting release version for 2009 Allegheny County antiviral control strategy");
		message.setUrn(urn);
		message.setVersion(version);

		return port.setReleaseVersionForLibraryItem(message);
	}

	private static SetReleaseVersionResult setReleaseVersionForIndividualSchoolsControlStratgy(Authentication auth, LibraryServiceEI port, int version, int urn) {

		SetReleaseVersionMessage message = new SetReleaseVersionMessage();
		message.setAuthentication(auth);
		message.setComment("Setting release version for 2009 Allegheny County antiviral control strategy");
		message.setUrn(urn);
		message.setVersion(version);

		return port.setReleaseVersionForLibraryItem(message);
	}

	private static QueryResult query(Authentication auth, LibraryServiceEI port) {

		QueryMessage message = new QueryMessage();

		message.setAuthentication(auth);

		String query = "SELECT id,json_of_library_object->1->'catalogEntry'->1->'itemDescription' AS description FROM library_objects";
		message.setQuery(query);

		QueryResult result = port.query(message);
		return result;
	}

//	private static GetLibraryItemURNsResult getURNs(Authentication auth, LibraryServiceEI port) {
//
//		GetLibraryItemURNsMessage message = new GetLibraryItemURNsMessage();
//		message.setAuthentication(auth);
//		message.setItemType("Treatment");
//		return port.getLibraryItemURNs(message);
//	}
	private static AddLibraryItemContainerResult addInfectiousDiseaseScenarioToLibrary(Authentication auth, LibraryServiceEI port)
			throws DatatypeConfigurationException, ParseException {

		InfectiousDiseaseScenario scenario = ExampleInfectiousDiseaseScenario.getScenario();
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(scenario);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("2009 H1N1 Allegheny County R0 = 1.3");
		entry.setTextualIdentifier("2009 H1N1 Allegheny County R0 = 1.3");
		lic.setCatalogEntry(entry);

		AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding H1N1 scenario for Allegheny County in 2009");

		return port.addLibraryItemContainer(message);
	}

	private static UpdateLibraryItemContainerResult updateInfectiousDiseaseScenarioInLibrary(Authentication auth, int urn, LibraryServiceEI port)
			throws DatatypeConfigurationException, ParseException {

		InfectiousDiseaseScenario scenario = ExampleInfectiousDiseaseScenario.getScenario();
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(scenario);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("2009 H1N1 Allegheny County R0 = 1.3");
		entry.setTextualIdentifier("2009 H1N1 Allegheny County R0 = 1.3");
		lic.setCatalogEntry(entry);

		UpdateLibraryItemContainerMessage message = new UpdateLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding H1N1 scenario for Allegheny County in 2009");
		message.setUrn(urn);

		return port.updateLibraryItemContainer(message);
	}

	private static AddLibraryItemContainerResult addVaccinationControlMeasureToLibrary(XMLGregorianCalendar startDate, Authentication auth, LibraryServiceEI port) {

		IndividualTreatmentControlMeasure strategy = ExampleVaccinationControlStrategy.getStrategy(startDate);
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(strategy);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
		entry.setTextualIdentifier("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");

		lic.setCatalogEntry(entry);

		AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding vaccination control strategy for Allegheny County in 2009");

		return port.addLibraryItemContainer(message);

	}

	private static AddLibraryItemContainerResult addAntiviralControlMeasureToLibrary(XMLGregorianCalendar startDate, Authentication auth, LibraryServiceEI port) {

		IndividualTreatmentControlMeasure strategy = ExampleAntiviralControlStrategy.getAntiviralControlMeasure(startDate);
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(strategy);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("2009 Antiviral Control Strategy (Tamiflu)");
		entry.setTextualIdentifier("2009 Antiviral Control Strategy (Tamiflu)");

		lic.setCatalogEntry(entry);

		AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding antiviral control strategy for Allegheny County in 2009");

		return port.addLibraryItemContainer(message);

	}

	private static AddLibraryItemContainerResult addAllSchoolClosureControlMeasureToLibrary(Authentication auth, LibraryServiceEI port) {

		PlaceClosureControlMeasure strategy = ExampleSchoolClosureControlStrategy.getAllSchoolsControlMeasure();
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(strategy);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("2009 School Closure (All Schools)");
		entry.setTextualIdentifier("2009 School Closure (All Schools)");

		lic.setCatalogEntry(entry);

		AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding all school closure control strategy for Allegheny County in 2009");

		return port.addLibraryItemContainer(message);

	}

	private static AddLibraryItemContainerResult addIndividualSchoolClosureControlMeasureToLibrary(Authentication auth, LibraryServiceEI port) {

		PlaceClosureControlMeasure strategy = ExampleSchoolClosureControlStrategy.getIndividualSchoolsControlMeasure();
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(strategy);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("2009 School Closure (Individual Schools)");
		entry.setTextualIdentifier("2009 School Closure (Individual Schools)");

		lic.setCatalogEntry(entry);

		AddLibraryItemContainerMessage message = new AddLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setComment("Adding individual school closure control strategy for Allegheny County in 2009");

		return port.addLibraryItemContainer(message);

	}

	private static UpdateLibraryItemContainerResult updateVaccinationControlMeasureInLibrary(XMLGregorianCalendar startDate, Authentication auth, LibraryServiceEI port, int urn) {

		IndividualTreatmentControlMeasure strategy = ExampleVaccinationControlStrategy.getStrategy(startDate);
		LibraryItemContainer lic = new LibraryItemContainer();
		lic.setLibraryItem(strategy);

		CatalogEntry entry = new CatalogEntry();
		entry.setDisplayName("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");
		entry.setTextualIdentifier("The vaccination control strategy used by Allegheny County to mitigate the spread of H1N1 for the 2009 Influenza season.");

		lic.setCatalogEntry(entry);

		UpdateLibraryItemContainerMessage message = new UpdateLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setUrn(urn);
		message.setComment("Adding vaccination control strategy for Allegheny County in 2009");

		return port.updateLibraryItemContainer(message);
	}

	private static UpdateLibraryItemContainerResult updateLibraryItemComtainer(Authentication auth, LibraryServiceEI port, int urn) {

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
		entry.setDisplayName("test item update");
		entry.setTextualIdentifier("test item update");

		lic.setCatalogEntry(entry);

		UpdateLibraryItemContainerMessage message = new UpdateLibraryItemContainerMessage();
		message.setLibraryItemContainer(lic);
		message.setAuthentication(auth);
		message.setUrn(urn);
		message.setComment("update 1");

		return port.updateLibraryItemContainer(message);
	}

	private static GetRevisionsResult getVersions(Authentication auth, LibraryServiceEI port, int urn) {

		GetVersionsMessage message = new GetVersionsMessage();
		message.setAuthentication(auth);
		message.setUrn(urn);

		return port.getVersionNumbersForLibraryItem(message);

	}

	private static GetLibraryItemContainerResult getLibraryItem(Authentication auth, LibraryServiceEI port, int urn) {

		GetLibraryItemContainerMessage message = new GetLibraryItemContainerMessage();
		message.setAuthentication(auth);
		message.setUrn(urn);
		message.setVersion(4);

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
//        IndividualTreatmentControlMeasure atcm = new IndividualTreatmentControlMeasure();
//        atcm.setIndividualTreatment(avt);
//        atcm.setDescription("A control strategy in which all sick humans are treated with a course of Tamiflu.");
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(0.9);
//        atcm.setControlMeasureCompliance(compliance);
//
//        ControlMeasureTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlMeasureTargetPopulationsAndPrioritization();
//        targetPopulationsAndPrioritization.setControlMeasureNamedPrioritizationScheme("Treat Sick Only");
//
////        atcm.setControlMeasureNamedPrioritizationScheme("Treat Sick Only");
//        atcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);
//        atcm.setControlMeasureReactiveEndPointFraction(1d);
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setUnitOfMeasure(UnitOfMeasure.DAYS);
//        responseDelay.setValue(4d);
//        atcm.setControlMeasureResponseDelay(responseDelay);
//        FixedStartTime atcmFst = new FixedStartTime();
//        atcmFst.setStartTimeRelativeToScenarioDate(new BigInteger("0"));
//        atcmFst.setStopTimeRelativeToScenarioDate(new BigInteger("127"));
//        atcm.setControlMeasureStartTime(atcmFst);
//        for (int i = 0; i < 128; i++) {
//            atcm.getSupplySchedule().add(new BigInteger("2000"));
//            atcm.getAdministrationCapacity().add(
//                    new BigInteger("2000"));
//        }
//
//        ArrayList<String> itemIndexingLabels = new ArrayList<String>();
//        itemIndexingLabels.add("IndividualTreatmentControlMeasure");
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
//                "IndividualTreatmentControlMeasure", itemIndexingLabels);
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
//        IndividualTreatmentControlMeasure vcm = new IndividualTreatmentControlMeasure();
//        vcm.setIndividualTreatment(vacc);
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(0.5);
//        vcm.setControlMeasureCompliance(compliance);
//
//        ControlMeasureTargetPopulationsAndPrioritization targetPopulationsAndPrioritization = new ControlMeasureTargetPopulationsAndPrioritization();
//        targetPopulationsAndPrioritization.setControlMeasureNamedPrioritizationScheme("ACIP");
//
//        vcm.setTargetPopulationsAndPrioritizations(targetPopulationsAndPrioritization);
//        vcm.setControlMeasureReactiveEndPointFraction(1d);
//
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setUnitOfMeasure(UnitOfMeasure.DAYS);
//        responseDelay.setValue(0d);
//
//        vcm.setControlMeasureResponseDelay(responseDelay);
//        FixedStartTime vaccCmFst = new FixedStartTime();
//        vaccCmFst.setStartTimeRelativeToScenarioDate(new BigInteger("0"));
//        vaccCmFst.setStopTimeRelativeToScenarioDate(new BigInteger("127"));
//        vcm.setControlMeasureStartTime(vaccCmFst);
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
//        itemIndexingLabels.add("IndividualTreatmentControlMeasure");
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
//                "", "IndividualTreatmentControlMeasure",
//                itemIndexingLabels);
//    }
//
//    public static AddLibraryItemResult addAllSccm(LibraryServiceEI port) {
//        SchoolClosureControlMeasure scm = new SchoolClosureControlMeasure();
//        ReactiveStartTime rst = new ReactiveStartTime();
//        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
//        rtd.setReactiveControlMeasureThreshold(0.01);
//        rtd.setReactiveControlMeasureTest("Symptomatic Students");
//        rtd.setAscertainmentDelay(new BigInteger("2"));
//        rtd.setAscertainmentFraction(0.5);
//        rst.setTrigger(rtd);
//        scm.setControlMeasureStartTime(rst);
//
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(1d);
//        scm.setControlMeasureCompliance(compliance);
//
//        scm.setControlMeasureReactiveEndPointFraction(1d);
//
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setValue(2d);
//
//        scm.setControlMeasureResponseDelay(responseDelay);
//        scm.setDescription("A school closure control strategy where all schools in the jURNsdiction are closed to mitigate the spread of an infectious disease.");
//        scm.setSchoolClosureDuration(new BigInteger("56"));
//        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.ALL);
//        List<String> indexingValues = new ArrayList<String>();
//        indexingValues.add("BARDA");
//        indexingValues.add("SchoolClosureControlMeasure");
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
//                "SchoolClosureControlMeasure", indexingValues);
//    }
//
//    public static AddLibraryItemResult addIndividualSccm(LibraryServiceEI port) {
//        SchoolClosureControlMeasure scm = new SchoolClosureControlMeasure();
//
//        ReactiveStartTime rst = new ReactiveStartTime();
//        ReactiveTriggersDefinition rtd = new ReactiveTriggersDefinition();
//        rtd.setReactiveControlMeasureThreshold(0.01);
//        rtd.setReactiveControlMeasureTest("Symptomatic Students");
//        rtd.setAscertainmentDelay(new BigInteger("2"));
//        rtd.setAscertainmentFraction(0.5);
//        rst.setTrigger(rtd);
//        scm.setControlMeasureStartTime(rst);
//        
//        ProbabilisticParameterValue compliance = new ProbabilisticParameterValue();
//        compliance.setValue(1d);
//        
//        scm.setControlMeasureCompliance(compliance);
//        scm.setControlMeasureReactiveEndPointFraction(1d);
//        
//        NumericParameterValue responseDelay = new NumericParameterValue();
//        responseDelay.setValue(2d);
//        
//        scm.setControlMeasureResponseDelay(responseDelay);
//        scm.setDescription("A school closure control strategy where schools with high disease activity are closed to mitigate the spread of an infectious disease.");
//        scm.setSchoolClosureDuration(new BigInteger("56"));
//        scm.setSchoolClosureTargetFacilities(SchoolClosureTargetFacilities.INDIVIDUAL);
//        List<String> indexingValues = new ArrayList<String>();
//        indexingValues.add("BARDA");
//        indexingValues.add("SchoolClosureControlMeasure");
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
//                "SchoolClosureControlMeasure", indexingValues);
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
////        GetLibraryItemUuidsResult result = port.getUuidsForLibraryItemsGivenType("SchoolClosureControlMeasure");
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
