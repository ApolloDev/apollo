package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.thread.RunSimulationThread;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningSimulations;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import java.io.IOException;
import java.math.BigInteger;

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

    public static BigInteger runSimulation(RunSimulationMessage runSimulationMessage) {

        DatabaseAccessorForRunningSimulations databaseAccessorForRunningSimulations =
                new DatabaseAccessorForRunningSimulations(runSimulationMessage);
        try {
            BigInteger runId = databaseAccessorForRunningSimulations.getCachedRunIdFromDatabaseOrNull();

            if (runId != null) {
                int runIdInt = runId.intValue();
                if (isRunFailed(runIdInt)) {
                    databaseAccessorForRunningSimulations.removeAllDataAssociatedWithRunId(runIdInt);
                } else {
                    return runId;
                }
            }

            runId = databaseAccessorForRunningSimulations.insertRunIntoDatabase();

            new RunSimulationThread(runId.intValue(), runSimulationMessage).start();

            return runId;
        } catch (ApolloDatabaseException ex) {
            try {
                long runId = ApolloServiceErrorHandler.writeErrorWithErrorId(ex.getMessage());
                return new BigInteger(Long.toString(runId));
            } catch (IOException e) {
                System.err.println("IOException writing error file: "
                        + e.getMessage());
                return new BigInteger(ApolloServiceErrorHandler.FATAL_ERROR_CODE);
            }
        }
    }
}
