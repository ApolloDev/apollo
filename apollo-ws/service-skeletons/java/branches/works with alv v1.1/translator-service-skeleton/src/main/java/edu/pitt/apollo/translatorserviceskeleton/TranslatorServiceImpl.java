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
package edu.pitt.apollo.translatorserviceskeleton;

import java.math.BigInteger;

import javax.jws.WebService;

import edu.pitt.apollo.service.translatorservice.v2_1_0.TranslatorServiceEI;
import edu.pitt.apollo.types.v2_1_0.RunSimulationMessage;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/translatorservice/v2_1_0/", portName = "TranslatorServiceEndpoint", serviceName = "TranslatorService_v2.0.2", endpointInterface = "edu.pitt.apollo.service.translatorservice.v2_1_0.TranslatorServiceEI")
public class TranslatorServiceImpl implements TranslatorServiceEI {


	@Override
	public void translateRunSimulationMessage(BigInteger simulationRunId,
			RunSimulationMessage runSimulationMessage) {
		// TODO Auto-generated method stub
		
	}
}
