package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jul 30, 2014 Time: 3:03:10 PM Class: RunApolloServiceThreadFactory
 */
public class RunApolloServiceThreadFactory {

    public static RunApolloServiceThread getRunApolloServiceThread(RunMessage message, BigInteger runId, Authentication authentication) throws UnrecognizedMessageTypeException {

        SoftwareIdentification softwareId;
        if (message instanceof RunSimulationMessage) {
            return new RunSimulationThread(runId, ((RunSimulationMessage) message).getSoftwareIdentification(), authentication);
        } else if (message instanceof RunSimulationsMessage) {
            return new RunSimulationThread(runId, ((RunSimulationsMessage) message).getSoftwareIdentification(), authentication);
        } else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
            return new RunInfectiousDiseaseTransmissionExperimentThread(runId, (RunInfectiousDiseaseTransmissionExperimentMessage) message,
                    authentication);
        } else if (message instanceof RunVisualizationMessage) {
            return new RunVisualizationThread(runId, ((RunVisualizationMessage) message).getSoftwareIdentification(), authentication);
//		} else if (message instanceof GetOutputFilesURLsMessage) {
//			softwareId = ((GetOutputFilesURLsMessage) message).getSoftwareIdentification();
//			return new RunDatabaseJobThread(runId, softwareId, authentication);
        } else if (message instanceof DataRetrievalRequestMessage) {
            return new RunDatabaseJobThread(runId, ((DataRetrievalRequestMessage) message).getSoftwareIdentification(), authentication);
//		} else if (message instanceof GetAllOutputFilesURLAsZipMessage) {
//			softwareId = ((GetAllOutputFilesURLAsZipMessage) message).getSoftwareIdentification();
//			return new RunDatabaseJobThread(runId, softwareId, authentication);
        } else {
            throw new UnrecognizedMessageTypeException("Unrecognized message type in RunApolloServiceThreadFactory");
        }

    }

}
