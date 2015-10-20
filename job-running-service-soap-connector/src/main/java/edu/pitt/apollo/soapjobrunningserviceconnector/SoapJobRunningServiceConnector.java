package edu.pitt.apollo.soapjobrunningserviceconnector;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.service.simulatorservice.v3_0_2.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_2.SimulatorServiceV302;
import edu.pitt.apollo.service.visualizerservice.v3_0_2.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice.v3_0_2.VisualizerServiceV302;
import edu.pitt.apollo.services_common.v3_0_2.*;
import edu.pitt.apollo.types.v3_0_2.ApolloSoftwareTypeEnum;
import edu.pitt.apollo.types.v3_0_2.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v3_0_2.RunVisualizationMessage;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.ws.Service;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author nem41
 */
public class SoapJobRunningServiceConnector extends JobRunningServiceConnector {

	private SoftwareIdentification softwareIdentification;

	public SoapJobRunningServiceConnector(String url, SoftwareIdentification softwareIdentification) throws JobRunningServiceException {
		super(url);
		this.softwareIdentification = softwareIdentification;
	}

	private void initializePort(Service port) throws JobRunningServiceException {
		try {
			// disable chunking for ZSI
			Client simulatorClient = ClientProxy.getClient(port);
			HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
			HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
			simulatorHttpClientPolicy.setConnectionTimeout(36000);
			simulatorHttpClientPolicy.setAllowChunking(false);
			simulatorHttp.setClient(simulatorHttpClientPolicy);
		} catch (Exception ex) {
			throw new JobRunningServiceException("Exception getting simulator service endpoint: " + ex.getMessage());
		}
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {

		MethodCallStatus status = null;
		try {
			if (softwareIdentification.getSoftwareType().equals(ApolloSoftwareTypeEnum.SIMULATOR)) {
				SimulatorServiceEI port = new SimulatorServiceV302(new URL(serviceUrl)).getSimulatorServiceEndpoint();
				status = port.runSimulation(runId);
			} else if (softwareIdentification.getSoftwareType().equals(ApolloSoftwareTypeEnum.VISUALIZER)) {
				VisualizerServiceEI port = new VisualizerServiceV302(new URL(serviceUrl)).getVisualizerServiceEndpoint();

				try {
				    // SOAP visualizer requires RunVisualizationMessage as a parameter, not just runID, so we have to load it
					// temporarily we need ApolloDbUtils, should remove this in the future
					ApolloDbUtils dbUtils = new ApolloDbUtils();
					int vizKey = dbUtils.getSoftwareIdentificationKey(softwareIdentification);
					RunVisualizationMessage runMessage = dbUtils.getRunVisualizationMessageForRun(runId, vizKey);
					
					// runVisualization also does not return a status at this time
					port.runVisualization(runId, runMessage);
				} catch (ApolloDatabaseException | IOException | JsonUtilsException ex) {
					throw new JobRunningServiceException(ex.getMessage());
				}
			} else {
				throw new JobRunningServiceException("Unsupported software type " + softwareIdentification.getSoftwareType() + " in SoapJobRunningServiceConnector");
			}
		} catch (MalformedURLException ex) {
			throw new JobRunningServiceException("MalformedURLException: " + ex.getMessage());
		}

		if (status != null && status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
			throw new JobRunningServiceException("The run simulation request to the simulator failed: " + status.getMessage());
		}
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		TerminateRunRequest request = new TerminateRunRequest();
		request.setAuthentication(authentication);
		request.setRunIdentification(runId);

		TerminteRunResult response = null;
		try {
			if (softwareIdentification.getSoftwareType().equals(ApolloSoftwareTypeEnum.SIMULATOR)) {
				SimulatorServiceEI port = new SimulatorServiceV302(new URL(serviceUrl)).getSimulatorServiceEndpoint();
				response = port.terminateRun(request);
			} else if (softwareIdentification.getSoftwareType().equals(ApolloSoftwareTypeEnum.VISUALIZER)) {
				// not supported now, maybe in the future?
			} else {
				throw new JobRunningServiceException("Unsupported software type " + softwareIdentification.getSoftwareType() + " in SoapJobRunningServiceConnector");
			}
		} catch (MalformedURLException ex) {
			throw new JobRunningServiceException("MalformedURLException: " + ex.getMessage());
		}

		if (response != null) {
			MethodCallStatus status = response.getMethodCallStatus();
			if (status != null && status.getStatus().equals(MethodCallStatusEnum.FAILED)) {
				throw new JobRunningServiceException("The terminate run request to the simulator failed: " + status.getMessage());
			}
		}
	}

}
