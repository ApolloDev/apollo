package edu.pitt.apollo.translatorservice.thread;


import edu.pitt.apollo.apollotranslator.types.translator.TranslatorInput;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v4_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.types.BatchResultSetRow;
import edu.pitt.apollo.translatorservice.utility.BatchFileProcessingUtility;
import edu.pitt.apollo.translatorservice.utility.TranslatorServiceUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 27, 2015 Time: 1:39:17 PM Class: BatchTranslationThread
 */
public class BatchTranslationThread extends TranslationThread {

	private int numberOfRunsTranslated;
	private BatchFileProcessingUtility batchFileProcessingUtility;

	public BatchTranslationThread(BigInteger runId) {
		super(runId);
		batchFileProcessingUtility = new BatchFileProcessingUtility(runId, TranslatorServiceUtils.getAuthentication());
	}

	@Override
	protected void runTranslation() throws TranslatorServiceException {

		batchFileProcessingUtility.initialize();

		numberOfRunsTranslated = 0;

		TranslatorInput translationInput = new TranslatorInput();
		translationInput.setTranslationInstructionsFilePath(simulatorTranslationInstructionsFilename);
		translationInput.setJavaScriptFilePath(simulatorJavascriptMethodsFilename);
		translationInput.setTranslationMode(translationMode);

		if (urnOfBaseConfigurationInLibrary != null) {
			// use apollo library rest connector here
		} else if (fileLocationOfBaseConfiguration != null) {
			try {
				String fileContent = new String(Files.readAllBytes(Paths.get(fileLocationOfBaseConfiguration)));
				translationInput.setBaseConfigurationFileContent(fileContent);
			} catch (IOException ex) {
				TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, "IO exception reading base configuration file content for batch run ID "
						+ runId + ": " + ex.getMessage());
				return;
			}
		}

		try {
			if (batchFileProcessingUtility.getNumRunsLeft() == 0) {
				// this means no results were returned
				TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, "No run IDs were associated with batch ID " + runId);
				return;
			}

			TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.TRANSLATING, "Starting translation threads...");
			// create and start all translation threads
            int numThreadsRequired;
            if (batchFileProcessingUtility.getNumRunsLeft() >= numThreads) {
                numThreadsRequired = numThreads;
            } else {
                numThreadsRequired = batchFileProcessingUtility.getNumRunsLeft();
            }
			ExecutorService executor = Executors.newFixedThreadPool(numThreadsRequired);
			for (int i = 0; i < numThreadsRequired; i++) {
				Thread thread = new IndividualTranslationThread(this, translationInput);
				executor.execute(thread);
			}

			executor.shutdown();
			try {
				executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (InterruptedException ex) {
				String message = "InterrupterException running awaiting executor service termination: " + ex.getMessage();
				logger.error(message);
				TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, message);
				return;
			}

		} catch (ApolloDatabaseException ex) {
			String message = "ApolloDatabaseException creating and starting threads for run ID " + runId + ": " + ex.getMessage();
			logger.error(message);
			TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.FAILED, message);
			return;
		}
	}

	public synchronized BatchResultSetRow getNextResultSetRow() throws TranslatorServiceException {
		if (batchFileProcessingUtility.getNumRunsLeft() > 0) {
			return batchFileProcessingUtility.getNextRunMessage();
		} else {
			return null;
		}
	}

	public synchronized void completeTranslationOfSingleRun() {
		numberOfRunsTranslated++;
		if (!isRunFailed()) {
			// if the run is already failed, don't update the status
			TranslatorServiceUtils.updateStatusForRun(runId, MethodCallStatusEnum.TRANSLATING, numberOfRunsTranslated + " runs have been translated.");
		}
	}

}
