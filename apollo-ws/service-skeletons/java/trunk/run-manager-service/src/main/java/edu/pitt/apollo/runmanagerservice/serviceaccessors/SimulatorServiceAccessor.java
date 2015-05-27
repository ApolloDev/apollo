package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.interfaces.SimulatorServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class SimulatorServiceAccessor implements SimulatorServiceInterface {
    @Override
    public RunResult run(BigInteger runId) {
        return null;
    }

    @Override
    public RunResult runSimulations(BigInteger runId) {
        return null;
    }

    @Override
    public TerminteRunResult terminate(TerminateRunRequest terminateRunRequest) {
        return null;
    }
}
