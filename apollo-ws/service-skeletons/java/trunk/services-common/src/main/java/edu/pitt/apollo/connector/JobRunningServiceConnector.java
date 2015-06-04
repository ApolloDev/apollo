package edu.pitt.apollo.connector;

import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class JobRunningServiceConnector extends ServiceConnector implements JobRunningServiceInterface {
	
	
	public JobRunningServiceConnector(String url) throws SimulatorServiceException {
		super(url);
	}
}
