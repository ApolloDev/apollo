package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jul 30, 2014 Time:
 * 3:46:22 PM Class: DatabaseAccessorFactory
 */
public class DatabaseAccessorFactory {

	public static DatabaseAccessor getDatabaseAccessor(Object message, Authentication authentication) throws UnrecognizedMessageTypeException {


		if (message instanceof RunSimulationMessage) {
			((RunSimulationMessage) message).getAuthentication()
					.setRequesterPassword("");
			return new DatabaseAccessorForRunningASingleSimulation(
					(RunSimulationMessage) message, authentication);
		} else if (message instanceof RunSimulationsMessage) {
			((RunSimulationsMessage) message).getAuthentication()
					.setRequesterPassword("");
			return new DatabaseAccessorForRunningMultipleSimulations(
					(RunSimulationsMessage) message, authentication);
		} else if (message instanceof RunVisualizationMessage) {
			((RunVisualizationMessage) message).getAuthentication()
					.setRequesterPassword("");
			return new DatabaseAccessorForRunningVisualizations(
					(RunVisualizationMessage) message, authentication);
		} else if (message instanceof GetOutputFilesURLsMessage) {
			return new DatabaseAccessorForRunningDataService((GetOutputFilesURLsMessage) message, authentication);
		} else if (message instanceof GetOutputFilesURLAsZipMessage) {
			return new DatabaseAccessorForRunningDataService((GetOutputFilesURLAsZipMessage) message, authentication);
		} else {
			throw new UnrecognizedMessageTypeException(
					"Unrecognized message type in DatabaseAccessorFactory");
		}

	}

}
