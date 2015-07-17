package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.InsertRunResult;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.soapjobrunningserviceconnector.SoapJobRunningServiceConnector;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements RunManagementInterface, JobRunningServiceInterface {

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		return dataServiceAccessor.getRunIdsAssociatedWithSimulationGroupForRun(runId, authentication);
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		return dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
	}

	@Override
	public InsertRunResult insertRun(RunMessage message) throws RunManagementException {
		final BigInteger NULL_PARENT_RUN_ID = null;
		StageMethod stageMethod = new StageMethod(message, NULL_PARENT_RUN_ID);
		return stageMethod.stage();
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		dataServiceAccessor.updateStatusOfRun(runId, statusEnumToSet, messageToSet, authentication);
	}

	@Override
	public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareIdentification, authentication);
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		return dataServiceAccessor.getLastServiceToBeCalledForRun(runId, authentication);
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		dataServiceAccessor.addRunIdsToSimulationGroupForRun(runId, runIds, authentication);
	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		dataServiceAccessor.removeRunData(runId, authentication);
	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws RunManagementException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		return dataServiceAccessor.getRunStatus(runId, authentication);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		try {
			SoftwareIdentification softwareIdentification = dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
			String urlOfSimulator = dataServiceAccessor.getURLForSoftwareIdentification(softwareIdentification, authentication);
			SoapJobRunningServiceConnector soapJobRunningServiceConnector = new SoapJobRunningServiceConnector(urlOfSimulator, softwareIdentification);
			soapJobRunningServiceConnector.run(runId, authentication);
		} catch (DataServiceException e) {
			throw new JobRunningServiceException("Error running job, error was: (" + e.getClass().getName() + ") " + e.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		try {
			SoftwareIdentification softwareIdentification = dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
			String urlOfSimulator = dataServiceAccessor.getURLForSoftwareIdentification(softwareIdentification, authentication);
			SoapJobRunningServiceConnector soapJobRunningServiceConnector = new SoapJobRunningServiceConnector(urlOfSimulator, softwareIdentification);
			soapJobRunningServiceConnector.terminate(runId, authentication);
		} catch (DataServiceException e) {
			throw new JobRunningServiceException("Error terminating job, error was: (" + e.getClass().getName() + ") " + e.getMessage());
		}
	}
}
