package edu.pitt.apollo.connector;

import edu.pitt.apollo.exception.VisulizerServiceException;
import edu.pitt.apollo.interfaces.VisualizerServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class VisualizerServiceConnector extends ServiceConnector implements VisualizerServiceInterface{

	public VisualizerServiceConnector(String url) throws VisulizerServiceException {
		super(url);
	}
	
}
