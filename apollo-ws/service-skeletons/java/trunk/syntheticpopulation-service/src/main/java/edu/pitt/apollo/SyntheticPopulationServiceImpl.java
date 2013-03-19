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

package edu.pitt.apollo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.syntheticpopulationservice.SyntheticPopulationServiceEI;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", 
portName="SyntheticPopulationServiceEndpoint",
serviceName="SyntheticPopulationService", 
endpointInterface="edu.pitt.apollo.service.visualizerservice.Synthis_WSSoap")
class SyntheticPopulationService implements SyntheticPopulationServiceEI {

	@Override
	@WebResult(name = "out", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetRunStatusResponse")
	public String getRunStatus(
			@WebParam(name = "in", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/") String in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "out", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/")
	@RequestWrapper(localName = "run", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.Run")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/run")
	@ResponseWrapper(localName = "runResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.RunResponse")
	public String run(
			@WebParam(name = "in", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/") String in) {
		// TODO Auto-generated method stub
		return null;
	}

}