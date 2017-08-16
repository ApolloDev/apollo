package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.SyntheticPopulationServiceException;
import edu.pitt.apollo.synthetic_population_service_types.v4_0_2.RunSyntheticPopulationGenerationMessage;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/29/15.
 */
public interface SyntheticPopulationServiceInterface {

    public void killRun(BigInteger runId) throws SyntheticPopulationServiceException;

    public BigInteger runSyntheticPopulationGeneration(RunSyntheticPopulationGenerationMessage runSyntheticPopulationGenerationMessage) throws SyntheticPopulationServiceException;
}
