package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessor;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThreadFactory;

import edu.pitt.apollo.apolloservice.types.ReturnObjectForRun;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;

import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 8, 2014 Time:
 * 11:52:26 AM Class: RunMethod IDE: NetBeans 6.9.1
 */
public abstract class RunMethod extends AbstractRunMethod {

	private final Object message;

	public RunMethod(Authentication authentication,
			SoftwareIdentification softwareIdentification, Object message) {
		super(authentication, softwareIdentification);
		this.message = message;
	}

	public final RunResultAndSimulationGroupId stageInDatabase(
			BigInteger simulationGroupId) {
		return stageRun(message, simulationGroupId);

	}

//	protected  MethodCallStatus getRunStatus(BigInteger runId)
//			throws ApolloDatabaseException {
//		MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
//
//		return status;
//	}
	protected final boolean authenticateUser(DatabaseAccessor dbAccessor)
			throws ApolloDatabaseException {
		boolean userSuccessfulyAuthenticated = dbAccessor
				.authenticateUser(authentication);
		return userSuccessfulyAuthenticated;
	}

	protected final boolean userAuthorizedForCachedResults(
			DatabaseAccessor dbAccessor) throws ApolloDatabaseException {
		boolean userAuthorizedForCachedResults = dbAccessor
				.authorizeUserForSoftwareCacheData(authentication,
						softwareIdentification);
		return userAuthorizedForCachedResults;

	}

	protected final boolean userAuthorizedToRunSoftware(
			DatabaseAccessor dbAccessor) throws ApolloDatabaseException {
		boolean userAuthorizedToRunSoftware = dbAccessor.authorizeUserForRunningSoftware(
				authentication, softwareIdentification);
		return userAuthorizedToRunSoftware;
	}

	public final ReturnObjectForRun run()  {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = stageInDatabase(null);
		RunResult runResult = runResultAndSimulationGroupId.getRunResult();
		
		ReturnObjectForRun returnObj = getReturnObjectForRun(runResult);
		if (returnObj.getStatus().getStatus().equals(MethodCallStatusEnum.FAILED)) {
			return returnObj;
		}
		
		if ((runResult.getMethodCallStatus().getStatus() == MethodCallStatusEnum.LOADING_RUN_CONFIG_INTO_DATABASE)
				|| (runResult.getMethodCallStatus().getStatus() == MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)) {
			try {
				runApolloServiceThread = RunApolloServiceThreadFactory
						.getRunApolloServiceThread(authentication, message,
								runResult.getRunId(), runResultAndSimulationGroupId.getSimulationGroupId());

				runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
				runApolloServiceThread.start();
			} catch (Exception  e) {
				returnObj.setObjectToReturnFromBroker(createRunResult(
						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
						MethodCallStatusEnum.FAILED,
						"Error of type + " + e.getClass().toString() + ":"
						+ e.getMessage()));
			}
		}
		
		return returnObj;
	}

	public abstract ReturnObjectForRun getReturnObjectForRun(RunResult runResult);

//	public final ReturnObjectForRun run() {
//
//		RunResult runResult = new RunResult();
//		boolean shouldRunSoftware = authorizeAndCheckForCachedRun(runResult);
//		ReturnObjectForRun returnObj = getReturnObjectForRun(runResult);
//		if (returnObj.getStatus().getStatus().equals(MethodCallStatusEnum.FAILED)) {
//			return returnObj;
//		}
//		if (shouldRunSoftware) {
//			runApolloServiceThread.setRunId(runResult.getRunId());
//			runApolloServiceThread.start();
//		}
//
//		return returnObj;
//
//	}
}
