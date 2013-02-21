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

import java.math.BigInteger;
import java.util.List;

import javax.xml.ws.Holder;

import edu.pitt.apollo.service.apolloservice.ApolloService;
import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.types.AntiviralControlMeasure;
import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.Disease;
import edu.pitt.apollo.types.PopulationDiseaseState;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatedPopulation;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.SimulatorTimeSpecification;
import edu.pitt.apollo.types.TimeStepUnit;
import edu.pitt.apollo.types.VaccinationControlMeasure;

public class WSClient {
	public static void main(String[] args) throws InterruptedException {
		ApolloService service = new ApolloService();
		ApolloServiceEI port = service.getApolloServiceEndpoint();

		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();

		Authentication auth = new Authentication();
		auth.setRequesterId("fake_user");
		auth.setRequesterPassword("fake_password");
		srr.setAuthentication(auth);

		ServiceRecord sr = new ServiceRecord();
		SimulatorIdentification si = new SimulatorIdentification();
		si.setSimulatorDeveloper("UPitt,PSC,CMU");
		si.setSimulatorName("FRED");
		si.setSimulatorVersion("2.0.1");
		sr.setSimulatorIdentification(si);

		srr.setServiceRecord(sr);
		srr.setUrl("http://warhol-fred.psc.edu:8087/fred?wsdl");
		//srr.setUrl("http://localhost:8087/fred?wsdl");

		Holder<Boolean> success = new Holder<Boolean>();
		Holder<String> msg = new Holder<String>();
		port.registerService(srr, success, msg);
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

		String runId = port.runSimulation(simulatorConfiguration);
		System.out.println("Simulator returned runId: "	+ runId );
	//	String runId = "Pitt,PSC,CMU_FRED_2.0.1_231162";
		RunStatus rs = port.getRunStatus(runId, sr);
		while (rs.getStatus() != RunStatusEnum.COMPLETED) {
			System.out.println("Status is " + rs.getStatus());
			System.out.println("Message is " + rs.getMessage());
			System.out.println("\n");
			Thread.sleep(500);
		    rs = port.getRunStatus(runId, sr);
		}
		System.out.println("Status is " + rs.getStatus());
		System.out.println("Message is " + rs.getMessage());

		// port.unRegisterService(srr, success, msg);
		// System.out.println(msg.value);*/

	}
}
