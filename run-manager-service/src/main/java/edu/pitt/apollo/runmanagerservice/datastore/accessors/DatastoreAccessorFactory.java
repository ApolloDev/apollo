package edu.pitt.apollo.runmanagerservice.datastore.accessors;

import edu.pitt.apollo.apollo_service_types.v4_0_1.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.query_service_types.v4_0_1.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0_1.RunMessage;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v4_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v4_0_1.RunVisualizationMessage;

public class DatastoreAccessorFactory {

    public static final SoftwareIdentification queryServiceSoftwareId;

    static {
        queryServiceSoftwareId = new SoftwareIdentification();
        queryServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.QUERY_SERVICE);
        queryServiceSoftwareId.setSoftwareName("Query Service");
        queryServiceSoftwareId.setSoftwareVersion("4.0");
        queryServiceSoftwareId.setSoftwareDeveloper("UPitt");
    }

	public static DatastoreAccessor getDatabaseAccessor() throws UnrecognizedMessageTypeException, ApolloDatabaseException, DatastoreException {
		return new DatastoreAccessor();
	}

	public static DatastoreAccessor getDatabaseAccessor(RunMessage message) throws UnrecognizedMessageTypeException, ApolloDatabaseException, DatastoreException {
		if (message instanceof RunSimulationMessage) {
			return new DatastoreAccessorForRunningJobs(((RunSimulationMessage) message).getSoftwareIdentification());
		} else if (message instanceof RunSimulationsMessage) {
			return new DatastoreAccessorForRunningJobs(((RunSimulationsMessage) message).getSoftwareIdentification());
		} else if (message instanceof RunVisualizationMessage) {
			return new DatastoreAccessorForRunningJobs(((RunVisualizationMessage) message).getSoftwareIdentification());
//		} else if (message instanceof DataRetrievalRequestMessage) {
//			Authentication authentication = message.getAuthentication();
//			return new DatastoreAccessorForRunningDataRetrievalJob((DataRetrievalRequestMessage) message, authentication);
		} else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
            return new DatastoreAccessorForRunningJobs(null);
        } else if (message instanceof RunSimulatorOutputQueryMessage) {
            return new DatastoreAccessorForRunningJobs(queryServiceSoftwareId);
        }
        else {
			throw new UnrecognizedMessageTypeException(
					"Unrecognized message type in DatabaseAccessorFactory");
		}

	}

}
