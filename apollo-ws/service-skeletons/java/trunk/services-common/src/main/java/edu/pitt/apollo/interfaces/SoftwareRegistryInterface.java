package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DataServiceException;
import java.util.Map;

import edu.pitt.apollo.services_common.v3_0_0.*;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public interface SoftwareRegistryInterface {

	public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException;

}
