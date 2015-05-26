package edu.pitt.apollo.runmanagerservice.serviceaccessors;
import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.rest.RestDataServiceConnector;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 5/21/15.
 */
public abstract class DataServiceAccessor implements DataServiceAccessorInterface {
    
	private static final String DATA_SERVICE_URL = "";
	
	protected DataServiceConnector connector = new RestDataServiceConnector(DATA_SERVICE_URL);
	
	@Override
    public void removeAllDataAssociatedWithRunId(BigInteger runId) throws DataServiceException {

    }

    @Override
    public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId) throws DataServiceException {
        return null;
    }

    @Override
    public void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds) throws DataServiceException {

    }

    @Override
    public BigInteger getSimulationGroupIdForRun(BigInteger runId) throws DataServiceException {
        return null;
    }

	@Override
	public MethodCallStatus getStatusOfRun(BigInteger runId) {
		return connector.getRunStatus(runId);
	}

	@Override
	public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws DataServiceException {
		return null; // NEED TO IMPLEMENT
	}

	@Override
	public String getURLForSoftwareId(SoftwareIdentification softwareId) {
		return connector.getURLForSoftwareId(softwareId);
	}

	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification) {
		return connector.updateLastServiceToBeCalledForRun(runId, softwareIdentification);
	}

	@Override
	public RunResult getAllOutputFilesURLAsZip(BigInteger runId) {
		return connector.getAllOutputFilesURLAsZip(runId);
	}

	@Override
	public RunResult getOutputFilesURLAsZip(BigInteger runId) {
		return connector.getOutputFilesURLAsZip(runId);
	}

	@Override
	public RunResult getOutputFilesURLs(BigInteger runId) {
		return connector.getOutputFilesURLs(runId);
	}
	
	
	
	
}
