package edu.pitt.apollo.apolloservice.methods.run;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessor;
import edu.pitt.apollo.apolloservice.database.DatabaseAccessorFactory;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThread;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThreadFactory;
import edu.pitt.apollo.apolloservice.types.ReturnObjectForRun;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

public abstract class AbstractRunMethod implements RunMethod {

    private final Authentication authentication;
    private final SoftwareIdentification softwareIdentification;
    private final Object message;
    private final RunResult USER_AUTHENTICATED_AND_AUTHORIZED = null;
    private final BigInteger associatedSimulationGroup;
    protected DatabaseAccessor dao;

    public AbstractRunMethod(Authentication authentication,
                             SoftwareIdentification softwareIdentification, BigInteger associatedSimulationGroup, Object message) {
        this.authentication = new Authentication();
        this.authentication.setRequesterId(authentication.getRequesterId());
        this.authentication.setRequesterPassword(authentication
                .getRequesterPassword());
        this.message = message;
        this.associatedSimulationGroup = associatedSimulationGroup;
        this.softwareIdentification = softwareIdentification;
    }

    protected final MethodCallStatus getRunStatus(BigInteger runId) {
        return GetRunStatusMethod.getRunStatus(runId);
    }

    protected final RunResult createRunResult(BigInteger runId, MethodCallStatusEnum errorCode, String error) {
        RunResult runResult = new RunResult();
        MethodCallStatus methodCallStatus = new MethodCallStatus();
        runResult.setMethodCallStatus(methodCallStatus);
        runResult.setRunId(runId);
        runResult.getMethodCallStatus().setMessage(error);
        runResult.getMethodCallStatus().setStatus(errorCode);
        return runResult;
    }

