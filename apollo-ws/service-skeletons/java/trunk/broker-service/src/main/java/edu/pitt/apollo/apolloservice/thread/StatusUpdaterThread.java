package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.TimerTask;

/**
 * Created by jdl50 on 4/15/15.
 */
public class StatusUpdaterThread extends TimerTask {

    private final RunSimulationsThread.CounterRef counter;
    private final RunSimulationsThread.BooleanRef error;
    private final BigInteger runId;

    StatusUpdaterThread(BigInteger runId, RunSimulationsThread.CounterRef counter, RunSimulationsThread.BooleanRef error) {
        this.counter = counter;
        this.error = error;
        this.runId = runId;

    }
    @Override
    public void run() {
        if (!error.value) {
            try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
                dbUtils.updateStatusOfRun(runId,
                        MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE,
                        "Added " + counter.count + " runs to the db for runId: "
                                + runId.toString());
            } catch (ApolloDatabaseException e) {
                e.printStackTrace();
            }
        }
    }
}
