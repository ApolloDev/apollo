package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.TranslatorServiceConnector;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.interfaces.TranslatorServiceInterface;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class TranslatorServiceAccessor extends ServiceAccessor implements TranslatorServiceInterface {

	private TranslatorServiceConnector connector;

	public TranslatorServiceAccessor(String url) {
		super(url);
	}

	@Override
	public void translateRun(BigInteger runId) throws TranslatorServiceException {
		connector.translateRun(runId);
	}

}
