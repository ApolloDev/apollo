package edu.pitt.apollo.apolloservice.database;

import static edu.pitt.apollo.ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
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

	private GetOutputFilesURLsMessage getOutputFilesURLsMessage = null;
	private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage = null;

	private static final SoftwareIdentification DATA_SERVICE_SOFTWARE_ID;
	private static final int DATA_SERVICE_SOFTWARE_KEY;

	static {
		DATA_SERVICE_SOFTWARE_ID = new SoftwareIdentification();
		DATA_SERVICE_SOFTWARE_ID.setSoftwareDeveloper("UPitt");
		DATA_SERVICE_SOFTWARE_ID.setSoftwareName("Data Service");
		DATA_SERVICE_SOFTWARE_ID.setSoftwareType(ApolloSoftwareTypeEnum.DATA);
		DATA_SERVICE_SOFTWARE_ID.setSoftwareVersion("1.0");

		try {
			DATA_SERVICE_SOFTWARE_KEY = dbUtils.getSoftwareIdentificationKey(DATA_SERVICE_SOFTWARE_ID);
		} catch (ApolloDatabaseException ex) {
			throw new ExceptionInInitializerError("Could not get data service software key: " + ex.getMessage());
		}
	}

	public DatabaseAccessorForRunningDataService(GetOutputFilesURLsMessage message, Authentication authentication) {
		super(authentication);
		this.getOutputFilesURLsMessage = message;
	}

	public DatabaseAccessorForRunningDataService(GetOutputFilesURLAsZipMessage message, Authentication authentication) {
		super(authentication);
		this.getOutputFilesURLAsZipMessage = message;
	}

	public static SoftwareIdentification getDataServiceSoftwareId() {
		return DATA_SERVICE_SOFTWARE_ID;
	}

	@Override
	public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException {
		List<BigInteger> runIds = null;
		String targetRunSimulationMessageAsJson = null;
		if (getOutputFilesURLsMessage != null) {
			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(getOutputFilesURLsMessage, DATA_SERVICE_SOFTWARE_ID);
			targetRunSimulationMessageAsJson = ApolloDbUtils.getJSONString(getOutputFilesURLsMessage);

		} else if (getOutputFilesURLAsZipMessage != null) {
			runIds = dbUtils.getRunIdsAssociatedWithMessageHashAndSoftware(getOutputFilesURLAsZipMessage, DATA_SERVICE_SOFTWARE_ID);
			targetRunSimulationMessageAsJson = ApolloDbUtils.getJSONString(getOutputFilesURLAsZipMessage);
		}

		if (runIds != null && targetRunSimulationMessageAsJson != null && runIds.size() > 0) {
			for (BigInteger runIdAssociatedWithRunSimulationMessageHash : runIds) {
				if (isRunIdAssociatedWithMatchingRunMessage(targetRunSimulationMessageAsJson,
						runIdAssociatedWithRunSimulationMessageHash)) {
					return runIdAssociatedWithRunSimulationMessageHash;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> currentRunMessageAsJsonMap
				= dbUtils.getDataContentForSoftware(
						runId, END_USER_APPLICATION_SOURCE_ID,
						DATA_SERVICE_SOFTWARE_KEY);
		for (String label : currentRunMessageAsJsonMap.keySet()) {
			if (label.equals("run_data_service_message.json")) {
				return currentRunMessageAsJsonMap.get(label).toString();
			}
		}
		return null;
	}

	@Override
	public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws ApolloDatabaseException {
		int md5CollisionId;
		BigInteger[] runIds = null;
		if (getOutputFilesURLsMessage != null) {
			md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(getOutputFilesURLsMessage) + 1;
			runIds = dbUtils.addDataServiceRun(getOutputFilesURLsMessage, md5CollisionId, authentication, DATA_SERVICE_SOFTWARE_ID);
		} else if (getOutputFilesURLAsZipMessage != null) {
			md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(getOutputFilesURLAsZipMessage) + 1;
			runIds = dbUtils.addDataServiceRun(getOutputFilesURLAsZipMessage, md5CollisionId, authentication, DATA_SERVICE_SOFTWARE_ID);
		}

		return runIds;
	}

}
