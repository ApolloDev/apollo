package edu.pitt.apollo.soapjobrunningserviceconnector;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.SimulatorServiceException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.TerminateRunRequest;
import edu.pitt.apollo.services_common.v3_0_0.TerminteRunResult;
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
public class SoapJobRunningServiceConnector extends JobRunningServiceConnector {

	private SimulatorServiceEI port;

	public SoapJobRunningServiceConnector(String url) throws SimulatorServiceException {
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
	public void run(BigInteger runId, Authentication authentication) throws SimulatorServiceException {
		MethodCallStatus status = port.runSimulation(runId);
		if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new SimulatorServiceException("The run simulation request to the simulator failed: " + status.getMessage());
		}
	}

	@Override
	public void terminate(TerminateRunRequest terminateRunRequest) throws SimulatorServiceException {
		TerminteRunResult response = port.terminateRun(terminateRunRequest);
		MethodCallStatus status = response.getMethodCallStatus();
		if (status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new SimulatorServiceException("The terminate run request to the simulator failed: " + status.getMessage());
		}
	}

}
