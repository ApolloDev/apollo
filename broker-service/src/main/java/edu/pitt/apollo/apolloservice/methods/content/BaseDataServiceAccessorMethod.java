
package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.apolloservice.methods.BaseMethod;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.RunManagerServiceConnector;
import edu.pitt.apollo.restrunmanagerserviceconnector.RestRunManagerServiceConnector;

/**
 *
 * @author nem41
 */
public abstract class BaseDataServiceAccessorMethod extends BaseMethod {
	
	protected RunManagerServiceConnector connector;
	
	public BaseDataServiceAccessorMethod(String dataServiceUrl) {
		super(dataServiceUrl);
		 connector = new RestRunManagerServiceConnector(dataServiceUrl);
	}
	
}
