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

package edu.pitt.apollo.simulatorclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import edu.pitt.apollo.types.AntiviralControlMeasure;
import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.Disease;
import edu.pitt.apollo.types.PopulationDiseaseState;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatedPopulation;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorTimeSpecification;
import edu.pitt.apollo.types.SoftwareIdentification;
import edu.pitt.apollo.types.TimeStepUnit;
import edu.pitt.apollo.types.VaccinationControlMeasure;

public class WSClient {
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {
		// SimulatorService service = new SimulatorService(
		// new URL(
		// "http://localhost:8080/seirsimulatorservice/services/seirsimulatorservice?wsdl"));
		// SimulatorServiceEI port = service.getSimulatorServiceEndpoint();

		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();

		Authentication auth = new Authentication();
		auth.setRequesterId("fake_user");
		auth.setRequesterPassword("fake_password");
		srr.setAuthentication(auth);

		// ServiceRecord sr = new ServiceRecord();
		SoftwareIdentification si = new SoftwareIdentification();
		si.setSoftwareDeveloper("UPitt,PSC,CMU");
		si.setSoftwareName("FRED");
		si.setSoftwareVersion("2.0.1");
		// si.set
		// sr.setSoftwareIdentification(si);

		srr.setSoftwareIdentification(si);
		srr.setUrl("http://warhol-fred.psc.edu:8087/fred?wsdl");
		// srr.setUrl("http://localhost:8087/fred?wsdl");

		Holder<Boolean> success = new Holder<Boolean>();
		Holder<String> msg = new Holder<String>();
		// port.registerService(srr, success, msg);
		System.out.println(msg.value);

		SimulatorConfiguration simulatorConfiguration = new SimulatorConfiguration();

		simulatorConfiguration.setAuthentication(auth);
		simulatorConfiguration
				.setAntiviralControlMeasure(new AntiviralControlMeasure());
		AntiviralControlMeasure acm = simulatorConfiguration
				.getAntiviralControlMeasure();
		acm.setAntiviralCmCompliance(0d);
		acm.setAntiviralEfficacy(0d);
		acm.setAntiviralEfficacyDelay(0d);
		acm.getAntiviralAdminSchedule().add(1d);
		acm.getAntiviralSupplySchedule().add(1d);

		simulatorConfiguration.setSimulatorIdentification(si);

		simulatorConfiguration.setDisease(new Disease());
		Disease disease = simulatorConfiguration.getDisease();
		disease.setAsymptomaticInfectionFraction(0.5);
		disease.setDiseaseName("Influenza");
		disease.setInfectiousPeriod(3.2);
		disease.setLatentPeriod(2.0);
		disease.setReproductionNumber(1.7);

		simulatorConfiguration
				.setPopulationInitialization(new SimulatedPopulation());
		SimulatedPopulation sp = simulatorConfiguration
				.getPopulationInitialization();
		sp.setPopulationLocation("42003");

		List<PopulationDiseaseState> ds = sp.getPopulationDiseaseState();
		PopulationDiseaseState pds = new PopulationDiseaseState();
		pds.setDiseaseState("susceptible");
		pds.setPopCount(new BigInteger("1157474"));
		ds.add(pds);
		pds = new PopulationDiseaseState();
		pds.setDiseaseState("exposed");
		pds.setPopCount(new BigInteger("0"));
		ds.add(pds);
		pds = new PopulationDiseaseState();
		pds.setDiseaseState("infectious");
		pds.setPopCount(new BigInteger("100"));
		ds.add(pds);
		pds = new PopulationDiseaseState();
		pds.setDiseaseState("recovered");
		pds.setPopCount(new BigInteger("60920"));
		ds.add(pds);

		simulatorConfiguration
				.setSimulatorTimeSpecification(new SimulatorTimeSpecification());
		SimulatorTimeSpecification stc = simulatorConfiguration
				.getSimulatorTimeSpecification();
		stc.setRunLength(new BigInteger("30"));
		stc.setTimeStepUnit(TimeStepUnit.DAY);
		stc.setTimeStepValue(1d);

		simulatorConfiguration
				.setVaccinationControlMeasure(new VaccinationControlMeasure());
		VaccinationControlMeasure vcm = simulatorConfiguration
				.getVaccinationControlMeasure();
		vcm.setVaccineCmCompliance(0d);
		vcm.setVaccineEfficacy(0d);
		vcm.setVaccineEfficacyDelay(0d);
		for (int i = 0; i < 30; i++) {
			vcm.getVaccinationAdminSchedule().add(0d);
			vcm.getVaccineSupplySchedule().add(0d);
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
		FileOutputStream out = new FileOutputStream(new File(
				"/apollo/test.json"));
		for (int i = 0; i < 5; i++) {
			simulatorConfiguration.getSimulatorIdentification()
					.setSoftwareDeveloper(String.valueOf(i));
			mapper.writeValue(out, simulatorConfiguration);
		}
		out.close();

		FileInputStream fis = new FileInputStream(new File("/apollo/test.json"));
		for (Iterator it = new ObjectMapper().readValues(
				new JsonFactory().createJsonParser(fis),
				SimulatorConfiguration.class); it.hasNext();)

			System.out.println(((SimulatorConfiguration) it.next())
					.getSimulatorIdentification().getSoftwareDeveloper());
		fis.close();

		// //String runId = port.runSimulation(simulatorConfiguration);
		// System.out.println("Simulator returned runId: " + runId );
		// // String runId = "Pitt,PSC,CMU_FRED_2.0.1_231162";
		// // RunStatus rs = port.getRunStatus(runId, sr);
		// while (rs.getStatus() != RunStatusEnum.COMPLETED) {
		// System.out.println("Status is " + rs.getStatus());
		// System.out.println("Message is " + rs.getMessage());
		// System.out.println("\n");
		// Thread.sleep(500);
		// rs = port.getRunStatus(runId, sr);
		// }
		// System.out.println("Status is " + rs.getStatus());
		// System.out.println("Message is " + rs.getMessage());

	}
}
