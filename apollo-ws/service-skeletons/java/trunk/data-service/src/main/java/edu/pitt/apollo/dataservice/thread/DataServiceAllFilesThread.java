package edu.pitt.apollo.dataservice.thread;

import edu.pitt.apollo.ApolloServiceQueue;
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
 * Email: nick.millett@gmail.com
 * Date: Feb 6, 2015
 * Time: 4:50:52 PM
 * Class: DataServiceAllFilesThread
 */
public class DataServiceAllFilesThread extends DataServiceThread {

	private final String outputDirectory;
	private static final String ZIP_FILE_NAME = "run_%d_all_files.zip";
	private final BigInteger runIdFromMessage;

	public DataServiceAllFilesThread(BigInteger runId, BigInteger runIdFromMessage, ApolloServiceQueue queue, ApolloDbUtils dbUtils, String outputDirectory) {
		super(runId, queue, dbUtils);
		this.outputDirectory = outputDirectory;
		this.runIdFromMessage = runIdFromMessage;
	}

	@Override
	public void runApolloService() {

		try {
			List<BigInteger> runIdsAssociatedWithSoftwareRunId = dbUtils.getRunIdsForBatch(runIdFromMessage);
			if (!runIdsAssociatedWithSoftwareRunId.contains(runIdFromMessage)) {
				runIdsAssociatedWithSoftwareRunId.add(runIdFromMessage); // this will get all files associated with the batch run as well
			}

			for (BigInteger singleRunId : runIdsAssociatedWithSoftwareRunId) {

				Map<String, ByteArrayOutputStream> contentMap = dbUtils.getDataContentForSoftware(singleRunId);
				for (String filename : contentMap.keySet()) {
					ByteArrayOutputStream content = contentMap.get(filename);

					String filePath = outputDirectory + File.separator + singleRunId + File.separator + filename;
					filePath = String.format(filePath, singleRunId);
					File outputFile = new File(filePath);
					outputFile.getParentFile().mkdirs();
					outputFile.createNewFile();

					FileOutputStream fos = new FileOutputStream(outputFile);
					content.writeTo(fos);

					fos.close();
				}
			}

			String zipFileName = String.format(ZIP_FILE_NAME, runIdFromMessage);
			zipOutputFiles(outputDirectory, zipFileName);

		} catch (ApolloDatabaseException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error getting the run output from the Apollo database.");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error creating the output file for the run.");
		}
	}

}
