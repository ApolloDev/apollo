package edu.pitt.apollo.apolloclient.tutorialv2;

import edu.pitt.apollo.filestore_service_types.v4_0_2.GetFileUrlRequest;
import edu.pitt.apollo.filestore_service_types.v4_0_2.ListFilesForRunRequest;
import edu.pitt.apollo.service.apolloservice.v4_0_2.ApolloServiceEI;
import edu.pitt.apollo.service.apolloservice.v4_0_2.ApolloServiceV402;
import edu.pitt.apollo.services_common.v4_0_2.*;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;

import javax.xml.namespace.QName;
import java.math.BigInteger;

/**
 * In this example "SoapRun" class, we show how to interact with the Apollo Web Service by connecting to the service,
 * running a remote simulation, and downloading the simulation results.  The full code to perform these tasks is in
 * contained in this class.  What follows is an overview of how this class works.  For specific usage instructions
 * please examine the methods in this class.
 * <p/>
 * <p/>
 * <h2>Prerequisites:</h2>
 * 1. Download apollo.jars from some location
 * <h2>1. Connecting to the SOAP version of the Apollo Web Serivce</h2>
 * One main benefit of using the SOAP protocol to interact with the Apollo Web Service is that executing methods on the
 * Apollo Web Service is as easy as calling a local method.
 * <p/>
 * In order to begin executing these remote methods, we first connect to the web service as a client and then send
 * requests through this client.  The service that we connect to in this tutorial is the service named
 * ApolloService_v4.0, which is defined by the following WSDL:
 * <a href="http://betaweb.rods.pitt.edu/broker-service-war-4.0.2-SNAPSHOT/services/apolloservice?wsdl">
 *     http://betaweb.rods.pitt.edu/broker-service-war-4.0.2-SNAPSHOT/services/apolloservice?wsdl</a>.
 * <p/>
 * <h3>Creating a client connection to {@link SoapRun#SERVICE_NAME ApolloService_v4.0}</h3>
 * A {@link edu.pitt.apollo.service.apolloservice.v4_0_2.ApolloServiceV402} is the class that provides the client view of
 * the ApolloService_v4.0.  In this example, we name the variable {@link SoapRun#apolloServiceClientView}.
 * <p/>
 * To create the client view of ApolloService_v4.0, we execute the following code in {@link SoapRun#initializeServiceClientView()}:
 * <br/>
 * <pre>
 * {@code
 *         apolloServiceClientView = new ApolloServiceV40(new URL(WSDL_LOC),SERVICE_NAME);
 * }
 * </pre>
 * <h3>1.a Getting the endpoint for the Apollo methods</h3>
 * Now that we have the client view of ApolloSerivice_v4.0, we can make calls to the Apollo Web Service.  If you would
 * explore the capabilities of the service you would see that it contains many confusing looking boilerplate methods
 * that having nothing to do with running simulators.  Luckily SOAP allows us to ask for specific function sets (or "endpoints")
 * that implement an interface.  The interface that defines the web service methods on the Apollo Web Service, is the
 * {@link edu.pitt.apollo.service.apolloservice.v4_0_2.ApolloServiceEI ApolloServiceEI}
 * We ask for an instance of the object that implements the {@link edu.pitt.apollo.service.apolloservice.v4_0_2.ApolloServiceEI ApolloServiceEI}
 * by executing the following command in {@link SoapRun#getApolloServiceEndpoint()}:
 * <pre>
 *     {@code
 *     apolloServiceEndpoint = apolloServiceClientView.getApolloServiceEndpoint();
 *     }
 * </pre>
 * With the service and endpoint instantiated, now have the ability to make calls like the following:
 * <p/>
 * <pre>
 * {@code
 * apolloServiceEndpoint.runSimulation();
 * apolloServiceEndpoint.getRunStatus();
 * apolloServiceEndpoint.listFilesForRun();
 * }
 * </pre>
 * <p/>
 * <h2>2. Running a simulation</h2>
 * Now that we have an instance of the apolloServiceEndpoint we can run a simulation.  Running a simulation entails
 * calling the following method: {@link ApolloServiceEI#runSimulation(RunSimulationMessage)}
 * <p/>
 * Note that runSimulation takes as input {@link edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage}.
 * This is a farily complicated object that tells the Apollo Web Service three things:
 * <ul>
 * <li>1. A description of the infectious disease scenario that is to be simulated</li>
 * <li>2. The user credentials (for authorization / authentication purposes)</li>
 * <li>2. The simulator to run the simulation on</li>
 * </ul>
 * <p/>
 * The RunSimulationMessage is covered in greater depth in other sections and is beyond the scope of this introduction.  For the purposes of this example,
 * I'll simply ask for a default RunSimualtionMessage from a helper class that is used throughout this demo: {@link ApolloServiceTypeFactory}.  We get
 * an example RunSimulationMessage which we will run on the FRED simulator by issuing the following call:
 * <pre>
 *     <code>
 *          RunSimulationMessage runSimulationMessage = ApolloServiceTypeFactory.getExampleRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);
 *     </code>
 * </pre>
 * <p/>
 * With the runSimulationMessage in hand, we can now call {@link ApolloServiceEI#runSimulation(RunSimulationMessage)} in the following manner:
 * <pre>
 *     <code>
 *          RunResult simulationRunResult = apolloServiceEndpoint.runSimulation(runSimulationMessage);
 *     </code>
 * </pre>
 * Executing the above method will attempt to run the simulation on the designated simulator.  The result of a call to {@link ApolloServiceEI#runSimulation(RunSimulationMessage)}
 * is a {@link RunResult}.  The RunResult contains the ID that was assigned to the run, and the initial run status (could have been rejected, etc).
 * We discuss how to use the RunResult to check the status of a run in the next section
 * <p/>
 * <h2>3. Checking the status of a run using {@link RunResult}</h2>
 * To check on the status of a run, we submit a {@link ApolloServiceEI#getRunStatus(RunStatusRequest)}, which takes a
 * {@link edu.pitt.apollo.services_common.v4_0_2.RunStatusRequest}.  The RunStatusRequest class takes a runId, and the cridentals of the user checking the status.
 * Once the RunStatusRequest is built, we can periodically check back on the status of the run by issuing the following command:
 * <pre>
 *     <code>
 *         MethodCallStatus callStatus = apolloServiceEndpoint.getRunStatus(request);
 *     </code>
 * </pre>
 * We then know if the run terminates when the {@link MethodCallStatus#getStatus()} returns a value such as
 * {@link MethodCallStatusEnum#COMPLETED} in the event the simulation completes, or {@link MethodCallStatusEnum#FAILED}
 * in the event the simulation fails.  We can retrieve any error/status/success messages by calling {@link MethodCallStatus#getMessage()}
 * <p/>
 * In the tutorial code below, we periodically poll the RunStatus until the run completes.
 * <p/>
 * *
 * <h2>4. Downloading the results of a run</h2>
 * Once the simulation completes, we can ask for the output of the simulation.
 * <p/>
 * To ask for the output of the simulation, we need to call the method {@link ApolloServiceEI#listFilesForRun(ListFilesForRunRequest)}.
 * The ListFilesForRunRequest class requires us to identify the run by calling {@link ListFilesForRunRequest#setRunId(BigInteger)}, and
 * also provide the users credentials by calling {@link ListFilesForRunRequest#setAuthentication(Authentication)}.
 * <p/>
 * One the ListFilesForRunRequest object is populated, call the following method to grab the list of files that were output
 * from the simulation.
 * <pre>
 *     <code>
 *         ListFilesForRunResult files = apolloServiceEndpoint.listFilesForRun(listFilesForRunRequest);
 *     </code>
 * </pre>
 * <p/>
 * The variable "files" from above now contains a list of files that are available for download.  To get a URL to download
 * any of these files, the method {@link ApolloServiceEI#getFileUrl(GetFileUrlRequest)} is used.
 * <p/>
 * *
 * Created by John Levander on 9/15/16.
 */
