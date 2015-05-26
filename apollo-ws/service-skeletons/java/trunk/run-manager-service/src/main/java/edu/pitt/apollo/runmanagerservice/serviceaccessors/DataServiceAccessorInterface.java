package edu.pitt.apollo.runmanagerservice.serviceaccessors;


import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public interface DataServiceAccessorInterface {

    public void removeAllDataAssociatedWithRunId(BigInteger runId)
            throws DataServiceException;
	
    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
            throws DataServiceException;

    public void addRunIdsToSimulationGroup(
            BigInteger simulationGroupId,
            List<BigInteger> runIds) throws DataServiceException;

    public String getRunMessageAssociatedWithRunIdAsJsonOrNull(
            BigInteger runId) throws DataServiceException;

    public BigInteger[] insertRunIntoDatabase(
            BigInteger memberOfSimulationGroupIdOrNull)
            throws DataServiceException, RunManagerServiceException;

	public MethodCallStatus getStatusOfRun(BigInteger runId);
	
    public BigInteger getSimulationGroupIdForRun(BigInteger runId)
            throws DataServiceException;
}