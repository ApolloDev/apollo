package edu.pitt.apollo.apolloservice.methods.run;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessor;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessorFactory;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

public class AbstractRunMethod {

    protected DatabaseAccessor databaseAccessor;
    protected final Authentication authentication;
    protected final SoftwareIdentification softwareIdentification;
    protected RunApolloServiceThread runApolloServiceThread;
    protected final RunResult USER_AUTHENTICATED_AND_AUTHORIZED = null;
    private MethodCallStatus runStatus;

    public RunResultAndSimulationGroupId stageRun(Object message,
                                                  BigInteger simulationGroupIdOrNull) {

        RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();

        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            try {
                databaseAccessor = DatabaseAccessorFactory.getDatabaseAccessor(
                        message, authentication, dbUtils);

            } catch (UnrecognizedMessageTypeException ex) {
                runResultAndSimulationGroupId.setRunResult(createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.FAILED, ex.getMessage()));
                return runResultAndSimulationGroupId;
            }


            RunResult authResult = authenticateAndAuthorizeUser();
            if (authResult == USER_AUTHENTICATED_AND_AUTHORIZED) {

                BigInteger runId = databaseAccessor
                        .getCachedRunIdFromDatabaseOrNull();

                if (runId != null) {
                    MethodCallStatus runStatus = getRunStatus(runId);
                    if (runStatus.getStatus() != MethodCallStatusEnum.FAILED) {
                        if (simulationGroupIdOrNull != null) {
                            RunIdentificationAndLabel runIdentificationAndLabel = new RunIdentificationAndLabel();
                            runIdentificationAndLabel
                                    .setRunIdentification(runId);
                            runIdentificationAndLabel.setRunLabel("");
                            List<RunIdentificationAndLabel> runIdentificationAndLabels = new ArrayList<RunIdentificationAndLabel>();
                            runIdentificationAndLabels
                                    .add(runIdentificationAndLabel);
                            try {
                                if (!(message instanceof RunSimulationsMessage))
                                    databaseAccessor
                                            .addRunIdsToSimulationGroup(
                                                    simulationGroupIdOrNull,
                                                    runIdentificationAndLabels);
                            } catch (Exception e) {
                                runResultAndSimulationGroupId
                                        .setRunResult(createRunResult(
                                                ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                                                MethodCallStatusEnum.FAILED,
                                                "Database error adding run IDs to simulation group: " + e.getMessage()));
                                return runResultAndSimulationGroupId;
                            }

                        }
                        BigInteger simulationGroupId = databaseAccessor
                                .getSimulationGroupIdForRun(runId);

                        runResultAndSimulationGroupId
                                .setRunResult(createRunResult(runId,
                                        runStatus.getStatus(),
                                        runStatus.getMessage()));
                        runResultAndSimulationGroupId
                                .setSimulationGroupId(simulationGroupId);
                        return runResultAndSimulationGroupId;
                    } else if (runStatus.getStatus() == MethodCallStatusEnum.FAILED) {
                        databaseAccessor
                                .removeAllDataAssociatedWithRunId(runId);
                    }
                }

                BigInteger[] runIdSimulationGroupId = databaseAccessor
                        .insertRunIntoDatabase(simulationGroupIdOrNull);

                MethodCallStatusEnum methodCallStatusEnum = simulationGroupIdOrNull == null ? MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE
                        : MethodCallStatusEnum.LOADING_RUN_CONFIG_INTO_DATABASE;

                runResultAndSimulationGroupId.setRunResult(createRunResult(
                        runIdSimulationGroupId[0], methodCallStatusEnum,
                        "Apollo Broker is handling the run request."));
                runResultAndSimulationGroupId
                        .setSimulationGroupId(runIdSimulationGroupId[1]);
            } else
                runResultAndSimulationGroupId.setRunResult(authResult);
            return runResultAndSimulationGroupId;

        } catch (ApolloDatabaseException ex) {
            runResultAndSimulationGroupId.setRunResult(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, "Database exception staging run: " + ex.getMessage()));
            return runResultAndSimulationGroupId;
        }
    }

    public AbstractRunMethod(Authentication authentication,
                             SoftwareIdentification softwareIdentification) {

        this.authentication = new Authentication();
        this.authentication.setRequesterId(authentication.getRequesterId());
        this.authentication.setRequesterPassword(authentication
                .getRequesterPassword());

        this.softwareIdentification = softwareIdentification;
    }

    protected final RunResult getUnauthorizedSoftwareResult() {
        String errorString = "You are not authorized to view results for the "
                + softwareIdentification.getSoftwareName()
                + " "
                + softwareIdentification.getSoftwareType().toString()
                .toLowerCase() + ".";

        return createRunResult(
                ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                MethodCallStatusEnum.AUTHENTICATION_FAILURE, errorString);
    }

    protected static final MethodCallStatus getRunStatus(BigInteger runId)
            throws ApolloDatabaseException {
        MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
        return status;
    }

    protected final boolean authenticateUser() throws ApolloDatabaseException {
        boolean userSuccessfulyAuthenticated = databaseAccessor
                .authenticateUser(authentication);
        return userSuccessfulyAuthenticated;
    }

    protected final boolean userAuthorizedForCachedResults()
            throws ApolloDatabaseException {
        boolean userAuthorizedForCachedResults = databaseAccessor
                .authorizeUserForSoftwareCacheData(authentication,
                        softwareIdentification);
        return userAuthorizedForCachedResults;

    }

    protected final boolean userAuthorizedToRunSoftware()
            throws ApolloDatabaseException {
        boolean userAuthorizedToRunSoftware = databaseAccessor
                .authorizeUserForRunningSoftware(authentication,
                        softwareIdentification);
        return userAuthorizedToRunSoftware;
    }

    public static final RunResult createRunResult(BigInteger runId,
                                                  MethodCallStatusEnum errorCode, String error) {
        RunResult runResult = new RunResult();
        MethodCallStatus methodCallStatus = new MethodCallStatus();
        runResult.setMethodCallStatus(methodCallStatus);
        runResult.setRunId(runId);
        runResult.getMethodCallStatus().setMessage(error);
        runResult.getMethodCallStatus().setStatus(errorCode);
        return runResult;
    }

    public final RunResult authenticateAndAuthorizeUser() {
        try {
            boolean userSuccessfullyAuthenticated = authenticateUser();
            if (!userSuccessfullyAuthenticated) {
                return createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.AUTHENTICATION_FAILURE,
                        "Authentication failure.");
            }

            boolean userAuthorizedForCachedRun = userAuthorizedForCachedResults();
            if (!userAuthorizedForCachedRun) {
                return getUnauthorizedSoftwareResult();
            }
        } catch (ApolloDatabaseException ex) {
            return createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, ex.getMessage());
        }
        return USER_AUTHENTICATED_AND_AUTHORIZED;

    }

}
