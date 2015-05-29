package edu.pitt.apollo.connector.rest;

import edu.pitt.apollo.connector.TranslatorServiceConnector;
import edu.pitt.apollo.exception.TranslatorServiceException;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class RestTranslatorServiceConnector extends TranslatorServiceConnector {

	public RestTranslatorServiceConnector(String url) {
		super(url);
	}

	@Override
	public void translateRun(BigInteger runId) throws TranslatorServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
