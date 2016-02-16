package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.interfaces.UserManagementInterface;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.methods.run.AbstractRunMethod;
import edu.pitt.apollo.runmanagerservice.methods.run.RunMethodFactory;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.services_common.v4_0.ServiceRegistrationRecord;
import edu.pitt.apollo.soapjobrunningserviceconnector.RestJobRunningServiceConnector;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface, UserManagementInterface, JobRunningServiceInterface {

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getSoftwareIdentificationForRun(runId, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		final BigInteger NULL_PARENT_RUN_ID = null;
		StageMethod stageMethod = new StageMethod(message, NULL_PARENT_RUN_ID);
		return stageMethod.stage();
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getLastServiceToBeCalledForRun(runId, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
		} catch (DatastoreException | UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.removeRunData(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException | DatastoreException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getRunStatus(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException | DatastoreException ex) {
			throw new RunManagementException(ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		try {
			AbstractRunMethod runMethod = RunMethodFactory.getRunMethod(runId, authentication);
			runMethod.run(runId);
		} catch (RunManagementException | JsonUtilsException e) {
			throw new JobRunningServiceException("Error running job, error was: (" + e.getClass().getName() + ") " + e.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		try {
			DatastoreAccessor dataServiceAccessor = new DatastoreAccessor();

			SoftwareIdentification softwareIdentification = dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
			String urlOfSimulator = dataServiceAccessor.getURLForSoftwareIdentification(softwareIdentification, authentication);
			RestJobRunningServiceConnector restJobRunningServiceConnector = new RestJobRunningServiceConnector(urlOfSimulator);
			restJobRunningServiceConnector.terminate(runId, authentication);
		} catch (RunManagementException e) {
			throw new JobRunningServiceException("Error terminating job, error was: (" + e.getClass().getName() + ") " + e.getMessage());
		}
	}

	@Override
	public List<ServiceRegistrationRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getListOfRegisteredSoftwareRecords(authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public String getContentForContentId(BigInteger urlId, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getContentForContentId(urlId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void removeFileAssociationWithRun(BigInteger runId, BigInteger fileId, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.removeFileAssociationWithRun(runId, fileId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getListOfURLsForRunId(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public Map<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			return dba.getListOfFilesForRunId(runId, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void associateContentWithRunId(BigInteger runId, String content, SoftwareIdentification sourceSoftware, SoftwareIdentification destinationSoftware,
			String contentLabel, ContentDataFormatEnum contentDataFormat, ContentDataTypeEnum contentDataType, Authentication authentication) throws DatastoreException {

		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.associateContentWithRunId(runId, content, sourceSoftware, destinationSoftware, contentLabel, contentDataFormat, contentDataType, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}

	}

	@Override
	public void addUserRole(String username, String userPassword, SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean canRequestPrivileged, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.addUserRole(username, userPassword, softwareIdentification, canRunSoftware, canRequestPrivileged, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void addUser(String userId, String userPassword, String userEmail, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.addUser(userId, userPassword, userEmail, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void deleteUser(String username, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.deleteUser(username, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void addRole(SoftwareIdentification softwareIdentification, boolean canRunSoftware, boolean allowPrivilegedRequest,
			String roleDescription, Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.addRole(softwareIdentification, canRunSoftware, allowPrivilegedRequest, roleDescription, authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void authenticateUser(Authentication authentication) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.authenticateUser(authentication);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}

	@Override
	public void authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware) throws DatastoreException {
		try {
			DatastoreAccessor dba = DatastoreAccessorFactory.getDatabaseAccessor();
			dba.authorizeUser(authentication, softwareIdentification, requestToRunSoftware);
		} catch (UnrecognizedMessageTypeException | ApolloDatabaseException ex) {
			throw new DatastoreException(ex.getMessage());
		}
	}
}
