package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.VisualizerServiceConnector;
import edu.pitt.apollo.exception.VisulizerServiceException;
import edu.pitt.apollo.interfaces.VisualizerServiceInterface;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class VisualizerServiceAccessor extends ServiceAccessor implements VisualizerServiceInterface {

	private VisualizerServiceConnector connector;

	public VisualizerServiceAccessor(String url) {
		super(url);
	}

	@Override
	public void run(BigInteger runId) throws VisulizerServiceException {
		connector.run(runId);
	}

}
