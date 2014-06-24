package edu.pitt.apollo.apolloservice.methods.run;

import java.io.IOException;
import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningVisualizations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.thread.RunVisualizationThread;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;

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

    public static RunVisualizationResult runVisualization(RunVisualizationMessage runVisualizationMessage) {
        DatabaseAccessorForRunningVisualizations databaseAccessorForRunningVisualizations =
                new DatabaseAccessorForRunningVisualizations(runVisualizationMessage);
        RunVisualizationResult result = new RunVisualizationResult();
        try {
            BigInteger runId = databaseAccessorForRunningVisualizations.getCachedRunIdFromDatabaseOrNull();

            if (runId != null) {
                if (isRunFailed(runId)) {
                    databaseAccessorForRunningVisualizations.removeAllDataAssociatedWithRunId(runId);
                } else {
                    result.setVisualizationRunId(runId);
                    return result;
                }
            }

            runId = databaseAccessorForRunningVisualizations.insertRunIntoDatabase(runVisualizationMessage);

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
