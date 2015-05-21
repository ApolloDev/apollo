package edu.pitt.apollo.runmanagerservice.thread;

import java.math.BigInteger;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 30, 2014
 * Time: 3:03:10 PM
 * Class: RunApolloServiceThreadFactory
 */
public class RunApolloServiceThreadFactory {

	public static RunApolloServiceThread getRunApolloServiceThread(Authentication authentication, Object message, BigInteger runId, BigInteger simulationGroupId) throws UnrecognizedMessageTypeException, ApolloDatabaseException {

		if (message instanceof RunSimulationMessage) {
			return new RunSimulationThread((RunSimulationMessage) message, runId);
		} else if (message instanceof RunSimulationsMessage) {
			return new RunSimulationsThread(authentication, (RunSimulationsMessage) message, runId, simulationGroupId);
		} else if (message instanceof RunVisualizationMessage) {
			return new RunVisualizationThread((RunVisualizationMessage) message, runId);
		} else if (message instanceof GetOutputFilesURLsMessage) {
			return new RunDataServiceThread((GetOutputFilesURLsMessage) message, runId);
		} else if (message instanceof GetOutputFilesURLAsZipMessage) {
			return new RunDataServiceThread((GetOutputFilesURLAsZipMessage) message, runId);
		} else if (message instanceof GetAllOutputFilesURLAsZipMessage) {
			return new RunDataServiceThread((GetAllOutputFilesURLAsZipMessage) message, runId);
		} else {
			throw new UnrecognizedMessageTypeException("Unrecognized message type in RunApolloServiceThreadFactory");
		}

	}

}
