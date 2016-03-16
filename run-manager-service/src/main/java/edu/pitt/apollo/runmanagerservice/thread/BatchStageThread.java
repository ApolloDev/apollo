package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.ApolloServiceThread;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.exception.BatchException;
import edu.pitt.apollo.runmanagerservice.methods.stage.BaseStageMethod;
import edu.pitt.apollo.runmanagerservice.thread.StageInDbWorkerThread;
import edu.pitt.apollo.runmanagerservice.thread.StatusUpdaterThread;
import edu.pitt.apollo.runmanagerservice.types.SynchronizedStringBuilder;
import edu.pitt.apollo.runmanagerservice.utils.ErrorUtils;
import edu.pitt.apollo.services_common.v4_0.*;
import edu.pitt.apollo.types.v4_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import org.slf4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by jdl50 on 6/9/15.
 */
public class BatchStageThread extends ApolloServiceThread {

    public static final Random rng = new Random(System.currentTimeMillis());
    private static final String FILE_NAME_FOR_INPUTS_WITH_RUN_IDS = "batch_inputs_with_run_ids.txt";
    private static SoftwareIdentification brokerServiceSoftwareIdentification;
    private static SoftwareIdentification endUserSoftwareIdentifcation;
    static Logger logger = org.slf4j.LoggerFactory.getLogger(BatchStageThread.class);

    private static void loadSoftwareIdentifications(Authentication authentication) throws DatastoreException {

        if (brokerServiceSoftwareIdentification == null) {
            DatastoreAccessor accessor = new DatastoreAccessor();
            List<ServiceRegistrationRecord> records = accessor.getListOfRegisteredSoftwareRecords(authentication);
            for (ServiceRegistrationRecord registrationRecord : records) {
                if (registrationRecord.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.BROKER)) {
                    brokerServiceSoftwareIdentification = registrationRecord.getSoftwareIdentification();
                } else if (registrationRecord.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.END_USER_APPLICATION)) {
                    endUserSoftwareIdentifcation = registrationRecord.getSoftwareIdentification();
                }
            }
        }

    }

    BigInteger batchRunId = null;
    DatastoreAccessor dataServiceAccessor = null;
    private RunSimulationsMessage runSimulationsMessage;
    private URL configFileUrl = null;
    private Authentication authentication;
    private List<BigInteger> failedRunIds = Collections.synchronizedList(new ArrayList<BigInteger>());
    public BatchStageThread(BigInteger batchRunId, RunSimulationsMessage runSimulationsMessage,
                            Authentication authentication, ApolloServiceQueue queue) throws MalformedURLException, DatastoreException {
        super(batchRunId, queue);
        this.dataServiceAccessor = new DatastoreAccessor();
        this.batchRunId = batchRunId;
        this.authentication = authentication;
        this.runSimulationsMessage = runSimulationsMessage;
        this.configFileUrl = new URL(runSimulationsMessage.getBatchConfigurationFile());

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

    private void addBatchInputsWithRunIdsFileToDatabase(SynchronizedStringBuilder sb) throws DatastoreException, FilestoreException {

        loadSoftwareIdentifications(authentication);

		FileIdentification fileIdentification = new FileIdentification();
		fileIdentification.setLabel(FILE_NAME_FOR_INPUTS_WITH_RUN_IDS);
		fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
		fileIdentification.setType(ContentDataTypeEnum.CONFIGURATION_FILE);
		
        dataServiceAccessor.uploadTextFileContent(sb.toString(), runId, fileIdentification, authentication);

    }

    @Override
    public void runApolloService() {
        String filename = null;

        XMLGregorianCalendar scenarioDate = (XMLGregorianCalendar) runSimulationsMessage.getBaseInfectiousDiseaseScenario().getScenarioDate().clone();
        try {
            try {
                filename = downloadUrlToFile(configFileUrl);
            } catch (BatchException | IOException ex) {
                ErrorUtils.reportError(batchRunId, "Error downloading batch configuration file, error was: " + ex.getMessage(), authentication);
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
                timer.schedule(new StatusUpdaterThread(dataServiceAccessor, batchRunId, counter, error, authentication), 0, 1000);

                while ((line = br.readLine()) != null) {

                    Runnable worker = new StageInDbWorkerThread(batchRunId, line, runSimulationsMessage, scenarioDate, stBuild, error, counter, failedRunIds, authentication);
                    executor.execute(worker);
                    if (error.value) {
                        break;
                    }
                }

                if (executor != null) {
                    executor.shutdown();
                    logger.debug("Waiting for all StageInDbWorkerThreads to finish");
                    try {
                        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException ex) {
                        logger.error("InterruptedException");
                    }
                    logger.debug("All StageInDbWorkerThreads finished!");
                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timer.cancel();

                if (failedRunIds.size() > 0) {

                    String listOfFailedRunIds = failedRunIds.toString();

                    ErrorUtils.reportError(batchRunId, "Error staging runs in database. The following run IDs have a status of FAILED after staging: "
                                    + listOfFailedRunIds + ". The batch runId is " + batchRunId,
                            authentication);
                    return;
                }

                addBatchInputsWithRunIdsFileToDatabase(stBuild);

                MethodCallStatus status = dataServiceAccessor.getRunStatus(batchRunId, authentication);
                System.out.println("TEST COMPLETE!");
                if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                    ErrorUtils.reportError(batchRunId, "Error staging runs in database, error was " + status.getMessage() + " for runId",
                            authentication);
                    return;
                }

                logger.debug("Finished all threads!");
				
				DatastoreAccessor.zipAndUploadBatchRunDir(batchRunId);
				
				// now the translator needs to be called
				BaseStageMethod.translateRun(runId, dataServiceAccessor, authentication);
				
                dataServiceAccessor.updateStatusOfRun(batchRunId, MethodCallStatusEnum.TRANSLATION_COMPLETED,
                        "All runs for this batch have been translated", authentication);


            } catch (RunManagementException | FilestoreException | TranslatorServiceException e) {
                ErrorUtils.reportError(batchRunId, "DB Error queuing and translating runs, error was " + e.getMessage(), authentication);
            } finally {
                br.close();
            }

        } catch (IOException e) {
            logger.error("Error writing error file!: " + e.getMessage());
        }

    }

    public class BooleanRef {
        public boolean value;
    }

    public class CounterRef {
        public int count = 0;
    }

}
