package edu.pitt.apollo.db;

import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import junit.framework.TestCase;


public class ApolloDbUtilsTest extends TestCase {

    // public TutorialChapter2_BasicRunSimulationExample tutorial;
    private Authentication authUser1;
    private Authentication authUser2;
    RunSimulationMessage message;
    private ApolloDbUtils apolloDbUtils;
    private static String APOLLO_DIR = "";
    private static final String DATABASE_PROPERTIES_FILE = "database.properties";


    public ApolloDbUtilsTest() {

    }

	public void testTemp() {
		assert(true);
	}
	
    // Steps to insert a run:
    // 1. Populate or read softwareIdentification from software_identification
    // table
    // 2. Populate or read Authentication from users table
    // 3. Create a run record
    // 4. Create data_content
    // 5. Associate data content with run
//    @Override
//    protected void setUp() throws Exception {
//
//        super.setUp();
//        Map<String, String> env = System.getenv();
//        APOLLO_DIR = env.get(GlobalConstants.APOLLO_WORKDIR_ENVIRONMENT_VARIABLE);
//        apolloDbUtils = new ApolloDbUtils(new File(APOLLO_DIR + "/" + DATABASE_PROPERTIES_FILE));
//
//        authUser1 = new Authentication();
//        authUser1.setRequesterId("user1");
//        authUser1.setRequesterPassword("pass1");
//        authUser2 = new Authentication();
//        authUser2.setRequesterId("user2");
//        authUser2.setRequesterPassword("pass2");
//
//        message = ApolloServiceTypeFactory.getDefaultRunSimulationMessage(SimulatorIdentificationEnum.FRED);
        // insertUsers();

        // fredSoftwareId = tutorial.getSoftwareIdentificationForSimulator();
        // gaiaSoftwareId = tutorial.getSoftwareIdentifiationForGaia();
        // insertSoftwareIds();

//    }

//    @Override
//    protected void tearDown() throws Exception {
//
//        super.tearDown();
//        message = null;
//    }

//	private void insertUsers() throws ApolloDatabaseException, ApolloDatabaseRecordAlreadyExistsException {
//
//		int userKey;
//		try {
//			userKey = apolloDbUtils.addUser(authUser1.getRequesterId(), authUser1.getRequesterPassword(), "testemail@email.com");
//			assertEquals(1, userKey);
//
//			boolean canRun = true;
//			boolean canViewCache = true;
//			apolloDbUtils.addRole(tutorial.getSoftwareIdentificationForSimulator(), canRun, canViewCache);
//
//			userKey = apolloDbUtils.getUserKey(authUser1.getRequesterId(), authUser1.getRequesterPassword());
//			assertEquals(1, userKey);
//
//			// userKey = apolloDbUtils.getUserKey(authUser2.getRequesterId(),
//			// authUser2.getRequesterPassword());
//			// assertEquals(ApolloDbUtils.RECORD_NOT_FOUND, userKey);
//
//			userKey = apolloDbUtils
//					.addUser(authUser2.getRequesterId(), authUser2.getRequesterPassword(), "testemail2@email2.com");
//			assertEquals(2, userKey);
//
//			// userKey = apolloDbUtils.addUser(authUser2.getRequesterId(),
//			// authUser2.getRequesterPassword(), "hello!@hello.com");
//			// assertEquals(ApolloDbUtils.RECORD_ALREADY_EXISTS, userKey);
//
//			userKey = apolloDbUtils.getUserKey(authUser2.getRequesterId(), authUser2.getRequesterPassword());
//			assertEquals(2, userKey);
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//
//			e.printStackTrace();
//		}
//	}
//    public void testAddTextDataContent() {
//        int key;
//        try {
//            key = apolloDbUtils.addTextDataContent("This is a sample config file!");
//            assertEquals(1, key);
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//
//    }

//    public void testGetSoftwareIdentification() {
//        SoftwareIdentification fredSoftwareId;
//        try {
//            fredSoftwareId = apolloDbUtils.getSoftwareIdentification(3);
//            assertEquals("UPitt,PSC,CMU", fredSoftwareId.getSoftwareDeveloper());
//            assertEquals("FRED", fredSoftwareId.getSoftwareName());
//            assertEquals("2.0.1_i", fredSoftwareId.getSoftwareVersion());
//            assertEquals(ApolloSoftwareTypeEnum.SIMULATOR, fredSoftwareId.getSoftwareType());
//        } catch (Exception e) {
//            fail(e.getMessage());
//        } // 2
//        // ==
//        // FRED
//        try {
//            SoftwareIdentification gaiaSoftwareId = apolloDbUtils.getSoftwareIdentification(5);
//            assertEquals("PSC", gaiaSoftwareId.getSoftwareDeveloper());
//            assertEquals("GAIA", gaiaSoftwareId.getSoftwareName());
//            assertEquals("1.0", gaiaSoftwareId.getSoftwareVersion());
//            assertEquals(ApolloSoftwareTypeEnum.VISUALIZER, gaiaSoftwareId.getSoftwareType());
//        } catch (Exception e) {
//            fail(e.getMessage());
//        } // 2
//        // ==
//        // GAIA
//
//    }

