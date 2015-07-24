package edu.pitt.apollo.runmanagerservice.thread;

import java.math.BigInteger;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunSimulationThread extends RunApolloServiceThread {

	public RunSimulationThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
		super(runId, softwareId, authentication);
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();

		String url;
		try {
			url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
			return;
		}

		try {
			JobRunningServiceAccessor simulatorServiceAccessor = new JobRunningServiceAccessor(url);
			simulatorServiceAccessor.run(runId, authentication);
		} catch (JobRunningServiceException ex) {
			ApolloServiceErrorHandler.reportError("Error calling simulator, error was:" + ex.getMessage(), runId);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}
	}
}
