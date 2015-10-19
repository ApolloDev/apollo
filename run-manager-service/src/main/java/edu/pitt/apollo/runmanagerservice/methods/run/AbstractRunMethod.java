package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.types.ReturnObjectForRun;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.runmanagerservice.thread.RunApolloServiceThreadFactory;

import edu.pitt.apollo.services_common.v3_0_2.*;

import java.math.BigInteger;

public abstract class AbstractRunMethod implements RunMethod {

    private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
    protected final BigInteger runId;
    protected RunMessage runMessage;
    protected DataServiceAccessor dataServiceDao;
    protected Authentication authentication;


    public AbstractRunMethod(BigInteger runId, Authentication authentication, String runMessageFilename) throws DataServiceException, JsonUtilsException {
        this.runId = runId;
        this.authentication = authentication;
        dataServiceDao = new DataServiceAccessor();
        String json = getRunMessageJson(runMessageFilename);
        runMessage = convertRunMessageJson(json);
    }

    protected String getRunMessageJson(String runMessageFilename) throws DataServiceException {
        return dataServiceDao.getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, runMessageFilename);
    }

    protected abstract RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException;

//    protected abstract Object getObjectToReturn(BigInteger runId) throws RunManagerServiceException;

    protected abstract MethodCallStatus getDefaultSuccessfulMethodCallStatus();

    protected final RunResult createRunResult(BigInteger runId, MethodCallStatusEnum statusEnum, String error) {
        RunResult runResult = new RunResult();
        MethodCallStatus methodCallStatus = new MethodCallStatus();
        runResult.setMethodCallStatus(methodCallStatus);
        runResult.setRunId(runId);
        runResult.getMethodCallStatus().setMessage(error);
        runResult.getMethodCallStatus().setStatus(statusEnum);
        return runResult;
    }


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
    public final ReturnObjectForRun run(BigInteger stagedRunId) {
        MethodCallStatus status = getDefaultSuccessfulMethodCallStatus();
        ReturnObjectForRun returnObj = new ReturnObjectForRun();
        returnObj.setStatus(status);

//        try {
////            returnObj.setObjectToReturnFromBroker(getObjectToReturn(runId));
//        } catch (RunManagerServiceException ex) {
//            status.setStatus(MethodCallStatusEnum.FAILED);
//            status.setMessage("There was an exception getting the object to return from the run manager service: " + ex.getMessage());
//            return returnObj;
//        }

        try {
            status = dataServiceDao.getRunStatus(runId, authentication);

            if (!status.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
                status.setStatus(MethodCallStatusEnum.FAILED);
                status.setMessage("The run has not been translated. The run must be translated before being sent to the simulator.");
                returnObj.setStatus(status);
                return returnObj;
            }
        } catch (DataServiceException e) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage("Error getting run status for runId " + runId + ", error was:" + e.getMessage());
            returnObj.setStatus(status);
            return returnObj;
        }

        try {
            RunApolloServiceThread runApolloServiceThread = RunApolloServiceThreadFactory
                    .getRunApolloServiceThread(runMessage,
                            runId, authentication);
            runApolloServiceThread.start();
        } catch (UnrecognizedMessageTypeException e) {
            status.setStatus(MethodCallStatusEnum.FAILED);
            status.setMessage(e.getClass().toString() + ": Error creating RunApolloServiceThread.  Error was: " + e.getMessage());
            returnObj.setStatus(status);
            return returnObj;
        }
        return returnObj;
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
