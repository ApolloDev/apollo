
package edu.pitt.apollo.connector;

import edu.pitt.apollo.exception.SyntheticPopulationServiceException;
import edu.pitt.apollo.interfaces.SyntheticPopulationServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class SyntheticPopulationServiceConnector extends ServiceConnector implements SyntheticPopulationServiceInterface{

	public SyntheticPopulationServiceConnector(String url) throws SyntheticPopulationServiceException {
		super(url);
	}
	
}
