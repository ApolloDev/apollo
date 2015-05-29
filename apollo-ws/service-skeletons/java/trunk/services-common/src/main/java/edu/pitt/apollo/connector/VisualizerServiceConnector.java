package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.VisualizerServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class VisualizerServiceConnector extends ServiceConnector implements VisualizerServiceInterface{

	public VisualizerServiceConnector(String url) {
		super(url);
	}
	
}
