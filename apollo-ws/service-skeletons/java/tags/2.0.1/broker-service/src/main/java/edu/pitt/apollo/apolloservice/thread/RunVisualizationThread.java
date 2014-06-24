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

import edu.pitt.apollo.apolloservice.database.ApolloDbUtilsContainer;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceEI;
import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceV201;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 4, 2014
 * Time: 10:23:41 AM
 * Class: RunVisualizationThread
 * IDE: NetBeans 6.9.1
 */
public class RunVisualizationThread extends Thread {

    private RunVisualizationMessage message;
    private BigInteger runId;
    private ApolloDbUtils dbUtils;

    public RunVisualizationThread(BigInteger runId, RunVisualizationMessage message) {
        this.message = message;
        this.runId = runId;
        this.dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
    }

    @Override
    public void run() {
        SoftwareIdentification visualizerIdentification = message.getVisualizerIdentification();
        String url = null;
        try {
            try {

                url = dbUtils.getUrlForSoftwareIdentification(visualizerIdentification);
                VisualizerServiceEI visualizerPort = new VisualizerServiceV201(new URL(url)).getVisualizerServiceEndpoint();

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
            } catch (ClassNotFoundException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile(
                        "ClassNotFoundException attempting to get URL for visualizer: "
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
            } catch (SQLException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile(
                        "SQLException attempting to get URL for visualizer: " + visualizerIdentification.getSoftwareName()
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
            } catch (SQLException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile("SQLException attempting to update last service" + " call for run id " + runId + ": "
                        + ex.getMessage(), runId);
                return;
            } catch (ClassNotFoundException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile("ClassNotFoundException attempting to update last service" + " call for run id "
                        + runId + ": " + ex.getMessage(), runId);
                return;
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.writeErrorToErrorFile("ApolloDatabaseException attempting to update last service" + " call for run id "
                        + runId + ": " + ex.getMessage(), runId);
                return;
            }
        } catch (IOException e) {
            System.out.println("Error writing error file!: " + e.getMessage());
        }
    }
}
