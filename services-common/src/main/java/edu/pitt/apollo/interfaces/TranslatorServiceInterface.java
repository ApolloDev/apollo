package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.TranslatorServiceException;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public interface TranslatorServiceInterface {
	
	public void translateRun(BigInteger runId) throws TranslatorServiceException;
	
}
