package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.connector.DataServiceConnector;
import edu.pitt.apollo.connector.rest.RestDataServiceConnector;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:25:44 PM Class: RunDataServiceThread
 */
public class RunDataServiceThread extends RunApolloServiceThread {

	private static final String DATA_SERVICE_URL = "";
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

		DataServiceConnector dataServiceConnector = new RestDataServiceConnector(DATA_SERVICE_URL);
		RunResult runResult;
		
		if (getOutputFilesURLsMessage != null) {
			runResult = dataServiceConnector.getOutputFilesURLs(runId);
		} else if (getOutputFilesURLAsZipMessage != null) {
			runResult = dataServiceConnector.getOutputFilesURLAsZip(runId);
		} else if (getAllOutputFilesURLAsZipMessage != null) {
			runResult = dataServiceConnector.getAllOutputFilesURLAsZip(runId);
		}

	}

}
