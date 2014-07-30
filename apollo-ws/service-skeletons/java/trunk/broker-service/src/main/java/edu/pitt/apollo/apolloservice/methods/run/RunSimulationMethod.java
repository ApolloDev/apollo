package edu.pitt.apollo.apolloservice.methods.run;

import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningSimulations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.thread.RunSimulationThread;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.RunResult;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 8, 2014 Time:
 * 11:50:15 AM Class: RunSimulationMethod IDE: NetBeans 6.9.1
 */
public class RunSimulationMethod extends RunMethod {

	private final RunSimulationMessage runSimulationMessage;

	public RunSimulationMethod(RunSimulationMessage runSimulationMessage) {
		super(runSimulationMessage.getAuthentication(), runSimulationMessage
				.getSimulatorIdentification());
		this.runSimulationMessage = runSimulationMessage;
		databaseAccessor = new DatabaseAccessorForRunningSimulations(
				runSimulationMessage);
	}

	private RunResult getUnauthorizedSoftwareResult() {
		RunResult runResult = new RunResult();
		runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
		runResult.getMethodCallStatus().setStatus(
				MethodCallStatusEnum.AUTHENTICATION_FAILURE);
		runResult.getMethodCallStatus().setMessage(
				"You are not authorized to view results for the "
						+ runSimulationMessage.getSimulatorIdentification()
								.getSoftwareName()
						+ " "
						+ runSimulationMessage.getSimulatorIdentification()
								.getSoftwareType().toString().toLowerCase()
						+ ".");
		return runResult;
	}

	public RunResult runSimulation() {
		RunResult runResult = new RunResult();
		MethodCallStatus methodCallStatus = new MethodCallStatus();
		runResult.setMethodCallStatus(methodCallStatus);
		try {
			boolean userSuccessfullyAuthenticated = authenticateUser(databaseAccessor);

			if (!userSuccessfullyAuthenticated) {
				runResult.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
				runResult.getMethodCallStatus().setStatus(
						MethodCallStatusEnum.AUTHENTICATION_FAILURE);
				runResult.getMethodCallStatus().setMessage(
						"Invalid username or password.");
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
							MethodCallStatusEnum.CALLED_SIMULATOR);
					runResult.getMethodCallStatus().setMessage(
							"This run is already running!");
					return runResult;
				}
			}

			boolean userAuthorizedToRunSoftware = userAuthorizedToRunSoftware(databaseAccessor);
			if (!userAuthorizedToRunSoftware) {
				return getUnauthorizedSoftwareResult();
			}

			if (isRunFailed(runId)) {
				databaseAccessor.removeAllDataAssociatedWithRunId(runId);
			}
			runId = databaseAccessor.insertRunIntoDatabase();
			runResult.setRunId(runId);
			runResult.getMethodCallStatus().setStatus(
					MethodCallStatusEnum.STAGING);
			runResult.getMethodCallStatus().setMessage(
					"Apollo Broker is handling the run request.");

			new RunSimulationThread(runId, runSimulationMessage).start();

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
