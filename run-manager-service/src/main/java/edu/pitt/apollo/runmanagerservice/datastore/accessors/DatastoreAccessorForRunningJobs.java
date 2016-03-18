package edu.pitt.apollo.runmanagerservice.datastore.accessors;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.runmanagerservice.utils.ApolloSoftwareIdentificationResolver;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Map;

import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import edu.pitt.apollo.utilities.FileStoreServiceUtility;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time: 11:56:26 AM Class: DatabaseAccessorForRunningSimulations IDE: NetBeans 6.9.1
 */
public class DatastoreAccessorForRunningJobs extends
        DatastoreAccessor {

    static Logger logger = LoggerFactory.getLogger(DatastoreAccessorForRunningJobs.class);
    private SoftwareIdentification softwareIdentification;
    //private Class runMessageClass;

    public DatastoreAccessorForRunningJobs(SoftwareIdentification softwareIdentification) throws ApolloDatabaseException, DatastoreException {
        super();
        //this.runMessageClass = clazz;
        this.softwareIdentification = softwareIdentification;
    }

//    @Override
//    protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
//            BigInteger runId) throws ApolloDatabaseException {
//
//		return getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, "run_message.json");
//		
//    }

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
    public InsertRunResult insertRun(RunMessage message) throws RunManagementException {

        if (message.getAuthentication().getRequesterId().equals("default_user")) {
            throw new RunManagementException("Default user set in run message");
        }

        Authentication authentication = message.getAuthentication();
        message.setAuthentication(new Authentication());

        InsertRunResult insertRunResult = new InsertRunResult();
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
                destSoftwareId = ((RunVisualizationMessage) message).getSoftwareIdentification();
            } else {
                destSoftwareId = ApolloSoftwareIdentificationResolver.getTranslatorSoftwareIdentification();
            }
            BigInteger[] runIdSimulationGroupId;
            if (needToAddRun) {
                insertRunResult.setRunCached(false);
                runIdSimulationGroupId = dbUtils.addSimulationRun(
                        message,
                        runIdAndHighestMD5CollisionIdForRun.getCollisionId() + 1,
                        softwareIdentification,
                        ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
                        destSoftwareId, authentication);
            } else {
                insertRunResult.setRunCached(true);
                insertRunResult.setRunId(runIdAndHighestMD5CollisionIdForRun.getRunId());
                return insertRunResult;
            }
            insertRunResult.setRunId(runIdSimulationGroupId[0]);
            return insertRunResult;
        } catch (ApolloDatabaseException | Md5UtilsException e) {
            throw new RunManagementException("Error adding run to the database.  Error (" + e.getClass().getName() + ") was " + e.getMessage());
        }
    }

}
