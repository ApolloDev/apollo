package edu.pitt.apollo.apolloservice.methods.services;

import edu.pitt.apollo.apolloservice.methods.content.BaseDataServiceAccessorMethod;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRecord;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 1:54:22 PM Class: GetRegisteredServicesMethod IDE: NetBeans 6.9.1
 */
public class GetRegisteredServicesMethod extends BaseDataServiceAccessorMethod {

	static Logger logger = LoggerFactory.getLogger(GetRegisteredServicesMethod.class);


	public GetRegisteredServicesMethod(String dataServiceUrl) {
		super(dataServiceUrl);
	}

	public List<ServiceRecord> getRegisteredServices(Authentication authentication) {

		List<ServiceRecord> serviceRecords = new ArrayList<>();
		try {
			Map<Integer, ServiceRegistrationRecord> serviceRegistrationRecords = connector.getListOfRegisteredSoftwareRecords(authentication);

			for (ServiceRegistrationRecord record : serviceRegistrationRecords.values()) {
				ServiceRecord serviceRecord = new ServiceRecord();
				serviceRecord.setSoftwareIdentification(record.getSoftwareIdentification());
				serviceRecord.setUrl(record.getUrl());
				serviceRecords.add(serviceRecord);
			}
		} catch (DataServiceException ex) {
			logger.error("Exception attempting to get registered services: "
					+ ex.getMessage());
		}

		return serviceRecords;

	}
}
