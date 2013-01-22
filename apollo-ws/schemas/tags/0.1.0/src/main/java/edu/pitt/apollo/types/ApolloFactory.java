
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

package edu.pitt.apollo.types;

import java.util.ArrayList;

public class ApolloFactory {
	public static EpidemicModelInput createEpidemicModelInput() {
		EpidemicModelInput input = new EpidemicModelInput();
		input.setAntiviralControlMeasure(createAntiviralControlMeasure());
		input.setVaccinationControlMeasure(createVaccinationControlMeasure());
		input.setDiseaseDynamics(createDisease());
		input.setSimulatorIdentification(new SimulatorIdentification());
		input.setSimulatorConfiguration(new SimulatorConfiguration());
		input.setAuthentication(new Authentication());
		return input;
	}

	public static EpidemicModelOutput createEpidemicModelOutput() {
		EpidemicModelOutput output = new EpidemicModelOutput();
		output.controlMeasureTimeSeries = new ControlMeasureTimeSeriesOutput();
		output.controlMeasureTimeSeries.receivedAntiviralTreatmentTimeSeries = new ArrayList<Double>();
		output.controlMeasureTimeSeries.receivedVaccinationTimeSeries = new ArrayList<Double>();
		output.populationTimeSeries = new PopulationTimeSeries();
		output.populationTimeSeries.popCount = new ArrayList<Double>();
		output.populationTimeSeries.simulatedPopulation = new ArrayList<String>();
		return output;
	}

	public static Disease createDisease() {
		Disease disease = new Disease();
		disease.popCount = new ArrayList<Double>();
		disease.simulatedPopulation = new ArrayList<String>();
		return disease;
	}

	public static AntiviralControlMeasure createAntiviralControlMeasure() {
		AntiviralControlMeasure acm = new AntiviralControlMeasure();
		acm.antiviralAdminSchedule = new ArrayList<Double>();
		acm.antiviralSupplySchedule = new ArrayList<Double>();
		return acm;
	}

	public static VaccinationControlMeasure createVaccinationControlMeasure() {
		VaccinationControlMeasure vcm = new VaccinationControlMeasure();
		vcm.vaccinationAdminSchedule = new ArrayList<Double>();
		vcm.vaccineSupplySchedule = new ArrayList<Double>();
		return vcm;
	}
}
