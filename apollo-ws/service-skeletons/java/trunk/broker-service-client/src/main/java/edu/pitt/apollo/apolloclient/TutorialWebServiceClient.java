package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v2_0_1.ApolloServiceV201;
import edu.pitt.apollo.types.v2_0_1.GetPopulationAndEnvironmentCensusResult;
import edu.pitt.apollo.types.v2_0_1.GetScenarioLocationCodesSupportedBySimulatorResult;
import edu.pitt.apollo.types.v2_0_1.GetVisualizerOutputResourcesResult;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.PopulationAndEnvironmentCensus;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationResult;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.UrlOutputResource;

public class TutorialWebServiceClient {

    enum VisualizerIdentificationEnum {

        GAIA, TIME_SERIES
    }
	public static final String WSDL_LOC = "http://research.rods.pitt.edu/apolloservice2.0.1/services/apolloservice?wsdl";
//    public static final String WSDL_LOC = "http://localhost:8080/apolloservice2.0.1/services/apolloservice?wsdl";
    private static final QName SERVICE_NAME = new QName(
            "http://service.apollo.pitt.edu/apolloservice/v2_0_1/", "ApolloService_v2.0.1");
    public static final long TWO_SECONDS = 2000;
    public static final boolean RUN_IS_NOT_COMPLETED_OR_FAILED = true;
    public static final boolean RUN_WAS_SUCCESSFUL = true;
    public static final boolean RUN_FAILED = false;
    private static ApolloServiceEI port;

    public TutorialWebServiceClient() {
    }

    public ApolloServiceEI getPort() {
        return port;
    }

    private List<String> getScenarioLocationCodesSupportedBySimulatorOrNull(
            SoftwareIdentification simulatorIdentification) {
        GetScenarioLocationCodesSupportedBySimulatorResult resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall = getPort().getScenarioLocationCodesSupportedBySimulator(simulatorIdentification);

        MethodCallStatusEnum callStatus = resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall.getMethodCallStatus().getStatus();
        System.out.printf("Call to getScenarioLocationCodesSupportedBySimulator() returned with result: %s\n"
                + callStatus);
        if (callStatus == MethodCallStatusEnum.COMPLETED) {
            return resultOfGetScenarioLocationCodesSupportedBySimulatorWebServiceCall.getLocationCodes();
        } else {
            return null;
        }

    }

    public PopulationAndEnvironmentCensus getPopulationAndEnvironmentCensus(SoftwareIdentification simulatorSoftwareIdentification, String apolloLocationCode) {
        GetPopulationAndEnvironmentCensusResult getPopulationAndEnvironmentCensusResult = getPort().getPopulationAndEnvironmentCensus(simulatorSoftwareIdentification, apolloLocationCode);

        return getPopulationAndEnvironmentCensusResult.getPopulationAndEnvironmentCensus();
    }

    protected static List<UrlOutputResource> getUrlOutputResourcesForVisualization(
            BigInteger visualizationRunIdentifier) {
        GetVisualizerOutputResourcesResult results = port.getVisualizerOutputResources(visualizationRunIdentifier);
        if (results.getMethodCallStatus().getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
            for (UrlOutputResource r : results.getUrlOutputResources()) {
                System.out.println("\t" + r.getURL());
            }
        } else {
            System.out.printf("Unable to retrieve visualizer output resources. MethodCallStatus was %s with message: %s\n",
                    results.getMethodCallStatus().getStatus(), results.getMethodCallStatus().getMessage());
        }
        return results.getUrlOutputResources();
    }

    public static BigInteger runSimulation(RunSimulationMessage runSimulationMessage) {
        BigInteger simulationRunId = port.runSimulation(runSimulationMessage);
        System.out.printf("The simulator returned a runId of %s\n", simulationRunId);

        boolean runWasSuccessful = waitForRunToCompleteOrFail(simulationRunId);

        if (runWasSuccessful) {
            return simulationRunId;
        } else {
            return null;
        }
    }

    protected static BigInteger runVisualization(RunVisualizationMessage runVisualizationMessage) {
        RunVisualizationResult runVisualizationResult = port.runVisualization(runVisualizationMessage);
        BigInteger visualizationRunId = runVisualizationResult.getVisualizationRunId();
        return visualizationRunId;
    }

    protected static boolean waitForRunToCompleteOrFail(BigInteger runIdentification) {

        while (RUN_IS_NOT_COMPLETED_OR_FAILED) {
            try {
                Thread.sleep(TWO_SECONDS);
                MethodCallStatus callStatus = port.getRunStatus(runIdentification);
                MethodCallStatusEnum status = callStatus.getStatus();
                String message = callStatus.getMessage();

                System.out.printf("The status of run %s is: %s\n", status, message);

                if (status == null) {
                    System.out.printf("Fatal Web Service Error: The Web service did not return a status for run: %s\n",
                            runIdentification.toString());
                    System.exit(-1);
                }

                switch (status) {
                    case AUTHENTICATION_FAILURE:
                    case UNAUTHORIZED:
                        System.out.printf("No authorization for this run! Error message is: %s\n", message);
                        return RUN_FAILED;
                    case FAILED:
                    case UNKNOWN_RUNID:
                        System.out.printf("Run Failed. The status message is: %s\n", message);
                        return RUN_FAILED;
                    case LOG_FILES_WRITTEN:
                    case COMPLETED:
                        return RUN_WAS_SUCCESSFUL;
                    default:
                        Thread.sleep(TWO_SECONDS);
                        break;
                }
            } catch (InterruptedException nonFatalSleepInterruptedException) {
            }
        }
    }

    static {
        try {
            ApolloServiceV201 ss = new ApolloServiceV201(new URL(WSDL_LOC), SERVICE_NAME);
            port = ss.getApolloServiceEndpoint();
        } catch (MalformedURLException ex) {
            throw new ExceptionInInitializerError("MalformedURLException: " + ex.getMessage());
        }
    }
}
