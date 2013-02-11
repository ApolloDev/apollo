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

import javax.xml.ws.Holder;

import edu.pitt.apollo.service.apolloservice.ApolloService;
import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.types.Authentication;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.ServiceRecord;
import edu.pitt.apollo.types.ServiceRegistrationRecord;
import edu.pitt.apollo.types.SimulatorIdentification;

public class WSClient {
	public static void main(String[] args) {
		ApolloService service = new ApolloService();
		ApolloServiceEI port = service.getApolloServiceEndpoint();

		RunStatus rs = port.getRunStatus("test");
		System.out.println(rs.getMessage());

		System.out.println("Status: " + rs.getStatus());
		
		ServiceRegistrationRecord srr = new ServiceRegistrationRecord();
		Authentication auth = new Authentication();
		auth.setRequesterId("John");
		auth.setRequesterPassword("password");
		srr.setAuthentication(auth);
		SimulatorIdentification sid = new SimulatorIdentification();
		
		sid.setSimulatorName("SEIR");
		sid.setSimulatorDeveloper("John Lv");
		sid.setSimulatorVersion("2.32 beta");
		
		ServiceRecord sr = new ServiceRecord();
		sr.setSimulatorIdentification(sid);
		srr.setServiceRecord(sr);
		srr.setUrl("www.google.com");
		
		Holder<Boolean> success = new Holder<Boolean>();
		Holder<String> msg = new Holder<String>();
		port.registerService(srr, success, msg);
		System.out.println(msg.value);
		
		port.unRegisterService(srr, success, msg);
		System.out.println(msg.value);
	}
}
