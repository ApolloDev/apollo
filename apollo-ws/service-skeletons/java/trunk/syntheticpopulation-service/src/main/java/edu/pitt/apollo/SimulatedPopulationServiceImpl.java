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

import org.tempuri.SynthiaWSSoap;

import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.SimulatedPopulationConfiguration;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/simulatedpopulationservice/", 
portName="SimulatedPopulationServiceEndpoint",
serviceName="SimulatedPopulationService", 
endpointInterface="edu.pitt.apollo.service.visualizerservice.Synthis_WSSoap")
class SimulatedPopulationService implements SynthiaWSSoap {

	@Override
	@WebResult(name = "zipFileLocation", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetURL", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetURL")
	@WebMethod(operationName = "GetURL", action = "http://tempuri.org/GetURL")
	@ResponseWrapper(localName = "GetURLResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetURLResponse")
	public String getURL(
			@WebParam(name = "runId", targetNamespace = "http://tempuri.org/") String runId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "SubmitJobResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "SubmitJob", targetNamespace = "http://tempuri.org/", className = "org.tempuri.SubmitJob")
	@WebMethod(operationName = "SubmitJob", action = "http://tempuri.org/SubmitJob")
	@ResponseWrapper(localName = "SubmitJobResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.SubmitJobResponse")
	public String submitJob(
			@WebParam(name = "simulatedPopulationConfiguration", targetNamespace = "http://tempuri.org/") SimulatedPopulationConfiguration simulatedPopulationConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@WebResult(name = "GetStatusResult", targetNamespace = "http://tempuri.org/")
	@RequestWrapper(localName = "GetStatus", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetStatus")
	@WebMethod(operationName = "GetStatus", action = "http://tempuri.org/GetStatus")
	@ResponseWrapper(localName = "GetStatusResponse", targetNamespace = "http://tempuri.org/", className = "org.tempuri.GetStatusResponse")
	public RunStatus getStatus(
			@WebParam(name = "runId", targetNamespace = "http://tempuri.org/") String runId) {
		// TODO Auto-generated method stub
		return null;
	}


	
}