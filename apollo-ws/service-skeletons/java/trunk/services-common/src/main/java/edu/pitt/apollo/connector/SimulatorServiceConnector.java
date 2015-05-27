package edu.pitt.apollo.connector;

import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public abstract class SimulatorServiceConnector {
	
	protected final String serviceUrl;
	
	public SimulatorServiceConnector(String url) {
		this.serviceUrl = url;
	}
	
	public abstract RunResult run(BigInteger runId); 
	
}
