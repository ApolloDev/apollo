package edu.pitt.apollo.dataservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.dataservice.types.FileInformation;
import edu.pitt.apollo.dataservice.types.FileInformationCollection;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 Email: nick.millett@gmail.com
 Date: Jan 20, 2015
 Time: 11:38:26 AM
 Class: DataServiceSpecifiedFilesThread
 */
public class DataServiceSpecifiedFilesThread extends DataServiceThread {

	private final FileInformationCollection fileInformationCollection;

	public DataServiceSpecifiedFilesThread(BigInteger runId, FileInformationCollection fileInformationCollection, ApolloServiceQueue queue, ApolloDbUtils dbUtils) {
		super(runId, queue, dbUtils);
		this.fileInformationCollection = fileInformationCollection;
	}

	@Override
	public void runApolloService() {

		try {
			for (BigInteger softwareRunId : fileInformationCollection.keySet()) {

				List<FileInformation> fileInformationForRun = fileInformationCollection.get(softwareRunId);

				Map<String, ByteArrayOutputStream> contentMap = dbUtils.getDataContentForSoftware(softwareRunId);

				for (FileInformation fileInformation : fileInformationForRun) {
					String fileName = fileInformation.getFileName();
					if (contentMap.containsKey(fileName)) {
						ByteArrayOutputStream content = contentMap.get(fileName);

						String filePath = fileInformation.getFilePath();
						filePath = String.format(filePath, softwareRunId);
						File outputFile = new File(filePath);
						outputFile.getParentFile().mkdirs();
						outputFile.createNewFile();

						FileOutputStream fos = new FileOutputStream(outputFile);
						content.writeTo(fos);

						fos.close();
					} else {
						updateStatus(MethodCallStatusEnum.FAILED, "The run does not have a '" + fileName + "' file associated with it.");
						return;
					}
				}
			}

			if (fileInformationCollection.isZipFiles()) {

//				String zipFileName = fileInformationCollection.getZipFileName();
//				String outputDirectory = fileInformationCollection.getOutputDirectory();
//				zipOutputFiles(outputDirectory, zipFileName);
			}

			updateStatus(MethodCallStatusEnum.COMPLETED, "The data service run has completed.");
		} catch (ApolloDatabaseException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error getting the run output from the Apollo database.");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error creating the output file for the run.");
		}
	}

}
