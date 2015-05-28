package edu.pitt.apollo.connector.rest;

import edu.pitt.apollo.connector.VisualizerServiceConnector;
import edu.pitt.apollo.exception.VisulizerServiceException;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class RestVisualizerServiceConnector extends VisualizerServiceConnector {

	public RestVisualizerServiceConnector(String url) {
		super(url);
	}

	@Override
	public void run(BigInteger runId) throws VisulizerServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
