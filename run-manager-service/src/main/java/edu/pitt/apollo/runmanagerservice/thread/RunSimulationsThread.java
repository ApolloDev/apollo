package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.JobRunningServiceAccessor;
import edu.pitt.apollo.runmanagerservice.types.SynchronizedStringBuilder;
import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.exception.JobRunningServiceException;

import edu.pitt.apollo.runmanagerservice.exception.BatchException;

import edu.pitt.apollo.runmanagerservice.utils.ErrorUtils;
import edu.pitt.apollo.services_common.v4_0.*;

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

public class RunSimulationsThread
{ //extends RunApolloServiceThread {
/*
    public static final Random rng = new Random(System.currentTimeMillis());
    private static final String FILE_NAME_FOR_INPUTS_WITH_RUN_IDS = "batch_inputs_with_run_ids.txt";
    private static final SoftwareIdentification brokerServiceSoftwareIdentification;
    private static final SoftwareIdentification endUserSoftwareIdentifcation;
    private static int BROKER_SERVICE_SOFTWARE_ID_KEY;

    static {
        brokerServiceSoftwareIdentification = new SoftwareIdentification();
        brokerServiceSoftwareIdentification.setSoftwareDeveloper("UPitt");
        brokerServiceSoftwareIdentification.setSoftwareName("Broker Service");
        brokerServiceSoftwareIdentification.setSoftwareType(ApolloSoftwareTypeEnum.BROKER);
        brokerServiceSoftwareIdentification.setSoftwareVersion("3.0.0");

        endUserSoftwareIdentifcation = new SoftwareIdentification();
        endUserSoftwareIdentifcation.setSoftwareDeveloper("any");
        endUserSoftwareIdentifcation.setSoftwareName("any");
        endUserSoftwareIdentifcation.setSoftwareType(ApolloSoftwareTypeEnum.END_USER_APPLICATION);
        endUserSoftwareIdentifcation.setSoftwareVersion("any");
    }

    private final RunSimulationsMessage message;
    DataAccessor dataServiceAccessor = new DataAccessor();
    Exception error = null;
    BigInteger simulationGroupId;
    private URL configFileUrl;
    public RunSimulationsThread(RunSimulationsMessage message, SoftwareIdentification softwareId, BigInteger runId, Authentication authentication) {
        super(runId, softwareId, authentication);
        this.message = message;
        try {
            this.configFileUrl = new URL(message.getBatchConfigurationFile());
        } catch (MalformedURLException e) {
            error = e;
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

    public boolean queueAndTranslateRuns() {
        String filename = null;

        XMLGregorianCalendar scenarioDate = (XMLGregorianCalendar) message.getBaseInfectiousDiseaseScenario().getScenarioDate().clone();
        try {
            try {
                filename = downloadUrlToFile(configFileUrl);
            } catch (BatchException ex) {

                ErrorUtils.reportError(
                        "Error downloading batch configuration file, error was: "
                                + softwareId.getSoftwareName()
                                + ", version: "
                                + softwareId.getSoftwareVersion()
                                + ", developer: "
                                + softwareId
                                .getSoftwareDeveloper()
                                + " for run id " + runId + ": "
                                + ex.getMessage(), runId);
                return false;
            } catch (IOException ex) {
                ErrorUtils.reportError(
                        "Error downloading batch configuration file, error was: "
                                + softwareId.getSoftwareName()
                                + ", version: "
                                + softwareId.getSoftwareVersion()
                                + ", developer: "
                                + softwareId
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
                timer.schedule(new StatusUpdaterThread(dataServiceAccessor, runId, counter, error, authentication), 0, 1000);

                while ((line = br.readLine()) != null) {

                    Runnable worker = new StageInDbWorkerThread(runId, simulationGroupId, softwareId, line, message, scenarioDate, stBuild, error, counter, authentication);
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

                MethodCallStatus status = dataServiceAccessor.getRunStatus(runId, authentication);
                System.out.println("TEST COMPLETE!");
                if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                    ErrorUtils.reportError("Error staging runs in database, error was " + status.getMessage() + " for runId",
                            runId);
                    return false;
                }

                logger.debug("Finished all threads!");


                return false;
            } catch (DataServiceException e) {
                ErrorUtils.reportError("DB Error queuing and translating runs, error was " + e.getMessage(), runId);
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
            dataServiceAccessor.associateContentWithRunId(runId, sb.toString(), brokerServiceSoftwareIdentification, 
					endUserSoftwareIdentifcation, FILE_NAME_FOR_INPUTS_WITH_RUN_IDS, ContentDataFormatEnum.TEXT, ContentDataTypeEnum.CONFIGURATION_FILE, authentication);
        } catch (DataServiceException e) {
            ErrorUtils.reportError("Unable to create instance of ApolloDbUtils to call addBatchInputsWithRunIdsFileToDatabase", runId);
        }
    }

    private void startSimulations() {
        SoftwareIdentification simulatorIdentification = message.getSoftwareIdentification();

            String url;
            try {
                url = dataServiceAccessor.getURLForSoftwareIdentification(simulatorIdentification, authentication);
            } catch (DataServiceException e1) {
                ErrorUtils.reportError(
                        "Apollo database key not found attempting to get URL for simulator: "
                                + simulatorIdentification.getSoftwareName() + ", version: "
                                + simulatorIdentification.getSoftwareVersion() + ", developer: "
                                + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                                + e1.getMessage(), runId);
                return;
            }

            try{
                JobRunningServiceAccessor simulatorServiceAccessor = new JobRunningServiceAccessor(url);
                simulatorServiceAccessor.run(runId, authentication);
            } catch (JobRunningServiceException | WebServiceException e) {
                ErrorUtils.reportError("Error calling runSimulations(): " + "\n\tError was: " + e.getMessage(),
                        runId);
                return;
            }

            try {
                dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, simulatorIdentification, authentication);
            } catch (DataServiceException ex) {
                ErrorUtils.reportError("Apollo database key not found attempting to update last service"
                        + " call for run id " + runId + ": " + ex.getMessage(), runId);
            }

    }

    @Override
    public void run() {
        if (error != null) {
            SoftwareIdentification simulatorIdentification = message
                    .getSoftwareIdentification();

            ErrorUtils.reportError(
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

//    @Override
//    public void setAuthenticationPasswordFieldToBlank() {
//        message.getAuthentication().setRequesterPassword("");
//    }

*/

}
