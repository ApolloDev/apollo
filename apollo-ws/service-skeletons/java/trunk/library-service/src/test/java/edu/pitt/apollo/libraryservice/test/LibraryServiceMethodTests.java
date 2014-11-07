package edu.pitt.apollo.libraryservice.test;


/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Aug 13, 2014
 * Time: 3:27:28 PM
 * Class: LibraryServiceMethodTests
 */
public class LibraryServiceMethodTests {

//	private static final String TEMP_DIR = "java.io.tmpdir";
//	private static final String DB40_FILE_NAME = "db40_test";
//	private static final String TEST_OBJECT_TYPE = "InfectiousDisease";
//	private static Db4oDatabaseAccessor db40Accessor;
//
//	@BeforeClass
//	public static void setUpResources() {
//		String tempDirectory = System.getProperty(TEMP_DIR);
//		String db4oFilePath = tempDirectory + File.separator + DB40_FILE_NAME;
//		db40Accessor = new Db4oDatabaseAccessor(db4oFilePath);
//	}
//
//	@AfterClass
//	public static void cleanUpResources() {
//		String tempDirectory = System.getProperty(TEMP_DIR);
//		String db4oFilePath = tempDirectory + File.separator + DB40_FILE_NAME;
//		db40Accessor.closeDb40();
//
//		File db4oFile = new File(db4oFilePath);
//		db4oFile.delete();
//	}
//
//	@Test
//	public void testAddLibraryItem() {
//
//		AddLibraryItemResult result = addLibraryItemForTest();
//
//		MethodCallStatus status = result.getMethodCallStatus();
//		checkStatus(status);
//
//		String uuid = result.getUuid();
//		if (uuid == null || uuid.length() == 0) {
//			fail("No UUID was returned");
//		}
//	}
//	
//	@Test
//	public void testAddIdenticalLibraryItems() {
//		
//		AddLibraryItemResult addResult1 = addLibraryItemForTest();
//		MethodCallStatus status1 = addResult1.getMethodCallStatus();
//		checkStatus(status1);
//		
//		AddLibraryItemResult addResult2 = addLibraryItemForTest();
//		MethodCallStatus status2 = addResult2.getMethodCallStatus();
//		checkStatus(status2);
//		
//		String uuid1 = addResult1.getUuid();
//		String uuid2 = addResult2.getUuid();
//		
//		assertEquals(uuid1, uuid2);
//	}
//
//	@Test
//	public void testGetLibrayItem() {
//
//		AddLibraryItemResult addResult = addLibraryItemForTest();
//		String uuid = addResult.getUuid();
//
//		GetLibraryItemResult getResult = GetLibraryItemMethod.getLibraryItemMethod(db40Accessor, uuid);
//		MethodCallStatus status = getResult.getMethodCallStatus();
//		checkStatus(status);
//	}
//
//	@Test
//	public void testGetUuidsForLibraryItemsCreatedSinceDateTime() {
//
//		AddLibraryItemResult addResult = addLibraryItemForTest();
//		String uuid = addResult.getUuid();
//
//		GregorianCalendar c = new GregorianCalendar();
//		c.add(GregorianCalendar.DAY_OF_MONTH, -1); // get objects created since yesterday
//		XMLGregorianCalendar date;
//		try {
//			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
//		} catch (DatatypeConfigurationException ex) {
//			fail("DatatypeConfigurationException: " + ex.getMessage());
//			return;
//		}
//
//		GetLibraryItemUuidsResult getResult = GetUuidsForLibraryItemsCreatedSinceDateTimeMethod
//				.getUuidsForLibraryItemsCreatedSinceDateTime(db40Accessor, date);
//		MethodCallStatus status = getResult.getMethodCallStatus();
//		checkStatus(status);
//
//		checkUuidList(uuid, getResult.getUuids());
//	}
//
//	@Test
//	public void testGetUuidsForLibraryItemsGivenType() {
//
//		AddLibraryItemResult addResult = addLibraryItemForTest();
//		String uuid = addResult.getUuid();
//
//		GetLibraryItemUuidsResult getResult = GetUuidsForLibraryItemsGivenTypeMethod.getUuidsForLibraryItemsGivenType(db40Accessor, TEST_OBJECT_TYPE);
//
//		MethodCallStatus status = getResult.getMethodCallStatus();
//		checkStatus(status);
//
//		checkUuidList(uuid, getResult.getUuids());
//
//	}
//	
//	@Test
//	public void testRemoveLibraryItem() {
//		
//		Authentication authentication = new Authentication();
//		AddLibraryItemResult addResult = addLibraryItemForTest();
//		String uuid = addResult.getUuid();
//		
//		// check item succesfully added
//		GetLibraryItemResult getResult = GetLibraryItemMethod.getLibraryItemMethod(db40Accessor, uuid);
//		MethodCallStatus getStatus = getResult.getMethodCallStatus();
//		checkStatus(getStatus);
//		
//		MethodCallStatus removeStatus = RemoveLibraryItemMethod.removeLibraryItem(db40Accessor, authentication, uuid);
//		checkStatus(removeStatus);
//		
//		// check item succesfully deleted
//		getResult = GetLibraryItemMethod.getLibraryItemMethod(db40Accessor, uuid);
//		getStatus = getResult.getMethodCallStatus();
//		assertEquals(MethodCallStatusEnum.FAILED, getStatus.getStatus());
//	}
//
//	private void checkStatus(MethodCallStatus status) {
//		if (!status.getStatus().equals(MethodCallStatusEnum.COMPLETED)) {
//			fail("Status was " + status.getStatus() + " with message \"" + status.getMessage() + "\"");
//		}
//	}
//
//	private void checkUuidList(String uuid, List<String> uuids) {
//		if (!uuids.contains(uuid)) {
//			fail("Returned list of UUIDs does not contain the added UUID of \"" + uuid + "\"");
//		}
//	}
//
//	private AddLibraryItemResult addLibraryItemForTest() {
//		Authentication authentication = new Authentication();
//
//		InfectiousDisease itemToStoreInLibrary = new InfectiousDisease();
//		itemToStoreInLibrary.setDiseaseId("H1N1");
//
//		ApolloPathogenCode diseaseCode = new ApolloPathogenCode();
//		diseaseCode.setNcbiTaxonId("114727");
//		diseaseCode.setCladeName("Influenza A (H1N1)pdm09");
//		itemToStoreInLibrary.setCausalPathogen(diseaseCode);
//		itemToStoreInLibrary.setSpeciesWithDisease("human");
//
//		DiseaseOutcomeWithProbability diseaseOutcomeWithProbability = new DiseaseOutcomeWithProbability();
//		diseaseOutcomeWithProbability.setDiseaseOutcome(DiseaseOutcomeEnum.ASYMPTOMATIC);
//		ProbabilisticParameter probability = new ProbabilisticParameter();
//		probability.setProbability(0.33);
//		diseaseOutcomeWithProbability.setProbability(probability);
//		itemToStoreInLibrary.getDiseaseOutcomesWithProbabilities().add(diseaseOutcomeWithProbability);
//
//		List<String> itemIndexingLabels = new ArrayList<String>();
//		itemIndexingLabels.add(TEST_OBJECT_TYPE);
//		itemIndexingLabels.add("Influenza");
//		itemIndexingLabels.add("H1N1");
//
//		AddLibraryItemResult result = AddLibraryItemMethod.addLibraryItem(db40Accessor, authentication, itemToStoreInLibrary,
//				"H1N1", "test object", TEST_OBJECT_TYPE, itemIndexingLabels);
//
//		return result;
//	}
}
