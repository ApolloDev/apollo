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
package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import static edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod.logger;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunMessage;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRecord;
import edu.pitt.apollo.services_common.v4_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v4_0_1.ApolloSoftwareTypeEnum;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author nem41
 */
public abstract class BaseStageMethod {

	protected static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
	protected final Authentication authentication;
	protected final DatastoreAccessor dataServiceDao;
	protected final RunMessage message;
	protected static ApolloServiceQueue apolloServiceQueue;

	static {
		apolloServiceQueue = new ApolloServiceQueue();
	}

	public BaseStageMethod(RunMessage runMessage, Authentication authentication) throws RunManagementException, UnrecognizedMessageTypeException, ApolloDatabaseException {
		this.message = runMessage;
		this.authentication = authentication;
		dataServiceDao = DatastoreAccessorFactory.getDatabaseAccessor(message);
	}

	public static void translateRun(BigInteger runId, DatastoreAccessor datastoreAccessor, Authentication authentication) throws DatastoreException, RunManagementException, TranslatorServiceException {
		String url = getTranslatorServiceUrl(datastoreAccessor, authentication);
		if (url == null) {
			throw new RunManagementException("There was no translator URL available!");
		}
		// run is loaded, now call translator

		TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor(url);
		translatorDao.translateRun(runId);

		MethodCallStatus methodCallStatus = datastoreAccessor.getRunStatus(runId, authentication);
		while (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)
				&& !methodCallStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			try {
				switch (methodCallStatus.getStatus()) {
					case FAILED:
						break;
					case RUN_TERMINATED:
						break;
					case UNKNOWN_RUNID:
						break;
					case UNAUTHORIZED:
						break;
					default:
						logger.debug("Status was: (" + methodCallStatus.getMessage() + ") " + methodCallStatus.getStatus());
						Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
						methodCallStatus = datastoreAccessor.getRunStatus(runId, authentication);
						break;
				}
			} catch (InterruptedException ex) {
				// it's okay if the sleep timer is interrupted
			}
		}
	}

	protected static String getTranslatorServiceUrl(DatastoreAccessor dataServiceDao, Authentication authentication) throws DatastoreException {

		List<ServiceRecord> software = dataServiceDao.getListOfRegisteredSoftwareRecords(authentication);
		for (ServiceRecord record : software) {
			if (record.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.TRANSLATOR)) {
				return record.getUrl();
			}
		}
		return null;
	}

}
