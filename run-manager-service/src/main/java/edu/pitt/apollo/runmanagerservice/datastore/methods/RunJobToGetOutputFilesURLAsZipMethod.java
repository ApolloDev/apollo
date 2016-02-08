package edu.pitt.apollo.runmanagerservice.datastore.methods;

import edu.pitt.apollo.data_service_types.v4_0.DataRetrievalRequestMessage;
import static edu.pitt.apollo.runmanagerservice.datastore.methods.RunJobMethod.OUTPUT_DIRECTORY;

import edu.pitt.apollo.runmanagerservice.thread.DatastoreGetOutputFilesAsZipThread;
import edu.pitt.apollo.runmanagerservice.thread.DatastoreAccessThread;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
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
        this.message = message;
	}

	@Override
	public void runDataService() throws DataServiceException {
		

		String outputDirectory = OUTPUT_DIRECTORY + runId + File.separator;
		DatastoreAccessThread thread = new DatastoreGetOutputFilesAsZipThread(runId, message.getRunId(), 
				serviceQueue, outputDirectory, ZIP_FILE_NAME, message.getOptionalFileNamesToMatch(), message.getAuthentication());

		MethodCallStatus queueStatus = serviceQueue.addThreadToQueueAndRun(thread);
		if (queueStatus.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new DataServiceException("Queueing failed for run ID " + runId + ": " + queueStatus.getMessage());
		}
	}
}
