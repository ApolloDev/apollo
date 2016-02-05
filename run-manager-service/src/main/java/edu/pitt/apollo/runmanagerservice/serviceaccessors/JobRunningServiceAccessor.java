package edu.pitt.apollo.runmanagerservice.serviceaccessors;

import edu.pitt.apollo.connector.JobRunningServiceConnector;
import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.interfaces.JobRunningServiceInterface;
import edu.pitt.apollo.soapjobrunningserviceconnector.RestJobRunningServiceConnector;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.soapjobrunningserviceconnector.RestJobRunningServiceConnector;
import edu.pitt.apollo.types.v4_0.SoftwareIdentification;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public class JobRunningServiceAccessor extends ServiceAccessor implements JobRunningServiceInterface {

	private final JobRunningServiceConnector connector;
	
	public JobRunningServiceAccessor(String url, SoftwareIdentification softwareIdentification) throws JobRunningServiceException {
		super(url);
        connector = new RestJobRunningServiceConnector(url);
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
