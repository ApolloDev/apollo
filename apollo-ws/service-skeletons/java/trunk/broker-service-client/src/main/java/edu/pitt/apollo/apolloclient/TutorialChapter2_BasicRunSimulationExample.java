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

import java.net.MalformedURLException;

import edu.pitt.apollo.examples.ExampleRunSimulationMessageFactory;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;

public class TutorialChapter2_BasicRunSimulationExample extends AbstractTutorialWebServiceClient {
	public TutorialChapter2_BasicRunSimulationExample() throws MalformedURLException {
		super();
	}

	public static void main(String args[]) throws java.lang.Exception {
		TutorialChapter2_BasicRunSimulationExample example = new TutorialChapter2_BasicRunSimulationExample();
		RunSimulationMessage runSimulationMessage = ExampleRunSimulationMessageFactory
				.getRunSimulationMessage();
		example.runSimulationAndDisplayResults(runSimulationMessage);
	}
}