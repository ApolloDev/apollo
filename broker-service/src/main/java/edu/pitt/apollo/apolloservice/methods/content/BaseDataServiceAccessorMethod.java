
package edu.pitt.apollo.apolloservice.methods.content;

import edu.pitt.apollo.apolloservice.methods.BaseMethod;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.restdataserviceconnector.RestDataServiceConnector;

/**
 *
 * @author nem41
 */
public abstract class BaseDataServiceAccessorMethod extends BaseMethod {
	
	protected DataServiceConnector connector;
	
	public BaseDataServiceAccessorMethod(String dataServiceUrl) {
		super(dataServiceUrl);
		 connector = new RestDataServiceConnector(dataServiceUrl);
	}
	
}
