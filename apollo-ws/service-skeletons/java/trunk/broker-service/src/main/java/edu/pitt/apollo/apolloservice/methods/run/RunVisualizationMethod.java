package edu.pitt.apollo.apolloservice.methods.run;

import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningVisualizations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.thread.RunVisualizationThread;
import edu.pitt.apollo.db.ApolloDatabaseException;
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

	public RunVisualizationResult runVisualization() {

		// check authorization
		RunVisualizationResult result = new RunVisualizationResult();
		try {
			BigInteger runId = databaseAccessor.getCachedRunIdFromDatabaseOrNull();

			if (runId != null) {
				if (isRunFailed(runId)) {
					databaseAccessor.removeAllDataAssociatedWithRunId(runId);
				} else {
					result.setVisualizationRunId(runId);
					return result;
				}
			}

			runId = databaseAccessor.insertRunIntoDatabase();

			new RunVisualizationThread(runId, runVisualizationMessage).start();

			result.setVisualizationRunId(runId);
			return result;
		} catch (ApolloDatabaseException ex) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId(ex.getMessage());
				result.setVisualizationRunId(new BigInteger(String.valueOf(runId)));
				return result;
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				result.setVisualizationRunId(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
				return result;
			}
		}
	}
}
