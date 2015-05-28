package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessorFactory {

	static public DataServiceAccessor getDataServiceAccessor(Object message) throws UnrecognizedMessageTypeException {

		if (message instanceof RunSimulationMessage) {
			return new DataServiceAccessor();
		} else if (message instanceof RunVisualizationMessage) {
			return new DataServiceAccessor();
		} else if (message instanceof GetAllOutputFilesURLAsZipMessage) {
			return new DataServiceAccessor();
		} else if (message instanceof GetOutputFilesURLAsZipMessage) {
			return new DataServiceAccessor();
		} else if (message instanceof GetOutputFilesURLsMessage) {
			return new DataServiceAccessor();
		} else if (message instanceof RunSimulationsMessage) {
			return new DataServiceAccessor();
		} else {
			throw new UnrecognizedMessageTypeException("Unrecognized message type creating data service accessor");
		}

	}
}
