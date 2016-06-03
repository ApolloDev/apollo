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

import edu.pitt.apollo.service.simulatorservice.v3_1_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_1_0.SimulatorServiceV310;
import edu.pitt.apollo.services_common.v3_1_0.MethodCallStatus;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

public class WSClient {

	public static final String WSDL_LOC = "http://gaia.pha.psc.edu:13500/pscsimu?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/simulatorservice/v3_1_0/", "SimulatorService_v3.1.0");

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		SimulatorServiceV310 simulatorService = new SimulatorServiceV310(new URL(WSDL_LOC), SERVICE);
		SimulatorServiceEI port = simulatorService.getSimulatorServiceEndpoint();

		MethodCallStatus result = port.runSimulation(BigInteger.ONE);
		System.out.println(result.getStatus());
		
	}
}
