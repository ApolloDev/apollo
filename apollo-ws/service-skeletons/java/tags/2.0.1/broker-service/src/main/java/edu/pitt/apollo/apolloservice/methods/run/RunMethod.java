package edu.pitt.apollo.apolloservice.methods.run;

import java.math.BigInteger;

import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;

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

    protected static boolean isRunFailed(BigInteger runId) throws ApolloDatabaseException {
        MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
        MethodCallStatusEnum statusEnum = status.getStatus();

        if (statusEnum.equals(MethodCallStatusEnum.FAILED)) {
            return true;
        } else {
            return false;
        }
    }
}
