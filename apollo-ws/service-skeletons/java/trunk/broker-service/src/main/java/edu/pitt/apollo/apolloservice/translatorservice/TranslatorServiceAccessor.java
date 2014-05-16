package edu.pitt.apollo.apolloservice.translatorservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceException;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.service.translatorservice.v2_0_1.TranslatorServiceEI;
import edu.pitt.apollo.service.translatorservice.v2_0_1.TranslatorServiceV201;
import edu.pitt.apollo.types.v2_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_1.RunSimulationMessage;
import edu.pitt.apollo.types.v2_0_1.ServiceRegistrationRecord;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 9, 2014
 * Time: 2:49:06 PM
 * Class: TranslatorServiceAccessor
 * IDE: NetBeans 6.9.1
 */
public class TranslatorServiceAccessor {

    public static boolean runTranslatorAndReturnIfRunWasSuccessful(int runId, RunSimulationMessage message) throws IOException {
    	String runIdentification = Integer.toString(runId);
        ServiceRegistrationRecord translatorServiceRecord = TranslatorServiceRecordContainer.getTranslatorServiceRegistrationRecord();
        TranslatorServiceEI translatorPort;
        try {
            translatorPort = new TranslatorServiceV201(new URL(translatorServiceRecord.getUrl())).getTranslatorServiceEndpoint();
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
            translatorPort.translateRunSimulationMessage(runIdentification, message);
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.writeErrorToErrorFile("WebServiceException attempting to call translateRunSimulationMessage() for runId:  " + runId
                    + ". Error was: " + e.getMessage(),
                    runId);
            return false;
        }

        // while translator is running, query the status

        MethodCallStatusEnum status = GetRunStatusMethod.getRunStatus(runIdentification).getStatus();
        // MethodCallStatusEnum status = MethodCallStatusEnum.QUEUED; //
        // doesn't
        // really
        // matter
        try {
            while (!status.equals(MethodCallStatusEnum.TRANSLATION_COMPLETED)) {

                Thread.sleep(1000);
                status = GetRunStatusMethod.getRunStatus(runIdentification).getStatus();

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
