package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.RunSimulation;
import edu.pitt.apollo.services_common.v3_0_0.*;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.soapjobrunningserviceconnector.SoapJobRunningServiceConnector;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements RunManagementInterface, JobRunningServiceInterface {

    @Override
    public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        return null;
    }

    @Override
    public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        return null;
    }

    @Override
    public BigInteger insertRun(Object message) throws RunManagementException {
        final BigInteger NULL_PARENT_RUN_ID = null;
        StageMethod stageMethod = new StageMethod(message, NULL_PARENT_RUN_ID);
        return stageMethod.stage();
    }

    @Override
    public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws RunManagementException {

    }

    @Override
    public void updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws RunManagementException {

    }

    @Override
    public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws RunManagementException {
        return null;
    }

    @Override
    public void addRunIdsToSimulationGroupForRun(BigInteger runId, List<BigInteger> runIds, Authentication authentication) throws RunManagementException {

    }

    @Override
    public void removeRunData(BigInteger runId, Authentication authentication) throws RunManagementException {

    }

    @Override
    public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException {
        return null;
    }

    @Override
    public void run(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
        DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
        try {
            SoftwareIdentification softwareIdentification = dataServiceAccessor.getSoftwareIdentificationForRun(runId, authentication);
            String urlOfSimulator = dataServiceAccessor.getURLForSoftwareIdentification(softwareIdentification, authentication);
            SoapJobRunningServiceConnector soapJobRunningServiceConnector = new SoapJobRunningServiceConnector(urlOfSimulator);
            soapJobRunningServiceConnector.run(runId, authentication);
        } catch (DataServiceException e) {
            throw new SimulatorServiceException("Error running job, error was: (" + e.getClass().getName() + ") " + e.getMessage());
        }
    }

    @Override
    public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException {

    }
}




