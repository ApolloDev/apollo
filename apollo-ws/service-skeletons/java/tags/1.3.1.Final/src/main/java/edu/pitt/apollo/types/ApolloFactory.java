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

import javax.naming.spi.ObjectFactory;

public class ApolloFactory {
	private static ObjectFactory factory = new ObjectFactory();

	public static SimulatorConfiguration createEpidemicModelInput() {

		SimulatorConfiguration cfg = factory.createSimulatorConfiguration();
		cfg.setAntiviralControlMeasure(createAntiviralControlMeasure());
		cfg.setVaccinationControlMeasure(createVaccinationControlMeasure());
		cfg.setDisease(factory.createDisease());
		cfg.setSimulatorIdentification(factory.createSimulatorIdentification());
		cfg.setSimulatorTimeSpecification(factory
				.createSimulatorTimeSpecification());
		cfg.setPopulationInitialization(factory.createSimulatedPopulation());
		cfg.setAuthentication(factory.createAuthentication());
		return cfg;
	}

	public static AntiviralControlMeasure createAntiviralControlMeasure() {
		AntiviralControlMeasure acm = factory.createAntiviralControlMeasure();
		acm.antiviralAdminSchedule = new ArrayList<Double>();
		acm.antiviralSupplySchedule = new ArrayList<Double>();
		return acm;
	}

	public static VaccinationControlMeasure createVaccinationControlMeasure() {
		VaccinationControlMeasure vcm = factory
				.createVaccinationControlMeasure();
		vcm.vaccinationAdminSchedule = new ArrayList<Double>();
		vcm.vaccineSupplySchedule = new ArrayList<Double>();
		return vcm;
	}
}
