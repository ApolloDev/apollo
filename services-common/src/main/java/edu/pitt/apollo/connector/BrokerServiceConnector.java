package edu.pitt.apollo.connector;

import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;

/**
 * Created by nem41 on 8/17/15.
 */
public abstract class BrokerServiceConnector extends ServiceConnector implements ContentManagementInterface, RunManagementInterface, JobRunningServiceInterface, SoftwareRegistryInterface {

    public BrokerServiceConnector(String url) {
        super(url);
    }
}
