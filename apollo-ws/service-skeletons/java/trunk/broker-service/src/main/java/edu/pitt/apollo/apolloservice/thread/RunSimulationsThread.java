package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.BatchException;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceAccessor;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
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

public class RunSimulationsThread extends RunApolloServiceThread {

    private static final String FILE_NAME_FOR_INPUTS_WITH_RUN_IDS = "batch_inputs_with_run_ids.txt";
    private static final SoftwareIdentification brokerServiceSoftwareId;
    private static final int BROKER_SERVICE_SOFTWARE_ID_KEY;
    private final Authentication authentication;
    Exception error = null;
    private final RunSimulationsMessage message;
    BigInteger simulationGroupId;

    private URL configFileUrl;


    public static final Random rng = new Random(System.currentTimeMillis());


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

    public void queueAndTranslateRuns() {
        String filename = null;
        SoftwareIdentification simulatorIdentification = message
                .getSimulatorIdentification();

        XMLGregorianCalendar scenarioDate = (XMLGregorianCalendar) message.getBaseInfectiousDiseaseScenario().getScenarioDate().clone();
        try {
            try {
                filename = downloadUrlToFile(configFileUrl);
            } catch (BatchException ex) {
                ApolloServiceErrorHandler.writeErrorToDatabase(
                        "Error downloading batch configuration file, error was: "
                                + simulatorIdentification.getSoftwareName()
                                + ", version: "
                                + simulatorIdentification.getSoftwareVersion()
                                + ", developer: "
                                + simulatorIdentification
                                .getSoftwareDeveloper()
                                + " for run id " + runId + ": "
                                + ex.getMessage(), runId, dbUtils);
                return;

            } catch (IOException ex) {
                ApolloServiceErrorHandler.writeErrorToDatabase(
                        "Error downloading batch configuration file, error was: "
                                + simulatorIdentification.getSoftwareName()
                                + ", version: "
                                + simulatorIdentification.getSoftwareVersion()
                                + ", developer: "
                                + simulatorIdentification
                                .getSoftwareDeveloper()
                                + " for run id " + runId + ": "
                                + ex.getMessage(), runId, dbUtils);
                return;

            }

            File inputsFile = new File(filename);
//			String inputsFileWithRunIdsPath = OUTPUT_DIRECTORY + runId + File.separator + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;
//			String urlForFileWithRunIds = BASE_URL + runId + "/" + FILE_NAME_FOR_INPUTS_WITH_RUN_IDS;

//			File inputFileWithRunIds = new File(inputsFileWithRunIdsPath);
//			inputFileWithRunIds.getParentFile().mkdirs();
            StringBuilder stBuild = new StringBuilder();

            BufferedReader br = new BufferedReader(new FileReader(inputsFile));
            try {

                //Start the threads on the input file!!!

                boolean translatorRunSuccessful = TranslatorServiceAccessor
                        .runTranslatorAndReturnIfRunWasSuccessful(
                                runId, dbUtils);

                if (!translatorRunSuccessful) {
                    return;
                }
            } finally {
                br.close();
            }

            try {
                // add the text data content for the URL
                int dataContentKey = dbUtils.addTextDataContent(stBuild.toString());
                int runDataDescriptionId = dbUtils.getRunDataDescriptionId(ApolloDbUtils.DbContentDataFormatEnum.TEXT, FILE_NAME_FOR_INPUTS_WITH_RUN_IDS,
                        ApolloDbUtils.DbContentDataType.CONFIGURATION_FILE, BROKER_SERVICE_SOFTWARE_ID_KEY, ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID);
                dbUtils.associateContentWithRunId(runId, dataContentKey, runDataDescriptionId);

            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.writeErrorToDatabase(
                        "ApolloDatabaseException inserting text data content for run: " + ex.getMessage(), runId, dbUtils);
            }

        } catch (IOException e) {
            logger.error("Error writing error file!: " + e.getMessage());
        }

    }

