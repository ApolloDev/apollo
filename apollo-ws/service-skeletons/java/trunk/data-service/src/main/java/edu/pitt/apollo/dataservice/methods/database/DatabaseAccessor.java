package edu.pitt.apollo.dataservice.methods.database;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.Md5Utils;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time:
 * 2:26:02 PM Class: DatabaseAccessor IDE: NetBeans 6.9.1
 */
public abstract class DatabaseAccessor implements DatabaseAccessorInterface {
    protected JsonUtils jsonUtils = new JsonUtils();
    protected Md5Utils md5Utils = new Md5Utils();

    protected final ApolloDbUtils dbUtils;

    protected final Authentication authentication;

    static Logger logger = LoggerFactory.getLogger(DatabaseAccessor.class);

    public DatabaseAccessor(Authentication authentication, ApolloDbUtils dbUtils) throws ApolloDatabaseException {
        this.authentication = authentication;
        this.dbUtils = dbUtils;
    }


    public void removeAllDataAssociatedWithRunId(BigInteger runId)
            throws ApolloDatabaseException {
        dbUtils.removeRunData(runId);
    }

    public boolean authenticateUser(Authentication authentication)
            throws ApolloDatabaseException {
        return dbUtils.authenticateUser(authentication);
    }

    public boolean authorizeUserForSoftwareCacheData(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws ApolloDatabaseException {
        boolean requestRunSimulator = false;
        return dbUtils.authorizeUser(authentication, softwareIdentification,
                requestRunSimulator);
    }

    public boolean authorizeUserForRunningSoftware(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws ApolloDatabaseException {
        boolean requestRunSimulator = true;
        return dbUtils.authorizeUser(authentication, softwareIdentification,
                requestRunSimulator);
    }

    protected final boolean isRunIdAssociatedWithMatchingRunMessage(
            String targetRunMessageAsJson,
            BigInteger runIdAssociatedWithRunMessageHash)
            throws ApolloDatabaseException {

        String runSimulationMessageAssociatedWithRunIdAsJson = getRunMessageAssociatedWithRunIdAsJsonOrNull(runIdAssociatedWithRunMessageHash);

        if (runSimulationMessageAssociatedWithRunIdAsJson == null) {
            throw new ApolloDatabaseException(
                    "There was no run_simulation_message.json content for run ID "
                            + runIdAssociatedWithRunMessageHash);
        }

        boolean result = targetRunMessageAsJson
                .equals(runSimulationMessageAssociatedWithRunIdAsJson);
        if (!result) {
            String targetHash = md5Utils.getMd5FromString(targetRunMessageAsJson);
            String existingHash = md5Utils.getMd5FromString(runSimulationMessageAssociatedWithRunIdAsJson);

            logger.warn("Warning!!! (" + targetHash + ") " + targetRunMessageAsJson +
                    " \n is not equal to \n " +
                    "(" + existingHash +")" + runSimulationMessageAssociatedWithRunIdAsJson);

        }return result;

    }

    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws ApolloDatabaseException {
        return dbUtils.getRunIdsForBatch(runId);
    }

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds) throws ApolloDatabaseException,Md5UtilsException {
        dbUtils.addRunIdsToSimulationGroup(simulationGroupId,
                runIds);
    }

    public abstract BigInteger getCachedRunIdFromDatabaseOrNull()
            throws ApolloDatabaseException, Md5UtilsException;

    protected abstract String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws ApolloDatabaseException;

    public abstract BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws ApolloDatabaseException, Md5UtilsException;

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws ApolloDatabaseException {
        return dbUtils.getSimulationGroupIdForRun(runId);
    }
}
