package edu.pitt.apollo.dataservice.methods;

import edu.pitt.apollo.data_service_types.v3_0_2.DataRetrievalRequestMessage;
import static edu.pitt.apollo.dataservice.methods.RunJobMethod.OUTPUT_DIRECTORY;

import edu.pitt.apollo.dataservice.thread.DataServiceOutputFilesAsZipThread;
import edu.pitt.apollo.dataservice.thread.DataServiceThread;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import java.io.File;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Feb 6, 2015 Time: 4:40:06 PM Class: GetAllOutputFilesURLAsZipMethod
 */
public class RunJobToGetOutputFilesURLAsZipMethod extends RunJobMethod {

	private DataRetrievalRequestMessage message;

	public RunJobToGetOutputFilesURLAsZipMethod(DataRetrievalRequestMessage message, BigInteger runId) {
		super(runId);
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
}
