package edu.pitt.apollo.dataservice.utils;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.math.BigInteger;

public class RunUtils {

    public static void updateStatus(ApolloDbUtils dbUtils, BigInteger runId, MethodCallStatusEnum statusEnum, String message) {
        try {
            dbUtils.updateStatusOfRun(runId, statusEnum, message);
        } catch (ApolloDatabaseException ex) {
            try {
                dbUtils.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, "ApolloDatabaseException updating status for run ID "
                        + runId + ": " + ex.getMessage());
            } catch (ApolloDatabaseException ex1) {
                ex1.printStackTrace();
            }
        }
    }
}