public class SoapRun {

    /**
     * The location of the Web Service Definition Language (WSDL) for the Apollo Web Serivce
     */
    private static final String WSDL_LOC = "http://betaweb.rods.pitt.edu/broker-service-war-4.0.2-SNAPSHOT/services/apolloservice?wsdl";

    /**
     * The name as the service as defined in the Web Service Definition Language (WSDL) file.
     */
    private static final QName SERVICE_NAME = new QName("http://service.apollo.pitt.edu/apolloservice/v4_0/", "ApolloService_v4.0");

    /**
     * Provides a client view of the Apollo Web Service, this is the main connection to the entirety of the Apollo Web Service.
     */
    private ApolloServiceV402 apolloServiceClientView;

    /**
     * A collection of Apollo related calls that are runnable through the apolloServiceClientView.
     */
    private ApolloServiceEI apolloServiceEndpoint;

    /**
     * The number of the simulation, that is assigned by the Apollo Web Service.
     */
    private BigInteger runIdentification = new BigInteger("368");

    /**
     * A class that holds the credentials of the user.
     */
    private Authentication authentication;

    /**
     * Indicates whether or not the simulation was successful.
     */
    private boolean runWasSuccessful;

    public SoapRun() {

    }

  /*  *//**
     * A simple method that initializes the global Authentication variable which is used throughout the demo.
     *//*
    private void initailizeAuthentication() {
        System.out.println("Creating Authentication object...");

        authentication = new Authentication();
        authentication.setRequesterId("apollo_demo");
        authentication.setRequesterPassword("apollo_demo");
    }

    *//**
     * Connect to the {@link SoapRun#SERVICE_NAME} to instantiate the service.
     *//*
    private void initializeServiceClientView() {
        System.out.println("Creating client connection to the Apollo Web Service...");

        try {
            apolloServiceClientView = new ApolloServiceV40(new URL(WSDL_LOC),
                    SERVICE_NAME);

        } catch (MalformedURLException e) {
            throw new ExceptionInInitializerError("MalformedURLException: "
                    + e.getMessage());
        }
    }

    *//**
     * Get an instance of the port, given a working {@link SoapRun#apolloServiceClientView}.  We will use this port to invoke methods on the Apollo
     * Web Service.
     *//*
    private void getApolloServiceEndpoint() {
        apolloServiceEndpoint = apolloServiceClientView.getApolloServiceEndpoint();
    }

    *//**
     * Waits for the current run (indicated by the global variable runIdentification) to complete, or fail.
     *
     * @return "true" if the run completes, "false" if the run encounters and error
     *//*
    protected boolean waitForRunToCompleteOrFail() {
        RunStatusRequest request = new RunStatusRequest();
        request.setRunIdentification(runIdentification);
        request.setAuthentication(authentication);
        while (true) {
            try {
                Thread.sleep(2000);
                MethodCallStatus callStatus = apolloServiceEndpoint.getRunStatus(request);
                MethodCallStatusEnum status = callStatus.getStatus();
                String message = callStatus.getMessage();

                System.out.printf("\tThe status of run %s is: %s\n", runIdentification, message);

                if (status == null) {
                    System.out.printf("\tFatal Web Service Error: The Web service did not return a status for run: %s\n",
                            runIdentification.toString());
                    System.exit(-1);
                }

                switch (status) {
                    case AUTHENTICATION_FAILURE:
                    case UNAUTHORIZED:
                        System.out
                                .printf("\tNo authorization for this run! Error message is: %s\n",
                                        message);
                        System.out.println("");
                        return false;
                    case FAILED:
                    case UNKNOWN_RUNID:
                        System.out.printf(
                                "\tRun Failed. The status message is: %s\n", message);
                        System.out.println("");
                        return false;
                    case LOG_FILES_WRITTEN:
                    case COMPLETED:
                        System.out.println("");
                        return true;
                    default:
                        Thread.sleep(2000);
                        break;
                }
            } catch (InterruptedException nonFatalSleepInterruptedException) {
            }
        }

    }

    *//**
     * Runs a preconfigured simulation and returns when the run either completes or fails.
     *
     * @return "true" if the run completes, "false" if the run encounters and error
     *//*
    private boolean runSimulation() {
        System.out.println("Retreieving a prebuilt RunSimulationMessage...");
        RunSimulationMessage runSimulationMessage = ApolloServiceTypeFactory.getExampleRunSimulationMessage(ApolloServiceTypeFactory.SimulatorIdentificationEnum.FRED);

        System.out.println("Submitting RunSimulationMessage to the Apollo Web Service...");
        RunResult simulationRunResult = apolloServiceEndpoint.runSimulation(runSimulationMessage);

        runIdentification = simulationRunResult.getRunId();

        System.out.printf("\tThe simulator returned a runId of %s\n",
                simulationRunResult.getRunId());
        System.out.println("\tThe Apollo Web Service returned the following status: "
                + simulationRunResult.getMethodCallStatus().getStatus() + " : "
                + simulationRunResult.getMethodCallStatus().getMessage());

        return waitForRunToCompleteOrFail();

    }

    *//**
     * Retrieves the list of output files for a run, finds the file that ends with h5, and reports the download location.
     *//*
    private void retrieveResults() {
        System.out.println("Requesting the names of available output files for run " + runIdentification +"...");

        *//**
         * Here, we build the ListFilesForRunRequest object, passing in the runId,  and authentication information.
         *//*
        ListFilesForRunRequest listFilesForRunRequest = new ListFilesForRunRequest();
        listFilesForRunRequest.setRunId(runIdentification);
        listFilesForRunRequest.setAuthentication(authentication);

        *//**
         * We now pass the ListFilesForRunRequest to the listFilesForRun method.  This method returns a list of output
         * files that are available for download.
         *//*
        ListFilesForRunResult files = apolloServiceEndpoint.listFilesForRun(listFilesForRunRequest);
        FileIdentification fileIdentificationToDownload = null;
        for (FileIdentification fileIdentification : files.getFiles()) {
            System.out.println("\tOutput file available: " + fileIdentification.getLabel());
            *//**
             * For example purposes, we are going to download the file with the .h5 extension, so we save the
             * cooresponding FileIdentification object for late.
              *//*
            if (fileIdentification.getLabel().endsWith("h5")) {
                fileIdentificationToDownload = fileIdentification;
            }
        }
        System.out.println();

        *//**
         * Finally, we download the "h5" file mentioned in the comment directly above this comment."   The
         * GetFileUrlRequest needs to know the authentication, runId, and fileIdentificationToDownload.  We send the
         * built GetFileUrlRequest to the .getFileUrl endpoint, and in response we get a URL to the requested file.
         *//*

        System.out.println("Getting download URL for file: " + fileIdentificationToDownload.getLabel());
        GetFileUrlRequest getFileUrlRequest = new GetFileUrlRequest();
        getFileUrlRequest.setAuthentication(authentication);
        getFileUrlRequest.setRunId(runIdentification);
        getFileUrlRequest.setFileIdentification(fileIdentificationToDownload);
        GetFileUrlResult url = apolloServiceEndpoint.getFileUrl(getFileUrlRequest);
        System.out.println("\tFile: " + fileIdentificationToDownload.getLabel() + " is available for download at: " + url.getUrl());
    }

    *//**
     * Runs the example program.
     *//*
    public void run() {
        initailizeAuthentication();
        initializeServiceClientView();
        getApolloServiceEndpoint();
        runSimulation();
        retrieveResults();
    }

    public static void main(String[] args) {
        SoapRun soapRun = new SoapRun();
        soapRun.run();
    }*/
}
