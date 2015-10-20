package edu.pitt.apollo.apolloclient;

import java.math.BigInteger;

import junit.framework.TestCase;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.services_common.v3_1_0.ServiceRegistrationRecord;
import edu.pitt.apollo.simulator_service_types.v3_1_0.RunSimulationMessage;


/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 6, 2014 Time:
 * 5:52:14 PM Class: ConfigurationFileTest IDE: NetBeans 6.9.1
 */
public class ConfigurationFileTest extends TestCase {

	private static final String RES_DIR = "./src/test/resources/fred";
	private static final String CONFIG_FILES_DIRECTORY = RES_DIR + "/cfg-files/";
	private static final String OUTPUT_DIRECTORY = RES_DIR + "/output/";
	private static final String NATIVE_FILE_LABEL = "config.txt";
	private static final String VERBOSE_FILE_LABEL = "verbose.html";
	private static final String DATABASE_PROPERTIES_FILENAME = "database.properties";
	private static String APOLLO_DIR;
	private static ApolloDbUtils dbUtils;
	private static ServiceRegistrationRecord translatorServiceRecord;
	private static BigInteger runId;
	private RunSimulationMessage message;

	public void testTemp() {
		assert(true);
	}
	
//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		Map<String, String> env = System.getenv();
//		String apolloDir = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
//		if (apolloDir != null) {
//			if (!apolloDir.endsWith(File.separator)) {
//				apolloDir += File.separator;
//			}
//			APOLLO_DIR = apolloDir;
//			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " is now:" + APOLLO_DIR);
//		} else {
//			System.out.println(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE + " environment variable not found!");
//			APOLLO_DIR = "";
//		}
//
//		try {
//			dbUtils = new ApolloDbUtils(new File(APOLLO_DIR + DATABASE_PROPERTIES_FILENAME));
//		} catch (IOException ex) {
//			throw new ExceptionInInitializerError("IOException initializing ApolloDbUtils: " + ex.getMessage());
//		}
//
//		try {
//			Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
//			for (Integer id : softwareIdMap.keySet()) {
//				SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
//				if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
//					translatorServiceRecord = softwareIdMap.get(id);
//					break;
//				}
//			}
//		} catch (ClassNotFoundException ex) {
//			throw new ExceptionInInitializerError("ClassNotFoundException attempting to load the translator service record: "
//					+ ex.getMessage());
//		} catch (SQLException ex) {
//			throw new ExceptionInInitializerError("SQLException attempting to load the translator service record: "
//					+ ex.getMessage());
//		}
//
//		if (translatorServiceRecord == null) {
//			throw new ExceptionInInitializerError("Translator ServiceRegistrationRecord object could not be initialized");
//		}
//
//		SoftwareIdentification fredSoftwareId = new SoftwareIdentification();
//		fredSoftwareId.setSoftwareDeveloper("UPitt,PSC,CMU");
//		fredSoftwareId.setSoftwareName("FRED");
//		fredSoftwareId.setSoftwareType(ApolloSoftwareTypeEnum.SIMULATOR);
//		fredSoftwareId.setSoftwareVersion("2.0.1_i");
//
//		message = ApolloServiceTypeFactory.getMinimalistRunSimulationMessage(SimulatorIdentificationEnum.FRED);
//		message.setSimulatorIdentification(fredSoftwareId);
//
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		super.tearDown();
//		System.out.println("Closing database connection...");
//		dbUtils.closeConnection();
//	}
//
//	public void testRunningFred() {
//		
//
//		try {
//			BigInteger runId = TutorialWebServiceClient.runSimulation(message);
//			if (runId == null) {
//				assert false;
//			} else {
//				assert true;
//			}
//		} catch (Exception ex) {
//			fail("Exception running the simulator: " + ex.getMessage());
//		}
//	}
//
//	public void testConfigurationFilesExist() throws FileNotFoundException {
//		// first get the configuration file from the database
//		Map<String, ByteArrayOutputStream> map;
//		try {
//			int translatorKey = dbUtils.getSoftwareIdentificationKey(translatorServiceRecord.getSoftwareIdentification());
//			int simulatorKey = dbUtils.getSoftwareIdentificationKey(message.getSimulatorIdentification());
//			map = dbUtils.getDataContentForSoftware(runId, translatorKey, simulatorKey);
//			// update this
//		} catch (ApolloDatabaseException ex) {
//			fail(ex.getMessage());
//			return;
//		}
//
//		String configurationFileContent = null;
//		String verboseContent = null;
//		for (String label : map.keySet()) {
//			if (label.equals(NATIVE_FILE_LABEL)) {
//				configurationFileContent = map.get(label).toString();
//			} else if (label.equals(VERBOSE_FILE_LABEL)) {
//				verboseContent = map.get(label).toString();
//			}
//		}
//
//		if (configurationFileContent == null || verboseContent == null) {
//			assert false;
//		} else {
//
//			PrintStream ps = new PrintStream(new File(OUTPUT_DIRECTORY + NATIVE_FILE_LABEL));
//			ps.print(configurationFileContent);
//			ps.close();
//
//			ps = new PrintStream(new File(OUTPUT_DIRECTORY + VERBOSE_FILE_LABEL));
//			ps.print(verboseContent);
//			ps.close();
//
//			assert true;
//		}
//	}
//
//	public void testConfigurationFilesAreCorrect() throws FileNotFoundException {
//
//		Scanner scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + NATIVE_FILE_LABEL));
//		scanner.useDelimiter("\\Z");
//		String storedNativeContent = scanner.next();
//		scanner.close();
//
//		scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + NATIVE_FILE_LABEL));
//		scanner.useDelimiter("\\Z");
//		String nativeContent = scanner.next();
//		scanner.close();
//
//		if (!storedNativeContent.equals(nativeContent)) {
//			assert false;
//		}
//
//		scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + VERBOSE_FILE_LABEL));
//		scanner.useDelimiter("\\Z");
//		String storedVerboseContent = scanner.next();
//		scanner.close();
//
//		scanner = new Scanner(new File(CONFIG_FILES_DIRECTORY + VERBOSE_FILE_LABEL));
//		scanner.useDelimiter("\\Z");
//		String verboseContent = scanner.next();
//		scanner.close();
//
//		if (!storedVerboseContent.equals(verboseContent)) {
//			assert false;
//		}
//
//		assert true;
//	}
}
