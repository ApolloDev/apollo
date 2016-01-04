package edu.pitt.apollo;

import edu.pitt.apollo.dataservice.accessors.DatabaseAccessor;
import edu.pitt.apollo.dataservice.accessors.DatabaseAccessorFactory;
import edu.pitt.apollo.dataservice.methods.RunJobMethod;
import edu.pitt.apollo.dataservice.methods.RunJobMethodFactory;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.UserManagementInterface;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nem41
 */
public class DataServiceImpl implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface, UserManagementInterface, JobRunningServiceInterface {

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware,
			String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DataServiceException {

		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware, contentLabel, contentDataFormat, contentDataType, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}

	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getSoftwareIdentificationForRun(runId, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		try {
			DatabaseAccessor databaseAccessor = DatabaseAccessorFactory.getDatabaseAccessor(message);
			return databaseAccessor.insertRun(message);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			Logger.getLogger(DataServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getLastServiceToBeCalledForRun(runId, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.removeRunData(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getRunStatus(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getListOfFilesForRunId(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getListOfURLsForRunId(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.removeFileAssociationWithRun(runId, fileId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public String getContentForContentId(BigInteger urlId, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getContentForContentId(urlId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public List<ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			return dba.getListOfRegisteredSoftwareRecords(authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void addUserRole(String username, String userPassword, SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.addUserRole(username, userPassword, softwareIdentification, canRunSoftware, canRequestPrivileged, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.addUser(userId, userPassword, userEmail, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void deleteUser(String username, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.deleteUser(username, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest, 
			String roleDescription, Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.addRole(softwareIdentification, canRunSoftware, allowPrivilegedRequest, roleDescription, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void authenticateUser(Authentication authentication) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.authenticateUser(authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware) throws DataServiceException {
		try {
			DatabaseAccessor dba = DatabaseAccessorFactory.getDatabaseAccessor(authentication);
			dba.authorizeUser(authentication, softwareIdentification, requestToRunSoftware);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DataServiceException(ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		RunJobMethod dataServiceMethod;
		try {
			dataServiceMethod = RunJobMethodFactory.getDataServiceMethod(runId, authentication);
			dataServiceMethod.runDataService();
		} catch (DataServiceException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new JobRunningServiceException(ex.getMessage());
		}
		
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
