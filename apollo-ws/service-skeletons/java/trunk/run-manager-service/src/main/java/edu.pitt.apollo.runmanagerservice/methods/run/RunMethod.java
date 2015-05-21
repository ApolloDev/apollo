package edu.pitt.apollo.runmanagerservice.methods.run;

/**
 * Created by jdl50 on 5/11/15.
 */
public interface RunMethod {
    ReturnObjectForRun stageAndRun();

    RunResultAndSimulationGroupId stage();
}
