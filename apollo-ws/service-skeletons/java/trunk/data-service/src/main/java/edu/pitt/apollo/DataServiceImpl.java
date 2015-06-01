
package edu.pitt.apollo;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.interfaces.DataServiceInterface;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v3_0_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nem41
 */
public class DataServiceImpl implements DataServiceInterface {

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware, String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger insertRun(Object message, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getURLForURLId(BigInteger urlId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void runDataService(BigInteger runId, Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Map<Integer, ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
