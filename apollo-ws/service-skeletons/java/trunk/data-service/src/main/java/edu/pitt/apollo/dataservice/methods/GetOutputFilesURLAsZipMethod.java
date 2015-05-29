package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.ApolloServiceQueue;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.RunIdAndFiles;
import static edu.pitt.apollo.dataservice.methods.DataServiceMethod.dbUtils;

import edu.pitt.apollo.dataservice.methods.database.DatabaseAccessor;
import edu.pitt.apollo.dataservice.thread.DataServiceSpecifiedFilesThread;
import edu.pitt.apollo.dataservice.types.FileInformation;
import edu.pitt.apollo.dataservice.types.FileInformationCollection;
import edu.pitt.apollo.dataservice.utils.RunUtils;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
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
 * Date: Jan 16, 2015
 * Time: 3:44:53 PM
 * Class: GetOutputFilesURLAsZipMethod
 */
public class GetOutputFilesURLAsZipMethod extends DataServiceMethod {

	private GetOutputFilesURLAsZipMessage message;
	private static final String FILE_PREFIX = "run_%d_";


	public GetOutputFilesURLAsZipMethod(ApolloServiceQueue queue, BigInteger runId,Authentication authentication) {
		super(queue, runId);
		if(authentication==null) {
			loadGetOutputFilesURLsMessage();
		}
		else{
			loadGetOutputFilesURLsMessage(authentication);
		}
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
		String outputDirectory = OUTPUT_DIRECTORY + runId + File.separator;
		String zipFileName = String.format(ZIP_FILE_NAME, runId);
		fileInformationCollection.setZipFiles(true);
		fileInformationCollection.setZipFileName(zipFileName);
		fileInformationCollection.setOutputDirectory(outputDirectory);

		for (RunIdAndFiles runIdAndFiles : message.getRunIdsAndFiles()) {
			// create a url for each file in the list
			BigInteger run = runIdAndFiles.getRunId();
			List<FileInformation> fileInformationForRun = new ArrayList<FileInformation>();
			fileInformationCollection.put(run, fileInformationForRun);
			List<String> files = runIdAndFiles.getFiles();
			for (String file : files) {
				String outputFilePath = outputDirectory + FILE_PREFIX + file;
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
			message = dbUtils.getGetOutputFilesURLAsZipMessageForRun(runId);
			if (message == null) {
				RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
			}
		} catch (JsonUtilsException jue) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, jue.getMessage());
		} catch (ApolloDatabaseException ex) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, ex.getMessage());
		}
	}

	private void loadGetOutputFilesURLsMessage(Authentication authentication) {
		ApolloDbUtils dbUtils = null;
		try {
			dbUtils = new ApolloDbUtils();
		} catch (ApolloDatabaseException e) {
			e.printStackTrace();
		}
		try {
			DatabaseAccessor dbAccessor = new DatabaseAccessor(authentication,dbUtils);
			dbAccessor.runDataServiceToGetOutputFilesURLAsZip(runId,authentication);
		} catch (ApolloDatabaseException ex) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, ex.getMessage());
		} catch (DataServiceException dse) {
			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, dse.getMessage());
		}
	}
}
