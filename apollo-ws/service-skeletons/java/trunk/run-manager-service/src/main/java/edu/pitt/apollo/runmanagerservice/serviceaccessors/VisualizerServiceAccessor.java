package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.exception.VisulizerServiceException;
import edu.pitt.apollo.interfaces.VisualizerServiceInterface;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class VisualizerServiceAccessor extends ServiceAccessor implements VisualizerServiceInterface {

	public VisualizerServiceAccessor(String url) {
		super(url);
	}
	
	@Override
	public void run(BigInteger runId) throws VisulizerServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


 
}
