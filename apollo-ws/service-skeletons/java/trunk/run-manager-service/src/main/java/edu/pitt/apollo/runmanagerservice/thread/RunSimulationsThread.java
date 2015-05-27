package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.runmanagerservice.types.SynchronizedStringBuilder;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.dataservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.exception.BatchException;
import edu.pitt.apollo.dataservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.*;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceException;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunSimulationsThread extends RunApolloServiceThread {

    public static final Random rng = new Random(System.currentTimeMillis());
    private static final String FILE_NAME_FOR_INPUTS_WITH_RUN_IDS = "batch_inputs_with_run_ids.txt";
    private static final SoftwareIdentification brokerServiceSoftwareId;
    private static int BROKER_SERVICE_SOFTWARE_ID_KEY;


    static {
        brokerServiceSoftwareId = new SoftwareIdentification();
        brokerServiceSoftwareId.setSoftwareDeveloper("UPitt");
        brokerServiceSoftwareId.setSoftwareName("Broker Service");
        brokerServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.BROKER);
        brokerServiceSoftwareId.setSoftwareVersion("3.0.0");
    }

    public class BooleanRef {
        boolean value;
    }

    public class CounterRef {
        int count = 0;
    }

    private final RunSimulationsMessage message;
    Exception error = null;
    BigInteger simulationGroupId;
    private URL configFileUrl;

    public RunSimulationsThread(RunSimulationsMessage message, BigInteger runId) throws ApolloDatabaseException {
        super(runId);
        this.message = message;
        try {
            this.configFileUrl = new URL(message.getBatchConfigurationFile());
        } catch (MalformedURLException e) {
            error = e;
        }


        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            BROKER_SERVICE_SOFTWARE_ID_KEY = dbUtils.getSoftwareIdentificationKey(brokerServiceSoftwareId);
        } catch (ApolloDatabaseException ex) {
            throw new ExceptionInInitializerError("ApolloDatabaseException getting broker service software key: " + ex.getMessage());
        }
    }

    public static String getRandomFilenameString() {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            String time = ((Long) System.currentTimeMillis()).toString();
            String rnd = ((Long) rng.nextLong()).toString();
            String toHash = time + rnd;
            md5.update(toHash.getBytes());
            String filename = new BigInteger(1, md5.digest()).toString(16);
            File f = new File(ApolloServiceConstants.APOLLO_DIR + "tmp");
            f.mkdirs();
            filename = ApolloServiceConstants.APOLLO_DIR + "tmp" + File.separator + filename + ".txt";
            return filename;
        } catch (NoSuchAlgorithmException e) {
            // This is not likely to happen as MD5 isn't going anywhere.
            return null;
        }
    }

    public static synchronized File getTemporaryFile() {
        File f = new File(getRandomFilenameString());
        if (f.exists()) {
            while (f.exists()) {
                f = new File(getRandomFilenameString());
            }
        }
        return f;
    }

    public static String downloadUrlToFile(URL configFileUrl)
            throws BatchException, IOException {
        ReadableByteChannel rbc;

        try {
            rbc = Channels.newChannel(configFileUrl.openStream());

            File storageFile = getTemporaryFile();
            if (storageFile != null) {
                FileOutputStream fos = new FileOutputStream(storageFile);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                return storageFile.getAbsolutePath();
            } else {
                throw new BatchException(
                        "Unable to create temporary storage file for the batch processing.");
            }
        } catch (IOException e) {
            throw new IOException("Error downloading URL:" + configFileUrl
                    + ".  Error was: " + e.getMessage(), e);
        }
    }

    public boolean queueAndTranslateRuns() {
        String filename = null;
        SoftwareIdentification simulatorIdentification = message
                .getSimulatorIdentification();

        XMLGregorianCalendar scenarioDate = (XMLGregorianCalendar) message.getBaseInfectiousDiseaseScenario().getScenarioDate().clone();
        try {
            try {
                filename = downloadUrlToFile(configFileUrl);
            } catch (BatchException ex) {

                ApolloServiceErrorHandler.reportError(
                        "Error downloading batch configuration file, error was: "
                                + simulatorIdentification.getSoftwareName()
                                + ", version: "
                                + simulatorIdentification.getSoftwareVersion()
                                + ", developer: "
                                + simulatorIdentification
                                .getSoftwareDeveloper()
                                + " for run id " + runId + ": "
                                + ex.getMessage(), runId);
                return false;
            } catch (IOException ex) {
                ApolloServiceErrorHandler.reportError(
                        "Error downloading batch configuration file, error was: "
                                + simulatorIdentification.getSoftwareName()
                                + ", version: "
                                + simulatorIdentification.getSoftwareVersion()
                                + ", developer: "
                                + simulatorIdentification
                                .getSoftwareDeveloper()
                                + " for run id " + runId + ": "
                                + ex.getMessage(), runId);
                return false;
            }

            File inputsFile = new File(filename);
//			String inputsFileWithRunIdsPath = OUTPUT_DIRECTORY + runId + File.separator + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;
//			String urlForFileWithRunIds = BASE_URL + runId + "/" + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;

//			File inputFileWithRunIds = new File(inputsFileWithRunIdsPath);
//			inputFileWithRunIds.getParentFile().mkdirs();
            SynchronizedStringBuilder stBuild = new SynchronizedStringBuilder();

            BooleanRef error = new BooleanRef();
            BufferedReader br = new BufferedReader(new FileReader(inputsFile));
            try {
                ExecutorService executor = null;

                executor = Executors.newFixedThreadPool(10);
                String line = null;
                int i = 0;
                CounterRef counter = new CounterRef();
                error.value = false;
                Timer timer = new Timer();
                timer.schedule(new StatusUpdaterThread(runId, counter, error), 0, 1000);

                while ((line = br.readLine()) != null) {

                    Runnable worker = new StageInDbWorkerThread(runId, simulationGroupId, simulatorIdentification, authentication, line, message, scenarioDate, stBuild, error, counter);
                    executor.execute(worker);
                    if (error.value) {
                        break;
                    }
                }

                if (executor != null) {
                    executor.shutdown();
                    logger.debug("Waiting for all StageInDbWorkerThreads to finish");
                    while (!executor.isTerminated()) {

                        try {
                            sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    logger.debug("All StageInDbWorkerThreads finished!");
                }

                addBatchInputsWithRunIdsFileToDatabase(stBuild);

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer.cancel();

                MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
                System.out.println("TEST COMPLETE!");
                if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                    ApolloServiceErrorHandler.reportError("Error staging runs in database, error was " + status.getMessage() + " for runId",
                            runId);
                    return false;
                }

                logger.debug("Finished all threads!");

                if (!error.value) {
                    logger.info("Staging successful! Calling translator!");
                    try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {

                        boolean translatorRunSuccessful = TranslatorServiceAccessor
                                .runTranslatorAndReturnIfRunWasSuccessful(
                                        runId);

                        if (!translatorRunSuccessful) {
                            logger.error("Translator reported failure for runId {}", runId);
                            return false;
                        } else {
                            logger.info("Translator reported success for runId {}", runId);
                            return false; //success, but don't waste resources and call FRED for now
                            //change back to true
                        }
                    } catch (ApolloDatabaseException e) {
                        ApolloServiceErrorHandler.reportError("Unable to create instance of ApolloDbUtils to call runTranslatorAndReturnIfWasSuccessful", runId);
                    }
                } else {
                    logger.info("Bypassing calling translator as the staging returned an error.");
                }

                return false;
            } catch (ApolloDatabaseException e) {
                ApolloServiceErrorHandler.reportError("DB Error queuing and translating runs, error was " + e.getMessage(), runId);
            } finally {
                br.close();
            }
            return false;

        } catch (IOException e) {
            logger.error("Error writing error file!: " + e.getMessage());
            return false;
        }

    }


    private void addBatchInputsWithRunIdsFileToDatabase(SynchronizedStringBuilder sb) {
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils();
            try {
                // add the text data content for the URL
                int dataContentKey = dbUtils.addTextDataContent(sb.toString());
                int runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, FILE_NAME_FOR_INPUTS_WITH_RUN_IDS,
                        ApolloDbUtils.DbContentDataType.CONFIGURATION_FILE, BROKER_SERVICE_SOFTWARE_ID_KEY, ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID);
                dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);
            } catch (ApolloDatabaseException ex) {
                try {
                    ApolloServiceErrorHandler.writeErrorToDatabase(
                            "ApolloDatabaseException inserting text data content for run: " + ex.getMessage(), runId);
                } catch (ApolloDatabaseException e) {
                    e.printStackTrace();
                }
            } catch (Md5UtilsException md5ex) {
                try {
                   ApolloServiceErrorHandler.writeErrorToDatabase(
                            "Md5UtilsException inserting text data content for run: " + md5ex.getMessage(), runId);
                } catch (ApolloDatabaseException e) {
                e.printStackTrace();
                }
            }

        } catch (ApolloDatabaseException e) {
            try {
                ApolloServiceErrorHandler.writeErrorToErrorFile("Unable to create instance of ApolloDbUtils to call addBatchInputsWithRunIdsFileToDatabase", runId);
            } catch (IOException e1) {
                logger.error("Error writing error (Unable to create instance of ApolloDbUtils to call addBatchInputsWithRunIdsFileToDatabase) to error file!: " + e.getMessage());
            }
        }
    }

    private void startSimulations() {
        SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();


        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {


            String url = null;
            try {
                url = dbUtils.getUrlForSoftwareIdentification(simulatorIdentification);
            } catch (ApolloDatabaseKeyNotFoundException e1) {
                ApolloServiceErrorHandler.reportError(
                        "Apollo database key not found attempting to get URL for simulator: "
                                + simulatorIdentification.getSoftwareName() + ", version: "
                                + simulatorIdentification.getSoftwareVersion() + ", developer: "
                                + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                                + e1.getMessage(), runId);
                return;
            } catch (ApolloDatabaseException e) {
                ApolloServiceErrorHandler.reportError("Error getting WSDL address for software: " + simulatorIdentification.getSoftwareName() + " error was " + e.getMessage(), runId);
                return;
            }

            SimulatorServiceEI simulatorPort;
            try {
                simulatorPort = new SimulatorServiceV300(new URL(url)).getSimulatorServiceEndpoint();
            } catch (Exception e) {
                ApolloServiceErrorHandler.reportError(
                        "Unable to get simulator port for url: " + url + "\n\tError was: " + e.getMessage(),
                        runId);
                return;
            }

            // disable chunking for ZSI
            Client simulatorClient = ClientProxy.getClient(simulatorPort);
            HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
            HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
            simulatorHttpClientPolicy.setConnectionTimeout(36000);
            simulatorHttpClientPolicy.setAllowChunking(false);
            simulatorHttp.setClient(simulatorHttpClientPolicy);
            try {
                simulatorPort.runSimulations(runId);
            } catch (WebServiceException e) {
                ApolloServiceErrorHandler.reportError("Error calling runSimulations(): " + "\n\tError was: " + e.getMessage(),
                        runId);
                return;
            }

            try {
                dbUtils.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.reportError("Apollo database key not found attempting to update last service"
                        + " call for run id " + runId + ": " + ex.getMessage(), runId);
            }
        } catch (ApolloDatabaseException e) {
            ApolloServiceErrorHandler.reportError("Error creating an new ApolloDbUtils() object, error was " + e.getMessage(), runId);
        }
    }

    @Override
    public void run() {
        if (error != null) {
            SoftwareIdentification simulatorIdentification = message
                    .getSimulatorIdentification();

            ApolloServiceErrorHandler.reportError(
                    "Error staging batch run for: "
                            + simulatorIdentification.getSoftwareName()
                            + ", version: "
                            + simulatorIdentification.getSoftwareVersion()
                            + ", developer: "
                            + simulatorIdentification.getSoftwareDeveloper()
                            + " for run id " + runId + ": "
                            + error.getMessage(), runId);

            return;
        }
        if (queueAndTranslateRuns()) {
            startSimulations();
        }
    }

    @Override
    public void setAuthenticationPasswordFieldToBlank() {
        message.getAuthentication().setRequesterPassword("");
    }

}
