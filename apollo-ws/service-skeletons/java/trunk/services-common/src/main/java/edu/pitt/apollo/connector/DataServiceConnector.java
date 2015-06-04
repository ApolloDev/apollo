package edu.pitt.apollo.connector;


import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.UserManagementInterface;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public abstract class DataServiceConnector extends ServiceConnector implements SoftwareRegistryInterface, 
		RunManagementInterface, ContentManagementInterface, UserManagementInterface, JobRunningServiceInterface {
	
	public DataServiceConnector(String url) {
		super(url);
	}
}
