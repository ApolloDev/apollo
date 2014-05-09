package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDbUtils;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 7, 2014
 * Time: 2:26:02 PM
 * Class: DatabaseAccessor
 * IDE: NetBeans 6.9.1
 */
public class DatabaseAccessor {

    protected ApolloDbUtils dbUtils;

    public DatabaseAccessor() {
        this.dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
    }

    public void removeAllDataAssociatedWithRunId(int runId) throws ApolloDatabaseException {
        dbUtils.removeRunData(runId);
    }
}
