package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jul 30, 2014 Time: 3:03:10 PM Class: RunApolloServiceThreadFactory
 */
public class RunApolloServiceThreadFactory {

	public static RunApolloServiceThread getRunApolloServiceThread(Object message, BigInteger runId, Authentication authentication) throws UnrecognizedMessageTypeException {

		SoftwareIdentification softwareId;
		if (message instanceof RunSimulationMessage) {
			softwareId = ((RunSimulationMessage) message).getSimulatorIdentification();
			return new RunSimulationThread(runId, softwareId, authentication);
		} else if (message instanceof RunSimulationsMessage) {
			softwareId = ((RunSimulationsMessage) message).getSimulatorIdentification();
			return null;
			//return new RunSimulationsThread((RunSimulationsMessage) message, softwareId, runId, authentication);
		} else if (message instanceof RunVisualizationMessage) {
			softwareId = ((RunVisualizationMessage) message).getVisualizerIdentification();
			return new RunVisualizationThread(runId, softwareId, authentication);
//		} else if (message instanceof GetOutputFilesURLsMessage) {
//			softwareId = ((GetOutputFilesURLsMessage) message).getSoftwareIdentification();
//			return new RunDataServiceThread(runId, softwareId, authentication);
		} else if (message instanceof DataRetrievalRequestMessage) {
			softwareId = ((DataRetrievalRequestMessage) message).getSoftwareIdentification();
			return new RunDataServiceThread(runId, softwareId, authentication);
//		} else if (message instanceof GetAllOutputFilesURLAsZipMessage) {
//			softwareId = ((GetAllOutputFilesURLAsZipMessage) message).getSoftwareIdentification();
//			return new RunDataServiceThread(runId, softwareId, authentication);
		} else {
			throw new UnrecognizedMessageTypeException("Unrecognized message type in RunApolloServiceThreadFactory");
		}

	}

}
