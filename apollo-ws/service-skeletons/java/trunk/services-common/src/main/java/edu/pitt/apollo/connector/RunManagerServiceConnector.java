
package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;

/**
 *
 * @author nem41
 */
public abstract class RunManagerServiceConnector extends ServiceConnector implements RunManagementInterface, JobRunningServiceInterface {

	public RunManagerServiceConnector(String url) {
		super(url);
	}
	
}
