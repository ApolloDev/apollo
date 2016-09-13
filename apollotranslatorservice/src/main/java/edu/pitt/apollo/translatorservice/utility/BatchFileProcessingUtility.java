/*
 * Copyright 2016 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.translatorservice.utility;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.ContentDataFormatEnum;
import edu.pitt.apollo.services_common.v4_0.ContentDataTypeEnum;
import edu.pitt.apollo.translatorservice.exception.TranslatorServiceException;
import edu.pitt.apollo.translatorservice.types.BatchResultSetRow;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.wagon.util.FileUtils;
import org.zeroturnaround.zip.ZipUtil;

/**
 *
 * @author nem41
 */
public class BatchFileProcessingUtility {

	private static final String BATCH_FILE_NAME = "run_messages.zip";
	private static final String RUN_MESSAGE_FILE_NAME = "run_message.json";
	private final BigInteger runId;
	private final Authentication authentication;
	private File runDirectory;
	private Queue<BigInteger> runIds;

	public BatchFileProcessingUtility(BigInteger runId, Authentication authentication) {
		this.runId = runId;
		this.authentication = authentication;
	}

	public void initialize() throws TranslatorServiceException {
		setRunDirectory(runId);
		downloadRunMessages(runId, authentication);
		getListOfAllRunIds();
	}

	public BatchResultSetRow getNextRunMessage() throws TranslatorServiceException {

		BigInteger runId = runIds.poll();
		if (runId == null) {
			return null;
		}

		BatchResultSetRow batchResultSetRow = new BatchResultSetRow();
		batchResultSetRow.setSimulationRunId(runId);

		try {
			String messageContent = new String(Files.readAllBytes(Paths.get(runDirectory.getAbsolutePath()
					+ File.separator + runId + ".json")));
			batchResultSetRow.setRunSimulationMessageJson(messageContent);

			return batchResultSetRow;
		} catch (IOException ex) {
			throw new TranslatorServiceException("IOException reading run_message file for run ID " + runId + ": "
					+ ex.getMessage());
		}
	}
	
	public int getNumRunsLeft() {
		return runIds.size();
	}

	private void downloadRunMessages(BigInteger runId, Authentication authentication) throws TranslatorServiceException {

		// first check if batch
		boolean batchRun = false;
		try (ApolloDbUtils apolloDatabaseConnection = new ApolloDbUtils()) {
			batchRun = apolloDatabaseConnection.isRunBatch(runId);
		} catch (ApolloDatabaseException ex) {
			throw new TranslatorServiceException("Database exception checking if run is batch: " + ex.getMessage());
		}

		// make directory for this run
		String runDir = runDirectory.getAbsolutePath() + File.separator;

		try {
			if (batchRun) {
				// download zip file
				String url = TranslatorServiceUtils.getFilestoreServiceConnector().getUrlOfFile(runId, BATCH_FILE_NAME,
						ContentDataFormatEnum.ZIP, ContentDataTypeEnum.RUN_MESSAGE, authentication);
				FileUtils.copyURLToFile(new URL(url), new File(runDir + BATCH_FILE_NAME));

				// unzip the file
				ZipUtil.unpack(new File(runDir + BATCH_FILE_NAME), runDirectory);

			} else {
				String url = TranslatorServiceUtils.getFilestoreServiceConnector().getUrlOfFile(runId, RUN_MESSAGE_FILE_NAME,
						ContentDataFormatEnum.TEXT, ContentDataTypeEnum.RUN_MESSAGE, authentication);

				FileUtils.copyURLToFile(new URL(url), new File(runDir + runId + ".json"));
			}
		} catch (FilestoreException | IOException ex) {
			throw new TranslatorServiceException("FilestoreException downloading run messages: " + ex.getMessage());
		}

		// at this point, the run directory contains one or more .json files, each one a run message
	}

	private void setRunDirectory(BigInteger runId) {
		String runDir = TranslatorServiceUtils.LOCAL_FILE_DIR + File.separator + runId + File.separator;
		runDirectory = new File(runDir);
		if (!runDirectory.exists()) {
			runDirectory.mkdirs();
		}
	}

	private void getListOfAllRunIds() {
		runIds = new LinkedList<BigInteger>();

		File[] files = runDirectory.listFiles();
		for (File file : files) {
			String name = file.getName();
			String ext = FilenameUtils.getExtension(name);
			if (ext.equals("json")) {
				BigInteger runId = new BigInteger(FilenameUtils.getBaseName(name));
				runIds.add(runId);
			}
		}
	}
}
