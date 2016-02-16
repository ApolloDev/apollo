package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;
;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author jdl50
 */


public class StageMethod {

	static Logger logger = LoggerFactory.getLogger(StageMethod.class);
	private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
	private final RunMessage message;

	private final Authentication authentication;
	private final BigInteger parentRunId;
	protected DatastoreAccessor dataServiceDao;
	protected static ApolloServiceQueue apolloServiceQueue;

	static {
		apolloServiceQueue = new ApolloServiceQueue();
	}

	public StageMethod(RunMessage message, BigInteger parentRunId) throws RunManagementException {
		this.message = message;
		this.parentRunId = parentRunId;
		this.authentication = getAuthentication(message);

	}

	private Authentication cloneAuthentication(Authentication srcAuthentication) {
		Authentication authentication = new Authentication();
		authentication.setRequesterId(srcAuthentication.getRequesterId());
		authentication.setRequesterPassword(srcAuthentication.getRequesterPassword());
		return authentication;
	}

	private Authentication getAuthentication(RunMessage message) throws RunManagementException {
		Authentication authentication = message.getAuthentication();
		return cloneAuthentication(authentication);
	}

	public InsertRunResult stage() throws RunManagementException {

		try {
			dataServiceDao = DatastoreAccessorFactory.getDatabaseAccessor(message);

			InsertRunResult insertRunResult = dataServiceDao.insertRun(message);

			BigInteger runId = insertRunResult.getRunId();
			if (parentRunId != null) {
				dataServiceDao.addRunIdsToSimulationGroupForRun(parentRunId, Arrays.asList(new BigInteger[]{runId}), authentication);
			}

			if (insertRunResult.isRunCached()) {
				return insertRunResult;
			}

			MethodCallStatus methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
			while (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)
					&& (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED))) {
				try {
					switch (methodCallStatus.getStatus()) {
						case FAILED:
							throw new RunManagementException("Run " + runId + " FAILED with message " + methodCallStatus.getMessage());
						default:
							logger.debug("Status for run " + runId + " was: (" + methodCallStatus.getMessage() + ") " + methodCallStatus.getStatus());
							Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
							methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
							break;
					}
				} catch (InterruptedException ex) {
					// don't care
				}
			}

			if (!(message instanceof RunSimulationsMessage) && !(message instanceof RunInfectiousDiseaseTransmissionExperimentMessage)) {
				if (!(message instanceof RunVisualizationMessage) && !(message instanceof DataRetrievalRequestMessage)) {

					// message is RunSimulationMessage
					String url = getTranslatorServiceUrl(dataServiceDao, authentication);
					if (url == null) {
						throw new RunManagementException("There was no translator URL available!");
					}
					// run is loaded, now call translator

					TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor(url);
					translatorDao.translateRun(runId);

					methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
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
									methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
									break;
							}
						} catch (InterruptedException ex) {
							// it's okay if the sleep timer is interrupted
						}
					}
				} else {
					// update the status to translation completed even though visualization runs are not translated at this time
					// so that the WaitForTranslationAndStartRunThread will work
					dataServiceDao.updateStatusOfRun(runId, MethodCallStatusEnum.TRANSLATION_COMPLETED, "Translation completed", authentication);
				}
			} else if (message instanceof RunSimulationsMessage) {
				BatchStageMethod batchStageMethod = new BatchStageMethod(runId, (RunSimulationsMessage) message, authentication, apolloServiceQueue);
				apolloServiceQueue.addThreadToQueueAndRun(batchStageMethod);
			} else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
				StageExperimentMethod method = new StageExperimentMethod(runId, apolloServiceQueue,
						(RunInfectiousDiseaseTransmissionExperimentMessage) message, authentication);
				apolloServiceQueue.addThreadToQueueAndRun(method);
			}

			// run is now translated
			return insertRunResult;
		} catch (MalformedURLException ex) {
			throw new RunManagementException("Malformed URL exception staging run : " + ex.getMessage());
		} catch (DatastoreException ex) {
			throw new RunManagementException("Database exception staging run: " + ex.getMessage());
		} catch (TranslatorServiceException ex) {
			throw new RunManagementException("Translator service exception staging run: " + ex.getMessage());
		} catch (ApolloDatabaseException ex) {
			throw new RunManagementException("Database exception staging run: " + ex.getMessage());
		} catch (UnrecognizedMessageTypeException ex) {
			throw new RunManagementException("Unrecognized message type staging run: " + ex.getMessage());
		}
	}

	private String getTranslatorServiceUrl(DatastoreAccessor dataServiceDao, Authentication authentication) throws DatastoreException {

		List<ServiceRegistrationRecord> software = dataServiceDao.getListOfRegisteredSoftwareRecords(authentication);
		for (ServiceRegistrationRecord record : software) {
			if (record.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.TRANSLATOR)) {
				return record.getUrl();
			}
		}
		return null;
	}

}
