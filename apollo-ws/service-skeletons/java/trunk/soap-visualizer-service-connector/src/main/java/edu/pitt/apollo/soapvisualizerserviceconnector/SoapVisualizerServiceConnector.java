package edu.pitt.apollo.soapvisualizerserviceconnector;

import edu.pitt.apollo.connector.VisualizerServiceConnector;
import edu.pitt.apollo.exception.VisulizerServiceException;
import edu.pitt.apollo.service.visualizerservice.v3_0_0.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice.v3_0_0.VisualizerServiceV300;
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
public class SoapVisualizerServiceConnector extends VisualizerServiceConnector {

	private VisualizerServiceEI port;

	public SoapVisualizerServiceConnector(String url) throws VisulizerServiceException {
		super(url);
		initialize();
	}

	private void initialize() throws VisulizerServiceException {

		try {
			port = new VisualizerServiceV300(new URL(serviceUrl)).getVisualizerServiceEndpoint();

			// disable chunking for ZSI
			Client visualizerClient = ClientProxy.getClient(port);
			HTTPConduit visualizerHttp = (HTTPConduit) visualizerClient.getConduit();
			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
			httpClientPolicy.setConnectionTimeout(36000);
			httpClientPolicy.setAllowChunking(false);
			visualizerHttp.setClient(httpClientPolicy);
		} catch (Exception ex) {
			throw new VisulizerServiceException("Exception getting visualizer service endpoint: " + ex.getMessage());
		}

	}

	@Override
	public void run(BigInteger runId) throws VisulizerServiceException {
		port.runVisualization(runId, null);
	}

}
