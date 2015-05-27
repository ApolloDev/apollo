package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.SimulatorServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;

import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public abstract class SimulatorServiceConnector implements SimulatorServiceInterface{
	
	protected final String serviceUrl;
	
	public SimulatorServiceConnector(String url) {
		this.serviceUrl = url;
	}
	

}
