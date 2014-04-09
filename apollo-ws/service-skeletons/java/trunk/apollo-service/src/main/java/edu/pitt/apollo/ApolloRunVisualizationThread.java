package edu.pitt.apollo;

import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.service.visualizerservice.v2_0_1.VisualizerServiceEI;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 4, 2014
 * Time: 10:23:41 AM
 * Class: ApolloRunVisualizationThread
 * IDE: NetBeans 6.9.1
 */
public class ApolloRunVisualizationThread extends Thread {

    private ApolloDbUtils dbUtils;
    private RunVisualizationMessage message;
    private int runId;
    private ApolloServiceImpl apolloServiceImpl;

    public ApolloRunVisualizationThread(int runId, RunVisualizationMessage message, ApolloDbUtils dbUtils,
            ApolloServiceImpl apolloServiceImpl) {
        this.message = message;
        this.runId = runId;
        this.dbUtils = dbUtils;
        this.apolloServiceImpl = apolloServiceImpl;
    }

    @Override
    public void run() {
        SoftwareIdentification visualizerIdentification = message.getVisualizerIdentification();
        String url = null;
        try {
            try {

                url = dbUtils.getUrlForSoftwareIdentification(visualizerIdentification);
                VisualizerServiceEI visualizerPort = apolloServiceImpl.getVisualizerServicePort(new URL(url));

                // disable chunking for ZSI
                Client visualizerClient = ClientProxy.getClient(visualizerPort);
                HTTPConduit visualizerHttp = (HTTPConduit) visualizerClient.getConduit();
                HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
                httpClientPolicy.setConnectionTimeout(36000);
                httpClientPolicy.setAllowChunking(false);
                visualizerHttp.setClient(httpClientPolicy);

                visualizerPort.runVisualization(Integer.toString(runId), message);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ErrorUtils.writeErrorToFile(
                        "Apollo database key not found attempting to get URL for visualizer: "
                        + visualizerIdentification.getSoftwareName() + ", version: "
                        + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (ClassNotFoundException ex) {
                ErrorUtils.writeErrorToFile(
                        "ClassNotFoundException attempting to get URL for visualizer: "
                        + visualizerIdentification.getSoftwareName() + ", version: "
                        + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (MalformedURLException ex) {
                ErrorUtils.writeErrorToFile(
                        "MalformedURLException attempting to create port for visualizer: "
                        + visualizerIdentification.getSoftwareName() + ", version: "
                        + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
                        + ". Error message was: " + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (SQLException ex) {
                ErrorUtils.writeErrorToFile(
                        "SQLException attempting to get URL for visualizer: " + visualizerIdentification.getSoftwareName()
                        + ", version: " + visualizerIdentification.getSoftwareVersion() + ", developer: "
                        + visualizerIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            }
            try {
                dbUtils.updateLastServiceToBeCalledForRun(runId, visualizerIdentification);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ErrorUtils.writeErrorToFile("Apollo database key not found attempting to update last service"
                        + " call for run id " + runId + ": " + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (SQLException ex) {
                ErrorUtils.writeErrorToFile("SQLException attempting to update last service" + " call for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (ClassNotFoundException ex) {
                ErrorUtils.writeErrorToFile("ClassNotFoundException attempting to update last service" + " call for run id "
                        + runId + ": " + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            }
        } catch (IOException e) {
            System.out.println("Error writing error file!: " + e.getMessage());
        }
    }
}
