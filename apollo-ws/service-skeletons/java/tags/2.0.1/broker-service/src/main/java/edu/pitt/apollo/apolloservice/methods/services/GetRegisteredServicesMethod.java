package edu.pitt.apollo.apolloservice.methods.services;

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.types.v2_0_1.ServiceRecord;
import java.sql.SQLException;
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

    public static List<ServiceRecord> getRegisteredServices() {
        try {
            ApolloDbUtils dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
            return new ArrayList<ServiceRecord>(dbUtils.getRegisteredSoftware().values());
        } catch (SQLException ex) {
            System.out.println("SQLException attempting to get registered services: "
                    + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException attempting to get registered services: "
                    + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception attempting to get registered services: "
                    + ex.getMessage());
        }

        return null;
    }
}
