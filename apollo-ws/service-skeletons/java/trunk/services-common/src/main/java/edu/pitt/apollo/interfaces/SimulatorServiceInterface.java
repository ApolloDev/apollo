package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public interface SimulatorServiceInterface {

	public void run(BigInteger runId) throws SimulatorServiceException;

	public void runSimulations(BigInteger runId) throws SimulatorServiceException;

	public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException;
}
