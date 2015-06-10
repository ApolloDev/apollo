package edu.pitt.apollo.dataservice.accessors;


import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

public class DatabaseAccessorFactory {

    public static DatabaseAccessor getDatabaseAccessor(Authentication authentication) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
        return new DatabaseAccessor(authentication);
    }

    public static DatabaseAccessor getDatabaseAccessor(RunMessage message) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
        if (message instanceof RunSimulationMessage) {
            return new DatabaseAccessorForRunningJobs(
                    message.getAuthentication(),
                    "run_simulation_message.json",
                    ContentDataTypeEnum.RUN_SIMULATION_MESSAGE, message.getSoftwareIdentification());
        } else if (message instanceof RunSimulationsMessage) {
            return new DatabaseAccessorForRunningJobs(
                    message.getAuthentication(),
                    "run_simulations_message.json",
                    ContentDataTypeEnum.RUN_SIMULATIONS_MESSAGE,
                    message.getSoftwareIdentification());
        } else if (message instanceof RunVisualizationMessage) {
            return new DatabaseAccessorForRunningJobs(
                    message.getAuthentication(),
                    "run_visualization_message.json",
                    ContentDataTypeEnum.RUN_VISUALIZATION_MESSAGE,
                    message.getSoftwareIdentification());
        } else if (message instanceof DataRetrievalRequestMessage) {
            Authentication authentication = message.getAuthentication();
            return new DatabaseAccessorForRunningDataService((DataRetrievalRequestMessage) message, authentication);
        } else {
            throw new UnrecognizedMessageTypeException(
                    "Unrecognized message type in DatabaseAccessorFactory");
        }

	}

}