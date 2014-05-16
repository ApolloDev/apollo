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

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.examples.ExampleRunSimulationMessageFactory;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceV201;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;


public class WSClient {
	
	public static SoftwareIdentification getSoftwareIdentificationForSimulator() {

		SoftwareIdentification softwareId = new SoftwareIdentification();
		softwareId.setSoftwareDeveloper("UPitt,PSC,CMU");
		softwareId.setSoftwareName("FRED");
		softwareId.setSoftwareVersion("2.0.1_i");
		softwareId.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
		return softwareId;
	}
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		
		SimulatorServiceV201 ss = new SimulatorServiceV201(new URL(args[0]));
		SimulatorServiceEI port = ss.getSimulatorServiceEndpoint();
		
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(36000);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);
        
        RunSimulationMessage rsm = ExampleRunSimulationMessageFactory.getRunSimulationMessage();
        rsm.setSimulatorIdentification(WSClient.getSoftwareIdentificationForSimulator());
        
        BigInteger simulationRunId = new BigInteger("7");
        port.runSimulation(simulationRunId, rsm);
        
        MethodCallStatus runStatus = port.getRunStatus("7");
        System.out.println("RunStatus: " + runStatus.getStatus() + "\n\tMessage: " + runStatus.getMessage());
        while (runStatus.getStatus() != MethodCallStatusEnum.COMPLETED) {
        	System.out.println("RunStatus: " + runStatus.getStatus() + "\n\tMessage: " + runStatus.getMessage());
        }
        
        		
        
     
	}	 
}
