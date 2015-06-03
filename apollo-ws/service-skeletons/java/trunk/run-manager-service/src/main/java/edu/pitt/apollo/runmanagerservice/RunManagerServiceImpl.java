package edu.pitt.apollo.runmanagerservice;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.interfaces.RunManagementInterface;
import edu.pitt.apollo.runmanagerservice.methods.stage.StageMethod;
import edu.pitt.apollo.services_common.v3_0_0.*;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jdl50 on 6/3/15.
 */
public class RunManagerServiceImpl implements RunManagementInterface {

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
}




