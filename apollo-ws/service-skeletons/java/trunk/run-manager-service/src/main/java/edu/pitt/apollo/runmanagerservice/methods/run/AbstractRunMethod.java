package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.runmanagerservice.types.RunResultAndSimulationGroupId;
import edu.pitt.apollo.runmanagerservice.types.ReturnObjectForRun;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThreadFactory;

import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;

public abstract class AbstractRunMethod implements RunMethod {
	
	private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
	protected Object runMessage;
	protected final BigInteger runId;
	private final BigInteger associatedSimulationGroup;
	protected DataServiceAccessor dataServiceDao;
	
	public AbstractRunMethod(BigInteger runId, BigInteger associatedSimulationGroup) {
		this.associatedSimulationGroup = associatedSimulationGroup;
		this.runId = runId;
	}
	
	protected final RunResult createRunResult(BigInteger runId, MethodCallStatusEnum statusEnum, String error) {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);
		runResult.setRunId(runId);
		runResult.getMethodCallStatus().setMessage(error);
		runResult.getMethodCallStatus().setStatus(statusEnum);
		return runResult;
	}
	
	private void setRunMessage() throws DataServiceException, JsonUtilsException {
		String messageJson = dataServiceDao.getRunMessageAssociatedWithRunIdAsJsonOrNull(runId);
		runMessage = convertRunMessageJson(messageJson);
	}
	
	protected abstract Object convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException;
	
	protected abstract Object getObjectToReturn(BigInteger runId) throws RunManagerServiceException;
	
	protected abstract MethodCallStatus getMethodCallStatusToReturn();

//	@Override
//	public final ReturnObjectForRun stageAndRun() {
//		RunResultAndSimulationGroupId runResultAndSimulationGroupId = stage();
//		RunResult runResult = runResultAndSimulationGroupId.getRunResult();
//
//		ReturnObjectForRun returnObj = getReturnObjectForRun(runResult);
//		if (returnObj.getStatus().getStatus().equals(MethodCallStatusEnum.FAILED)) {
//			return returnObj;
//		}
//
//		if ((runResult.getMethodCallStatus().getStatus() == getSuccessfulMethodCallStatus())) {
//			try {
//				RunApolloServiceThread runApolloServiceThread = RunApolloServiceThreadFactory
//						.getRunApolloServiceThread(message,
//								runResult.getRunId(), runResultAndSimulationGroupId.getSimulationGroupId());
//				runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
//				runApolloServiceThread.start();
//			} catch (UnrecognizedMessageTypeException | ApolloDatabaseException e) {
//				returnObj.setObjectToReturnFromBroker(createRunResult(
//						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
//						MethodCallStatusEnum.FAILED,
//						"Error of type + " + e.getClass().toString() + ":"
//						+ e.getMessage()));
//			}
//		}
//		return returnObj;
//	}
	@Override
	public final ReturnObjectForRun run(BigInteger runId) {
		
		MethodCallStatus status = getMethodCallStatusToReturn();
		ReturnObjectForRun returnObj = new ReturnObjectForRun();
		returnObj.setStatus(status);
		
		try {
			setRunMessage();
		} catch (DataServiceException | JsonUtilsException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("Exception getting run message from data service: " + ex.getMessage());
			return returnObj;
		}
		
		try {
			returnObj.setObjectToReturnFromBroker(getObjectToReturn(runId));
		} catch (RunManagerServiceException ex) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("There was an exception getting the object to return from the run manager service: " + ex.getMessage());
			return returnObj;
		}
		
		status = dataServiceDao.getStatusOfRun(runId);
		if (!status.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
			status.setStatus(MethodCallStatusEnum.FAILED);
			status.setMessage("The run has not been translated. The run must be translated before being sent to the simulator.");
			returnObj.setStatus(status);
			return returnObj;
		}
		
		RunApolloServiceThread runApolloServiceThread = RunApolloServiceThreadFactory
				.getRunApolloServiceThread(runMessage,
						runId, runResultAndSimulationGroupId.getSimulationGroupId());
		runApolloServiceThread.start();
		
		return returnObj;
	}
	
	protected RunResultAndSimulationGroupId handlePreviouslyFailedRun(BigInteger cachedRunId) {
		final RunResultAndSimulationGroupId RUN_DATA_SUCCESSFULLY_REMOVED = null;
		
		try {
			dataServiceDao.removeAllDataAssociatedWithRunId(cachedRunId);
			return RUN_DATA_SUCCESSFULLY_REMOVED;
		} catch (DataServiceException e) {
			return getRunResultAndSimulationGroupId(createRunResult(
					ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
					MethodCallStatusEnum.FAILED, "Error removing data for cached run: " + cachedRunId), null);
		}
	}
	
	protected MethodCallStatusEnum getSuccessfulMethodCallStatus() {
		return MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE;
	}

//	private boolean isRunFailed(BigInteger runId) {
//		MethodCallStatus runStatus = dataServiceDao.getStatusOfRun(runId);
//		return (runStatus.getStatus() == MethodCallStatusEnum.FAILED);
//	}
//
//	private boolean needToAddToSimulationGroup(BigInteger associatedSimulationGroup) {
//		return associatedSimulationGroup != null;
//	}
}