    @Override
    public final ReturnObjectForRun stageAndRun() {
        RunResultAndSimulationGroupId runResultAndSimulationGroupId = stage();
        RunResult runResult = runResultAndSimulationGroupId.getRunResult();

        ReturnObjectForRun returnObj = getReturnObjectForRun(runResult);
        if (returnObj.getStatus().getStatus().equals(MethodCallStatusEnum.FAILED)) {
            return returnObj;
        }

        if ((runResult.getMethodCallStatus().getStatus() == getSuccessfulMethodCallStatus())) {
            try {
                RunApolloServiceThread runApolloServiceThread = RunApolloServiceThreadFactory
                        .getRunApolloServiceThread(authentication, message,
                                runResult.getRunId(), runResultAndSimulationGroupId.getSimulationGroupId());
                runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
                runApolloServiceThread.start();
            } catch (Exception e) {
                returnObj.setObjectToReturnFromBroker(createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.FAILED,
                        "Error of type + " + e.getClass().toString() + ":"
                                + e.getMessage()));
            }
        }
        return returnObj;
    }

    @Override
    public RunResultAndSimulationGroupId stage() {


        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {

            dao = DatabaseAccessorFactory.getDatabaseAccessor(
                    message, authentication, dbUtils);


            RunResult authResult = authenticateAndAuthorizeUser();
            if (authResult != USER_AUTHENTICATED_AND_AUTHORIZED) {
                return getRunResultAndSimulationGroupId(authResult, null);
            }

            RunResultAndSimulationGroupId runResultAndSimulationGroupId = null;

            BigInteger cachedRunId = dao.getCachedRunIdFromDatabaseOrNull();
            if (isCached(cachedRunId)) {
                if (!isRunFailed(cachedRunId)) {
                    runResultAndSimulationGroupId =
                            handleNonFailedCachedRun(cachedRunId, associatedSimulationGroup);
                } else {
                    runResultAndSimulationGroupId = handlePreviouslyFailedRun(cachedRunId);
                }
            }

            boolean needToAddRun = runResultAndSimulationGroupId == null;

            if (needToAddRun) {
                BigInteger[] runIdSimulationGroupId = dao
                        .insertRunIntoDatabase(associatedSimulationGroup);

                MethodCallStatusEnum methodCallStatusEnum = getSuccessfulMethodCallStatus();

                return getRunResultAndSimulationGroupId(createRunResult(
                        runIdSimulationGroupId[0], methodCallStatusEnum,
                        "Apollo Broker is handling the run request."), runIdSimulationGroupId[1]);
            } else {
                return runResultAndSimulationGroupId;
            }
        } catch (UnrecognizedMessageTypeException e) {
            return getRunResultAndSimulationGroupId(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, "Unrecognized message type: " + message.getClass().getName() +
                            ".  Error was: " + e.getMessage()), null);
        } catch (ApolloDatabaseException ex) {
            return getRunResultAndSimulationGroupId(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, "Database exception staging run: " + ex.getMessage()), null);
        } catch (Md5UtilsException md5ex) {
            return getRunResultAndSimulationGroupId(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, "Md5 exception staging run: " + md5ex.getMessage()), null);
        }
    }

    protected abstract ReturnObjectForRun getReturnObjectForRun(RunResult runResult);

    protected RunResultAndSimulationGroupId handlePreviouslyFailedRun(BigInteger cachedRunId) {
        final RunResultAndSimulationGroupId RUN_DATA_SUCCESSFULLY_REMOVED = null;

        try {
            dao.removeAllDataAssociatedWithRunId(cachedRunId);
            return RUN_DATA_SUCCESSFULLY_REMOVED;
        } catch (ApolloDatabaseException e) {
            return getRunResultAndSimulationGroupId(createRunResult(
                    ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                    MethodCallStatusEnum.FAILED, "Error removing data for cached run: " + cachedRunId), null);
        }
    }

    protected MethodCallStatusEnum getSuccessfulMethodCallStatus() {
        return MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE;
    }

    protected RunResultAndSimulationGroupId getRunResultAndSimulationGroupId(RunResult runResult, BigInteger simulationGroupId) {
        RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();
        runResultAndSimulationGroupId.setRunResult(runResult);
        runResultAndSimulationGroupId.setSimulationGroupId(simulationGroupId);
        return runResultAndSimulationGroupId;
    }

    protected RunResultAndSimulationGroupId handleNonFailedCachedRun(BigInteger cachedRunId, BigInteger associatedSimulationGroup) {
        if (needToAddToSimulationGroup(associatedSimulationGroup)) {
            List<BigInteger> runIds = new ArrayList<>();
            runIds.add(cachedRunId);
            try {
                dao.addRunIdsToSimulationGroup(associatedSimulationGroup, runIds);
            } catch (Exception e) {
                return getRunResultAndSimulationGroupId(createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.FAILED,
                        "Database error adding run IDs to simulation group: " + e.getMessage()), null);
            }
        }

        MethodCallStatus runStatus = getRunStatus(cachedRunId);

        return getRunResultAndSimulationGroupId(createRunResult(cachedRunId,
                runStatus.getStatus(),
                runStatus.getMessage()), null);
    }

    private boolean authenticateUser() throws ApolloDatabaseException {
        return dao.authenticateUser(authentication);
    }

    private boolean userAuthorizedForCachedResults()
            throws ApolloDatabaseException {
        return dao
                .authorizeUserForSoftwareCacheData(authentication,
                        softwareIdentification);

    }

    private boolean userAuthorizedToRunSoftware()
            throws ApolloDatabaseException {
        return dao.authorizeUserForRunningSoftware(authentication,
                softwareIdentification);
    }

    private RunResult getUnauthorizedSoftwareResult() {
        String errorString = "You are not authorized to view results for the "
                + softwareIdentification.getSoftwareName()
                + " "
                + softwareIdentification.getSoftwareType().toString()
                .toLowerCase() + ".";

        return createRunResult(
                ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                MethodCallStatusEnum.AUTHENTICATION_FAILURE, errorString);
    }

    //TODO: Handle authorization using bitmask
    private RunResult authenticateAndAuthorizeUser() {
        try {
            boolean userSuccessfullyAuthenticated = authenticateUser();
            if (!userSuccessfullyAuthenticated) {
                return createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.AUTHENTICATION_FAILURE,
                        "Authentication failure.");
            }

            boolean userAuthorizedToRunSoftware = userAuthorizedToRunSoftware();
            if (!userAuthorizedToRunSoftware) {
                return createRunResult(
                        ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
                        MethodCallStatusEnum.AUTHENTICATION_FAILURE,
                        "Authentication successful, authorization failed.");
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

    private boolean isCached(BigInteger runId) {
        return runId != null;
    }

    private boolean isRunFailed(BigInteger runId) {
        MethodCallStatus runStatus = getRunStatus(runId);
        return (runStatus.getStatus() == MethodCallStatusEnum.FAILED);
    }

    private boolean needToAddToSimulationGroup(BigInteger associatedSimulationGroup) {
        return associatedSimulationGroup != null;
    }


}
