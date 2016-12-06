package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.interfaces.ContentManagementInterface;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.interfaces.SoftwareRegistryInterface;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorFactory;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.runmanagerservice.methods.run.AbstractRunMethod;
import edu.pitt.apollo.runmanagerservice.methods.run.RunMethodFactory;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.services_common.v4_0_1.*;
import edu.pitt.apollo.soapjobrunningserviceconnector.RestJobRunningServiceConnector;
import edu.pitt.apollo.types.v4_0_1.SoftwareIdentification;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements SoftwareRegistryInterface, RunManagementInterface, ContentManagementInterface, JobRunningServiceInterface {

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
	public InsertRunResult insertRun(RunMessage message, Authentication authentication) throws RunManagementException {
		final BigInteger NULL_PARENT_RUN_ID = null;
		try {
			StageMethod stageMethod = new StageMethod(message, NULL_PARENT_RUN_ID, authentication);
			return stageMethod.stage();
		} catch (ApolloDatabaseException | UnrecognizedMessageTypeException ex) {
			throw new RunManagementException("Exception inserting run: " + ex.getMessage());
		}
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
		} catch (RunManagementException | JsonUtilsException | FilestoreException e) {
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
	public List<ServiceRecord> getListOfRegisteredSoftwareRecords(Authentication authentication) throws DatastoreException {
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
}
