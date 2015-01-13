package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jul 30, 2014
 * Time: 3:46:22 PM
 * Class: DatabaseAccessorFactory
 */
public class DatabaseAccessorFactory {
	
	public static DatabaseAccessor getDatabaseAccessor(Object message, Authentication authentication) throws UnrecognizedMessageTypeException {
		
		if (message instanceof RunSimulationMessage) {
			return new DatabaseAccessorForRunningSimulations((RunSimulationMessage) message, authentication);
		} else if (message instanceof RunVisualizationMessage) {
			return new DatabaseAccessorForRunningVisualizations((RunVisualizationMessage) message, authentication);
		} else {
			throw new UnrecognizedMessageTypeException("Unrecognized message type in DatabaseAccessorFactory");
		}
		
	}
	
}
