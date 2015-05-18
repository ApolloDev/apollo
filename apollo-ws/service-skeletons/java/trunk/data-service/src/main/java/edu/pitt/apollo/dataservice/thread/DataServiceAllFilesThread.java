package edu.pitt.apollo.dataservice.thread;

import com.mysql.jdbc.Connection;
import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private final BigInteger runIdFromMessage;
	private final String outputFileName;
	private final List<String> fileNamesToMatch;

	public DataServiceAllFilesThread(BigInteger runId, BigInteger runIdFromMessage, ApolloServiceQueue queue,
			ApolloDbUtils dbUtils, String outputDirectory, String outputFileName, List<String> fileNamesToMatch) {
		super(runId, queue, dbUtils);
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

			PreparedStatement pstmt = null;
			ApolloDbUtils localDbUtils = null;

			try {
				localDbUtils = new ApolloDbUtils();
				pstmt = localDbUtils.getDataContentForBatchSimulations(runIdFromMessage, fileNamesToMatch);

				ResultSet results = pstmt.executeQuery();
//			try {
				while (results.next()) {

					int simulationRunId = results.getInt("id");
					String textContent = results.getString("text_content");
					String name = results.getString("name");

					if (fileNamesToMatch != null && fileNamesToMatch.size() > 0) {
						if (!fileNamesToMatch.contains(name)) {
							continue;
						}
					}

					String filePath = simulationRunId + File.separator + name;
					addToZip(filePath, textContent.getBytes());
				}

				Map<String, ByteArrayOutputStream> dataContentForBatchRun = dbUtils.getDataContentForSoftware(runIdFromMessage);
				for (String name : dataContentForBatchRun.keySet()) {

					if (fileNamesToMatch != null && fileNamesToMatch.size() > 0) {
						if (!fileNamesToMatch.contains(name)) {
							continue;
						}
					}

					String filePath = runIdFromMessage + "/" + name;
					addToZip(filePath, dataContentForBatchRun.get(name).toByteArray());
				}

//			} catch (SQLException ex) {
//				
//			}
			} finally {
				if (pstmt != null) {
					pstmt.close();
				}
				if (localDbUtils != null) {
					localDbUtils.close();
				}
			}

			finalizeZipFile();

//			List<BigInteger> runIdsAssociatedWithSoftwareRunId = dbUtils.getRunIdsForBatch(runIdFromMessage);
//			if (!runIdsAssociatedWithSoftwareRunId.contains(runIdFromMessage)) {
//				runIdsAssociatedWithSoftwareRunId.add(runIdFromMessage); // this will get all files associated with the batch run as well
//			}
//
//			for (BigInteger singleRunId : runIdsAssociatedWithSoftwareRunId) {
//
//				Map<String, ByteArrayOutputStream> contentMap = dbUtils.getDataContentForSoftware(singleRunId);
//				for (String filename : contentMap.keySet()) {
//
//					if (fileNamesToMatch != null && fileNamesToMatch.size() > 0) {
//						if (!fileNamesToMatch.contains(filename)) {
//							continue;
//						}
//					}
//
//					ByteArrayOutputStream content = contentMap.get(filename);
//
//					String filePath = outputDirectory + File.separator + singleRunId + File.separator + filename;
//					filePath = String.format(filePath, singleRunId);
//					File outputFile = new File(filePath);
//					outputFile.getParentFile().mkdirs();
//					outputFile.createNewFile();
//
//					FileOutputStream fos = new FileOutputStream(outputFile);
//					content.writeTo(fos);
//
//					fos.close();
//				}
//			}
//			String zipFileName = String.format(outputFileName, runId);
//			zipOutputFiles(outputDirectory, zipFileName);
			updateStatus(MethodCallStatusEnum.COMPLETED, "The data service run has completed");
		} catch (ApolloDatabaseException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error getting the run output from the Apollo database.");
		} catch (IOException ex) {
			logger.error(ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error creating the output file for the run.");
		} catch (SQLException ex) {
			logger.error("SQLException getting data for run " + runId + ": " + ex.getMessage());
			updateStatus(MethodCallStatusEnum.FAILED, "There was an error getting the data for the run.");
		}
	}

}
