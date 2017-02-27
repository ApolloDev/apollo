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
package edu.pitt.apollo.brokersoapservice;

import edu.pitt.apollo.filestore_service_types.v4_0_1.ListFilesForRunRequest;
import edu.pitt.apollo.filestore_service_types.v4_0_1.ListFilesForRunResult;
import edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v4_0_1.ApolloServiceV401;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author nem41
 */
public class WSClient {
	
	public static void main(String[] args) throws MalformedURLException {
		ApolloServiceV401 service = new ApolloServiceV401(new URL("http://localhost:8080/broker-service-war-4.0.1-SNAPSHOT/services/apolloservice?wsdl"));
		ApolloServiceEI port = service.getApolloServiceEndpoint();
	
		ListFilesForRunRequest message = new ListFilesForRunRequest();
		message.setRunId(new BigInteger("210"));

		ListFilesForRunResult result = port.listFilesForRun(message);
		System.out.println(result.getFiles().size());
	}
	
}
