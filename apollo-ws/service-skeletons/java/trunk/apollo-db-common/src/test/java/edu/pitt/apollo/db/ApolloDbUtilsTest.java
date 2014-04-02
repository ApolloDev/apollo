package edu.pitt.apollo.db;

import java.sql.SQLException;

import junit.framework.TestCase;
import edu.pitt.apollo.types.v2_0_1.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v2_0_1.Authentication;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

public class ApolloDbUtilsTest extends TestCase {

	//public TutorialChapter2_BasicRunSimulationExample tutorial;
	private SoftwareIdentification fredSoftwareId;
	private SoftwareIdentification gaiaSoftwareId;
	private Authentication authUser1;
	private Authentication authUser2;

	public ApolloDbUtilsTest() {
		// TODO Auto-generated constructor stub
	}

	// Steps to insert a run:
	// 1. Populate or read softwareIdentification from software_identification
	// table
	// 2. Populate or read Authentication from users table
	// 3. Create a run record
	// 4. Create data_content
	// 5. Associate data content with run
//	@Override
//	protected void setUp() throws Exception {
//		// TODO Auto-generated method stub
//		super.setUp();
//		TutorialChapter2_BasicRunSimulationExample tutorial = new TutorialChapter2_BasicRunSimulationExample();
//
//		authUser1 = new Authentication();
//		authUser1.setRequesterId("user1");
//		authUser1.setRequesterPassword("pass1");
//		authUser2 = new Authentication();
//		authUser2.setRequesterId("user2");
//		authUser2.setRequesterPassword("pass2");
//		//insertUsers();
//		
////		fredSoftwareId = tutorial.getSoftwareIdentificationForSimulator();
////		gaiaSoftwareId = tutorial.getSoftwareIdentifiationForGaia();
////		insertSoftwareIds();
//
//	
//
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		// TODO Auto-generated method stub
//		super.tearDown();
//		tutorial = null;
//	}
//
//	private void insertUsers() throws ApolloDatabaseException {
//
//		int userKey;
//		try {
//			userKey = ApolloDbUtils.addUser(authUser1.getRequesterId(), authUser1.getRequesterPassword(), "testemail@email.com");
//			assertEquals(1, userKey);
//
//			userKey = ApolloDbUtils.addUser(authUser1.getRequesterId(), authUser1.getRequesterPassword(), "testemail@email.com");
//			assertEquals(ApolloDbUtils.RECORD_ALREADY_EXISTS, userKey);
//			
//			boolean canRun = true;
//			boolean canViewCache = true;
//			ApolloDbUtils.addRole(tutorial.getSoftwareIdentificationForSimulator(), canRun, canViewCache);
//
//			userKey = ApolloDbUtils.getUserKey(authUser1.getRequesterId(), authUser1.getRequesterPassword());
//			assertEquals(1, userKey);
//
//			userKey = ApolloDbUtils.getUserKey(authUser2.getRequesterId(), authUser2.getRequesterPassword());
//			assertEquals(ApolloDbUtils.RECORD_NOT_FOUND, userKey);
//			
//			userKey = ApolloDbUtils.addUser(authUser2.getRequesterId(), authUser2.getRequesterPassword(), "testemail2@email2.com");
//			assertEquals(2, userKey);
//			
//			userKey = ApolloDbUtils.addUser(authUser2.getRequesterId(), authUser2.getRequesterPassword(), "hello!@hello.com");
//			assertEquals(ApolloDbUtils.RECORD_ALREADY_EXISTS, userKey);
//			
//			userKey = ApolloDbUtils.getUserKey(authUser2.getRequesterId(), authUser2.getRequesterPassword());
//			assertEquals(2, userKey);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/* used by AB to build the "run" record */
//	private void insertSoftwareIds() {
//		// test inserting & retrieving
//		int fredKey = ApolloDbUtils.getSoftwareIdentificationKey(fredSoftwareId);
//		assertEquals(1, fredKey);
//
//		// test inserting the same record multiple times, ensure key value isn't
//		// changed
//		fredKey = ApolloDbUtils.getSoftwareIdentificationKey(fredSoftwareId);
//		assertEquals(1, fredKey);
//
//		// test inserting new software id
//		int gaiaKey = ApolloDbUtils.getSoftwareIdentificationKey(gaiaSoftwareId);
//		assertEquals(2, gaiaKey);
//	}
//
//	public int insertTextContent() {
//		int key = ApolloDbUtils.insertTextContent("This is a sample config file!");
//		assertEquals(1, key);
//		return key;
//
//	}
//
//	private void insertCfgFile() {
//		// FRED CONFIG FILE
//	}
//
//	public void testGetSoftwareIdentification() {
//		SoftwareIdentification fredSoftwareId = ApolloDbUtils.getSoftwareIdentification(1); // 1
//																						// ==
//																						// FRED
//		SoftwareIdentification gaiaSoftwareId = ApolloDbUtils.getSoftwareIdentification(2); // 2
//																						// ==
//																						// GAIA
//
//		assertEquals("UPitt,PSC,CMU", fredSoftwareId.getSoftwareDeveloper());
//		assertEquals("FRED", fredSoftwareId.getSoftwareName());
//		assertEquals("2.0.1_i", fredSoftwareId.getSoftwareVersion());
//		assertEquals(ApolloSoftwareTypeEnum.SIMULATOR, fredSoftwareId.getSoftwareType());
//
//		assertEquals("PSC", fredSoftwareId.getSoftwareDeveloper());
//		assertEquals("GAIA", fredSoftwareId.getSoftwareName());
//		assertEquals("1.0", fredSoftwareId.getSoftwareVersion());
//		assertEquals(ApolloSoftwareTypeEnum.VISUALIZER, fredSoftwareId.getSoftwareType());
//	}
//
//	public void testGetAuthentication() {
//		Authentication auth = ApolloDbUtils.getAuthentication(1);
//		assertEquals("user1", auth.getRequesterId());
//		assertEquals("user1pw", auth.getRequesterPassword());
//
//		auth = ApolloDbUtils.getAuthentication(2);
//		assertEquals("user2", auth.getRequesterId());
//		assertEquals("user2pw", auth.getRequesterPassword());
//	}
//
////	public void testInsertRun() {
////		int runKey = ApolloDbUtils.getRunKey(tutorial.getRunSimulationMessage());
////
////		assertEquals(1, runKey);
////		int dataContentKey = insertTextContent();
////		String dataFormat = "text";
////		String dataLabel = "config.txt";
////		String dataType = "configuration file";
////		String dataSourceSoftware = "translator";
////		String dataDestinationSoftware = "FRED";
////
////		int runDataKey = ApolloDbUtils.addContentToRun(runKey, dataContentKey, dataFormat, dataLabel, dataType, dataSourceSoftware,
////				dataDestinationSoftware);
////	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
