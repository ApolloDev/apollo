package edu.pitt.apollo.apolloservice.translatorservice;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.WebServiceException;


import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.methods.run.GetRunStatusMethod;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.service.translatorservice.v3_0_0.TranslatorServiceEI;
import edu.pitt.apollo.service.translatorservice.v3_0_0.TranslatorServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 9, 2014 Time: 2:49:06 PM Class: TranslatorServiceAccessor IDE: NetBeans 6.9.1
 */
public class TranslatorServiceAccessor {


    static TranslatorServiceEI translatorPortSingleton = null;
    private static  TranslatorServiceEI getTranslatorPort(BigInteger runId, ApolloDbUtils dbUtils) {
        if (translatorPortSingleton != null)
            return translatorPortSingleton;

        ServiceRegistrationRecord translatorServiceRecord = TranslatorServiceRecordContainer.getTranslatorServiceRegistrationRecord();

        try {
            translatorPortSingleton = new TranslatorServiceV300(new URL(translatorServiceRecord.getUrl())).getTranslatorServiceEndpoint();
            return translatorPortSingleton;
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.reportError("WebServiceException attempting to get the translator port for runId " + runId
                            + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + e.getMessage(),
                    runId);
            return null;
        } catch (MalformedURLException ex) {
            ApolloServiceErrorHandler.reportError("MalformedURLEXception attempting to get the translator port for runId " + runId
                            + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + ex.getMessage(),
                    runId);
            return null;
        }

    }

    public static boolean runTranslatorAndReturnIfRunWasSuccessful(BigInteger runId, ApolloDbUtils dbUtils) {
        ServiceRegistrationRecord translatorServiceRecord = TranslatorServiceRecordContainer.getTranslatorServiceRegistrationRecord();
        TranslatorServiceEI translatorPort;
        try {
            translatorPort = getTranslatorPort(runId, dbUtils);
            if (translatorPort == null)
                return false;
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.reportError("WebServiceException attempting to get the translator port for runId " + runId
                    + ". URL was " + translatorServiceRecord.getUrl() + ". Error message was: " + e.getMessage(),
                    runId);
            return false;
        }

        // disable chunking for ZSI
//        Client client = ClientProxy.getClient(translatorPort);
//        HTTPConduit http = (HTTPConduit) client.getConduit();
//        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
//        httpClientPolicy.setConnectionTimeout(36000);
//        httpClientPolicy.setAllowChunking(false);
//        http.setClient(httpClientPolicy);

        try {
            translatorPort.translateRun(runId);
        } catch (WebServiceException e) {
            ApolloServiceErrorHandler.reportError("WebServiceException attempting to call translateRunSimulationMessage() for runId:  " + runId
                    + ". Error was: " + e.getMessage(),
                    runId);
            return false;
        }

        try {
            dbUtils.updateLastServiceToBeCalledForRun(runId, translatorServiceRecord.getSoftwareIdentification());
        } catch (ApolloDatabaseException ex) {
            ApolloServiceErrorHandler.reportError("ApolloDatabaseException attempting to update last service to be called to translator for runId " + runId
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
                    ApolloServiceErrorHandler.reportError("Translator service returned status of FAILED for runId " + runId,
                            runId);
                    return false;
                }
            }
        } catch (InterruptedException ex) {
            ApolloServiceErrorHandler.reportError("InterruptedException while attempting to get status of translator for runId "
                    + runId + ": " + ex.getMessage(), runId);
            return false;
        }

        return true;
    }
}