    // public void testGetAuthentication() {
    // Authentication auth = apolloDbUtils.getAuthentication(1);
    // assertEquals("user1", auth.getRequesterId());
    // assertEquals("user1pw", auth.getRequesterPassword());
    //
    // auth = apolloDbUtils.getAuthentication(2);
    // assertEquals("user2", auth.getRequesterId());
    // assertEquals("user2pw", auth.getRequesterPassword());
    // }
    // public void testInsertRun() {
    // int runKey = ApolloDbUtils.getRunKey(tutorial.getRunSimulationMessage());
    //
    // assertEquals(1, runKey);
    // int dataContentKey = insertTextContent();
    // String dataFormat = "text";
    // String dataLabel = "config.txt";
    // String dataType = "configuration file";
    // String dataSourceSoftware = "translator";
    // String dataDestinationSoftware = "FRED";
    //
    // int runDataKey = ApolloDbUtils.addContentToRun(runKey, dataContentKey,
    // dataFormat, dataLabel, dataType, dataSourceSoftware,
    // dataDestinationSoftware);
    // }
//    public void testAddSimulationRun() {
//
//        ServiceRegistrationRecord translatorServiceRecord = null;
//        System.out.println("Loading translator software identification");
//        try {
//            Map<Integer, ServiceRegistrationRecord> softwareIdMap = apolloDbUtils.getRegisteredSoftware();
//            for (Integer id : softwareIdMap.keySet()) {
//                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
//                if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
//                    translatorServiceRecord = softwareIdMap.get(id);
//                    break;
//                }
//
//            }
//
//        } catch (ClassNotFoundException ex) {
//            System.out.println("ClassNotFoundException attempting to load the translator service record: "
//                    + ex.getMessage());
//            return;
//        } catch (SQLException ex) {
//            System.out.println("SQLException attempting to load the translator service record: " + ex.getMessage());
//            return;
//        } catch (Exception ex) {
//            System.out.println("Exception attempting to load the translator service record: " + ex.getMessage());
//            return;
//        }
//        
//        if (translatorServiceRecord == null) {
//            System.out.println("Translator service registration record not found");
//            return;
//        }
//
//        try {
//            apolloDbUtils.addSimulationRun(message, 1, translatorServiceRecord.getSoftwareIdentification());
//
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }

//    public void testGetUrlForSoftwareIdentification() {
//        try {
//            assertEquals("http://gaia.pha.psc.edu:8098/pscsimu?wsdl", apolloDbUtils.getUrlForSoftwareIdentification(message.getSimulatorIdentification()));
//        } catch (Exception e) {
//            fail(e.getMessage());
//        }
//    }

//	public void testDeleteRunData() {
//		try {
//			apolloDbUtils.removeRunData(1);
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
    /**
     * @param args
     */
//    public static void main(String[] args) {
//
//    }
}
