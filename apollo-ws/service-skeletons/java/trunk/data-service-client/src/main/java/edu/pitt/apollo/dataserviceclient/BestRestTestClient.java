package edu.pitt.apollo.dataserviceclient;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleInfectiousDiseaseScenario;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;

import java.io.*;
import java.util.Properties;

/**
 * Created by jdl50 on 6/2/15.
 */
public class BestRestTestClient {

    private static final String REST_DATA_SERVICE_TEST_CLIENT_PROPERTIES_FILE = "data_service_test_client.properties";

    private static Authentication getAuthentication() throws FileNotFoundException, IOException {
        File props = new File(REST_DATA_SERVICE_TEST_CLIENT_PROPERTIES_FILE);
        InputStream fis = new FileInputStream(props);
        Properties properties = new Properties();
        properties.load(fis);
        fis.close();

        Authentication auth = new Authentication();
        auth.setRequesterId(properties.getProperty("username"));
        auth.setRequesterPassword(properties.getProperty("password"));
        return auth;
    }

    public static void main(String[] args) throws IOException, DataServiceException {
        RunSimulationMessage runSimulationMessage =
                ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);

        FixedDuration latentPeriodDuration = (FixedDuration) runSimulationMessage.getInfectiousDiseaseScenario().
                getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0)
                .getInfectiousPeriodDuration();

        latentPeriodDuration.setValue(new Double(String.valueOf(latentPeriodDuration.getValue()) + System.currentTimeMillis()));
        System.out.println("Set LPD to: " + latentPeriodDuration);


        DataServiceImpl dataService = new DataServiceImpl();
        System.out.println(dataService.insertRun(runSimulationMessage);


    }
}
