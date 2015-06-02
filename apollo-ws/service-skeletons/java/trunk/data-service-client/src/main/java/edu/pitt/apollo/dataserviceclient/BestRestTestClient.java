package edu.pitt.apollo.dataserviceclient;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleInfectiousDiseaseScenario;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;

/**
 * Created by jdl50 on 6/2/15.
 */
public class BestRestTestClient {

    private static final String REST_DATA_SERVICE_TEST_CLIENT_PROPERTIES_FILE = "data_service_test_client.properties";

    public static void main(String[] args) {
        RunSimulationMessage runSimulationMessage =
                ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);

        DataServiceImpl dataService = new DataServiceImpl();
        dataService.insertRun(runSimulationMessage, "");

    }
}
