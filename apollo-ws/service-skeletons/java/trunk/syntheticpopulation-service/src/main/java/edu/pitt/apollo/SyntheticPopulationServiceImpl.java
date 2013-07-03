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

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import edu.pitt.apollo.service.syntheticpopulationservice._07._03._2013.SyntheticPopulationServiceEI;
import edu.pitt.apollo.types._07._03._2013.RunStatus;
import edu.pitt.apollo.types._07._03._2013.ServiceResult;
import edu.pitt.apollo.types._07._03._2013.SyntheticPopulationConfiguration;


@WebService(targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", 
portName="SyntheticPopulationServiceEndpoint",
serviceName="SyntheticPopulationService", 
endpointInterface="edu.pitt.apollo.service.visualizerservice.Synthis_WSSoap")
class SyntheticPopulationServiceImpl implements SyntheticPopulationServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/") String runId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "serviceResult", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/")
	@RequestWrapper(localName = "run", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.Run")
	@WebMethod(action = "http://service.apollo.pitt.edu/syntheticpopulationservice/run")
	@ResponseWrapper(localName = "runResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.RunResponse")
	public List<ServiceResult> run(
			@WebParam(name = "syntheticPopulationConfiguration", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/") SyntheticPopulationConfiguration syntheticPopulationConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "configurationFile", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/")
	@RequestWrapper(localName = "getConfigurationFileForRun", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetConfigurationFileForRun")
	@WebMethod
	@ResponseWrapper(localName = "getConfigurationFileForRunResponse", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/", className = "edu.pitt.apollo.service.syntheticpopulationservice.GetConfigurationFileForRunResponse")
	public String getConfigurationFileForRun(
			@WebParam(name = "runIdentification", targetNamespace = "http://service.apollo.pitt.edu/syntheticpopulationservice/") String runIdentification) {
		// TODO Auto-generated method stub
		return null;
	}

	

}