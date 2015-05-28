package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import java.math.BigInteger;

import edu.pitt.apollo.runmanagerservice.serviceaccessors.VisualizerServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

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

		try {
			String url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
		}

		VisualizerServiceAccessor visualizerServiceAccessor = new VisualizerServiceAccessor();
		//VisualizerServiceConnector visualizerServiceConnector = new RestVisualizerServiceConnector(url);

		RunResult result = visualizerServiceAccessor.run(runId);

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}

	}
}
