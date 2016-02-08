package edu.pitt.apollo.runmanagerservice.thread;


import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.runmanagerservice.methods.stage.BatchStageMethod;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;

import java.math.BigInteger;
import java.util.TimerTask;

/**
 * Created by jdl50 on 4/15/15.
 */
public class StatusUpdaterThread extends TimerTask {

    private final BatchStageMethod.CounterRef counter;
    private final BatchStageMethod.BooleanRef error;
    private final BigInteger runId;
    private final Authentication authentication;
    DatastoreAccessImpl dataServiceAccessor;

    public StatusUpdaterThread(DatastoreAccessImpl dataServiceAccessor, BigInteger runId, BatchStageMethod.CounterRef counter, BatchStageMethod.BooleanRef error, Authentication authentication) {
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
            } catch (DataServiceException e) {
                e.printStackTrace();
            }

        }
    }
}
