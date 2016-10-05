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

package edu.pitt.apollo.apolloclient.tutorial;

import java.net.MalformedURLException;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory.SimulatorIdentificationEnum;
import edu.pitt.apollo.examples.ExampleVaccinationControlStrategy;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;


public class Chapter8_RunSimulationWithVaccinationControlStrategy extends AbstractRunAndVisualizeSimulationClass {

	public Chapter8_RunSimulationWithVaccinationControlStrategy() throws MalformedURLException {
		super();
	}

	public void runExample() {
		RunSimulationMessage runSimulationMessageWithoutVaccination = ApolloServiceTypeFactory
				.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR);

        Authentication authentication = new Authentication();

		ExampleVaccinationControlStrategy vaccinationControlMeasure = new ExampleVaccinationControlStrategy();
		RunSimulationMessage runSimulationMessageWithVaccination = vaccinationControlMeasure
				.addVaccinationControlMeasureToRunSimulationMessage(ApolloServiceTypeFactory
						.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR));

		runScenariosAndDisplayResults("No Vaccination", runSimulationMessageWithoutVaccination, "Vaccination",
				runSimulationMessageWithVaccination, authentication);

	}

	public static void main(String[] args) throws MalformedURLException {
		Chapter8_RunSimulationWithVaccinationControlStrategy tutorialChapter8 = new Chapter8_RunSimulationWithVaccinationControlStrategy();
		tutorialChapter8.runExample();

	}

}
