package edu.pitt.apollo.apolloservice.translatorservice;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.service.translatorservice.v2_1_0.TranslatorServiceEI;
import edu.pitt.apollo.service.translatorservice.v2_1_0.TranslatorServiceV202;
import edu.pitt.apollo.types.v2_1_0.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_1_0.RunSimulationMessage;
import edu.pitt.apollo.types.v2_1_0.ServiceRegistrationRecord;
import java.sql.SQLException;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 2:49:06 PM Class: TranslatorServiceAccessor IDE: NetBeans 6.9.1
 */
public class TranslatorServiceAccessor {

    public static boolean runTranslatorAndReturnIfRunWasSuccessful(BigInteger runId, RunSimulationMessage message, ApolloDbUtils dbUtils) throws IOException {
        ServiceRegistrationRecord translatorServiceRecord = TranslatorServiceRecordContainer.getTranslatorServiceRegistrationRecord();
        TranslatorServiceEI translatorPort;
        try {
            translatorPort = new TranslatorServiceV202(new URL(translatorServiceRecord.getUrl())).getTranslatorServiceEndpoint();
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("WebServiceException attempting to get the translator port for runId " + runId
                    + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + e.getMessage(),
                    runId);
            return false;
        } catch (MalformedURLException ex) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("MalformedURLEXception attempting to get the translator port for runId " + runId
                    + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + ex.getMessage(),
                    runId);
            return false;
        }

        // disable chunking for ZSI
        Client client = ClientProxy.getClient(translatorPort);
        HTTPConduit http = (HTTPConduit) client.getConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(36000);
        httpClientPolicy.setAllowChunking(false);
        http.setClient(httpClientPolicy);

        try {
            translatorPort.translateRunSimulationMessage(runId, message);
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("WebServiceException attempting to call translateRunSimulationMessage() for runId:  " + runId
                    + ". Error was: " + e.getMessage(),
                    runId);
            return false;
        }

        try {
            dbUtils.updateLastServiceToBeCalledForRun(runId, translatorServiceRecord.getSoftwareIdentification());
        } catch (ApolloDatabaseException ex) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("ApolloDatabaseException attempting to update last service to be called to translator for runId " + runId
                    + ". Error message was: " + ex.getMessage(),
                    runId);
            return false;
        } catch (ClassNotFoundException ex) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("ClassNotFoundException attempting to update last service to be called to translator for runId " + runId
                    + ". Error message was: " + ex.getMessage(),
                    runId);
            return false;
        } catch (SQLException ex) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("SQLException attempting to update last service to be called to translator for runId " + runId
                    + ". Error message was: " + ex.getMessage(),
                    runId);
            return false;
        }

        // while translator is running, query the status
        MethodCallStatusEnum status = GetRunStatusMethod.getRunStatus(runId).getStatus();
        // MethodCallStatusEnum status = MethodCallStatusEnum.QUEUED; //
        // doesn't
        // really
        // matter
        try {
            while (!status.equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {

                Thread.sleep(1000);
                status = GetRunStatusMethod.getRunStatus(runId).getStatus();

                if (status.equals(MethodCallStatusEnum.FAILED)) {
                    ApolloServiceErrorHandler.writeErrorToErrorFile("Translator service returned status of FAILED for runId " + runId,
                            runId);
                    return false;
                }
            }
        } catch (InterruptedException ex) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("InterruptedException while attempting to get status of translator for runId "
                    + runId + ": " + ex.getMessage(), runId);
            return false;
        }

        return true;
    }
}
