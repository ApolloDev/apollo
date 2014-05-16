package edu.pitt.apollo.apolloclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import junit.framework.TestCase;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.examples.runsimulationmessages.ExampleRunSimulationMessageFactory;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 6, 2014
 * Time: 5:52:14 PM
 * Class: ConfigurationFileTest
 * IDE: NetBeans 6.9.1
 */
public class ConfigurationFileTest extends TestCase {

    private static final String RES_DIR = "./src/test/resources/fred";
    private static final String CONFIG_FILES_DIRECTORY = RES_DIR + "/cfg-files/";
    private static final String OUTPUT_DIRECTORY = RES_DIR + "/output/";
    private static final String NATIVE_FILE_LABEL = "config.txt";
    private static final String VERBOSE_FILE_LABEL = "verbose.html";
    private static final String APOLLO_WORKDIR_ENVIRONMENT_VARIABLE = "APOLLO_201_WORK_DIR";
    private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
    private static String APOLLO_DIR;
    private static ApolloDbUtils dbUtils;
    private static ServiceRegistrationRecord translatorServiceRecord;
    private static int runId;
    private RunSimulationMessage message;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Map<String, String> env = System.getenv();
        String apolloDir = env.get(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
        if (apolloDir != null) {
            if (!apolloDir.endsWith(File.separator)) {
                apolloDir += File.separator;
            }
            APOLLO_DIR = apolloDir;
            System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:"
                    + APOLLO_DIR);
        } else {
            System.out.println(APOLLO_WORKDIR_ENVIRONMENT_VARIABLE
                    + " environment variable not found!");
            APOLLO_DIR = "";
        }

        try {
            dbUtils = new ApolloDbUtils(new File(APOLLO_DIR + DATABASE_PROPERTIES_FILENAME));
        } catch (IOException ex) {
            throw new ExceptionInInitializerError("IOException initializing ApolloDbUtils: " + ex.getMessage());
        }

        try {
            Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
            for (Integer id : softwareIdMap.keySet()) {
                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
                if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
                    translatorServiceRecord = softwareIdMap.get(id);
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("ClassNotFoundException attempting to load the translator service record: "
                    + ex.getMessage());
        } catch (SQLException ex) {
            throw new ExceptionInInitializerError("SQLException attempting to load the translator service record: "
                    + ex.getMessage());
        }

        if (translatorServiceRecord == null) {
            throw new ExceptionInInitializerError("Translator ServiceRegistrationRecord object could not be initialized");
        }

        SoftwareIdentification fredSoftwareId = new SoftwareIdentification();
        fredSoftwareId.setSoftwareDeveloper("UPitt,PSC,CMU");
        fredSoftwareId.setSoftwareName("FRED");
        fredSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
        fredSoftwareId.setSoftwareVersion("2.0.1_i");

        message = ExampleRunSimulationMessageFactory.getRunSimulationMessage();
        message.setSimulatorIdentification(fredSoftwareId);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        System.out.println("Closing database connection...");
        dbUtils.closeConnection();
    }

    public void testRunningFred() {
        TutorialChapter2_RunSimulationWithNoIntervention example;
        try {
            example = new TutorialChapter2_RunSimulationWithNoIntervention();
        } catch (MalformedURLException ex) {
            fail("MalformedURLException creating TutorialChapter2_BasicRunSimulationExample: " + ex.getMessage());
            return;
        }
        try {
            String runIdAsString = TutorialWebServiceClient.runSimulation(message);
            if (runIdAsString == null) {
                assert false;
            } else {
                runId = Integer.parseInt(runIdAsString);
                assert true;
            }
        } catch (Exception ex) {
            fail("Exception running the simulator: " + ex.getMessage());
        }
    }

    public void testConfigurationFilesExist() throws FileNotFoundException {
        // first get the configuration file from the database
        Map<String, ByteArrayOutputStream> map;
        try {
            int translatorKey = dbUtils.getSoftwareIdentificationKey(translatorServiceRecord.getSoftwareIdentification());
            int simulatorKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
            map = dbUtils.getDataContentForSoftware(runId,
                    translatorKey, simulatorKey);
            // update this
        } catch (ApolloDatabaseException ex) {
            fail(ex.getMessage());
            return;
        }

        String configurationFileContent = null;
        String verboseContent = null;
        for (String label : map.keySet()) {
            if (label.equals(NATIVE_FILE_LABEL)) {
                configurationFileContent = map.get(label).toString();
            } else if (label.equals(VERBOSE_FILE_LABEL)) {
                verboseContent = map.get(label).toString();
            }
        }

        if (configurationFileContent == null || verboseContent == null) {
            assert false;
        } else {

            PrintStream ps = new PrintStream(new File(OUTPUT_DIRECTORY + NATIVE_FILE_LABEL));
            ps.print(configurationFileContent);
            ps.close();

            ps = new PrintStream(new File(OUTPUT_DIRECTORY + VERBOSE_FILE_LABEL));
            ps.print(verboseContent);
            ps.close();

            assert true;
        }
    }

    public void testConfigurationFilesAreCorrect() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + NATIVE_FILE_LABEL));
        scanner.useDelimiter("\\Z");
        String storedNativeContent = scanner.next();

        scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + NATIVE_FILE_LABEL));
        scanner.useDelimiter("\\Z");
        String nativeContent = scanner.next();

        if (!storedNativeContent.equals(nativeContent)) {
            assert false;
        }

        scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + VERBOSE_FILE_LABEL));
        scanner.useDelimiter("\\Z");
        String storedVerboseContent = scanner.next();

        scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + VERBOSE_FILE_LABEL));
        scanner.useDelimiter("\\Z");
        String verboseContent = scanner.next();

        if (!storedVerboseContent.equals(verboseContent)) {
            assert false;
        }

        assert true;
    }
}
