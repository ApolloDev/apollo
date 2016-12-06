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
package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.OutputTranslatorServiceException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public interface OutputTranslatorServiceInterface {

	void translateOutputForRun(BigInteger runId, String baseOutputURL, Authentication authentication) throws OutputTranslatorServiceException;

}
