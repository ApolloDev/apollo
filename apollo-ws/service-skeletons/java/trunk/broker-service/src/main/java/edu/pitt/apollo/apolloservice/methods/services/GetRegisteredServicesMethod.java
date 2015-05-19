package edu.pitt.apollo.apolloservice.methods.services;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 1:54:22 PM
 * Class: GetRegisteredServicesMethod
 * IDE: NetBeans 6.9.1
 */
public class GetRegisteredServicesMethod {
	
	static Logger logger = LoggerFactory.getLogger(GetRegisteredServicesMethod.class);

    public static List<ServiceRecord> getRegisteredServices() {

        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            return new ArrayList<ServiceRecord>(dbUtils.getRegisteredSoftware().values());
        } catch (Exception ex) {
            logger.error("Exception attempting to get registered services: "
                    + ex.getMessage());
        }

        return null;
    }
}
