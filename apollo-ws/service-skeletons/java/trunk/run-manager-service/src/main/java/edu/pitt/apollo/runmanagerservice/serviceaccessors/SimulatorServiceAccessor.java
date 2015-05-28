package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.SimulatorServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class SimulatorServiceAccessor extends ServiceAccessor implements SimulatorServiceInterface {

	public SimulatorServiceAccessor(String url) {
		super(url);
	}

	@Override
	public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void run(BigInteger runId) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void runSimulations(BigInteger runId) throws SimulatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
