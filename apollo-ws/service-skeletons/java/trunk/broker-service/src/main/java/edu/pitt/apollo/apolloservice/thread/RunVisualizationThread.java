package edu.pitt.apollo.apolloservice.thread;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.visualizerservice.v3_0_0.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice.v3_0_0.VisualizerServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 4, 2014
 * Time: 10:23:41 AM
 * Class: RunVisualizationThread
 * IDE: NetBeans 6.9.1
 */
public class RunVisualizationThread extends RunApolloServiceThread {

    private final RunVisualizationMessage message;

    public RunVisualizationThread(RunVisualizationMessage message, BigInteger runId) {
		super(runId);
        this.message = message;
    }

    @Override
    public void run() {
        SoftwareIdentification visualizerIdentification = message.getVisualizerIdentification();
        String url = null;
        try {
            try {

                url = dbUtils.getUrlForSoftwareIdentification(visualizerIdentification);
                VisualizerServiceEI visualizerPort = new VisualizerServiceV300(new URL(url)).getVisualizerServiceEndpoint();

                // disable chunking for ZSI
                Client visualizerClient = ClientProxy.getClient(visualizerPort);
                HTTPConduit visualizerHttp = (HTTPConduit) visualizerClient.getConduit();
                HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
                httpClientPolicy.setConnectionTimeout(36000);
                httpClientPolicy.setAllowChunking(false);
                visualizerHttp.setClient(httpClientPolicy);

                visualizerPort.runVisualization(runId, message);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile(
                        "Apollo database key not found attempting to get URL for visualizer: "
                        + visualizerIdentification.getSoftwareName() + ", version: "
                        + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), runId);
                return;
            } catch (MalformedURLException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile(
                        "MalformedURLException attempting to create port for visualizer: "
                        + visualizerIdentification.getSoftwareName() + ", version: "
                        + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
                        + ". Error message was: " + ex.getMessage(), runId);
                return;
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile(
                        "ApolloDatabaseException attempting to get URL for visualizer: " + visualizerIdentification.getSoftwareName()
                        + ", version: " + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), runId);
                return;
            }
            try {
                dbUtils.updateLastServiceToBeCalledForRun(runId, visualizerIdentification);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile("Apollo database key not found attempting to update last service"
                        + " call for run id " + runId + ": " + ex.getMessage(), runId);
                return;
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile("ApolloDatabaseException attempting to update last service" + " call for run id "
                        + runId + ": " + ex.getMessage(), runId);
                return;
            }
        } catch (IOException e) {
            logger.error("Error writing error file!: " + e.getMessage());
        }
    }

	@Override
	public void setAuthenticationPasswordFieldToBlank() {
		message.getAuthentication().setRequesterPassword("");
	}
}
