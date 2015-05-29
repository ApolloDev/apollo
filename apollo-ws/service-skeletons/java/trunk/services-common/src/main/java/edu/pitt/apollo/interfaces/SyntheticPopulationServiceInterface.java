package edu.pitt.apollo.interfaces;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/29/15.
 */
public interface SyntheticPopulationServiceInterface {

    public void killRun(BigInteger runId);

    public java.math.BigInteger runSyntheticPopulationGeneration(edu.pitt.apollo.synthetic_population_service_types.v3_0_0.RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage);
}
