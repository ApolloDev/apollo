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

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareResult;
import edu.pitt.apollo.dataservice.methods.GetAllOutputFilesURLAsZipMethod;
import edu.pitt.apollo.dataservice.methods.GetOutputFilesURLAsZipMethod;
import edu.pitt.apollo.dataservice.methods.GetOutputFilesURLsMethod;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import javax.jws.WebService;

@WebService(targetNamespace = "http://service.apollo.pitt.edu/dataservice/v3_0_0/", portName = "DataServiceEndpoint", serviceName = "DataService_v3.0.0", endpointInterface = "edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI")
class DataServiceImpl implements DataServiceEI {

	private static final ApolloServiceQueue serviceQueue;

	static {
		serviceQueue = new ApolloServiceQueue();
	}

	@Override
	public ListOutputFilesForSoftwareResult listOutputFilesForSoftware(
			ListOutputFilesForSoftwareMessage listOutputFilesForSoftwareMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MethodCallStatus getOutputFilesURLAsZip(BigInteger runId) {
		GetOutputFilesURLAsZipMethod method = new GetOutputFilesURLAsZipMethod(serviceQueue, runId);
		method.downloadFiles();
		
		return null;
	}

	@Override
	public MethodCallStatus getOutputFilesURLs(BigInteger runId) {
		GetOutputFilesURLsMethod method = new GetOutputFilesURLsMethod(serviceQueue, runId);
		method.downloadFiles();
		
		return null;
	}

	@Override
	public MethodCallStatus getAllOutputFilesURLAsZip(BigInteger runId) {
		GetAllOutputFilesURLAsZipMethod method = new GetAllOutputFilesURLAsZipMethod(serviceQueue, runId);
		method.downloadFiles();
		
		return null;
	}


}
