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
package edu.pitt.apollo.connector.rest;

import edu.pitt.apollo.connector.SimulatorServiceConnector;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class RestSimulatorServiceConnector extends SimulatorServiceConnector {

	public RestSimulatorServiceConnector(String url) {
		super(url);
	}

	@Override
	public RunResult run(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public RunResult runSimulations(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public TerminteRunResult terminate(TerminateRunRequest terminateRunRequest) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
