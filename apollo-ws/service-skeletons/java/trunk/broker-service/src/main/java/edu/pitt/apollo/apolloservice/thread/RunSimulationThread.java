package edu.pitt.apollo.apolloservice.thread;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceException;

import edu.pitt.apollo.db.ApolloDbUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceAccessor;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceEI;
import edu.pitt.apollo.service.simulatorservice.v3_0_0.SimulatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time:
 * 11:56:31 AM Class: RunSimulationThread IDE: NetBeans 6.9.1
 */
public class RunSimulationThread extends RunApolloServiceThread {

    private final RunSimulationMessage message;

    public RunSimulationThread(RunSimulationMessage message, BigInteger runId) {
        super(runId);
        this.message = message;
    }

    @Override
    public void run() {
        // first call the translator and translate the runSimulationMessage
        try (ApolloDbUtils dbUtils = new ApolloDbUtils()) {
            boolean translatorRunSuccessful = TranslatorServiceAccessor.runTranslatorAndReturnIfRunWasSuccessful(runId, dbUtils);
            if (!translatorRunSuccessful) {
                return;
            }

            // once the translator has finished, call the simulator and start
            // the simulation
            SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
            String url = null;

            try {


                url = dbUtils.getUrlForSoftwareIdentification(simulatorIdentification);
                SimulatorServiceEI simulatorPort = null;
                try {
                    simulatorPort = new SimulatorServiceV300(new URL(url)).getSimulatorServiceEndpoint();
                } catch (Exception e) {
                    ApolloServiceErrorHandler.reportError(
                            "Unable to get simulator port for url: " + url + "\n\tError was: " + e.getMessage(),
                            runId);
                    return;
                }

                // disable chunking for ZSI
                Client simulatorClient = ClientProxy.getClient(simulatorPort);
                HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
                HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
                simulatorHttpClientPolicy.setConnectionTimeout(36000);
                simulatorHttpClientPolicy.setAllowChunking(false);
                simulatorHttp.setClient(simulatorHttpClientPolicy);
                try {
                    simulatorPort.runSimulation(runId /*, message*/);
                } catch (WebServiceException e) {
                    ApolloServiceErrorHandler.reportError("Error calling runSimulation(): " + "\n\tError was: " + e.getMessage(),
                            runId);
                    return;
                }
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ApolloServiceErrorHandler.reportError(
                        "Apollo database key not found attempting to get URL for simulator: "
                                + simulatorIdentification.getSoftwareName() + ", version: "
                                + simulatorIdentification.getSoftwareVersion() + ", developer: "
                                + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                                + ex.getMessage(), runId);
                return;
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.reportError(
                        "ApolloDatabaseException attempting to create port for simulator: "
                                + simulatorIdentification.getSoftwareName() + ", version: "
                                + simulatorIdentification.getSoftwareVersion() + ", developer: "
                                + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
                                + ". Error message was: " + ex.getMessage(), runId);
                return;
            }

            try {
                dbUtils.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ApolloServiceErrorHandler.reportError("Apollo database key not found attempting to update last service"
                        + " call for run id " + runId + ": " + ex.getMessage(), runId);
            } catch (ApolloDatabaseException ex) {
                ApolloServiceErrorHandler.reportError("ApolloDatabaseException attempting to update last service" + " call for run id "
                        + runId + ": " + ex.getMessage(), runId);
            }
        } catch (ApolloDatabaseException e) {
            ApolloServiceErrorHandler.reportError("Error getting ApolloDbUtils connection for RunSimulationThread.", runId);
        }
    }

    @Override
    public void setAuthenticationPasswordFieldToBlank() {
        message.getAuthentication().setRequesterPassword("");
    }

    public static void main(String[] args) {

        SimulatorServiceEI simulatorPort = null;
        try {
            simulatorPort = new SimulatorServiceV300(new URL("http://gaia.pha.psc.edu:13500/pscsimu?wsdl")).getSimulatorServiceEndpoint();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // disable chunking for ZSI
        Client simulatorClient = ClientProxy.getClient(simulatorPort);
        HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
        HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
        simulatorHttpClientPolicy.setConnectionTimeout(36000);
        simulatorHttpClientPolicy.setAllowChunking(false);
        simulatorHttp.setClient(simulatorHttpClientPolicy);
        try {
            simulatorPort.runSimulation(new BigInteger("196") /*, message*/);
        } catch (WebServiceException e) {
            e.printStackTrace();
        }

    }
}
