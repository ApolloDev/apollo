package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessImpl;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:25:44 PM Class: RunDatabaseJobThread
 */
public class RunDatabaseJobThread extends RunApolloServiceThread {

	public RunDatabaseJobThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
		super(runId, softwareId, authentication);
	}

	@Override
	public void run() {

		DatastoreAccessImpl dataServiceAccessor = new DatastoreAccessImpl();

		try {
//            JobRunningServiceAccessor jobRunningServiceAccessor = new JobRunningServiceAccessor(DataAccessor.DATA_SERVICE_URL, softwareId);
//            dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.CALLED_DATA_SERVICE,
//                    "Attempting to call data service", authentication);
//			jobRunningServiceAccessor.run(runId, authentication);

            DatastoreAccessImpl dataService = new DatastoreAccessImpl();
            dataService.run(runId, authentication);

		} catch (JobRunningServiceException ex) {
			ApolloServiceErrorHandler.reportError("Error running data service, error was:" + ex.getMessage(), runId, authentication);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId, authentication);
		}
	}

}
