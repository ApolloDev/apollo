package edu.pitt.apollo.runmanagerservice.methods.stage;

import edu.pitt.apollo.exception.*;
import edu.pitt.apollo.query_service_types.v4_0.RunSimulatorOutputQueryMessage;
import edu.pitt.apollo.runmanagerservice.thread.StageExperimentThread;
import edu.pitt.apollo.runmanagerservice.thread.BatchStageThread;
import edu.pitt.apollo.apollo_service_types.v4_0.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0.RunSimulationsMessage;
import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.filestore_service_types.v4_0.FileIdentification;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessor;
import edu.pitt.apollo.runmanagerservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.services_common.v4_0.InsertRunResult;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.utilities.Md5Utils;
import edu.pitt.apollo.utilities.XMLSerializer;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * @author jdl50
 */


public class StageMethod extends BaseStageMethod {

	static Logger logger = LoggerFactory.getLogger(StageMethod.class);
	private final BigInteger parentRunId;
	
	public StageMethod(RunMessage message, BigInteger parentRunId) throws RunManagementException, UnrecognizedMessageTypeException, ApolloDatabaseException {
		super(message);
		this.parentRunId = parentRunId;
	}
	
	public InsertRunResult stage() throws RunManagementException {

		try {

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

			// need to add message to file store unless part of a batch
			if (parentRunId == null) {
				FileIdentification fileIdentification = new FileIdentification();
				fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
				fileIdentification.setType(ContentDataTypeEnum.RUN_MESSAGE);
				fileIdentification.setLabel("run_message.json");
				String content = new JsonUtils().getJSONString(message);
				DatastoreAccessor.uploadTextFileContent(content, runId, fileIdentification, authentication);

                if (message instanceof RunSimulatorOutputQueryMessage) {
                    // also store XML message
                    fileIdentification = new FileIdentification();
                    fileIdentification.setFormat(ContentDataFormatEnum.TEXT);
                    fileIdentification.setType(ContentDataTypeEnum.RUN_MESSAGE);
                    fileIdentification.setLabel("run_message.xml");
                    content = new XMLSerializer().serializeObject(message);
                    DatastoreAccessor.uploadTextFileContent(content, runId, fileIdentification, authentication);
                }
            }

			if (!(message instanceof RunSimulationsMessage) && !(message instanceof RunInfectiousDiseaseTransmissionExperimentMessage)) {
				if (!(message instanceof RunVisualizationMessage) && !(message instanceof DataRetrievalRequestMessage)
                        && !(message instanceof RunSimulatorOutputQueryMessage)) {

					// if parentRunId is set, this run is part of a batch and will be translated later
					if (parentRunId == null) {
						// message is RunSimulationMessage
						translateRun(runId, dataServiceDao, authentication);
					}
					
				} else {
					// update the status to translation completed even though visualization runs are not translated at this time
					// so that the WaitForTranslationAndStartRunThread will work
					dataServiceDao.updateStatusOfRun(runId, MethodCallStatusEnum.TRANSLATION_COMPLETED, "Translation completed", authentication);
				}
			} else if (message instanceof RunSimulationsMessage) {
				BatchStageThread batchStageMethod = new BatchStageThread(runId, (RunSimulationsMessage) message, authentication, apolloServiceQueue);
				apolloServiceQueue.addThreadToQueueAndRun(batchStageMethod);
			} else if (message instanceof RunInfectiousDiseaseTransmissionExperimentMessage) {
				StageExperimentThread method = new StageExperimentThread(runId, apolloServiceQueue,
						(RunInfectiousDiseaseTransmissionExperimentMessage) message, authentication);
				apolloServiceQueue.addThreadToQueueAndRun(method);
			}

			// run is now translated
			return insertRunResult;
		} catch (MalformedURLException ex) {
			throw new RunManagementException("Malformed URL exception staging run : " + ex.getMessage());
		} catch (DatastoreException ex) {
			throw new RunManagementException("Database exception staging run: " + ex.getMessage());
		} catch (TranslatorServiceException ex) {
			throw new RunManagementException("Translator service exception staging run: " + ex.getMessage());
		} catch (FilestoreException ex) {
			throw new RunManagementException("Filestore exception staging run: " + ex.getMessage());
		} catch (SerializationException ex) {
            throw new RunManagementException("Serialization exception staging run: " + ex.getMessage());
        }
	}
}
