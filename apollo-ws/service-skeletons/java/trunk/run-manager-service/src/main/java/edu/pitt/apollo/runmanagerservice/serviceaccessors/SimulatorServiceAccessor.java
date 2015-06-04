package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class SimulatorServiceAccessor extends ServiceAccessor implements JobRunningServiceInterface {

	private JobRunningServiceConnector connector;
	
	public SimulatorServiceAccessor(String url) {
		super(url);
	}

	@Override
	public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException {
		connector.terminate(terminateRunRequest);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		connector.run(runId, authentication);
	}

}
