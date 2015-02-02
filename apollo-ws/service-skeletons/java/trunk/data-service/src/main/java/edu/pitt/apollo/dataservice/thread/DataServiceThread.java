package edu.pitt.apollo.dataservice.thread;

import edu.pitt.apollo.apolloqueue.ApolloServiceQueue;
import edu.pitt.apollo.apolloqueue.types.ApolloServiceThread;
import edu.pitt.apollo.dataservice.types.FileInformation;
import edu.pitt.apollo.dataservice.types.FileInformationCollection;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 20, 2015
 * Time: 11:38:26 AM
 * Class: DataServiceThread
 */
public class DataServiceThread extends ApolloServiceThread {

	private final FileInformationCollection fileInformationCollection;
	private final ApolloDbUtils dbUtils;
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(DataServiceThread.class);

	public DataServiceThread(BigInteger runId, FileInformationCollection fileInformationCollection, ApolloServiceQueue queue, ApolloDbUtils dbUtils) {
		super(runId, queue);
		this.dbUtils = dbUtils;
		this.fileInformationCollection = fileInformationCollection;
	}

	@Override
	public void runApolloService() {

		try {
			for (BigInteger softwareRunId : fileInformationCollection.keySet()) {

				// first need to get the simulation run IDs in the simulation group for the run
				List<BigInteger> runIdsAssociatedWithSoftwareRunId = dbUtils.getRunIdsForBatch(softwareRunId);

				List<FileInformation> fileInformationForRun = fileInformationCollection.get(softwareRunId);
				for (BigInteger singleRunId : runIdsAssociatedWithSoftwareRunId) {

					Map<String, ByteArrayOutputStream> contentMap = dbUtils.getDataContentForSoftware(singleRunId);

					for (FileInformation fileInformation : fileInformationForRun) {
						String fileName = fileInformation.getFileName();
						if (contentMap.containsKey(fileName)) {
							ByteArrayOutputStream content = contentMap.get(fileName);

							String filePath = fileInformation.getFilePath();
							filePath = String.format(filePath, singleRunId);
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
			}

			if (fileInformationCollection.isZipFiles()) {

				String zipFileName = fileInformationCollection.getZipFileName();
				String outputDirectory = fileInformationCollection.getOutputDirectory();
				zipOutputFiles(outputDirectory, zipFileName);
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

	private void zipOutputFiles(String outputDirectory, String zipFileName) {

		List<String> filesList = new ArrayList<String>();
		File outputDirFile = new File(outputDirectory);
		for (File file : outputDirFile.listFiles()) {
			filesList.add(file.getName());
		}

		byte[] buffer = new byte[1024];
		String zipFile = outputDirectory + zipFileName;
		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String file : filesList) {

				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(outputDirectory + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();
			fos.close();

		} catch (IOException ex) {
			logger.error("IOExcpetion zipping files: " + ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "An error occurred zipping the files.");
		}
	}

	private void updateStatus(MethodCallStatusEnum statusEnum, String message) {
		try {
			dbUtils.updateStatusOfRun(runId, statusEnum, message);
		} catch (ApolloDatabaseException ex) {
			try {
				dbUtils.updateStatusOfRun(runId, MethodCallStatusEnum.FAILED, "ApolloDatabaseException updating status for run ID "
						+ runId + ": " + ex.getMessage());
			} catch (ApolloDatabaseException ex1) {
				logger.error("Could not update the run status for run ID " + runId + ": " + ex1.getMessage());
			}
		}
	}

}
