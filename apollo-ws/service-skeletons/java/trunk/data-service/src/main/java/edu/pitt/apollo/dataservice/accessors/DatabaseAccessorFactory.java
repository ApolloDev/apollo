package edu.pitt.apollo.dataservice.accessors;


import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

public class DatabaseAccessorFactory {

    public static DatabaseAccessor getDatabaseAccessor(Authentication authentication) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
        return new DatabaseAccessor(authentication);
    }

    public static DatabaseAccessor getDatabaseAccessor(Object message) throws UnrecognizedMessageTypeException, ApolloDatabaseException {
        if (message instanceof RunSimulationMessage) {
            return new DatabaseAccessorForRunningJobs(
                    ((RunSimulationMessage) message).getAuthentication(),
                    "run_simulation_message.json",
                    ContentDataTypeEnum.RUN_SIMULATION_MESSAGE,
                    ((RunSimulationMessage) message).getSimulatorIdentification());
        } else if (message instanceof RunSimulationsMessage) {
            return new DatabaseAccessorForRunningJobs(
                    ((RunSimulationsMessage) message).getAuthentication(),
                    "run_simulations_message.json",
                    ContentDataTypeEnum.RUN_SIMULATION_MESSAGE,
                    ((RunSimulationsMessage) message).getSimulatorIdentification());
        } else if (message instanceof RunVisualizationMessage) {
            return new DatabaseAccessorForRunningJobs(
                    ((RunVisualizationMessage) message).getAuthentication(),
                    "run_visualization_message.json",
                    ContentDataTypeEnum.RUN_VISUALIZATION_MESSAGE,
                    ((RunVisualizationMessage) message).getVisualizerIdentification());
        } else if (message instanceof GetOutputFilesURLAsZipMessage) {
            Authentication authentication = ((GetOutputFilesURLAsZipMessage) message).getAuthentication();
            return new DatabaseAccessorForRunningDataService((GetOutputFilesURLAsZipMessage) message, authentication);
        } else {
            throw new UnrecognizedMessageTypeException(
                    "Unrecognized message type in DatabaseAccessorFactory");
        }

    }

}