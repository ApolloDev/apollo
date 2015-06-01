package edu.pitt.apollo.connector;

import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.SimulatorServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class SimulatorServiceConnector extends ServiceConnector implements SimulatorServiceInterface {
	
	
	public SimulatorServiceConnector(String url) throws SimulatorServiceException {
		super(url);
	}
}
