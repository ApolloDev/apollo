
package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;

/**
 *
 * @author nem41
 */
public abstract class RunManagerServiceConnector extends ServiceConnector implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface, JobRunningServiceInterface {

	public RunManagerServiceConnector(String url) {
		super(url);
	}
	
}
