package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.types.RunResultAndSimulationGroupId;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.runmanagerservice.utils.MessageUtils;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class StageMethod {

	private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
	private final Object message;
	private final BigInteger associatedSimulationGroup;
	protected DataServiceAccessor dataServiceDao;

	public StageMethod(Object message, BigInteger associatedSimulationGroup) {
		this.message = message;
		this.associatedSimulationGroup = associatedSimulationGroup;
	}

	public RunResultAndSimulationGroupId stage() {

		try {

			dataServiceDao = DataServiceAccessorFactory.getDataServiceAccessor(message);

			BigInteger[] runIdSimulationGroupId = dataServiceDao.insertRunIntoDatabase(associatedSimulationGroup);

			BigInteger runId = runIdSimulationGroupId[0];
			BigInteger simulationGroupId = runIdSimulationGroupId[1];
			MethodCallStatus runStatus = dataServiceDao.getStatusOfRun(runId);
			MethodCallStatusEnum statusEnum = runStatus.getStatus();

			long statusStartTime = System.currentTimeMillis();
			while (!statusEnum.equals(MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)) {
				try {
					switch (statusEnum) {

						case FAILED:
							return getRunResultAndSimulationGroupId(MessageUtils.createRunResult(runId,
									MethodCallStatusEnum.FAILED, runStatus.getMessage()), null);
						default:
							Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
							break;
					}
				} catch (InterruptedException ex) {
					// don't care
				}
			}

			// run is loaded, now call translator
			TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor();
			translatorDao.translateRun(runId);

			runStatus = dataServiceDao.getStatusOfRun(runId);
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
					// don't care
				}
			}

			// run is now translated
			return getRunResultAndSimulationGroupId(MessageUtils.createRunResult(
					runId, statusEnum, "Apollo Broker is handling the run request."), simulationGroupId);

		} catch (UnrecognizedMessageTypeException e) {
			return getRunResultAndSimulationGroupId(MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Unrecognized message type: " + message.getClass().getName()
					+ ".  Error was: " + e.getMessage()), null);
		} catch (DataServiceException ex) {
			return getRunResultAndSimulationGroupId(MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Database exception staging run: " + ex.getMessage()), null);
		} catch (RunManagerServiceException ex) {
			return getRunResultAndSimulationGroupId(MessageUtils.createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Run manager service exception staging run: " + ex.getMessage()), null);
		}
	}

	protected RunResultAndSimulationGroupId getRunResultAndSimulationGroupId(RunResult runResult, BigInteger simulationGroupId) {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();
		runResultAndSimulationGroupId.setRunResult(runResult);
		runResultAndSimulationGroupId.setSimulationGroupId(simulationGroupId);
		return runResultAndSimulationGroupId;
	}

}
