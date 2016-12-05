package edu.pitt.apollo.apolloclient;

import edu.pitt.apollo.apolloclient.tutorial.ApolloServiceTypeFactory;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.restbrokerserviceconnector.RestBrokerServiceConnector;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.simulator_service_types.v4_0.RunSimulationMessage;

/**
 * Created by nem41 on 8/17/15.
 */
public class BrokerRestClient {

    public static void main(String[] args) {


        RestBrokerServiceConnector connector = new RestBrokerServiceConnector("http://betaweb.rods.pitt.edu/broker-service-rest-frontend-3.1.0-SNAPSHOT/");

        Authentication authentication = new Authentication();

        try {
            RunSimulationMessage runSimulationMessage = ApolloServiceTypeFactory
                    .getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.SEIR);

            runSimulationMessage.getInfectiousDiseaseScenario().getInfections().get(0)
                    .getInfectionAcquisitionsFromInfectedHosts().get(0)
                    .getBasicReproductionNumbers().get(0).setExactValue(1.76);

            InsertRunResult result = connector.insertRun(runSimulationMessage, authentication);

            System.out.println(result.getRunId());

        } catch (RunManagementException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

}

