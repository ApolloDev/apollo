package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.services_common.v3_0_0.RunResult;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public interface VisualizerServiceInterface {
    public RunResult run(BigInteger runId);
}
