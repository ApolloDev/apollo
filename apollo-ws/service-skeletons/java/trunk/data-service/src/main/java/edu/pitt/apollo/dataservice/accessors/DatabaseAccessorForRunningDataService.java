package edu.pitt.apollo.dataservice.accessors;

import static edu.pitt.apollo.ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 22, 2015
 * Time: 10:24:52 AM
 * Class: DatabaseAccessorForRunningDataService
 */
public class DatabaseAccessorForRunningDataService extends DatabaseAccessor {

	private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage = null;

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

	public DatabaseAccessorForRunningDataService(GetOutputFilesURLAsZipMessage message, Authentication authentication) throws ApolloDatabaseException {
		super(authentication);
		this.getOutputFilesURLAsZipMessage = message;
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
//		} else if (getOutputFilesURLAsZipMessage != null) {
//			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(getOutputFilesURLAsZipMessage, DATA_SERVICE_SOFTWARE_ID);
//			targetRunSimulationMessageAsJson = jsonUtils.getJSONString(getOutputFilesURLAsZipMessage);
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
			if (label.equals("run_data_service_message.json")) {
				return currentRunMessageAsJsonMap.get(label).toString();
			}
		}
		return null;
	}

	@Override
	public BigInteger insertRun(Object message) throws RunManagementException {
		Authentication authentication = stripAuthentication(message);
		int md5CollisionId;
		BigInteger runIds = null;
		try {
//			if (getOutputFilesURLsMessage != null) {
//				md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(getOutputFilesURLsMessage) + 1;
//				runIds = dbUtils.addDataServiceRun(getOutputFilesURLsMessage, md5CollisionId, authentication, DATA_SERVICE_SOFTWARE_ID);
//			} else 
				if (getOutputFilesURLAsZipMessage != null) {
				md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(getOutputFilesURLAsZipMessage) + 1;
				runIds = dbUtils.addDataServiceRun(getOutputFilesURLAsZipMessage, md5CollisionId, authentication, DATA_SERVICE_SOFTWARE_ID);
			} 
//				else if (getAllOutputFilesURLAsZipMessage != null) {
//				md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(getAllOutputFilesURLAsZipMessage) + 1;
//				runIds = dbUtils.addDataServiceRun(getAllOutputFilesURLAsZipMessage, md5CollisionId, authentication, DATA_SERVICE_SOFTWARE_ID);
//			}
		} catch (Md5UtilsException | ApolloDatabaseException e) {
			throw new RunManagementException("Error inserting run into database, error was: " + "(" + e.getClass().getName() + ") " + e.getMessage());
		}

		return runIds;
	}
}
