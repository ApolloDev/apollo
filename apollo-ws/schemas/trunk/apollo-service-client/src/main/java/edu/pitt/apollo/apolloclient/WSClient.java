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

import edu.pitt.apollo.service.apolloservice.ApolloService;
import edu.pitt.apollo.service.apolloservice.ApolloServiceEI;
import edu.pitt.apollo.types.RunStatus;

public class WSClient {
	public static void main(String[] args) {
		ApolloService service = new ApolloService();
		ApolloServiceEI port = service.getApolloServiceEndpoint();

		RunStatus rs = port.getRunStatus("test");
		System.out.println(rs.getMessage());

		System.out.println("Status: " + rs.getStatus());
	}
}
