package edu.pitt.apollo.apolloservice.thread;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessorForRunningDataService;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import static edu.pitt.apollo.apolloservice.thread.RunApolloServiceThread.logger;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseKeyNotFoundException;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceEI;
import edu.pitt.apollo.service.dataservice.v3_0_0.DataServiceV300;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import javax.xml.ws.WebServiceException;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Jan 22, 2015
 * Time: 12:25:44 PM
 * Class: RunDataServiceThread
 */
public class RunDataServiceThread extends RunApolloServiceThread {

	private GetOutputFilesURLsMessage getOutputFilesURLsMessage;
	private GetOutputFilesURLAsZipMessage getOutputFilesURLAsZipMessage;
	
	public RunDataServiceThread(GetOutputFilesURLsMessage message) {
		this.getOutputFilesURLsMessage = message;
	}
	
	public RunDataServiceThread(GetOutputFilesURLAsZipMessage message) {
		this.getOutputFilesURLAsZipMessage = message;
	}

	@Override
	public void run() {
		try {
			SoftwareIdentification dataServiceSoftwareId = DatabaseAccessorForRunningDataService.getDataServiceSoftwareId();
			String url = null;
			try {

				url = dbUtils.getUrlForSoftwareIdentification(dataServiceSoftwareId);
				DataServiceEI dataServicePort = null;
				try {
					dataServicePort = new DataServiceV300(new URL(url)).getDataServiceEndpoint();
				} catch (Exception e) {
					ApolloServiceErrorHandler.writeErrorToErrorFile(
							"Unable to get data service port for url: " + url + "\n\tError was: " + e.getMessage(),
							runId);
					return;
				}

				// disable chunking for ZSI
				Client dataServiceClient = ClientProxy.getClient(dataServicePort);
				HTTPConduit dataServiceHttp = (HTTPConduit) dataServiceClient.getConduit();
				HTTPClientPolicy dataServiceHttpClientPolicy = new HTTPClientPolicy();
				dataServiceHttpClientPolicy.setConnectionTimeout(36000);
				dataServiceHttpClientPolicy.setAllowChunking(false);
				dataServiceHttp.setClient(dataServiceHttpClientPolicy);
				try {
					if (getOutputFilesURLsMessage != null) {
						dataServicePort.getOutputFilesURLs(runId);
					} else if (getOutputFilesURLAsZipMessage != null) {
						dataServicePort.getOutputFilesURLAsZip(runId);
					}
				} catch (WebServiceException e) {
					ApolloServiceErrorHandler.writeErrorToErrorFile("Error calling data service: " + "\n\tError was: " + e.getMessage(),
							runId);
					return;
				}
			} catch (ApolloDatabaseKeyNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"Apollo database key not found attempting to get URL for data service for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			} catch (ClassNotFoundException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"ClassNotFoundException attempting to get URL for data service for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			} catch (MalformedURLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"MalformedURLException attempting to create port for data service for run id " + runId + ". URL was: " + url
						+ ". Error message was: " + ex.getMessage(), runId);
				return;
			} catch (SQLException ex) {
				ApolloServiceErrorHandler.writeErrorToErrorFile(
						"SQLException attempting to get URL for data service for run id " + runId + ": "
						+ ex.getMessage(), runId);
				return;
			}
		} catch (IOException e) {
			logger.error("Error writing error file!: " + e.getMessage());
		}
	}

	@Override
	public void setAuthenticationPasswordFieldToBlank() {
		if (getOutputFilesURLsMessage != null) {
			getOutputFilesURLsMessage.getAuthentication().setRequesterPassword("");
		}

	}

}
