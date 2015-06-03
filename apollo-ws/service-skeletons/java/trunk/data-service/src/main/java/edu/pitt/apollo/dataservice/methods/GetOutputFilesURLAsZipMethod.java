package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import static edu.pitt.apollo.dataservice.methods.DataServiceMethod.OUTPUT_DIRECTORY;

import edu.pitt.apollo.dataservice.thread.DataServiceOutputFilesAsZipThread;
import edu.pitt.apollo.dataservice.thread.DataServiceThread;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import java.io.File;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 6, 2015 Time: 4:40:06 PM Class: GetAllOutputFilesURLAsZipMethod
 */
public class GetOutputFilesURLAsZipMethod extends DataServiceMethod {

	private GetOutputFilesURLAsZipMessage message;

	public GetOutputFilesURLAsZipMethod(GetOutputFilesURLAsZipMessage message, BigInteger runId) {
		super(runId);
//		loadGetAllOutputFilesURLAzZipMessageAndDownloadFiles();
	}

	@Override
	public void runDataService() throws DataServiceException {
		

		String outputDirectory = OUTPUT_DIRECTORY + runId + File.separator;
		DataServiceThread thread = new DataServiceOutputFilesAsZipThread(runId, message.getRunId(), 
				serviceQueue, outputDirectory, ZIP_FILE_NAME, message.getOptionalFileNamesToMatch(), message.getAuthentication());

		MethodCallStatus queueStatus = serviceQueue.addThreadToQueueAndRun(thread);
		if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new DataServiceException("Queueing failed for run ID " + runId + ": " + queueStatus.getMessage());
		}
	}

//	private void loadGetOutputFilesURLAzZipMessage() {
//		ApolloDbUtils dbUtils = null;
//		try {
//			dbUtils = new ApolloDbUtils();
//		} catch (ApolloDatabaseException e) {
//			e.printStackTrace();
//		}
//		try {
//
//			message = dbUtils.getGetAllOutputFilesURLAsZipMessageForRun(runId);
//			if (message == null) {
//				RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, "The runSimulationMessage obtained from the database was null");
//			}
//		} catch (ApolloDatabaseException ex) {
//
//			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, ex.getMessage());
//		} catch (JsonUtilsException jue) {
//			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, jue.getMessage());
//		}
//	}
//
//	private void loadGetAllOutputFilesURLAzZipMessageAndDownloadFiles(Authentication authentication) {
//
//		ApolloDbUtils dbUtils = null;
//		try {
//			dbUtils = new ApolloDbUtils();
//		} catch (ApolloDatabaseException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			DatabaseAccessor dbAccessor = new DatabaseAccessor(authentication, dbUtils);
//			dbAccessor.runDataService(runId, authentication);
//		} catch (ApolloDatabaseException ex) {
//			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, ex.getMessage());
//		} catch (DataServiceException dse) {
//			RunUtils.updateStatus(dbUtils, runId, MethodCallStatusEnum.FAILED, dse.getMessage());
//		}
//	}
}
