package edu.pitt.apollo.dataservice.accessors;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.dataservice.utils.ApolloSoftwareIdentificationResolver;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time: 11:56:26 AM Class: DatabaseAccessorForRunningSimulations IDE: NetBeans 6.9.1
 */
public class DatabaseAccessorForRunningJobs extends
		DatabaseAccessor {

	static Logger logger = LoggerFactory.getLogger(DatabaseAccessorForRunningJobs.class);
	private final String runMessageFileName;
	private final ContentDataTypeEnum contentDataTypeEnum;
	protected Object runMessage;
	private SoftwareIdentification softwareIdentification;
	//private Class runMessageClass;

	public DatabaseAccessorForRunningJobs(Authentication authentication, String runMessageFileName, ContentDataTypeEnum contentDataTypeEnum,
			SoftwareIdentification softwareIdentification) throws ApolloDatabaseException {
		super(authentication);
		//this.runMessageClass = clazz;
		this.runMessageFileName = runMessageFileName;
		this.contentDataTypeEnum = contentDataTypeEnum;
		this.softwareIdentification = softwareIdentification;
	}

	@Override
	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
			BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> currentRunSimulationMessageAsJsonMap = null;
//		Map<String, ByteArrayOutputStream> currentRunSimulationMessageAsJsonMap = dbUtils
//				.getDataContentForSoftware(
//						runId,
//						ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
//						dbUtils.getSoftwareIdentificationKey(TranslatorServiceRecordContainer
//								.getTranslatorSoftwareIdentification()));
		for (String label : currentRunSimulationMessageAsJsonMap.keySet()) {
			if (label.equals(runMessageFileName)) {
				return currentRunSimulationMessageAsJsonMap.get(label)
						.toString();
			}
		}
		return null;
	}

	//not used for the time being...
//	@Override
//	public synchronized BigInteger getCachedRunIdFromDatabaseOrNull()
//			throws ApolloDatabaseException, Md5UtilsException {
//		String hash = md5Utils.getMd5(runMessage);
//		logger.info("Thread" + Thread.currentThread().getName() + " hash: " + hash);
//
//		List<BigInteger> runIds = dbUtils
//				.getSimulationRunIdsAssociatedWithRunSimulationMessageHashGivenHash(
//						softwareIdentification, hash);
//		if (runIds.size() > 0) {
//			logger.debug("Possible cache hit!  Checking to see if it's a true hit or if we have a collision...");
//			// String targetRunSimulationMessageAsJson =
//			// dbUtils.getJSONString(runSimulationMessage,
//			// RunSimulationMessage.class);
//			for (BigInteger runIdAssociatedWithRunSimulationMessageHash : runIds) {
//				if (isRunIdAssociatedWithMatchingRunMessage(
//						jsonUtils.getJSONString(runMessage),
//						runIdAssociatedWithRunSimulationMessageHash)) {
//					return runIdAssociatedWithRunSimulationMessageHash;
//				}
//			}
//			throw new ApolloDatabaseException("Collision detected, but a true extremely unlikely.");
//
//		} else {
//			return null;
//		}
//	}
	@Override
	public BigInteger insertRun(RunMessage message) throws RunManagementException {
		Authentication authentication = stripAuthentication(message);

		RunIdAndCollisionId runIdAndHighestMD5CollisionIdForRun = null;
		try {
			runIdAndHighestMD5CollisionIdForRun = dbUtils.getRunIdAndHighestMD5CollisionIdForRun(message);

			boolean needToAddRun = true;
			if (runIdAndHighestMD5CollisionIdForRun.getCollisionId() > 0) {
				//just assume they are the same for now...it's not like MD5 will collide, but we should verify
				needToAddRun = false;
			}

			SoftwareIdentification destSoftwareId;
			if (message instanceof RunVisualizationMessage) {
				destSoftwareId = message.getSoftwareIdentification();
			} else {
				destSoftwareId = ApolloSoftwareIdentificationResolver.getTranslatorSoftwareIdentification();
			}
			BigInteger[] runIdSimulationGroupId;
			if (needToAddRun) {
				if (runMessageFileName != null) {

					runIdSimulationGroupId = dbUtils.addSimulationRun(
							message, runMessageFileName, contentDataTypeEnum,
							runIdAndHighestMD5CollisionIdForRun.getCollisionId() + 1,
							softwareIdentification,
							ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
							destSoftwareId, authentication);
				} else {
					throw new RunManagementException(("Error inserting run into database, unknown message type: " + message.getClass().getName()));
				}
			} else {
				return runIdAndHighestMD5CollisionIdForRun.getRunId();
			}
			return runIdSimulationGroupId[0];
		} catch (ApolloDatabaseException | Md5UtilsException e) {
			throw new RunManagementException("Error adding run to the database.  Error (" + e.getClass().getName() + ") was " + e.getMessage());
		}
	}

}
