package edu.pitt.apollo.interfaces;

import edu.pitt.apollo.exception.JobRunningServiceException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/27/15.
 */
public interface JobRunningServiceInterface {

	public void run(BigInteger runId, Authentication authentication) throws JobRunningServiceException;

	public void terminate(BigInteger runId, Authentication authentication) throws JobRunningServiceException;
}
