package edu.pitt.apollo.runmanagerservice.thread;

import java.math.BigInteger;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.SimulatorServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunSimulationThread extends RunApolloServiceThread {

	public RunSimulationThread(BigInteger runId, SoftwareIdentification softwareId, Authentication authentication) {
		super(runId, softwareId, authentication);
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessor();
		
		// the simulation
		try {
			String url = dataServiceAccessor.getURLForSoftwareIdentification(softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
		}
		SimulatorServiceAccessor simulatorServiceAccessor = new SimulatorServiceAccessor();
		//SimulatorServiceConnector simulatorServiceConnector = new RestSimulatorServiceConnector(url);

		RunResult result = simulatorServiceAccessor.run(runId);

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, softwareId, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}
	}
}
