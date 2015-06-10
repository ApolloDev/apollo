package edu.pitt.apollo.connector;

import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.interfaces.TranslatorServiceInterface;

/**
 *
 * @author nem41
 */
public abstract class TranslatorServiceConnector extends ServiceConnector implements TranslatorServiceInterface {

	public TranslatorServiceConnector(String url) throws TranslatorServiceException {
		super(url);
	}
}
