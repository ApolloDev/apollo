package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.VisualizerServiceConnector;
import edu.pitt.apollo.connector.rest.RestDataServiceConnector;
import edu.pitt.apollo.connector.rest.RestVisualizerServiceConnector;
import java.math.BigInteger;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 4, 2014 Time: 10:23:41 AM Class: RunVisualizationThread IDE: NetBeans 6.9.1
 */
public class RunVisualizationThread extends RunApolloServiceThread {

	private final RunVisualizationMessage message;

	public RunVisualizationThread(RunVisualizationMessage message, BigInteger runId) {
		super(runId);
		this.message = message;
	}

	@Override
	public void run() {

		DataServiceConnector dataServiceConnector = new RestDataServiceConnector();

		SoftwareIdentification visualizerIdentification = message.getVisualizerIdentification();
		String url = dataServiceConnector.getURLForSoftwareId(visualizerIdentification);

		VisualizerServiceConnector visualizerServiceConnector = new RestVisualizerServiceConnector(url);

		RunResult result = visualizerServiceConnector.run(runId);

		dataServiceConnector.updateLastServiceToBeCalledForRun(runId, visualizerIdentification);

	}
}
