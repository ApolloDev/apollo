package edu.pitt.apollo.runmanagerserviceclient;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.runmanagerservice.RunManagerServiceImpl;
import edu.pitt.apollo.runmanagerserviceclient.ApolloServiceTypeFactory;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.types.v3_0_0.FixedDuration;

import java.io.*;
import java.math.BigInteger;
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

    public static void main(String[] args) throws IOException, DataServiceException, SimulatorServiceException {
        RunSimulationMessage runSimulationMessage =
                ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);

        FixedDuration latentPeriodDuration = (FixedDuration) runSimulationMessage.getInfectiousDiseaseScenario().
                getInfections().get(0).getInfectionAcquisitionsFromInfectiousHosts().get(0)
                .getInfectiousPeriodDuration();

        latentPeriodDuration.setValue(new Double(String.valueOf(latentPeriodDuration.getValue()) + System.currentTimeMillis()));
        System.out.println("Set LPD to: " + latentPeriodDuration);


        RunManagementInterface runManagementInterface = new RunManagerServiceImpl();
        BigInteger runId = runManagementInterface.insertRun(runSimulationMessage);
        while (runManagementInterface.getRunStatus(runId, getAuthentication()).getStatus() != (MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
            MethodCallStatus status = runManagementInterface.getRunStatus(runId, getAuthentication()) ;
            System.out.println("Status of run " + runId + " is (" + status.getStatus().value() + ")" + status.getMessage());
        }

        JobRunningServiceInterface jobRunningServiceInterface = new RunManagerServiceImpl();
        jobRunningServiceInterface.run(runId, getAuthentication());

        while (runManagementInterface.getRunStatus(runId, getAuthentication()).getStatus() != (MethodCallStatusEnum.COMPLETED)) {
            MethodCallStatus status = runManagementInterface.getRunStatus(runId, getAuthentication()) ;
            System.out.println("Status of run " + runId + " is (" + status.getStatus().value() + ")" + status.getMessage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
