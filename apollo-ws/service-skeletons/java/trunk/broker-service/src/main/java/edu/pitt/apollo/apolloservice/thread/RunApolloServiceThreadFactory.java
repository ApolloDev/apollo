package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.simulator_service_types.v2_1_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v2_1_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 30, 2014
 * Time: 3:03:10 PM
 * Class: RunApolloServiceThreadFactory
 */
public class RunApolloServiceThreadFactory {

	public static RunApolloServiceThread getRunApolloServiceThread(Object message) throws UnrecognizedMessageTypeException {

		if (message instanceof RunSimulationMessage) {
			return new RunSimulationThread((RunSimulationMessage) message);
		} else if (message instanceof RunVisualizationMessage) {
			return new RunVisualizationThread((RunVisualizationMessage) message);
		} else {
			throw new UnrecognizedMessageTypeException("Unrecognized message type in RunApolloServiceThreadFactory");
		}

	}

}
