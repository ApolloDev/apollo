package edu.pitt.apollo.connector;

/**
 *
 * @author nem41
 */
public abstract class ServiceConnector {

	protected final String serviceUrl;

	public ServiceConnector(String url) {
		this.serviceUrl = url;
	}

}