    public RunSimulationsThread(Authentication authentication,
                                RunSimulationsMessage message, BigInteger runId,
                                BigInteger simulationGroupId) {
        super(runId);
        this.message = message;
        this.simulationGroupId = simulationGroupId;
        this.authentication = authentication;
        try {
            this.configFileUrl = new URL(message.getBatchConfigurationFile());
        } catch (MalformedURLException e) {
            error = e;
        }
    }

    private void startSimulations() {
        SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
        String url = null;

        try {

            url = dbUtils.getUrlForSoftwareIdentification(simulatorIdentification);
            SimulatorServiceEI simulatorPort;
            try {
                simulatorPort = new SimulatorServiceV300(new URL(url)).getSimulatorServiceEndpoint();
            } catch (Exception e) {
                ApolloServiceErrorHandler.writeErrorToDatabase(
                        "Unable to get simulator port for url: " + url + "\n\tError was: " + e.getMessage(),
                        runId, dbUtils);
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
                ApolloServiceErrorHandler.writeErrorToDatabase("Error calling runSimulations(): " + "\n\tError was: " + e.getMessage(),
                        runId, dbUtils);
                return;
            }
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            ApolloServiceErrorHandler.writeErrorToDatabase(
                    "Apollo database key not found attempting to get URL for simulator: "
                            + simulatorIdentification.getSoftwareName() + ", version: "
                            + simulatorIdentification.getSoftwareVersion() + ", developer: "
                            + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                            + ex.getMessage(), runId, dbUtils);
            return;
        } catch (ApolloDatabaseException ex) {
            ApolloServiceErrorHandler.writeErrorToDatabase(
                    "ApolloDatabaseException attempting to create port for simulator: "
                            + simulatorIdentification.getSoftwareName() + ", version: "
                            + simulatorIdentification.getSoftwareVersion() + ", developer: "
                            + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
                            + ". Error message was: " + ex.getMessage(), runId, dbUtils);
            return;
        }
        try {
            dbUtils.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
        } catch (ApolloDatabaseKeyNotFoundException ex) {
            ApolloServiceErrorHandler.writeErrorToDatabase("Apollo database key not found attempting to update last service"
                    + " call for run id " + runId + ": " + ex.getMessage(), runId, dbUtils);
        } catch (ApolloDatabaseException ex) {
            ApolloServiceErrorHandler.writeErrorToDatabase("ApolloDatabaseException attempting to update last service" + " call for run id "
                    + runId + ": " + ex.getMessage(), runId, dbUtils);
        }
    }

    @Override
    public void run() {
        if (error != null) {
            SoftwareIdentification simulatorIdentification = message
                    .getSimulatorIdentification();

            ApolloServiceErrorHandler.writeErrorToDatabase(
                    "Error staging batch run for: "
                            + simulatorIdentification.getSoftwareName()
                            + ", version: "
                            + simulatorIdentification.getSoftwareVersion()
                            + ", developer: "
                            + simulatorIdentification
                            .getSoftwareDeveloper()
                            + " for run id " + runId + ": "
                            + error.getMessage(), runId, dbUtils);

            return;
        }
        queueAndTranslateRuns();
        startSimulations();

    }

    @Override
    public void setAuthenticationPasswordFieldToBlank() {
        message.getAuthentication().setRequesterPassword("");
    }

    static {

        brokerServiceSoftwareId = new SoftwareIdentification();
        brokerServiceSoftwareId.setSoftwareDeveloper("UPitt");
        brokerServiceSoftwareId.setSoftwareName("Broker Service");
        brokerServiceSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.BROKER);
        brokerServiceSoftwareId.setSoftwareVersion("3.0.0");

        try {
            BROKER_SERVICE_SOFTWARE_ID_KEY = dbUtils.getSoftwareIdentificationKey(brokerServiceSoftwareId);
        } catch (ApolloDatabaseException ex) {
            throw new ExceptionInInitializerError("ApolloDatabaseException getting broker service software key: " + ex.getMessage());
        }
    }

}
