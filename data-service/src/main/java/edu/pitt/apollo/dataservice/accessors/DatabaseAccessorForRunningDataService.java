package edu.pitt.apollo.dataservice.accessors;

import static edu.pitt.apollo.ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.data_service_types.v3_0_2.DataRetrievalRequestMessage;
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.types.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v3_0_2.SoftwareIdentification;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 10:24:52 AM Class: DatabaseAccessorForRunningDataService
 */
public class DatabaseAccessorForRunningDataService extends DatabaseAccessor {

	private DataRetrievalRequestMessage dataRetrievalRequestMessage = null;

	private static final SoftwareIdentification DATA_SERVICE_SOFTWARE_ID;

	private static int dataServiceSoftwareKey;

	static {
		DATA_SERVICE_SOFTWARE_ID = new SoftwareIdentification();
		DATA_SERVICE_SOFTWARE_ID.setSoftwareDeveloper("UPitt");
		DATA_SERVICE_SOFTWARE_ID.setSoftwareName("Data Service");
		DATA_SERVICE_SOFTWARE_ID.setSoftwareType(ApolloSoftwareTypeEnum.DATA);
		DATA_SERVICE_SOFTWARE_ID.setSoftwareVersion("1.0");
	}

//	public DatabaseAccessorForRunningDataService(GetOutputFilesURLsMessage message, Authentication authentication, ApolloDbUtils dbUtils) throws ApolloDatabaseException {
//		super(authentication, dbUtils);
//		this.getOutputFilesURLsMessage = message;
//		dataServiceSoftwareKey = getDataServiceSoftwareKey();
//	}
	public DatabaseAccessorForRunningDataService(DataRetrievalRequestMessage message, Authentication authentication) throws ApolloDatabaseException {
		super(authentication);
		this.dataRetrievalRequestMessage = message;
		dataServiceSoftwareKey = getDataServiceSoftwareKey();
	}

//	public DatabaseAccessorForRunningDataService(GetAllOutputFilesURLAsZipMessage message, Authentication authentication, ApolloDbUtils dbUtils) throws ApolloDatabaseException {
//		super(authentication, dbUtils);
//		this.getAllOutputFilesURLAsZipMessage = message;
//		dataServiceSoftwareKey = getDataServiceSoftwareKey();
//	}
	private int getDataServiceSoftwareKey() throws ApolloDatabaseException {
		return dbUtils.getSoftwareIdentificationKey(DATA_SERVICE_SOFTWARE_ID);
	}

	public static SoftwareIdentification getDataServiceSoftwareId() {
		return DATA_SERVICE_SOFTWARE_ID;
	}

//	@Override
//	public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException, Md5UtilsException {
//		List<BigInteger> runIds = null;
//		String targetRunSimulationMessageAsJson = null;
//		if (getOutputFilesURLsMessage != null) {
//			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(getOutputFilesURLsMessage, DATA_SERVICE_SOFTWARE_ID);
//			targetRunSimulationMessageAsJson = jsonUtils.getJSONString(getOutputFilesURLsMessage);
//
//		} else if (DataRetrievalRequestMessage != null) {
//			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(DataRetrievalRequestMessage, DATA_SERVICE_SOFTWARE_ID);
//			targetRunSimulationMessageAsJson = jsonUtils.getJSONString(DataRetrievalRequestMessage);
//		} else if (getAllOutputFilesURLAsZipMessage != null) {
//			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(getAllOutputFilesURLAsZipMessage, DATA_SERVICE_SOFTWARE_ID);
//			targetRunSimulationMessageAsJson = jsonUtils.getJSONString(getAllOutputFilesURLAsZipMessage);
//		}
//
//		if (runIds != null && targetRunSimulationMessageAsJson != null && runIds.size() > 0) {
//			for (BigInteger runIdAssociatedWithRunSimulationMessageHash : runIds) {
//				if (isRunIdAssociatedWithMatchingRunMessage(targetRunSimulationMessageAsJson,
//						runIdAssociatedWithRunSimulationMessageHash)) {
//					return runIdAssociatedWithRunSimulationMessageHash;
//				}
//			}
//			return null;
//		} else {
//			return null;
//		}
//	}
	@Override
	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> currentRunMessageAsJsonMap
				= dbUtils.getDataContentForSoftware(
						runId, END_USER_APPLICATION_SOURCE_ID,
						dataServiceSoftwareKey);
		for (String label : currentRunMessageAsJsonMap.keySet()) {
			if (label.equals("data_retrieval_request_message.json")) {
				return currentRunMessageAsJsonMap.get(label).toString();
			}
		}
		return null;
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		InsertRunResult insertRunResult = new InsertRunResult();
		try {

			if (dataRetrievalRequestMessage != null) {
				RunIdAndCollisionId runIdAndHighestMD5CollisionIdForRun = dbUtils.getRunIdAndHighestMD5CollisionIdForRun(message);

				if (runIdAndHighestMD5CollisionIdForRun.getCollisionId() > 0) {
					//just assume they are the same for now...it's not like MD5 will collide, but we should verify
					insertRunResult.setRunCached(true);
					insertRunResult.setRunId(runIdAndHighestMD5CollisionIdForRun.getRunId());
				} else {
					insertRunResult.setRunCached(false);
					BigInteger runId = dbUtils.addDataServiceRun(dataRetrievalRequestMessage,
                            runIdAndHighestMD5CollisionIdForRun.getCollisionId() + 1, authentication,
                            DATA_SERVICE_SOFTWARE_ID, ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID);
					insertRunResult.setRunId(runId);
				}

				return insertRunResult;
			} else {
				throw new RunManagementException("The data retrieval request message was null");
			}

		} catch (Md5UtilsException | ApolloDatabaseException e) {
			throw new RunManagementException("Error inserting run into database, error was: " + "(" + e.getClass().getName() + ") " + e.getMessage());
		}

	}
}
