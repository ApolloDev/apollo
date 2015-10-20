package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.services_common.v3_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v3_1_0.SoftwareIdentification;
import edu.pitt.apollo.soapjobrunningserviceconnector.SoapJobRunningServiceConnector;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 4, 2014 Time: 10:23:41 AM Class: RunVisualizationThread IDE: NetBeans 6.9.1
 */
public class RunVisualizationThread extends RunApolloServiceThread {

	public RunVisualizationThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
		super(runId, softwareId, authentication);
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		String url;
		try {
			url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId, authentication);
			return;
		}

		try {
            dataServiceAccessor.updateStatusOfRun(runId, MethodCallStatusEnum.CALLED_VISUALIZER,
                    "Attempting to call visualizer", authentication);
			JobRunningServiceAccessor visualizerServiceAccessor = new JobRunningServiceAccessor(url, softwareId);
			visualizerServiceAccessor.run(runId, authentication);
		} catch (JobRunningServiceException | RunManagementException ex) {
			ApolloServiceErrorHandler.reportError("Error running visualizer, error was:" + ex.getMessage(), runId, authentication);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId, authentication);
		}

	}
}
