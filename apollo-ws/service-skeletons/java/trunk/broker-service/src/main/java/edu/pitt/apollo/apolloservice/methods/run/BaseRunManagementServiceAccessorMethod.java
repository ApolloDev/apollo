package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.methods.BaseMethod;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;

/**
 *
 * @author nem41
 */
public abstract class BaseRunManagementServiceAccessorMethod extends BaseMethod {

	protected String runManagerServiceUrl;
	RunManagerServiceConnector connector;

	public BaseRunManagementServiceAccessorMethod(String runManagerServiceUrl) {
		super(runManagerServiceUrl);
		this.runManagerServiceUrl = runManagerServiceUrl;
		connector = new RestRunManagerServiceConnector(runManagerServiceUrl);
	}

	protected BaseRunManagementServiceAccessorMethod(RunManagerServiceConnector connector) {
		super(null);
		this.connector = connector;
	}

}
