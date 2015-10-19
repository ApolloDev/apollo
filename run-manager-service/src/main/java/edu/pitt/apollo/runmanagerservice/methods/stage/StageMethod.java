package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.apollo_service_types.v3_0_2.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v3_0_2.DataRetrievalRequestMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.RunManagementException;
import edu.pitt.apollo.exception.TranslatorServiceException;
import edu.pitt.apollo.runmanagerservice.types.RunResultAndSimulationGroupId;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.TranslatorServiceAccessor;
import edu.pitt.apollo.types.v3_0_2.ApolloSoftwareTypeEnum;;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.InsertRunResult;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_2.RunMessage;
import edu.pitt.apollo.services_common.v3_0_2.RunResult;
import edu.pitt.apollo.services_common.v3_0_2.ServiceRegistrationRecord;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_2.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jdl50
 */
public class StageMethod {

    static Logger logger = LoggerFactory.getLogger(StageMethod.class);
    private static final long STATUS_CHECK_INTERVAL_TIME_IN_MILLIS = 5000;
    private final RunMessage message;

    private final Authentication authentication;
    private final BigInteger parentRunId;
    protected DataServiceAccessor dataServiceDao;
    protected static ApolloServiceQueue apolloServiceQueue;

    static {
        apolloServiceQueue = new ApolloServiceQueue();
    }

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

    private Authentication getAuthentication(RunMessage message) throws RunManagementException {
        Authentication authentication = message.getAuthentication();
        return cloneAuthentication(authentication);
    }

    public InsertRunResult stage() throws RunManagementException {

        try {
            dataServiceDao = new DataServiceAccessor();

            InsertRunResult insertRunResult = dataServiceDao.insertRun(message);

            BigInteger runId = insertRunResult.getRunId();
            if (parentRunId != null) {
                dataServiceDao.addRunIdsToSimulationGroupForRun(parentRunId, Arrays.asList(new BigInteger[]{runId}), authentication);
            }

            if (insertRunResult.isRunCached()) {
                return insertRunResult;
            }

            MethodCallStatus methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
            while (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)
                    && (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED))) {
                try {
                    switch (methodCallStatus.getStatus()) {
                        case FAILED:
                            throw new RunManagementException("Run " + runId + " FAILED with message " + methodCallStatus.getMessage());
                        default:
                            logger.debug("Status for run " + runId + " was: (" + methodCallStatus.getMessage() + ") " + methodCallStatus.getStatus());
                            Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
                            methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
                            break;
                    }
                } catch (InterruptedException ex) {
                    // don't care
                }
            }

            if (!(message instanceof RunSimulationsMessage)) {
                if (!(message instanceof RunVisualizationMessage) && !(message instanceof DataRetrievalRequestMessage)) {
                    String url = getTranslatorServiceUrl(dataServiceDao, authentication);
                    if (url == null) {
                        throw new RunManagementException("There was no translator URL available!");
                    }
                    // run is loaded, now call translator
                    TranslatorServiceAccessor translatorDao = new TranslatorServiceAccessor(url);
                    translatorDao.translateRun(runId);

                    methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
                    while (!methodCallStatus.getStatus().equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)
                            && !methodCallStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
                        try {
                            switch (methodCallStatus.getStatus()) {
                                case FAILED:
                                    break;
                                case RUN_TERMINATED:
                                    break;
                                case UNKNOWN_RUNID:
                                    break;
                                case UNAUTHORIZED:
                                    break;
                                default:
                                    logger.debug("Status was: (" + methodCallStatus.getMessage() + ") " + methodCallStatus.getStatus());
                                    Thread.sleep(STATUS_CHECK_INTERVAL_TIME_IN_MILLIS);
                                    methodCallStatus = dataServiceDao.getRunStatus(runId, authentication);
                                    break;
                            }
                        } catch (InterruptedException ex) {
                            // it's okay if the sleep timer is interrupted
                        }
                    }
                } else {
                    // update the status to translation completed even though visualization runs are not translated at this time
                    // so that the WaitForTranslationAndStartRunThread will work
                    dataServiceDao.updateStatusOfRun(runId, MethodCallStatusEnum.TRANSLATION_COMPLETED, "Translation completed", authentication);
                }
            } else {
                BatchStageMethod batchStageMethod = new BatchStageMethod(runId, (RunSimulationsMessage) message, authentication, apolloServiceQueue);
                apolloServiceQueue.addThreadToQueueAndRun(batchStageMethod);
            }

            // run is now translated
            return insertRunResult;
        } catch (MalformedURLException ex) {
            throw new RunManagementException("Malformed URL exception staging run : " + ex.getMessage());
        } catch (DataServiceException ex) {
            throw new RunManagementException("Database exception staging run: " + ex.getMessage());
        } catch (TranslatorServiceException ex) {
            throw new RunManagementException("Translator service exception staging run: " + ex.getMessage());
        }
    }

    private String getTranslatorServiceUrl(DataServiceAccessor dataServiceDao, Authentication authentication) throws DataServiceException {

        List<ServiceRegistrationRecord> software = dataServiceDao.getListOfRegisteredSoftwareRecords(authentication);
        for (ServiceRegistrationRecord record : software) {
            if (record.getSoftwareIdentification().getSoftwareType().equals(ApolloSoftwareTypeEnum.TRANSLATOR)) {
                return record.getUrl();
            }
        }
        return null;
    }

}
