package edu.pitt.apollo.runmanagerservice.thread;

import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:25:44 PM Class: RunDataServiceThread
 */
public class RunDataServiceThread extends RunApolloServiceThread {

	private static SoftwareIdentification dataServiceSoftwareId;

	public RunDataServiceThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
		super(runId, softwareId, authentication);
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		try {

			RunResult result = dataServiceAccessor.runDataService(runId, authentication);

			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}
	}

}
