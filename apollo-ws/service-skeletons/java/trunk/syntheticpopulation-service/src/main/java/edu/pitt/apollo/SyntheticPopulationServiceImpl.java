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

import java.math.BigInteger;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.syntheticpopulationservice.v2_1_0.SyntheticPopulationServiceEI;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_1_0.RunSyntheticPopulationGenerationMessage;
import edu.pitt.apollo.types.v2_1_0.ServiceResult;


@WebService(targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", 
portName="SyntheticPopulationServiceEndpoint",
serviceName="SyntheticPopulationService", 
endpointInterface="edu.pitt.apollo.service.visualizerservice.Synthis_WSSoap")
class SyntheticPopulationServiceImpl implements SyntheticPopulationServiceEI {

	@Override
	@WebResult(name = "serviceResult", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/")
	@RequestWrapper(localName = "runSyntheticPopulationGeneration", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v2_0.RunSyntheticPopulationGeneration")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/runSyntheticPopulationGeneration")
	@ResponseWrapper(localName = "runSyntheticPopulationGenerationResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v2_0.RunSyntheticPopulationGenerationResponse")
	public List<ServiceResult> runSyntheticPopulationGeneration(
			@WebParam(name = "runSyntheticPopulationGenerationMessage", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/") RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "runStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v2_0.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/", className = "edu.pitt.apollo.service.syntheticpopulationservice.v2_0.GetRunStatusResponse")
	public MethodCallStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/v2_0/") BigInteger runId) {
		// TODO Auto-generated method stub
		return null;
	}



	

}