package edu.pitt.apollo.dataservice.methods.database;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.Md5Utils;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.DataServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time:
 * 2:26:02 PM Class: DatabaseAccessor IDE: NetBeans 6.9.1
 */
public class DatabaseAccessor implements DataServiceInterface {
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

    public  BigInteger getCachedRunIdFromDatabaseOrNull()
            throws ApolloDatabaseException, Md5UtilsException{
        return null;
    }

    protected  String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws ApolloDatabaseException{
        return null;
    }

    public  BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws ApolloDatabaseException, Md5UtilsException{
        return null;
    }

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws ApolloDatabaseException {

        return dbUtils.getSimulationGroupIdForRun(runId);


    }

    /*--DAN'S ADDITIONS--*/
    public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException{
        try {
            authenticateUser(authentication);
            BigInteger groupId = getSimulationGroupIdForRun(runId);
            List<BigInteger> listOfRunIds = getRunIdsAssociatedWithRun(groupId);
            return listOfRunIds;
        } catch (ApolloDatabaseException e) {
           throw new DataServiceException(e.getMessage());
        }

    }
    public void addRunIdsToSimulationGroupWithRunId(BigInteger runId, List<BigInteger> listOfRunIdsToAssociateWithSimulationGroup, Authentication authentication) throws DataServiceException{
        try {
            authenticateUser(authentication);
            BigInteger groupId = getSimulationGroupIdForRun(runId);
            addRunIdsToSimulationGroup(groupId,listOfRunIdsToAssociateWithSimulationGroup);

        } catch (ApolloDatabaseException e) {
            throw new DataServiceException(e.getMessage());
        } catch (Md5UtilsException e) {
            throw new DataServiceException(e.getMessage());
        }
    }

    public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware,
                                          SoftwareIdentification destinationSoftware, String contentLabel, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException{

    }

    @Override
    public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException {

    }

    @Override
    public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
        return 0;
    }

    @Override
    public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public void addRunIdsToSimulationGroupForRun(BigInteger simulationGroupId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException {

    }

    @Override
    public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException {

    }

    @Override
    public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public HashMap<BigInteger, String> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public HashMap<BigInteger, String> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {

    }

    @Override
    public String getURLForURLId(BigInteger urlId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public void runDataServiceToGetOutputFilesURLs(BigInteger runId, Authentication authentication) throws DataServiceException {

    }

    @Override
    public void runDataServiceToGetOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {

    }

    @Override
    public void runDataServiceToGetAllOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {

    }

}
