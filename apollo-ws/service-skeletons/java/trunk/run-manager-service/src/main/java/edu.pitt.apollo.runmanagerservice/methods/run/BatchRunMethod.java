package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/6/15.
 */
public class BatchRunMethod extends RunMethodForSimulationAndVisualization {


    public BatchRunMethod(Authentication authentication, SoftwareIdentification softwareIdentification, BigInteger associatedSimulationGroupId, Object message) {
        super(authentication, softwareIdentification, associatedSimulationGroupId, message);
    }

    @Override
    public RunResultAndSimulationGroupId handlePreviouslyFailedRun(BigInteger cachedRunId) {
        return getRunResultAndSimulationGroupId(createRunResult(
                ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                MethodCallStatusEnum.FAILED, "The BatchRun has failed.  To restart manually, please delete the run" +
                        "from the database and resubmit. "), null);
    }

    @Override
    protected MethodCallStatusEnum getSuccessfulMethodCallStatus() {
        return MethodCallStatusEnum.LOADING_RUN_CONFIG_INTO_DATABASE;
    }

    @Override
    protected RunResultAndSimulationGroupId handleNonFailedCachedRun(BigInteger cachedRunId, BigInteger associatedSimulationGroup) {

        BigInteger simulationGroupId;
        try {
            simulationGroupId = dao
                    .getSimulationGroupIdForRun(cachedRunId);
        } catch (Exception e) {
            return getRunResultAndSimulationGroupId(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED,
                    "Database error retrieving simulation group for run: " + e.getMessage()), null);
        }

        MethodCallStatus runStatus = getRunStatus(cachedRunId);


        return getRunResultAndSimulationGroupId(createRunResult(cachedRunId,
                runStatus.getStatus(),
                runStatus.getMessage()), simulationGroupId);
    }
}