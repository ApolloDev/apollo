package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.interfaces.TranslatorServiceInterface;
import java.math.BigInteger;

/**
 *
 * @author nem41
 */
public class TranslatorServiceAccessor extends ServiceAccessor implements TranslatorServiceInterface {

	public TranslatorServiceAccessor(String url) {
		super(url);
	}
	
//	protected TranslatorServiceConnector connector = new RestTranslatorServiceConnector();
	
	@Override
	public void translateRun(BigInteger runId) {
//		connector.translateRun(runId);
	}
	
}
