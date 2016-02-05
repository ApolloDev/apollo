/*
 * Copyright 2016 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.soapjobrunningserviceconnector;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.restserviceconnectorcommon.RestServiceUtils;
import edu.pitt.apollo.restserviceconnectorcommon.exception.RestServiceException;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class RestJobRunningServiceConnector extends JobRunningServiceConnector {

	private final String restServiceUri;
	private final RestServiceUtils restServiceUtils = new RestServiceUtils();

	public RestJobRunningServiceConnector(String url) throws JobRunningServiceException {
		super(url);
		restServiceUri = serviceUrl + "/";

	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		String uri = restServiceUri + "runsimulation/" + runId;
		try {
			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
		} catch (RestServiceException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
//		String uri = restServiceUri + "runsimulation/" + runId + "?" + RestServiceUtils.getUsernameAndPasswordQueryParams(authentication);
//		uri += "&action=" + RunActionEnum.TERMINATE;
//		try {
//			restServiceUtils.makePostRequestAndCheckResponse(uri, "");
//		} catch (RestServiceException ex) {
//			throw new JobRunningServiceException(ex.getMessage());
//		}

		throw new UnsupportedOperationException("Not implemented yet");
	}

}
