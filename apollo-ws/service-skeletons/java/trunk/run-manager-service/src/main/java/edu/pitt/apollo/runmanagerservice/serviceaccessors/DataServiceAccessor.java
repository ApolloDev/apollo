package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.DataServiceImpl;
import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Created by jdl50 on 5/21/15.
 */
public class DataServiceAccessor extends ServiceAccessor implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface {
	
	public static final String DATA_SERVICE_URL = "";
	
	protected RunManagementInterface runManagementInterface;
	protected ContentManagementInterface contentManagementInterface;
	protected SoftwareRegistryInterface softwareRegistryInterface;
	
	public DataServiceAccessor() {
		super(DATA_SERVICE_URL);
		DataServiceImpl dataService = new DataServiceImpl();
		runManagementInterface = dataService;
		contentManagementInterface  = dataService;
		softwareRegistryInterface = dataService;
	}
	
	public String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId, Authentication authentication, String runMessageFilename) throws DataServiceException {
		Map<BigInteger, FileAndURLDescription> files = this.getListOfFilesForRunId(runId, authentication);
		// this is inadequate!! need to filter by source and dest software id too!
		for (BigInteger fileId : files.keySet()) {
			String filename = files.get(fileId).getName();
			if (filename.equals(runMessageFilename)) {
				return this.getContentForContentId(fileId, authentication);
			}
		}
		throw new DataServiceException("Couldn't find " + runMessageFilename + " in database for run " + runId);
	}

	public <T>T getRunMessageAssociatedWithRunIdAsTypeOrNull(BigInteger runId, Authentication authentication, String runMessageFilename, Class<T> clazz) throws DataServiceException, JsonUtilsException {
		String json = getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, runMessageFilename);
		JsonUtils jsonUtils = new JsonUtils();
		return (T) jsonUtils.getObjectFromJson(json, clazz);
	}
	
	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return runManagementInterface.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
	}
	
	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return runManagementInterface.getSoftwareIdentificationForRun(runId, authentication);
	}
	
	@Override
	public BigInteger insertRun(RunMessage message) throws RunManagementException {
		return runManagementInterface.insertRun(message);
	}
	
	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		runManagementInterface.updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
	}
	
	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		runManagementInterface.updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
	}
	
	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		return runManagementInterface.getLastServiceToBeCalledForRun(runId, authentication);
	}
	
	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		runManagementInterface.addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
	}
	
	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		runManagementInterface.removeRunData(runId, authentication);
	}
	
	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		return runManagementInterface.getRunStatus(runId, authentication);
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {
		contentManagementInterface.removeFileAssociationWithRun(runId, fileId, authentication);
	}
	
	@Override
	public String getContentForContentId(BigInteger urlId, Authentication authentication) throws DataServiceException {
		return contentManagementInterface.getContentForContentId(urlId, authentication);
	}
	
	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
		return contentManagementInterface.getURLForSoftwareIdentification(softwareId, authentication);
	}
	
	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {
		contentManagementInterface.associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware, contentLabel, contentDataFormat, contentDataType, authentication);
	}
	
//	@Override
//	public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
//		return runManagementInterface.getListOfFilesForRunId(runId, authentication);
//	}
//	
//	@Override
//	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
//		return runManagementInterface.getListOfURLsForRunId(runId, authentication);
//	}
	
	@Override
	public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
		return softwareRegistryInterface.getListOfRegisteredSoftwareRecords(authentication);
	}
	
//	@Override
//	public void runDataService(BigInteger runId, Authentication authentication) throws DataServiceException {
//		runManagementInterface.runDataService(runId, authentication);
//	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return contentManagementInterface.getListOfFilesForRunId(runId, authentication);
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return contentManagementInterface.getListOfURLsForRunId(runId, authentication);
	}
}
