package edu.pitt.apollo;

import edu.pitt.apollo.db.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.service.translatorservice.v2_0_1.TranslatorServiceEI;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunAndSoftwareIdentification;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;
import edu.pitt.apollo.types.v2_0_1.SoftwareIdentification;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import edu.pitt.apollo.service.simulatorservice.v2_0_1.SimulatorServiceEI;
import java.math.BigInteger;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Apr 3, 2014 Time:
 * 11:56:31 AM Class: ApolloRunSimulationThread IDE: NetBeans 6.9.1
 */
public class ApolloRunSimulationThread extends Thread {

    private ApolloDbUtils dbUtils;
    private RunSimulationMessage message;
    private int runId;
    private static ServiceRegistrationRecord translatorServiceRecord;
    private ApolloServiceImpl apolloServiceImpl;

    public ApolloRunSimulationThread(int runId, RunSimulationMessage message, ApolloDbUtils dbUtils,
            ApolloServiceImpl apolloServiceImpl) {
        this.message = message;
        this.runId = runId;
        this.dbUtils = dbUtils;
        this.apolloServiceImpl = apolloServiceImpl;
    }

    @Override
    public void run() {
        try {
            // first call the translator and translate the runSimulationMessage
            TranslatorServiceEI translatorPort;
            try {
                translatorPort = apolloServiceImpl.getTranslatorServicePort(new URL(translatorServiceRecord.getUrl()));
            } catch (MalformedURLException ex) {
                ErrorUtils.writeErrorToFile("MalformedURLEXception attempting to get the translator port for runId " + runId
                        + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + ex.getMessage(),
                        apolloServiceImpl.getErrorFile(runId));
                return;
            }

            // disable chunking for ZSI
            Client client = ClientProxy.getClient(translatorPort);
            HTTPConduit http = (HTTPConduit) client.getConduit();
            HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
            httpClientPolicy.setConnectionTimeout(36000);
            httpClientPolicy.setAllowChunking(false);
            http.setClient(httpClientPolicy);

            translatorPort.translateRunSimulationMessage(Integer.toString(runId), message);

            // while translator is running, query the status
            RunAndSoftwareIdentification translatorRasid = new RunAndSoftwareIdentification();
            translatorRasid.setRunId(Integer.toString(runId));
            translatorRasid.setSoftwareId(translatorServiceRecord.getSoftwareIdentification());
            MethodCallStatusEnum status = MethodCallStatusEnum.QUEUED; // doesn't
            // really
            // matter
            try {
                while (!status.equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {

                    Thread.sleep(5000);
                    status = apolloServiceImpl.getRunStatus(translatorRasid).getStatus();

                    if (status.equals(MethodCallStatusEnum.FAILED)) {
                        ErrorUtils.writeErrorToFile("Translator service returned status of FAILED for runId " + runId,
                                apolloServiceImpl.getErrorFile(runId));
                        return;
                    }
                }
            } catch (InterruptedException ex) {
                ErrorUtils.writeErrorToFile("InterruptedException while attempting to get status of translator for runId "
                        + runId + ": " + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
            }

            // once the translator has finished, call the simulator and start
            // the simulation
            SoftwareIdentification simulatorIdentification = message.getSimulatorIdentification();
            String url = null;
            try {

                url = dbUtils.getUrlForSoftwareIdentification(simulatorIdentification);
                SimulatorServiceEI simulatorPort = apolloServiceImpl.getSimulatorServicePort(new URL(url));

                // disable chunking for ZSI
                Client simulatorClient = ClientProxy.getClient(simulatorPort);
                HTTPConduit simulatorHttp = (HTTPConduit) simulatorClient.getConduit();
                HTTPClientPolicy simulatorHttpClientPolicy = new HTTPClientPolicy();
                simulatorHttpClientPolicy.setConnectionTimeout(36000);
                simulatorHttpClientPolicy.setAllowChunking(false);
                simulatorHttp.setClient(simulatorHttpClientPolicy);

                simulatorPort.runSimulation(new BigInteger(Integer.toString(runId)), message);
            } catch (ApolloDatabaseKeyNotFoundException ex) {
                ErrorUtils.writeErrorToFile(
                        "Apollo database key not found attempting to get URL for simulator: "
                        + simulatorIdentification.getSoftwareName() + ", version: "
                        + simulatorIdentification.getSoftwareVersion() + ", developer: "
                        + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (ClassNotFoundException ex) {
                ErrorUtils.writeErrorToFile(
                        "ClassNotFoundException attempting to get URL for simulator: "
                        + simulatorIdentification.getSoftwareName() + ", version: "
                        + simulatorIdentification.getSoftwareVersion() + ", developer: "
                        + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (MalformedURLException ex) {
                ErrorUtils.writeErrorToFile(
                        "MalformedURLException attempting to create port for simulator: "
                        + simulatorIdentification.getSoftwareName() + ", version: "
                        + simulatorIdentification.getSoftwareVersion() + ", developer: "
                        + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ". URL was: " + url
                        + ". Error message was: " + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            } catch (SQLException ex) {
                ErrorUtils.writeErrorToFile(
                        "SQLException attempting to get URL for simulator: " + simulatorIdentification.getSoftwareName()
                        + ", version: " + simulatorIdentification.getSoftwareVersion() + ", developer: "
                        + simulatorIdentification.getSoftwareDeveloper() + " for run id " + runId + ": "
                        + ex.getMessage(), apolloServiceImpl.getErrorFile(runId));
                return;
            }
            try {
                dbUtils.updateLastServiceToBeCalledForRun(runId, simulatorIdentification);
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

    public static void loadTranslatorSoftwareIdentification() {

        System.out.println("Loading translator software identification");
        try {
            ApolloDbUtils dbUtils = new ApolloDbUtils(new File(ApolloServiceImpl.getDatabasePropertiesFilename()));

            Map<Integer, ServiceRegistrationRecord> softwareIdMap = dbUtils.getRegisteredSoftware();
            for (Integer id : softwareIdMap.keySet()) {
                SoftwareIdentification softwareId = softwareIdMap.get(id).getSoftwareIdentification();
                if (softwareId.getSoftwareName().toLowerCase().equals("translator")) {
                    translatorServiceRecord = softwareIdMap.get(id);
                    break;
                }

            }

        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("ClassNotFoundException attempting to load the translator service record: "
                    + ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException("IOException attempting to load the translator service record: " + ex.getMessage());
        } catch (SQLException ex) {
            throw new RuntimeException("SQLException attempting to load the translator service record: " + ex.getMessage());
        }

        if (translatorServiceRecord == null) {
            throw new RuntimeException("Could not find translator in the list of registered services");
        }
    }
    // static {
    // try {
    // loadTranslatorSoftwareIdentification();
    // } catch (ApolloRunSimulationException ex) {
    // throw new
    // RuntimeException("ApolloRunSimulationException attempting to load the translator service record: "
    // + ex.getMessage());
    // } catch (ClassNotFoundException ex) {
    // throw new
    // RuntimeException("ClassNotFoundException attempting to load the translator service record: "
    // + ex.getMessage());
    // } catch (IOException ex) {
    // throw new
    // RuntimeException("IOException attempting to load the translator service record: "
    // + ex.getMessage());
    // } catch (SQLException ex) {
    // throw new
    // RuntimeException("SQLException attempting to load the translator service record: "
    // + ex.getMessage());
    // }
    // }
}
