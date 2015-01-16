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

import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFileURLMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFileURLResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsResult;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.ListOutputFilesForSoftwareResult;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;



class DataServiceImpl implements DataServiceEI {

	@Override
	public GetOutputFilesURLAsZipResult getOutputFilesURLAsZip(
			GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetOutputFileURLResult getOutputFileURL(
			GetOutputFileURLMessage getOutputFileUrlMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MethodCallStatus getRequestStatus(BigInteger requestIdentification) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetOutputFilesURLsResult getOutputFilesURLs(
			GetOutputFilesURLsMessage getOutputFilesURLsMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListOutputFilesForSoftwareResult listOutputFilesForSoftware(
			ListOutputFilesForSoftwareMessage listOutputFilesForSoftwareMessage) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}