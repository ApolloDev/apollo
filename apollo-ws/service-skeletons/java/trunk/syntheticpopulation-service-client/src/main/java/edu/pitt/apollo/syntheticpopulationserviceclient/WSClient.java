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

package edu.pitt.apollo.syntheticpopulationserviceclient;

import java.net.MalformedURLException;
import java.net.URL;

import org.tempuri.SynthiaWS;
import org.tempuri.SynthiaWSSoap;

import edu.pitt.apollo.types.RunStatus;


public class WSClient {
	public static void main(String[] args) throws MalformedURLException {
		SynthiaWS service = new SynthiaWS(new URL("https://synthiastage.rti.org/Account/synthia-WS.asmx?WSDL"));
		SynthiaWSSoap port = service.getSynthiaWSSoap();
		

//		RunStatus rs = port.getRunStatus("test");
//		System.out.println(rs.getMessage());

		System.out.println("Using WSDL: https://synthiastage.rti.org/Account/synthia-WS.asmx?WSDL");
		System.out.println("Calling getStatus for runId: 12497");
		RunStatus rs = port.getStatus("12497");
		System.out.println("Status Enum: " + rs.getStatus());
		System.out.println("Status Message: " + rs.getMessage());
	}
}
