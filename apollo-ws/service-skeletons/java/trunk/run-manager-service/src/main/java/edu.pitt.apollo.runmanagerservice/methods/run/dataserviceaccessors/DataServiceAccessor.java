package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;

import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public abstract class DataServiceAccessor implements DataServiceAccessorInterface {
    @Override
    public void removeAllDataAssociatedWithRunId(BigInteger runId) throws DataserviceException {

    }

    @Override
    public boolean authenticateUser(Authentication authentication) throws DataserviceException {
        return false;
    }

    @Override
    public boolean authorizeUserForSoftwareCacheData(Authentication authentication, SoftwareIdentification softwareIdentification) throws DataserviceException {
        return false;
    }

    @Override
    public boolean authorizeUserForRunningSoftware(Authentication authentication, SoftwareIdentification softwareIdentification) throws DataserviceException {
        return false;
    }

    @Override
    public boolean isRunIdAssociatedWithMatchingRunMessage(String targetRunMessageAsJson, BigInteger runIdAssociatedWithRunMessageHash) throws DataserviceException {
        return false;
    }

    @Override
    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId) throws DataserviceException {
        return null;
    }

    @Override
    public void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds) {

    }

    @Override
    public BigInteger getSimulationGroupIdForRun(BigInteger runId) throws DataserviceException {
        return null;
    }
}
