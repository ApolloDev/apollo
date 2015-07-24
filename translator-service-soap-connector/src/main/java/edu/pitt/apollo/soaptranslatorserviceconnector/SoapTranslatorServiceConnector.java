package edu.pitt.apollo.soaptranslatorserviceconnector;

import edu.pitt.apollo.connector.TranslatorServiceConnector;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.service.translatorservice.v3_0_2.TranslatorServiceEI;
import edu.pitt.apollo.service.translatorservice.v3_0_2.TranslatorServiceV300;
import java.math.BigInteger;
import java.net.URL;

/**
 *
 * @author nem41
 */
public class SoapTranslatorServiceConnector extends TranslatorServiceConnector {

	private TranslatorServiceEI port;

	public SoapTranslatorServiceConnector(String url) throws TranslatorServiceException {
		super(url);
		initialize();
	}

	private void initialize() throws TranslatorServiceException {
		try {
			port = new TranslatorServiceV300(new URL(serviceUrl)).getTranslatorServiceEndpoint();
		} catch (Exception ex) {
			throw new TranslatorServiceException("Exception getting translator service endpoint: " + ex.getMessage());
		}
	}

	@Override
	public void translateRun(BigInteger runId) throws TranslatorServiceException {
		port.translateRun(runId);
	}

}
