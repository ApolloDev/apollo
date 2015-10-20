package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.services_common.v3_1_0.Authentication;
import edu.pitt.apollo.types.v3_1_0.SoftwareIdentification;
import edu.pitt.apollo.soapjobrunningserviceconnector.SoapJobRunningServiceConnector;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class JobRunningServiceAccessor extends ServiceAccessor implements JobRunningServiceInterface {

	private JobRunningServiceConnector connector;
	
	public JobRunningServiceAccessor(String url, SoftwareIdentification softwareIdentification) throws JobRunningServiceException {
		super(url);
        connector = new SoapJobRunningServiceConnector(url, softwareIdentification);
	}

	@Override
	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		connector.run(runId, authentication);
	}

	@Override
	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException {
		connector.terminate(runId, authentication);
	}

}
