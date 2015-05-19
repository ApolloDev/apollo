package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.types.ReturnObjectForRun;

/**
 * Created by jdl50 on 5/11/15.
 */
public interface RunMethod {
    ReturnObjectForRun stageAndRun();

    RunResultAndSimulationGroupId stage();
}
