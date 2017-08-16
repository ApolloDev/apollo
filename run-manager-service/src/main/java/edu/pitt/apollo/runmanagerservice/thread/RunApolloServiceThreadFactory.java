package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.apollo_service_types.v4_0_2.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0_2.RunSimulationsMessage;
import edu.pitt.apollo.query_service_types.v4_0_2.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0_2.Authentication;
import edu.pitt.apollo.services_common.v4_0_2.RunMessage;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v4_0_2.RunVisualizationMessage;

import java.math.BigInteger;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jul 30, 2014 Time: 3:03:10 PM Class: RunApolloServiceThreadFactory
 */
public class RunApolloServiceThreadFactory {

    public static RunApolloServiceThread getRunApolloServiceThread(RunMessage message, BigInteger runId, Authentication authentication) throws UnrecognizedMessageTypeException {

        if (message instanceof RunSimulationMessage) {
            return new RunSimulationThread(runId, ((RunSimulationMessage) message).getSoftwareIdentification(), authentication);
        } else if (message instanceof RunSimulationsMessage) {
            return new RunSimulationThread(runId, ((RunSimulationsMessage) message).getSoftwareIdentification(), authentication);
        } else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
            return new RunInfectiousDiseaseTransmissionExperimentThread(runId, (RunInfectiousDiseaseTransmissionExperimentMessage) message,
                    authentication);
        } else if (message instanceof RunVisualizationMessage) {
            return new RunVisualizationThread(runId, ((RunVisualizationMessage) message).getSoftwareIdentification(), authentication);
        } else if (message instanceof RunSimulatorOutputQueryMessage) {
			return new RunQueryServiceThread(runId, authentication);
		} 
		else {
            throw new UnrecognizedMessageTypeException("Unrecognized message type in RunApolloServiceThreadFactory");
        }

    }

}
