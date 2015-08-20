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
import edu.pitt.apollo.examples.ExampleConditionalProbabilityDistribution;
import edu.pitt.apollo.examples.ExampleUnconditionalProbabilityDistribution;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;


public class Chapter7_ProbabilityDistributionExample extends TutorialWebServiceClient {

	public Chapter7_ProbabilityDistributionExample() throws MalformedURLException {
		super();
	}

	public void runExample() {
		

		ExampleConditionalProbabilityDistribution conditionalProbabilityDistributionExample = new ExampleConditionalProbabilityDistribution();

		@SuppressWarnings("unused")
		RunSimulationMessage runSimulationMessage = conditionalProbabilityDistributionExample
				.setLatentPeriodToConditionalProbabilityDistribution(ApolloServiceTypeFactory
						.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR));

		ExampleUnconditionalProbabilityDistribution unconditionalProbabilityDistributionExample = new ExampleUnconditionalProbabilityDistribution();
		runSimulationMessage = unconditionalProbabilityDistributionExample
				.setLatentPeriodToUnconditionalProbabilityDistribution(ApolloServiceTypeFactory
						.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.SEIR));

		// we do not run these scenarios as neither the SEIR, FRED, or
		// FluTE simulators can support this configuration
	}

	public static void main(String[] args) throws MalformedURLException {
		Chapter7_ProbabilityDistributionExample tutoralChapter7 = new Chapter7_ProbabilityDistributionExample();
		tutoralChapter7.runExample();
	}
}
