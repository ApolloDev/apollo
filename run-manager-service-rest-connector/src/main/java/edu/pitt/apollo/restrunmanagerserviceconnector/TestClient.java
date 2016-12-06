/*
 * Copyright 2015 nem41.
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
package edu.pitt.apollo.restrunmanagerserviceconnector;

import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public class TestClient {

	public static void main(String[] args) {
		RestRunManagerServiceConnector connector = new RestRunManagerServiceConnector("http://localhost:8080/run-manager-service-rest-frontend/");

		Authentication authentication = new Authentication();
//		authentication.setRequesterId("apollo_demo");
//		authentication.setRequesterPassword("apollo_demo");

		try {
			List<BigInteger> list = connector.getRunIdsAssociatedWithSimulationGroupForRun(new BigInteger("100"), authentication);
			System.out.println(list.get(0));

		} catch (RunManagementException ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

	}

}
