package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.DataServiceException;

import edu.pitt.apollo.services_common.v4_0.*;
import java.util.List;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public interface SoftwareRegistryInterface {

	public List<ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException;

}
