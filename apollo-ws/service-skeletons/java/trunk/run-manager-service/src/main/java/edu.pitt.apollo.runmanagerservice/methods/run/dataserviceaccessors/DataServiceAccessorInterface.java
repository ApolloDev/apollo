package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public interface DataServiceAccessorInterface {

    public void removeAllDataAssociatedWithRunId(BigInteger runId)
            throws DataserviceException;

    public boolean authenticateUser(Authentication authentication)
            throws DataserviceException;

    public boolean authorizeUserForSoftwareCacheData(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws DataserviceException;

    public boolean authorizeUserForRunningSoftware(
            Authentication authentication,
            SoftwareIdentification softwareIdentification)
            throws DataserviceException;

    public boolean isRunIdAssociatedWithMatchingRunMessage(
            String targetRunMessageAsJson,
            BigInteger runIdAssociatedWithRunMessageHash)
            throws DataserviceException;

    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws DataserviceException;

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds);

    public BigInteger getCachedRunIdFromDatabaseOrNull()
            throws DataserviceException, Md5UtilsException;

    public String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws DataserviceException;

    public BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws DataserviceException, Md5UtilsException;

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws DataserviceException;
}