package edu.pitt.apollo.runmanagerservice.serviceaccessors;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.DataServiceInterface;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.rest.RestDataServiceConnector;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessor implements DataServiceInterface {
    
	private static final String DATA_SERVICE_URL = "";
	
	protected DataServiceConnector connector = new RestDataServiceConnector(DATA_SERVICE_URL);


	public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId, Authentication authentication, String runMessageFilename) throws DataServiceException {
		Map<BigInteger, String> files = this.getListOfFilesForRunId(runId, authentication);
		// this is inadequate!! need to filter by source and dest software id too!
		for (BigInteger fileId : files.keySet()) {
			String filename = files.get(fileId);
			if (filename.equals(runMessageFilename)) {
				return this.getFileContentForFileId(fileId, authentication);
			}
		}
		throw new DataServiceException("Couldn't find "+runMessageFilename+" in database for run " + runId);
	}
	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataTypeEnum contentDataType, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public BigInteger insertRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return 0;
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger simulationGroupId, List<BigInteger> runIds, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public HashMap<BigInteger, String> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public HashMap<BigInteger, String> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public String getURLForURLId(BigInteger urlId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return null;
	}

	@Override
	public void runDataServiceToGetOutputFilesURLs(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public void runDataServiceToGetOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}

	@Override
	public void runDataServiceToGetAllOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {

	}
}
