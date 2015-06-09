package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.runmanagerservice.types.RunResultAndSimulationGroupId;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunMessage;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

/**
 * @author nem41
 */
public class StageMethod {

    private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
    private final RunMessage message;

    private final Authentication authentication;
    private final BigInteger parentRunId;
    protected DataServiceAccessor dataServiceDao;

    public StageMethod(RunMessage message, BigInteger parentRunId) throws RunManagementException {
        this.message = message;
        this.parentRunId = parentRunId;
        this.authentication = getAuthentication(message);
    }

    private Authentication cloneAuthentication(Authentication srcAuthentication) {
        Authentication authentication = new Authentication();
        authentication.setRequesterId(srcAuthentication.getRequesterId());
        authentication.setRequesterPassword(srcAuthentication.getRequesterPassword());
        return authentication;
    }

    private Authentication getAuthentication(Object message) throws RunManagementException {
        Authentication authentication;
        if (message instanceof RunSimulationMessage) {
            authentication = ((RunSimulationMessage) message).getAuthentication();
        } else if (message instanceof RunSimulationsMessage) {
            authentication = ((RunSimulationsMessage) message).getAuthentication();
        } else if (message instanceof RunVisualizationMessage) {
            authentication = ((RunVisualizationMessage) message).getAuthentication();
        } else {
            throw new RunManagementException("Unsupported message type of " + message.getClass().getName() + " passed to the Stage method of the RunManagerService.");
        }
        return cloneAuthentication(authentication);
    }

    public BigInteger stage() throws RunManagementException {

        try {
            dataServiceDao = new DataServiceAccessor();

            BigInteger runId = dataServiceDao.insertRun(message);
            if (parentRunId != null) {
                dataServiceDao.addRunIdsToSimulationGroupForRun(runId, Arrays.asList(new BigInteger[] {parentRunId}), authentication);
            }

            //BigInteger simulationGroupId = runIdSimulationGroupId[1];
            MethodCallStatus runStatus = dataServiceDao.getRunStatus(runId, authentication);
            MethodCallStatusEnum statusEnum = runStatus.getStatus();

            long statusStartTime = System.currentTimeMillis();
            while (!statusEnum.equals(MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)) {
                try {
                    switch (statusEnum) {
                        case FAILED:
                            throw new RunManagementException("Run " + runId + " FAILED with message " + runStatus.getMessage());
                        default:
                            Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
                            break;
                    }
                } catch (InterruptedException ex) {
                    // don't care
                }
            }

            String url = getTranslatorServiceUrl(dataServiceDao, authentication);
            if (url == null) {
                throw new RunManagementException("There was no translator URL available!");
            }


            // run is loaded, now call translator
            TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor(url);
            translatorDao.translateRun(runId);

            while (!dataServiceDao.getRunStatus(runId, authentication).getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {
                try {
                    switch (statusEnum) {
                        case FAILED:
                            break;
                        case RUN_TERMINATED:
                            break;
                        case UNKNOWN_RUNID:
                            break;
                        case UNAUTHORIZED:
                            break;
                        default:
                            Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
                            break;
                    }
                } catch (InterruptedException ex) {
                    // it's okay if the sleep timer is interrupted
                }
            }
            // run is now translated
            return runId;
        } catch (DataServiceException ex) {
            throw new RunManagementException("Database exception staging run: " + ex.getMessage());
        } catch (TranslatorServiceException ex) {
            throw new RunManagementException("Translator service service exception staging run: " + ex.getMessage());
        }
    }

    protected RunResultAndSimulationGroupId getRunResultAndSimulationGroupId(RunResult runResult, BigInteger simulationGroupId) {
        RunResultAndSimulationGroupId runResultAndSimulationGroupId = new RunResultAndSimulationGroupId();
        runResultAndSimulationGroupId.setRunResult(runResult);
        runResultAndSimulationGroupId.setSimulationGroupId(simulationGroupId);
        return runResultAndSimulationGroupId;
    }

    private String getTranslatorServiceUrl(DataServiceAccessor dataServiceDao, Authentication authentication) throws DataServiceException {

        Map<Integer, ServiceRegistrationRecord> software = dataServiceDao.getListOfRegisteredSoftwareRecords(authentication);
        for (ServiceRegistrationRecord record : software.values()) {
            if (record.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.TRANSLATOR)) {
                return record.getUrl();
            }
        }

        return null;
    }

}
