package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public interface SimulatorServiceInterface {

    public RunResult run(BigInteger runId);
    public RunResult runSimulations(BigInteger runId);
    public TerminteRunResult terminate(TerminateRunRequest terminateRunRequest);
}
