package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.Md5UtilsException;

import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;

import edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors.DataServiceAccessorFactory;
import edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors.DataserviceException;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThreadFactory;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;

public abstract class AbstractRunMethod implements RunMethod {

	private final Authentication authentication;
	private final SoftwareIdentification softwareIdentification;
	private final Object message;
	private final RunResult USER_AUTHENTICATED_AND_AUTHORIZED = null;
	private final BigInteger associatedSimulationGroup;
	protected DataServiceAccessor dao;

	public AbstractRunMethod(Authentication authentication,
			SoftwareIdentification softwareIdentification, BigInteger associatedSimulationGroup, Object message) {
		this.authentication = new Authentication();
		this.authentication.setRequesterId(authentication.getRequesterId());
		this.authentication.setRequesterPassword(authentication
				.getRequesterPassword());
		this.message = message;
		this.associatedSimulationGroup = associatedSimulationGroup;
		this.softwareIdentification = softwareIdentification;
	}

	protected final MethodCallStatus getRunStatus(BigInteger runId) {
		return GetRunStatusMethod.getRunStatus(runId);
	}

	protected final RunResult createRunResult(BigInteger runId, MethodCallStatusEnum errorCode, String error) {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);
		runResult.setRunId(runId);
		runResult.getMethodCallStatus().setMessage(error);
		runResult.getMethodCallStatus().setStatus(errorCode);
		return runResult;
	}

	@Override
	public final ReturnObjectForRun stageAndRun() {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = stage();
		RunResult runResult = runResultAndSimulationGroupId.getRunResult();

		ReturnObjectForRun returnObj = getReturnObjectForRun(runResult);
		if (returnObj.getStatus().getStatus().equals(MethodCallStatusEnum.FAILED)) {
			return returnObj;
		}

		if ((runResult.getMethodCallStatus().getStatus() == getSuccessfulMethodCallStatus())) {
			try {
				RunApolloServiceThread runApolloServiceThread = RunApolloServiceThreadFactory
						.getRunApolloServiceThread(authentication, message,
								runResult.getRunId(), runResultAndSimulationGroupId.getSimulationGroupId());
				runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
				runApolloServiceThread.start();
			} catch (Exception e) {
				returnObj.setObjectToReturnFromBroker(createRunResult(
						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
						MethodCallStatusEnum.FAILED,
						"Error of type + " + e.getClass().toString() + ":"
						+ e.getMessage()));
			}
		}
		return returnObj;
	}

	@Override
	public RunResultAndSimulationGroupId stage() {

		try {

			dao = DataServiceAccessorFactory.getDataServiceAccessor(
					message, authentication);

			BigInteger[] runIdSimulationGroupId = dao
					.insertRunIntoDatabase(associatedSimulationGroup);

			MethodCallStatusEnum methodCallStatusEnum = getSuccessfulMethodCallStatus();

			return getRunResultAndSimulationGroupId(createRunResult(
					runIdSimulationGroupId[0], methodCallStatusEnum,
					"Apollo Broker is handling the run request."), runIdSimulationGroupId[1]);

		} catch (UnrecognizedMessageTypeException e) {
			return getRunResultAndSimulationGroupId(createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Unrecognized message type: " + message.getClass().getName()
					+ ".  Error was: " + e.getMessage()), null);
		} catch (DataserviceException ex) {
			return getRunResultAndSimulationGroupId(createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Database exception staging run: " + ex.getMessage()), null);
		}
	}

	protected abstract ReturnObjectForRun getReturnObjectForRun(RunResult runResult);

	protected RunResultAndSimulationGroupId handlePreviouslyFailedRun(BigInteger cachedRunId) {
		final RunResultAndSimulationGroupId RUN_DATA_SUCCESSFULLY_REMOVED = null;

		try {
			dao.removeAllDataAssociatedWithRunId(cachedRunId);
			return RUN_DATA_SUCCESSFULLY_REMOVED;
		} catch (DataserviceException e) {
			return getRunResultAndSimulationGroupId(createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Error removing data for cached run: " + cachedRunId), null);
		}
	}

	protected MethodCallStatusEnum getSuccessfulMethodCallStatus() {
		return MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE;
	}

	protected RunResultAndSimulationGroupId getRunResultAndSimulationGroupId(RunResult runResult, BigInteger simulationGroupId) {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();
		runResultAndSimulationGroupId.setRunResult(runResult);
		runResultAndSimulationGroupId.setSimulationGroupId(simulationGroupId);
		return runResultAndSimulationGroupId;
	}

//	protected RunResultAndSimulationGroupId handleNonFailedCachedRun(BigInteger cachedRunId, BigInteger associatedSimulationGroup) {
//		if (needToAddToSimulationGroup(associatedSimulationGroup)) {
//			List<BigInteger> runIds = new ArrayList<>();
//			runIds.add(cachedRunId);
//			try {
//				dao.addRunIdsToSimulationGroup(associatedSimulationGroup, runIds);
//			} catch (Exception e) {
//				return getRunResultAndSimulationGroupId(createRunResult(
//						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
//						MethodCallStatusEnum.FAILED,
//						"Database error adding run IDs to simulation group: " + e.getMessage()), null);
//			}
//		}
//
//		MethodCallStatus runStatus = getRunStatus(cachedRunId);
//
//		return getRunResultAndSimulationGroupId(createRunResult(cachedRunId,
//				runStatus.getStatus(),
//				runStatus.getMessage()), null);
//	}

	private boolean isRunFailed(BigInteger runId) {
		MethodCallStatus runStatus = getRunStatus(runId);
		return (runStatus.getStatus() == MethodCallStatusEnum.FAILED);
	}

	private boolean needToAddToSimulationGroup(BigInteger associatedSimulationGroup) {
		return associatedSimulationGroup != null;
	}

}
