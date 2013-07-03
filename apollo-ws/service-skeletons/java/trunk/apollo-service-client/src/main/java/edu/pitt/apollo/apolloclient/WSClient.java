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

package edu.pitt.apollo.apolloclient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import edu.pitt.apollo.service.apolloservice._07._03._2013.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice._07._03._2013.ApolloServiceV13;
import edu.pitt.apollo.types._07._03._2013.ApolloSoftwareType;
import edu.pitt.apollo.types._07._03._2013.Authentication;
import edu.pitt.apollo.types._07._03._2013.ServiceRegistrationRecord;
import edu.pitt.apollo.types._07._03._2013.SimulatorConfiguration;
import edu.pitt.apollo.types._07._03._2013.SoftwareIdentification;



public class WSClient {
	
	//public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice/services/apolloservice?wsdl";
	public static final String WSDL_LOC = "http://localhost:8080/apolloservice/services/apolloservice?wsdl";
//	public static void main(String[] args) throws InterruptedException, IOException {
//		ApolloServiceV13 service = new ApolloServiceV13(new URL(WSDL_LOC));
//		@SuppressWarnings("unused")
//		ApolloServiceEI port = service.getApolloServiceEndpoint();
//
//		@SuppressWarnings("unused")
//		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
//		
//
//		Authentication auth = new Authentication();
//		auth.setRequesterId("fake_user");
//		auth.setRequesterPassword("fake_password");
//		srr.setAuthentication(auth);
////
////		ServiceRecord sr = new ServiceRecord();
//		SoftwareIdentification si = new SoftwareIdentification();
//		si.setSoftwareDeveloper("UPitt");
//		si.setSoftwareName("SEIR");
//		si.setSoftwareVersion("1.2");
//		si.setSoftwareType(ApolloSoftwareType.SIMULATOR);
//		srr.setSoftwareIdentification(si);
//		
//		srr.setUrl("http://localhost:8080/seirsimulatorservice/services/seirsimulatorservice?wsdl");
//		
//		port.registerService(srr);
////		sr.setSimulatorIdentification(si);
////
////		srr.setServiceRecord(sr);
////		srr.setUrl("http://youtube.com");
////		//srr.setUrl("http://localhost:8087/fred?wsdl");
////
////		Holder<Boolean> success = new Holder<Boolean>();
////		Holder<String> msg = new Holder<String>();
////		port.registerService(srr, success, msg);
////		System.out.println(msg.value);
//
////		 List<ServiceRecord> l = port.getRegisteredServices();
////		 System.out.println("Found " + l.size() + " registered services!");
////		 for (ServiceRecord r : l) {
////			 System.out.println(r.getSimulatorIdentification().getSimulatorDeveloper());
////			 System.out.println(r.getSimulatorIdentification().getSimulatorName());
////			 System.out.println(r.getSimulatorIdentification().getSimulatorVersion() + "\n\n");
////			 }
//		
//		SimulatorConfiguration simulatorConfiguration = new SimulatorConfiguration();
//
//		simulatorConfiguration.setAuthentication(auth);
////		simulatorConfiguration
////				.setAntiviralControlMeasure(new AntiviralTreatmentControlMeasure());
//////		AntiviralTreatmentControlMeasure acm = simulatorConfiguration
////				.getAntiviralControlMeasure();
////		acm.setAntiviralCmCompliance(0d);
////		acm.setAntiviralEfficacy(0d);
////		acm.setAntiviralEfficacyDelay(0d);
////		acm.getAntiviralAdminSchedule().add(1d);
////		acm.getAntiviralSupplySchedule().add(1d);
//
//		simulatorConfiguration.setSimulatorIdentification(si);
//
//		simulatorConfiguration.setDisease(new Disease());
//		Disease disease = simulatorConfiguration.getDisease();
//		disease.setAsymptomaticInfectionFraction(0.5);
//		disease.setDiseaseName("Influenza");
//		disease.setInfectiousPeriod(3.2);
//		disease.setLatentPeriod(2.0);
//		disease.setReproductionNumber(1.7);
//
//		simulatorConfiguration
//				.setPopulationInitialization(new SimulatedPopulation());
//		SimulatedPopulation sp = simulatorConfiguration
//				.getPopulationInitialization();
//		sp.setPopulationLocation("42003");
//
//		List<PopulationDiseaseState> ds = sp.getPopulationDiseaseState();
//		PopulationDiseaseState pds = new PopulationDiseaseState();
//		pds.setDiseaseState("susceptible");
//		pds.setFractionOfPopulation(0.95);
//		ds.add(pds);
//		pds = new PopulationDiseaseState();
//		pds.setDiseaseState("exposed");
//		pds.setFractionOfPopulation(0.0);
//		ds.add(pds);
//		pds = new PopulationDiseaseState();
//		pds.setDiseaseState("infectious");
//		pds.setFractionOfPopulation(0.01);
//		ds.add(pds);
//		pds = new PopulationDiseaseState();
//		pds.setDiseaseState("recovered");
//		pds.setFractionOfPopulation(0.04);
//		ds.add(pds);
//
//		simulatorConfiguration
//				.setSimulatorTimeSpecification(new SimulatorTimeSpecification());
//		SimulatorTimeSpecification stc = simulatorConfiguration
//				.getSimulatorTimeSpecification();
//		stc.setRunLength(new BigInteger("30"));
//		stc.setTimeStepUnit(TimeStepUnit.DAY);
//		stc.setTimeStepValue(1d);
//
////		simulatorConfiguration
////				.setVaccinationControlMeasure(new VaccinationControlMeasure());
////		VaccinationControlMeasure vcm = simulatorConfiguration
////				.getVaccinationControlMeasure();
////		vcm.setVaccineCmCompliance(0d);
////		vcm.setVaccineEfficacy(0d);
////		vcm.setVaccineEfficacyDelay(0d);
////		for (int i = 0; i < 30; i++) {
////			vcm.getVaccinationAdminSchedule().add(0d);
////			vcm.getVaccineSupplySchedule().add(0d);
////		}
//		
//		//MessageDigest md = MessageDigest.getInstance("MD5");
//		
////		XStream xStream = new XStream(new DomDriver());
////		FileWriter fw = new FileWriter(new File("simulatorConfiguration.xml"));
////		xStream.toXML(simulatorConfiguration, fw);
////		fw.close();
//
////		OBJECTMAPPER MAPPER = NEW OBJECTMAPPER();
////		WRITER STRWRITER = NEW FILEWRITER(NEW FILE("SIMULATORCONFIGURATION.JSON"));
////		MAPPER.WRITEVALUE(STRWRITER, SIMULATORCONFIGURATION);
////		@SUPPRESSWARNINGS("UNUSED")
////		STRING USERDATAJSON = STRWRITER.TOSTRING();
////		STRWRITER.CLOSE();
////		
//
////		String runId = port.runSimulation(simulatorConfiguration);
////		System.out.println("Simulator returned runId: "	+ runId );
////	//	String runId = "Pitt,PSC,CMU_FRED_2.0.1_231162";
////		RunStatus rs = port.getRunStatus(runId, sr);
////		while (rs.getStatus() != RunStatusEnum.COMPLETED) {
////			System.out.println("Status is " + rs.getStatus());
////			System.out.println("Message is " + rs.getMessage());
////			System.out.println("\n");
////			Thread.sleep(500);
////		    rs = port.getRunStatus(runId, sr);
////		}
////		System.out.println("Status is " + rs.getStatus());
////		System.out.println("Message is " + rs.getMessage());
//
//		// port.unRegisterService(srr, success, msg);
//		// System.out.println(msg.value);
//
//	}
}
