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

import edu.pitt.apollo.service.simulatorservice.v3_0_2.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_2.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

public class WSClient {

	public static final String WSDL_LOC = "http://gaia.pha.psc.edu:13500/pscsimu?wsdl";
	public static final QName SERVICE = new QName("http://service.apollo.pitt.edu/simulatorservice/v3_0_2/", "SimulatorService_v3.0.0");

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		SimulatorServiceV300 simulatorService = new SimulatorServiceV300(new URL(WSDL_LOC), SERVICE);
		SimulatorServiceEI port = simulatorService.getSimulatorServiceEndpoint();

		MethodCallStatus result = port.runSimulation(BigInteger.ONE);
		System.out.println(result.getStatus());
		
	}
}
