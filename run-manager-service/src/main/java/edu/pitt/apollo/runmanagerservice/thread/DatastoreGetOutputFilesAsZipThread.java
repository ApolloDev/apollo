package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.runmanagerservice.datastore.accessors.DatastoreAccessorForIterableFileContent;
import edu.pitt.apollo.runmanagerservice.types.FileContentForRun;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.FileAndURLDescription;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Feb 6, 2015
 * Time: 4:50:52 PM
 * Class: DataServiceAllFilesThread
 */
public class DatastoreGetOutputFilesAsZipThread extends DatastoreAccessThread {

	private final String outputDirectory;
	private final BigInteger runIdFromMessage;
	private final String outputFileName;
	private final List<String> fileNamesToMatch;

	public DatastoreGetOutputFilesAsZipThread(BigInteger runId, BigInteger runIdFromMessage, ApolloServiceQueue queue,
			String outputDirectory, String outputFileName, List<String> fileNamesToMatch, Authentication authentication) {
		super(runId, queue, authentication);
		this.outputDirectory = outputDirectory;
		this.runIdFromMessage = runIdFromMessage;
		this.outputFileName = outputFileName;
		this.fileNamesToMatch = fileNamesToMatch;
	}

	@Override
	public void runApolloService() {

		try {

			String zipFileName = String.format(outputDirectory + File.separator + outputFileName, runId);
			initializeZipFile(zipFileName);

			try (DatastoreAccessorForIterableFileContent dba = new DatastoreAccessorForIterableFileContent()) {

				dba.retrieveAllFilesForRunIdsAssociatedWithSimulationGroupForRun(runIdFromMessage, fileNamesToMatch);
				FileContentForRun fileContentForRun;
				while ((fileContentForRun = dba.next()) != null) {

					int simulationRunId = fileContentForRun.getRunId();
					String textContent = fileContentForRun.getFileContent();
					String name = fileContentForRun.getFileName();

					if (fileNamesToMatch != null && fileNamesToMatch.size() > 0) {
						if (!fileNamesToMatch.contains(name)) {
							continue;
						}
					}

					String filePath = simulationRunId + File.separator + name;
					addToZip(filePath, textContent.getBytes());
				}

//				Map<BigInteger, FileAndURLDescription> dataContentForBatchRun = dba.getListOfFilesForRunId(runIdFromMessage, authentication);
//				for (BigInteger fileId : dataContentForBatchRun.keySet()) {
//
//					FileAndURLDescription description = dataContentForBatchRun.get(fileId);
//					if (fileNamesToMatch != null && fileNamesToMatch.size() > 0) {
//						if (!fileNamesToMatch.contains(description.getName())) {
//							continue;
//						}
//					}
//
//					String fileContent = dba.getContentForContentId(fileId, authentication);
//					
//					String filePath = runIdFromMessage + "/" + description.getName();
//					addToZip(filePath, fileContent.getBytes());
//				}

			}

			finalizeZipFile();

			updateStatus(MethodCallStatusEnum.COMPLETED, "The data service run has completed");
		} catch (ApolloDatabaseException | IOException | DatastoreException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error creating the output file for the run.");
		}
	}

}
