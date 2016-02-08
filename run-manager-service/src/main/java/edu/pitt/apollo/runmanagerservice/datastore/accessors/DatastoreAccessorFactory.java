package edu.pitt.apollo.runmanagerservice.datastore.accessors;

import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;

public class DatastoreAccessorFactory {

	public static DatastoreAccessor getDatabaseAccessor(Authentication authentication) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
		return new DatastoreAccessor(authentication);
	}

	public static DatastoreAccessor getDatabaseAccessor(RunMessage message) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
		if (message instanceof RunSimulationMessage) {
			return new DatastoreAccessorForRunningJobs(
					message.getAuthentication(), ((RunSimulationMessage) message).getSoftwareIdentification());
		} else if (message instanceof RunSimulationsMessage) {
			return new DatastoreAccessorForRunningJobs(
					message.getAuthentication(), ((RunSimulationsMessage) message).getSoftwareIdentification());
		} else if (message instanceof RunVisualizationMessage) {
			return new DatastoreAccessorForRunningJobs(
					message.getAuthentication(), ((RunVisualizationMessage) message).getSoftwareIdentification());
		} else if (message instanceof DataRetrievalRequestMessage) {
			Authentication authentication = message.getAuthentication();
			return new DatastoreAccessorForRunningDataRetrievalJob((DataRetrievalRequestMessage) message, authentication);
		} else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
            return new DatastoreAccessorForRunningJobs(
                    message.getAuthentication(), null
            );
        }
        else {
			throw new UnrecognizedMessageTypeException(
					"Unrecognized message type in DatabaseAccessorFactory");
		}

	}

}
