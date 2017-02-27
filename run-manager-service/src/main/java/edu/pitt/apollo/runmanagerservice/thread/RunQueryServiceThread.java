package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.runmanagerservice.utils.ApolloServiceErrorHandler;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 4, 2014 Time: 10:23:41 AM Class: RunVisualizationThread IDE: NetBeans 6.9.1
 */
public class RunQueryServiceThread extends RunApolloServiceThread {
	
	public RunQueryServiceThread(BigInteger runId, Authentication authentication) {
		super(runId, DatastoreAccessorFactory.queryServiceSoftwareId, authentication);
	}

	@Override
	public void run() {

		DatastoreAccessor dataServiceAccessor;

		try {
			dataServiceAccessor = new DatastoreAccessor();
		} catch (DatastoreException ex) {
			ApolloServiceErrorHandler.reportError("Error creating data store accessor, error was:" + ex.getMessage(), runId, authentication);
			return;
		}
		
		String url;
		try {
			url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DatastoreException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId, authentication);
			return;
		}

		try {
			dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.CALLED_QUERY_SERVICE,
					"Attempting to call query service", authentication);
			JobRunningServiceAccessor queryServiceAccessor = new JobRunningServiceAccessor(url, softwareId);
			queryServiceAccessor.run(runId, authentication);
		} catch (JobRunningServiceException | RunManagementException ex) {
			ApolloServiceErrorHandler.reportError("Error running query service, error was:" + ex.getMessage(), runId, authentication);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (RunManagementException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId, authentication);
		}

	}
}
