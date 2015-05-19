package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import static edu.pitt.apollo.dataservice.methods.DataServiceMethod.dbUtils;
import edu.pitt.apollo.dataservice.thread.DataServiceSpecifiedFilesThread;
import edu.pitt.apollo.dataservice.types.FileInformation;
import edu.pitt.apollo.dataservice.types.FileInformationCollection;
import edu.pitt.apollo.dataservice.utils.RunUtils;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 20, 2015
 * Time: 4:00:41 PM
 * Class: GetOutputFilesURLsMethod
 */
public class GetOutputFilesURLsMethod extends DataServiceMethod {


	private GetOutputFilesURLsMessage message;
	private static final String FILE_PREFIX = "run_%d_";

	public GetOutputFilesURLsMethod(ApolloServiceQueue queue, BigInteger runId) {
		super(queue, runId);
		loadGetOutputFilesURLsMessage();
	}

	@Override
	public void downloadFiles() {
		ApolloDbUtils dbUtils = null;
		try {
			dbUtils = new ApolloDbUtils();
		} catch (ApolloDatabaseException e) {
			e.printStackTrace();
		}

		FileInformationCollection fileInformationCollection = new FileInformationCollection();
		for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
			// create a url for each file in the list
			BigInteger run = runIdAndFiles.getRunId();
			List<FileInformation> fileInformationForRun = new ArrayList<FileInformation>();
			fileInformationCollection.put(run, fileInformationForRun);
			List<String> files = runIdAndFiles.getFiles();
			for (String file : files) {
				String outputFilePath = OUTPUT_DIRECTORY + runId + File.separator + FILE_PREFIX + file;

				FileInformation fileInformation = new FileInformation();
				fileInformation.setFilePath(outputFilePath);
				fileInformation.setRunId(run.intValue());
				fileInformation.setFileName(file);
				fileInformationForRun.add(fileInformation);
			}

		}

		DataServiceSpecifiedFilesThread thread = new DataServiceSpecifiedFilesThread(runId, fileInformationCollection,
				queue, dbUtils);
		MethodCallStatus queueStatus = queue.addThreadToQueueAndRun(thread);
		if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, queueStatus.getMessage());
		} else {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.RUNNING, "The data service request was successful");
		}
	}

	private void loadGetOutputFilesURLsMessage() {
		ApolloDbUtils dbUtils = null;
		try {
			dbUtils = new ApolloDbUtils();
		} catch (ApolloDatabaseException e) {
			e.printStackTrace();
		}
		try {
			message = dbUtils.getGetOutputFilesURLsMessageForRun(runId);
			if (message == null) {
				RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
			}
		} catch (ApolloDatabaseException ex) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, ex.getMessage());
		}
	}
}
