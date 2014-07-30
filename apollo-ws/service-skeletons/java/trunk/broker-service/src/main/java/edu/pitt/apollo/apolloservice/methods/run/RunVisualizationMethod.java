package edu.pitt.apollo.apolloservice.methods.run;

import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningVisualizations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.thread.RunVisualizationThread;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_2.RunResult;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationResult;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:42:55 PM
 * Class: RunVisualizationMethod
 * IDE: NetBeans 6.9.1
 */
public class RunVisualizationMethod extends RunMethod {

	private final RunVisualizationMessage runVisualizationMessage;

	public RunVisualizationMethod(RunVisualizationMessage runVisualizationMessage) {
		super(runVisualizationMessage.getAuthentication(), runVisualizationMessage.getVisualizerIdentification());
		this.runVisualizationMessage = runVisualizationMessage;
		databaseAccessor = new DatabaseAccessorForRunningVisualizations(runVisualizationMessage);
	}

	public RunResult runVisualization() {

		// check authorization
		RunResult result = new RunResult();
		try {
			BigInteger runId = databaseAccessor.getCachedRunIdFromDatabaseOrNull();

			if (runId != null) {
				if (isRunFailed(runId)) {
					databaseAccessor.removeAllDataAssociatedWithRunId(runId);
				} else {
					result.setRunId(runId);
					return result;
				}
			}

			runId = databaseAccessor.insertRunIntoDatabase();

			new RunVisualizationThread(runId, runVisualizationMessage).start();

			result.setRunId(runId);
			return result;
		} catch (ApolloDatabaseException ex) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId(ex.getMessage());
				result.setRunId(new BigInteger(String.valueOf(runId)));
				return result;
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				result.setRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
				return result;
			}
		}
	}
}
