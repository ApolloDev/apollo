package edu.pitt.apollo.connector;


import edu.pitt.apollo.interfaces.DataServiceInterface;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public abstract class DataServiceConnector extends ServiceConnector implements DataServiceInterface {
	
	public DataServiceConnector(String url) {
		super(url);
	}
}
