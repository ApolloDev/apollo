
package edu.pitt.apollo.connector.rest;

import edu.pitt.apollo.connector.SimulatorServiceConnector;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class RestSimulatorServiceConnector extends SimulatorServiceConnector {

	public RestSimulatorServiceConnector(String url) {
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
