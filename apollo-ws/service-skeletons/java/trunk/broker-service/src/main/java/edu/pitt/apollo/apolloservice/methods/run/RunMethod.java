package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessor;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessorFactory;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThreadFactory;
import java.math.BigInteger;

import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_1_0.Authentication;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatus;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_1_0.RunResult;
import edu.pitt.apollo.types.v2_1_0.SoftwareIdentification;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 8, 2014 Time:
 * 11:52:26 AM Class: RunMethod IDE: NetBeans 6.9.1
 */
public class RunMethod {

	private final Authentication authentication;
	private final SoftwareIdentification softwareIdentification;
	protected DatabaseAccessor databaseAccessor;
	protected RunApolloServiceThread runApolloServiceThread;
	private final Object message;

	public RunMethod(Authentication authentication,
			SoftwareIdentification softwareIdentification, Object message) {

		this.authentication = new Authentication();
		this.authentication.setRequesterId(authentication.getRequesterId());
		this.authentication.setRequesterPassword(authentication.getRequesterPassword());

		this.softwareIdentification = softwareIdentification;
		this.message = message;
	}

	protected final RunResult getUnauthorizedSoftwareResult() {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);

		runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
		runResult.getMethodCallStatus().setStatus(
				MethodCallStatusEnum.AUTHENTICATION_FAILURE);
		runResult.getMethodCallStatus().setMessage(
				"You are not authorized to view results for the "
				+ softwareIdentification.getSoftwareName()
				+ " "
				+ softwareIdentification
				.getSoftwareType().toString().toLowerCase()
				+ ".");
		return runResult;
	}

	protected static final boolean isRunFailed(BigInteger runId)
			throws ApolloDatabaseException {
		MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
		MethodCallStatusEnum statusEnum = status.getStatus();

		return statusEnum.equals(MethodCallStatusEnum.FAILED);
	}

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

	public final RunResult run() {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);

		try {
			runApolloServiceThread = RunApolloServiceThreadFactory.getRunApolloServiceThread(message);
			runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
			
			databaseAccessor = DatabaseAccessorFactory.getDatabaseAccessor(message, authentication);
		} catch (UnrecognizedMessageTypeException ex) {
			runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
			runResult.getMethodCallStatus().setMessage(ex.getMessage());
			runResult.getMethodCallStatus().setStatus(
					MethodCallStatusEnum.FAILED);
			return runResult;
		}

		try {
			boolean userSuccessfullyAuthenticated = authenticateUser(databaseAccessor);

			if (!userSuccessfullyAuthenticated) {
				runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
				runResult.getMethodCallStatus().setStatus(
						MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				runResult.getMethodCallStatus().setMessage(
						"Invalid username");
				return runResult;
			}

			boolean userAuthorizedForCachedRun = userAuthorizedForCachedResults(databaseAccessor);
			if (!userAuthorizedForCachedRun) {
				return getUnauthorizedSoftwareResult();
			}

			BigInteger runId = databaseAccessor
					.getCachedRunIdFromDatabaseOrNull();

			if (runId != null) {
				if (!isRunFailed(runId)) {
					runResult.setRunId(runId);
					runResult.getMethodCallStatus().setStatus(
							MethodCallStatusEnum.RUNNING);
					runResult.getMethodCallStatus().setMessage(
							"This run is already running!");
					return runResult;
				}

			}

			boolean userAuthorizedToRunSoftware = userAuthorizedToRunSoftware(databaseAccessor);
			if (!userAuthorizedToRunSoftware) {
				return getUnauthorizedSoftwareResult();
			}

			if (runId != null) {
				if (isRunFailed(runId)) {
					databaseAccessor.removeAllDataAssociatedWithRunId(runId);
				}
			}

			runId = databaseAccessor.insertRunIntoDatabase();
			runResult.setRunId(runId);
			runResult.getMethodCallStatus().setStatus(
					MethodCallStatusEnum.STAGING);
			runResult.getMethodCallStatus().setMessage(
					"Apollo Broker is handling the run request.");

			runApolloServiceThread.setRunId(runId);
			runApolloServiceThread.start();

			return runResult;
		} catch (ApolloDatabaseException ex) {

			runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
			runResult.getMethodCallStatus().setMessage(ex.getMessage());
			runResult.getMethodCallStatus().setStatus(
					MethodCallStatusEnum.FAILED);
			return runResult;
		}
	}
}
