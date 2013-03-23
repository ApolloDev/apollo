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

import edu.pitt.apollo.service.visualizerservice.VisualizerServiceEI;
import edu.pitt.apollo.types.RunStatus;
import edu.pitt.apollo.types.RunStatusEnum;
import edu.pitt.apollo.types.VisualizerConfiguration;
import edu.pitt.apollo.types.VisualizerResult;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", 
portName="VisualizerServiceEndpoint",
serviceName="VisualizerService", 
endpointInterface="edu.pitt.apollo.service.visualizerservice.VisualizerServiceEI")
class VisualizerServiceImpl implements VisualizerServiceEI {

	@Override
	@WebResult(name = "runStatus", targetNamespace = "")
	@RequestWrapper(localName = "getRunStatus", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetRunStatus")
	@WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/getRunStatus")
	@ResponseWrapper(localName = "getRunStatusResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.GetRunStatusResponse")
	public RunStatus getRunStatus(
			@WebParam(name = "runId", targetNamespace = "") String runId) {
		RunStatus rs = new RunStatus();
		rs.setMessage("hello from visualizerservice");
		rs.setStatus(RunStatusEnum.COMPLETED);
		return rs;
	}

	@Override
	@WebResult(name = "visualizerResult", targetNamespace = "")
	@RequestWrapper(localName = "run", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.Run")
	@WebMethod(action = "http://service.apollo.pitt.edu/visualizerservice/run")
	@ResponseWrapper(localName = "runResponse", targetNamespace = "http://service.apollo.pitt.edu/visualizerservice/", className = "edu.pitt.apollo.service.visualizerservice.RunResponse")
	public VisualizerResult run(
			@WebParam(name = "visualizerConfiguration", targetNamespace = "") VisualizerConfiguration visualizerConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
	
}