package edu.pitt.apollo.db;

import junit.framework.TestCase;
//import edu.pitt.apollo.TutorialChapter2_BasicRunSimulationExample;

public class FilesTest extends TestCase {

	int runKey1 = -1;
	int runKey2 = -1;
	int runKey3 = -1;
	int runKey4 = -1;
	
	
	public FilesTest() {

	}
	
	public void testTemp() {
		assert(true);
	}

//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		RunSimulationMessage message  = new TutorialChapter2_BasicRunSimulationExample().getRunSimulationMessage();
//		message.getAuthentication().setRequesterId("test_user");
//		message.getAuthentication().setRequesterPassword("test_password");
//		runKey1 = DbUtils.getRunKey(message);
//		assertEquals(1, runKey1);
//		message.getInfectiousDiseaseScenario().getInfections().get(0).getInfectionAcquisition().get(0).getFromInfectiousHost().setBasicReproductionNumber(3.0);
//		runKey2 = DbUtils.getRunKey(message);
//		assertEquals(2, runKey2);
//		message.getInfectiousDiseaseScenario().getInfections().get(0).getInfectionAcquisition().get(0).getFromInfectiousHost().setBasicReproductionNumber(3.1);
//		runKey3 = DbUtils.getRunKey(message);
//		assertEquals(3, runKey3);
//		message.getInfectiousDiseaseScenario().getInfections().get(0).getInfectionAcquisition().get(0).getFromInfectiousHost().setBasicReproductionNumber(3.2);
//		runKey4 = DbUtils.getRunKey(message);
//		assertEquals(4, runKey4);
//	}
//	
//	
//	public void testAddFredConfigurationFile() throws SQLException, ClassNotFoundException, IOException {
//		InputStream is = new FileInputStream(new File("./src/test/resources/config.txt"));
//		int dataContentKey = Files.addTextDataContent(is);	
//		is = new FileInputStream(new File("./src/test/resources/config.txt"));
//		int dataContentKey2 = Files.addTextDataContent(is);
//		is = new FileInputStream(new File("./src/test/resources/config.txt"));
//		int dataContentKey3 = Files.addTextDataContent(is);
//		is = new FileInputStream(new File("./src/test/resources/config.txt"));
//		int dataContentKey4 = Files.addTextDataContent(is);
//		Files.associateContentWithRunId(runKey1, dataContentKey, "text", "config.txt", "configuration file", "translator", "FRED");
//		Files.associateContentWithRunId(runKey2, dataContentKey, "text", "config.txt", "configuration file", "translator", "FRED");
//		Files.associateContentWithRunId(runKey3, dataContentKey, "text", "config.txt", "configuration file", "translator", "FRED");
//		Files.associateContentWithRunId(runKey4, dataContentKey, "text", "config.txt", "configuration file", "translator", "FRED");
//	}
//	
//	public void testGetFredConfigurationFile() {
//		try {
//			int runDataDescriptionId = Files.getRunDataDescriptionId("text", "config.txt", "configuration file", "translator", "FRED");
//			assertEquals(1, runDataDescriptionId);
//			
//			
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			fail(e.getMessage());
//		} catch (ClassNotFoundException e) {
//
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//		
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//
//	}
//}
}
