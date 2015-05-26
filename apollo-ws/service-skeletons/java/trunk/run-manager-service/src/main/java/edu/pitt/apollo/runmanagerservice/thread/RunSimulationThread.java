package edu.pitt.apollo.runmanagerservice.thread;

import java.math.BigInteger;
import edu.pitt.apollo.connector.SimulatorServiceConnector;
import edu.pitt.apollo.connector.rest.RestSimulatorServiceConnector;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessor;
import edu.pitt.apollo.runmanagerservice.serviceaccessors.DataServiceAccessorForRunningASingleSimulation;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time: 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunSimulationThread extends RunApolloServiceThread {

	private final RunSimulationMessage message;

	public RunSimulationThread(RunSimulationMessage message, BigInteger runId) {
		super(runId);
		this.message = message;
	}

	@Override
	public void run() {

		DataServiceAccessor dataServiceAccessor = new DataServiceAccessorForRunningASingleSimulation(message);
		
		// the simulation
		SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
		String url = dataServiceAccessor.getURLForSoftwareId(simulatorIdentification);
		SimulatorServiceConnector simulatorServiceConnector = new RestSimulatorServiceConnector(url);

		RunResult result = simulatorServiceConnector.run(runId);

		dataServiceAccessor.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
	}
}
