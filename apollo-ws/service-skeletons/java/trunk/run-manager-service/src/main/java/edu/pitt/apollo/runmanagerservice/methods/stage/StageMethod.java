package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.types.RunResultAndSimulationGroupId;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.runmanagerservice.utils.MessageUtils;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

/**
 * @author nem41
 */
public class StageMethod {

	private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
	private final Object message;
	private final BigInteger parentRunId;
	private final Authentication authentication;
	protected DataServiceAccessor dataServiceDao;

	public StageMethod(Object message, BigInteger parentRunId, Authentication authentication) {
		this.message = message;
		this.parentRunId = parentRunId;
		this.authentication = authentication;
	}

	public RunResult stage() {

		try {
			dataServiceDao = DataServiceAccessorFactory.getDataServiceAccessor(message);

			BigInteger runId = dataServiceDao.insertRun(message, authentication);
			if (parentRunId != null) {
				dataServiceDao.addRunIdsToSimulationGroupForRun(parentRunId, Arrays.asList(new BigInteger[]{runId}), authentication);
			}

			//BigInteger simulationGroupId = runIdSimulationGroupId[1];
			MethodCallStatus runStatus = dataServiceDao.getRunStatus(runId, authentication);
			MethodCallStatusEnum statusEnum = runStatus.getStatus();

			long statusStartTime = System.currentTimeMillis();
			while (!statusEnum.equals(MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)) {
				try {
					switch (statusEnum) {
						case FAILED:
							return MessageUtils.createRunResult(runId,
									MethodCallStatusEnum.FAILED, runStatus.getMessage());
						default:
							Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
							break;
					}
				} catch (InterruptedException ex) {
					// don't care
				}
			}

			String url = getTranslatorServiceUrl(dataServiceDao, authentication);
			if (url == null) {
				return MessageUtils.createRunResult(
						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
						MethodCallStatusEnum.FAILED, "There was no translator URL available.");
			}

			// run is loaded, now call translator
			TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor(url);
			translatorDao.translateRun(runId);

			runStatus = dataServiceDao.getRunStatus(runId, authentication);
			statusEnum = runStatus.getStatus();

			while (!statusEnum.equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
				try {
					switch (statusEnum) {
						case FAILED:
							break;
						default:
							Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
							break;
					}
				} catch (InterruptedException ex) {
					// it's okay if the sleep timer is interrupted
				}
			}
			// run is now translated
			return (MessageUtils.createRunResult(
					runId, statusEnum, "Apollo Broker is handling the run request."));
		} catch (UnrecognizedMessageTypeException e) {
			return MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Unrecognized message type: " + message.getClass().getName()
					+ ".  Error was: " + e.getMessage());
		} catch (DataServiceException ex) {
			return (MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Database exception staging run: " + ex.getMessage()));
		} catch (RunManagerServiceException ex) {
			return (MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Run manager service exception staging run: " + ex.getMessage()));
		}
	}

	protected RunResultAndSimulationGroupId getRunResultAndSimulationGroupId(RunResult runResult, BigInteger simulationGroupId) {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();
		runResultAndSimulationGroupId.setRunResult(runResult);
		runResultAndSimulationGroupId.setSimulationGroupId(simulationGroupId);
		return runResultAndSimulationGroupId;
	}

	private String getTranslatorServiceUrl(DataServiceAccessor dataServiceDao, Authentication authentication) throws DataServiceException {

		Map<Integer, ServiceRegistrationRecord> software = dataServiceDao.getListOfRegisteredSoftwareRecords(authentication);
		for (ServiceRegistrationRecord record : software.values()) {
			if (record.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.TRANSLATOR)) {
				return record.getUrl();
			}
		}

		return null;
	}

}
