package edu.pitt.apollo.runmanagerservice.methods.run.dataserviceaccessors;


import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public interface DataServiceAccessorInterface {

    public void removeAllDataAssociatedWithRunId(BigInteger runId)
            throws DataserviceException;
	
    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws DataserviceException;

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds) throws DataserviceException;

    public String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws DataserviceException;

    public BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws DataserviceException;

    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws DataserviceException;
}