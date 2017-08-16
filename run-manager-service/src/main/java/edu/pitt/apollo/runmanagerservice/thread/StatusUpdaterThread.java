package edu.pitt.apollo.runmanagerservice.thread;


import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.TimerTask;

/**
 * Created by jdl50 on 4/15/15.
 */
public class StatusUpdaterThread extends TimerTask {

    private final BatchStageThread.CounterRef counter;
    private final BatchStageThread.BooleanRef error;
    private final BigInteger runId;
    private final Authentication authentication;
    DatastoreAccessor dataServiceAccessor;

    public StatusUpdaterThread(DatastoreAccessor dataServiceAccessor, BigInteger runId, BatchStageThread.CounterRef counter, BatchStageThread.BooleanRef error, Authentication authentication) {
        this.counter = counter;
        this.error = error;
        this.runId = runId;
        this.dataServiceAccessor = dataServiceAccessor;
        this.authentication = authentication;
    }

    @Override
    public void run() {
        if (!error.value) {
            try {
                dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE, "Added " + counter.count + " runs to the db for runId: "
                        + runId.toString(), authentication);
            } catch (RunManagementException e) {
                e.printStackTrace();
            }

        }
    }
}
