package edu.pitt.apollo.translatorservice.thread;

import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.Md5UtilsException;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.simulator_service_types.v4_0_2.RunSimulationMessage;
import edu.pitt.apollo.translatorservice.TranslatorRunner;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.types.BatchResultSetRow;
import edu.pitt.apollo.translatorservice.utility.TranslatorServiceUtils;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;

import java.math.BigInteger;
import java.util.Arrays;

import static edu.pitt.apollo.translatorservice.thread.TranslationThread.logger;

/**
 *
 * @author jdl50
 *
 */
public class IndividualTranslationThread extends Thread {

	private final BatchTranslationThread batchTranslationThread;
	private final TranslatorInput translatorInput;
	JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));

	public IndividualTranslationThread(BatchTranslationThread batchTranslationThread, TranslatorInput translatorInput) throws ApolloDatabaseException {
		this.batchTranslationThread = batchTranslationThread;
		this.translatorInput = translatorInput;
	}

	@Override
	public void run() {

		try {
			BatchResultSetRow row = batchTranslationThread.getNextResultSetRow();
			while (row != null) {

				boolean runFailed = batchTranslationThread.isRunFailed();
				if (!runFailed) {

					String runSimulationMessageString = row.getRunSimulationMessageJson();
					BigInteger simulationRunId = row.getSimulationRunId();

					RunSimulationMessage runSimulationMessage;
					try {
						runSimulationMessage = (RunSimulationMessage) jsonUtils.getObjectFromJson(runSimulationMessageString, RunSimulationMessage.class);
					} catch (JsonUtilsException jue) {
						String message = "JsonUtilsException creating object from Json." + jue.getMessage();
						logger.error(message);
						batchTranslationThread.setRunFailed(message);
						TranslatorServiceUtils.updateStatusForRun(simulationRunId, MethodCallStatusEnum.FAILED, message);
						return;
					}

					translatorInput.setRunSimulationMessage(runSimulationMessage);

					try {
						TranslatorRunner runner = new TranslatorRunner(simulationRunId, translatorInput);
						TranslatorServiceUtils.updateStatusForRun(simulationRunId, MethodCallStatusEnum.TRANSLATING, "Translating run...");
						runner.runTranslator();
						TranslatorServiceUtils.updateStatusForRun(simulationRunId, MethodCallStatusEnum.TRANSLATION_COMPLETED, "Translation complete!");
						batchTranslationThread.completeTranslationOfSingleRun();
					} catch (TranslatorServiceException | Md5UtilsException ex) {
						String message = "Exception running translator for runID: " + simulationRunId + ": " + ex.getMessage();
						logger.error(message);
						batchTranslationThread.setRunFailed(message);
						TranslatorServiceUtils.updateStatusForRun(simulationRunId, MethodCallStatusEnum.FAILED, ex.getMessage());
					}

					row = batchTranslationThread.getNextResultSetRow();
				} else {
					return;
				}
			}
		} catch (TranslatorServiceException ex) {
			String message = "Exception running translator thread: " + ex.getMessage();
			logger.error(message);
			batchTranslationThread.setRunFailed(message);
		}
	}

}
