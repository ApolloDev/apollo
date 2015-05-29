package edu.pitt.apollo.soapsimulatorserviceconnector;

import edu.pitt.apollo.connector.SimulatorServiceConnector;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import java.math.BigInteger;
import java.net.URL;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *
 * @author nem41
 */
public class SoapSimulatorServiceConnector extends SimulatorServiceConnector {

	private SimulatorServiceEI port;

	public SoapSimulatorServiceConnector(String url) throws SimulatorServiceException {
		super(url);
		initialize();
	}

	private void initialize() throws SimulatorServiceException {
		try {
			port = new SimulatorServiceV300(new URL(serviceUrl)).getSimulatorServiceEndpoint();

			// disable chunking for ZSI
			Client simulatorClient = ClientProxy.getClient(port);
			HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
			HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
			simulatorHttpClientPolicy.setConnectionTimeout(36000);
			simulatorHttpClientPolicy.setAllowChunking(false);
			simulatorHttp.setClient(simulatorHttpClientPolicy);
		} catch (Exception ex) {
			throw new SimulatorServiceException("Exception getting simulator service endpoint: " + ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId) throws SimulatorServiceException {
		port.runSimulation(runId);
	}

	@Override
	public void runSimulations(BigInteger runId) throws SimulatorServiceException {
		port.runSimulations(runId);
	}

	@Override
	public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException {
		port.terminateRun(terminateRunRequest);
	}

}
