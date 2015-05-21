package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;


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
    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId) throws DataserviceException {
        return null;
    }

    @Override
    public void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds) throws DataserviceException {

    }

    @Override
    public BigInteger getSimulationGroupIdForRun(BigInteger runId) throws DataserviceException {
        return null;
    }
}
