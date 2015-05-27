package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.connector.VisualizerServiceConnector;
import edu.pitt.apollo.connector.rest.RestVisualizerServiceConnector;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.VisualizerServiceInterface;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorForRunningVisualizations;
import java.math.BigInteger;

import edu.pitt.apollo.runmanagerservice.serviceaccessors.VisualizerServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 4, 2014 Time: 10:23:41 AM Class: RunVisualizationThread IDE: NetBeans 6.9.1
 */
public class RunVisualizationThread extends RunApolloServiceThread {

	private final RunVisualizationMessage message;

	public RunVisualizationThread(RunVisualizationMessage message, BigInteger runId, Authentication authentication) {
		super(runId, authentication);
		this.message = message;
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessorForRunningVisualizations(message);

		SoftwareIdentification visualizerIdentification = message.getVisualizerIdentification();
		try {
			String url = dataServiceAccessor.getURLForSoftwareIdentification(visualizerIdentification, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
		}

		VisualizerServiceAccessor visualizerServiceAccessor = new VisualizerServiceAccessor();
		//VisualizerServiceConnector visualizerServiceConnector = new RestVisualizerServiceConnector(url);

		RunResult result = visualizerServiceAccessor.run(runId);

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, visualizerIdentification, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}

	}
}
