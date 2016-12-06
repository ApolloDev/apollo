package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.runmanagerservice.types.ReturnObjectForRun;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/11/15.
 */
public interface RunMethod {
    public ReturnObjectForRun run(BigInteger runId);
}
