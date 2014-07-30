package edu.pitt.apollo.apolloservice.methods.run;

import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningSimulations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.thread.RunSimulationThread;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_2.RunSimulationMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 8, 2014
 * Time: 11:50:15 AM
 * Class: RunSimulationMethod
 * IDE: NetBeans 6.9.1
 */
public class RunSimulationMethod extends RunMethod {

	private final RunSimulationMessage runSimulationMessage;

	public RunSimulationMethod(RunSimulationMessage runSimulationMessage) {
		super(runSimulationMessage.getAuthentication(), runSimulationMessage.getSimulatorIdentification());
		this.runSimulationMessage = runSimulationMessage;
		databaseAccessor = new DatabaseAccessorForRunningSimulations(runSimulationMessage);
	}

	public BigInteger runSimulation() {

		try {
			BigInteger userAuthenticated = userAuthenticated(databaseAccessor);
			if (userAuthenticated.intValue() != 0) {
				return userAuthenticated;
			}

			BigInteger userAuthorizedForCachedRun = userAuthorizedForCachedResults(databaseAccessor);
			if (userAuthorizedForCachedRun.intValue() != 0) {
				return userAuthorizedForCachedRun;
			}

			BigInteger runId = databaseAccessor.getCachedRunIdFromDatabaseOrNull();
			if (runId != null) {
				if (!isRunFailed(runId)) {
					return runId;
				}
			}

			BigInteger userAuthorizedToRunSoftware = userAuthorizedToRunSoftware(databaseAccessor);
			if (userAuthorizedToRunSoftware.intValue() != 0) {
				return userAuthorizedToRunSoftware;
			}

			if (isRunFailed(runId)) {
				databaseAccessor.removeAllDataAssociatedWithRunId(runId);
			}
			runId = databaseAccessor.insertRunIntoDatabase();

			new RunSimulationThread(runId, runSimulationMessage).start();

			return runId;
		} catch (ApolloDatabaseException ex) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId(ex.getMessage());
				return new BigInteger(Long.toString(runId));
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				return ApolloServiceErrorHandler.FATAL_ERROR_CODE;
			}
		}
	}
}
