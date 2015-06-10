package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.VisulizerServiceException;
import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public interface VisualizerServiceInterface {

	public void run(BigInteger runId) throws VisulizerServiceException;
}
