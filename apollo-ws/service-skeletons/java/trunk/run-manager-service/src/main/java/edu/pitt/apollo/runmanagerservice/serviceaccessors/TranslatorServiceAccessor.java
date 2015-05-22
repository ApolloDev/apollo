package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.TranslatorServiceConnector;
import edu.pitt.apollo.connector.rest.RestTranslatorServiceConnector;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class TranslatorServiceAccessor implements TranslatorServiceAccessorInterface {
	
	protected TranslatorServiceConnector connector = new RestTranslatorServiceConnector();
	
	@Override
	public void translateRun(BigInteger runId) {
		connector.translate(runId);
	}
	
}
