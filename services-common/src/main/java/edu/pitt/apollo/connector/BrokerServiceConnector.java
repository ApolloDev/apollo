package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.*;

/**
 * Created by nem41 on 8/17/15.
 */
public abstract class BrokerServiceConnector extends ServiceConnector implements FilestoreServiceInterface, ContentManagementInterface, RunManagementInterface, JobRunningServiceInterface, SoftwareRegistryInterface {

    public BrokerServiceConnector(String url) {
        super(url);
    }
}
