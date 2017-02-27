/* Copyright 2012 University of Pittsburgh
 *
 * Licensed under the Apache License, Version 2.0.1 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0.1
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.pitt.apollo;

import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.timeseriesvisualizer.ImageGeneratorRunnable;

import java.math.BigInteger;

public class VisualizerServiceImpl implements JobRunningServiceInterface {

	protected static final ApolloServiceQueue serviceQueue = new ApolloServiceQueue();

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {

		ImageGeneratorRunnable runnable = new ImageGeneratorRunnable(runId, serviceQueue, authentication);
		serviceQueue.addThreadToQueueAndRun(runnable);
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
