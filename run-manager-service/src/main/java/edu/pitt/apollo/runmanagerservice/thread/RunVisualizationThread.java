package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.VisulizerServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import java.math.BigInteger;

import edu.pitt.apollo.runmanagerservice.serviceaccessors.VisualizerServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.SoftwareIdentification;

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
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
			return;
		}

		try {
			VisualizerServiceAccessor visualizerServiceAccessor = new VisualizerServiceAccessor(url);
			visualizerServiceAccessor.run(runId);
		} catch (VisulizerServiceException ex) {
			ApolloServiceErrorHandler.reportError("Error running visualizer, error was:" + ex.getMessage(), runId);
			return;
		}

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}

	}
}
