package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 8, 2014
 * Time: 11:52:26 AM
 * Class: RunMethod
 * IDE: NetBeans 6.9.1
 */
public abstract class RunMethod {

    protected static boolean isRunFailed(int runId) throws ApolloDatabaseException {

        RunAndSoftwareIdentification rasid = new RunAndSoftwareIdentification();
        rasid.setRunId(Integer.toString(runId));

        // If this call results in an exception, there is a bad database
        // configuration for that run (TODO: WE NEED TO DEAL WITH THIS)
        rasid.setSoftwareId(ApolloDbUtilsContainer.getApolloDbUtils().getLastServiceToBeCalledForRun(runId));

        MethodCallStatus status = GetRunStatusMethod.getRunStatus(rasid);
        MethodCallStatusEnum statusEnum = status.getStatus();

        if (statusEnum.equals(MethodCallStatusEnum.FAILED)) {
            return true;
        } else {
            return false;
        }
    }
}
