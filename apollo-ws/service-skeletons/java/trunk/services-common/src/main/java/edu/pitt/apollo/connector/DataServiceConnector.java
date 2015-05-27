package edu.pitt.apollo.connector;


/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public abstract class DataServiceConnector implements DataServiceInterface {

	protected final String serviceUrl;
	
	public DataServiceConnector(String url) {
		this.serviceUrl = url;
	}
}
