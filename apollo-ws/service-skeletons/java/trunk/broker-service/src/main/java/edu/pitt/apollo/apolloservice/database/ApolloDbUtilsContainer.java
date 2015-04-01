package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.db.ApolloDbUtils;
import java.io.File;
import java.io.IOException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 8, 2014
 * Time: 12:01:27 PM
 * Class: ApolloDbUtilsContainer
 * IDE: NetBeans 6.9.1
 */
public class ApolloDbUtilsContainer {

    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static final ApolloDbUtils DB_UTILS;

    static {

            //DB_UTILS = new ApolloDbUtils(new File(ApolloServiceConstants.APOLLO_DIR + DATABASE_PROPERTIES_FILENAME));
            DB_UTILS = new ApolloDbUtils();

    }
    
    public static ApolloDbUtils getApolloDbUtils() {
        return DB_UTILS;
    }
}
