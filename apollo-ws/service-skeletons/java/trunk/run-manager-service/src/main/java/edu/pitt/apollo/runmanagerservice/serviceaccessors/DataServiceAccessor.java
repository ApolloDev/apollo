package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.DataServiceInterface;
import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessor extends ServiceAccessor implements DataServiceInterface {
	
	private static final String DATA_SERVICE_URL = "";
	
	protected DataServiceConnector connector;
	
	public DataServiceAccessor() {
		super(DATA_SERVICE_URL);
	}
	
	public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId, Authentication authentication, String runMessageFilename) throws DataServiceException {
		Map<BigInteger, FileAndURLDescription> files = this.getListOfFilesForRunId(runId, authentication);
		// this is inadequate!! need to filter by source and dest software id too!
		for (BigInteger fileId : files.keySet()) {
			String filename = files.get(fileId).getName();
			if (filename.equals(runMessageFilename)) {
				return this.getFileContentForFileId(fileId, authentication);
			}
		}
		throw new DataServiceException("Couldn't find " + runMessageFilename + " in database for run " + runId);
	}
	
	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
	}
	
	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getSoftwareIdentificationForRun(runId, authentication);
	}
	
	@Override
	public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException {
		return connector.insertRun(message, authentication);
	}
	
	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		connector.updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
	}
	
	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
	}
	
	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getLastServiceToBeCalledForRun(runId, authentication);
	}
	
	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		connector.addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
	}
	
	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		connector.removeRunData(runId, authentication);
	}
	
	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getRunStatus(runId, authentication);
	}
	
	@Override
	public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getFileContentForFileId(fileId, authentication);
	}
	
	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		connector.removeFileAssociationWithRun(runId, fileId, authentication);
	}
	
	@Override
	public String getURLForURLId(BigInteger urlId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getURLForURLId(urlId, authentication);
	}
	
	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws edu.pitt.apollo.exception.DataServiceException {
		return connector.getURLForSoftwareIdentification(softwareId, authentication);
	}
	
	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {
		connector.associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware, contentLabel, contentDataFormat, contentDataType, authentication);
	}
	
	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return connector.getListOfFilesForRunId(runId, authentication);
	}
	
	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return connector.getListOfURLsForRunId(runId, authentication);
	}
	
	@Override
	public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
		return connector.getListOfRegisteredSoftwareRecords(authentication);
	}
	
	@Override
	public void runDataService(BigInteger runId, Authentication authentication) throws DataServiceException {
		connector.runDataService(runId, authentication);
	}
}
