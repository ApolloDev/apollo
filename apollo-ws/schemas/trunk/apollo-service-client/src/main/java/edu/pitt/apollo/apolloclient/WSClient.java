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
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatedPopulation;
import edu.pitt.apollo.types.SimulatorConfiguration;
import edu.pitt.apollo.types.SimulatorIdentification;
import edu.pitt.apollo.types.SimulatorTimeSpecification;
import edu.pitt.apollo.types.TimeStepUnit;
import edu.pitt.apollo.types.VaccinationControlMeasure;

public class WSClient {
	public static void main(String[] args) {
		ApolloService service = new ApolloService();
		ApolloServiceEI port = service.getApolloServiceEndpoint();

		/*
		ServiceRecord sr = new ServiceRecord();
		SimulatorIdentification si = new SimulatorIdentification();
		si.setSimulatorDeveloper("Fake University");
		si.setSimulatorName("Fake Simulator");
		si.setSimulatorVersion("1.0");
		sr.setSimulatorIdentification(si);
		RunStatus rs = port.getRunStatus("blah", sr);
		System.out.println("Message: " + rs.getMessage());
		System.out.println("Status: " + rs.getStatus());
		*/
		
		//RunStatus rs = port.getRunStatus("test");
		//System.out.println(rs.getMessage());

		//System.out.println("Status: " + rs.getStatus());
		
		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
		Authentication auth = new Authentication();
		auth.setRequesterId("Fake");
		auth.setRequesterPassword("password");
		srr.setAuthentication(auth);
		SimulatorIdentification sid = new SimulatorIdentification();
		
		sid.setSimulatorName("SEIR");
		sid.setSimulatorDeveloper("University of Pittsburgh");
		sid.setSimulatorVersion("1.0");
		
		ServiceRecord sr = new ServiceRecord();
		sr.setSimulatorIdentification(sid);
		srr.setServiceRecord(sr);
		srr.setUrl("http://localhost:8080/simulatorservice/services/simulatorservice?wsdl");
		
		//Holder<Boolean> success = new Holder<Boolean>();
		//Holder<String> msg = new Holder<String>();
		//port.registerService(srr, success, msg);
		//System.out.println(msg.value);
		
		SimulatorConfiguration simulatorConfiguration = new SimulatorConfiguration();
		
		
		simulatorConfiguration.setAuthentication(auth);
		simulatorConfiguration.setAntiviralControlMeasure(new AntiviralControlMeasure());
		AntiviralControlMeasure acm = simulatorConfiguration.getAntiviralControlMeasure();
		acm.setAntiviralCmCompliance(0d);
		acm.setAntiviralEfficacy(0d);
		acm.setAntiviralEfficacyDelay(0d);
		acm.getAntiviralAdminSchedule().add(1d);
		acm.getAntiviralSupplySchedule().add(1d);
		
		simulatorConfiguration.setSimulatorIdentification(sid);
		
		simulatorConfiguration.setDisease(new Disease());
		Disease disease = simulatorConfiguration.getDisease();
		disease.setAsymptomaticInfectionFraction(0d);
		disease.setDiseaseName("Influenza");
		disease.setInfectiousPeriod(0d);
		disease.setLatentPeriod(0d);
		disease.setReproductionNumber(0d);
		
		
		simulatorConfiguration.setPopulationInitialization(new SimulatedPopulation());
		SimulatedPopulation sp = simulatorConfiguration.getPopulationInitialization();
		sp.setPopulationLocation("42003");
		
		List<PopulationDiseaseState> ds = sp.getPopulationDiseaseState();
		PopulationDiseaseState pds = new PopulationDiseaseState();
		pds.setDiseaseState("Susceptible");
		pds.setPopCount(new BigInteger("1"));
		ds.add(pds);
		
		simulatorConfiguration.setSimulatorTimeSpecification(new SimulatorTimeSpecification());
		SimulatorTimeSpecification stc = simulatorConfiguration.getSimulatorTimeSpecification();
		stc.setRunLength(new BigInteger("1"));
		stc.setTimeStepUnit(TimeStepUnit.DAY);
		stc.setTimeStepValue(1d);
		
		simulatorConfiguration.setVaccinationControlMeasure(new VaccinationControlMeasure());
		VaccinationControlMeasure vcm = simulatorConfiguration.getVaccinationControlMeasure();
		vcm.setVaccineCmCompliance(0d);
		vcm.setVaccineEfficacy(0d);
		vcm.setVaccineEfficacyDelay(0d);
		vcm.getVaccinationAdminSchedule().add(1d);
		vcm.getVaccineSupplySchedule().add(1d);
		
		System.out.println("Simulation result: " + port.runSimulation(simulatorConfiguration));
		
		//port.unRegisterService(srr, success, msg);
		//System.out.println(msg.value);
		
	}
}
