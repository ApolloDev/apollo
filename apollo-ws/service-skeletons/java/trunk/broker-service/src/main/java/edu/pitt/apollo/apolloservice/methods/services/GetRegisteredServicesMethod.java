package edu.pitt.apollo.apolloservice.methods.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.types.v2_1_0.ServiceRecord;

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
        try {
            ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
            return new ArrayList<ServiceRecord>(dbUtils.getRegisteredSoftware().values());
        } catch (SQLException ex) {
            logger.error("SQLException attempting to get registered services: "
                    + ex.getMessage());
        } catch (ClassNotFoundException ex) {
           logger.error("ClassNotFoundException attempting to get registered services: "
                    + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Exception attempting to get registered services: "
                    + ex.getMessage());
        }

        return null;
    }
}
