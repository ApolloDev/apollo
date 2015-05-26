package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorForRunningDataServiceRequests;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:25:44 PM Class: RunDataServiceThread
 */
public class RunDataServiceThread extends RunApolloServiceThread {

	
	private GetOutputFilesURLsMessage getOutputFilesURLsMessage;
	private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage;
	private GetAllOutputFilesURLAsZipMessage getAllOutputFilesURLAsZipMessage;

	public RunDataServiceThread(GetOutputFilesURLsMessage message, BigInteger runId) {
		super(runId);
		this.getOutputFilesURLsMessage = message;
	}

	public RunDataServiceThread(GetOutputFilesURLAsZipMessage message, BigInteger runId) {
		super(runId);
		this.getOutputFilesURLAsZipMessage = message;
	}

	public RunDataServiceThread(GetAllOutputFilesURLAsZipMessage message, BigInteger runId) {
		super(runId);
		this.getAllOutputFilesURLAsZipMessage = message;
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor;
		RunResult runResult;
		
		if (getOutputFilesURLsMessage != null) {
			dataServiceAccessor = new DataServiceAccessorForRunningDataServiceRequests(getOutputFilesURLsMessage);
			runResult = dataServiceAccessor.getOutputFilesURLs(runId);
		} else if (getOutputFilesURLAsZipMessage != null) {
			dataServiceAccessor = new DataServiceAccessorForRunningDataServiceRequests(getOutputFilesURLAsZipMessage);
			runResult = dataServiceAccessor.getOutputFilesURLAsZip(runId);
		} else if (getAllOutputFilesURLAsZipMessage != null) {
			dataServiceAccessor = new DataServiceAccessorForRunningDataServiceRequests(getAllOutputFilesURLAsZipMessage);
			runResult = dataServiceAccessor.getAllOutputFilesURLAsZip(runId);
		}

	}

}
