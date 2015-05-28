package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.exception.DataServiceException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pitt.apollo.services_common.v3_0_0.*;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public interface DataServiceInterface {

	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware,
			SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException;

	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException;

	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException;

	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException;

	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException;

	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException;

	public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException;

	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException;

	public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException;

	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException;

	public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws DataServiceException;

	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException;
	
	public String getURLForURLId(BigInteger urlId, Authentication authentication) throws DataServiceException;

	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException;

	public RunResult runDataService(BigInteger runId, Authentication authentication) throws DataServiceException;

	public Map<Integer,ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException;
}
