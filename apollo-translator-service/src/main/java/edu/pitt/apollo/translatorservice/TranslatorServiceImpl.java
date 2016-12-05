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
package edu.pitt.apollo.translatorservice;

import edu.pitt.apollo.interfaces.TranslatorServiceInterface;
import edu.pitt.apollo.translatorservice.thread.TranslationThread;
import edu.pitt.apollo.translatorservice.thread.BatchTranslationThread;
import java.math.BigInteger;

public class TranslatorServiceImpl implements TranslatorServiceInterface {
	
public void translateRun(BigInteger runId) {

		TranslationThread thread = new BatchTranslationThread(runId);

		thread.start();
	}
}
