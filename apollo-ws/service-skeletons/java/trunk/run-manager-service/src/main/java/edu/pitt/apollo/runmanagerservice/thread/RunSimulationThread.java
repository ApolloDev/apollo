package edu.pitt.apollo.runmanagerservice.thread;

import java.math.BigInteger;
import edu.pitt.apollo.connector.SimulatorServiceConnector;
import edu.pitt.apollo.connector.rest.RestSimulatorServiceConnector;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.methods.run.ApolloServiceErrorHandler;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorForRunningASingleSimulation;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.SimulatorServiceAccessor;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunSimulationThread extends RunApolloServiceThread {

	private final RunSimulationMessage message;

	public RunSimulationThread(RunSimulationMessage message, BigInteger runId, Authentication authentication) {
		super(runId, authentication);
		this.message = message;
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessorForRunningASingleSimulation(message);
		
		// the simulation
		SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
		try {
			String url = dataServiceAccessor.getURLForSoftwareIdentification(simulatorIdentification, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Error getting URL for software identification, error was:" + e.getMessage(), runId);
		}
		SimulatorServiceAccessor simulatorServiceAccessor = new SimulatorServiceAccessor();
		//SimulatorServiceConnector simulatorServiceConnector = new RestSimulatorServiceConnector(url);

		RunResult result = simulatorServiceAccessor.run(runId);

		try {
			dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, simulatorIdentification, authentication);
		} catch (DataServiceException e) {
			ApolloServiceErrorHandler.reportError("Unable to update last service to be called for run, error was:" + e.getMessage(), runId);
		}
	}
}
