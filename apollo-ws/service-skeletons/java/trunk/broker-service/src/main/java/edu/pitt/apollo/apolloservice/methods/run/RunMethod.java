package edu.pitt.apollo.apolloservice.methods.run;

import edu.pitt.apollo.apolloservice.database.DatabaseAccessor;
import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import java.math.BigInteger;

import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_2.Authentication;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatus;
import edu.pitt.apollo.types.v2_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.types.v2_0_2.SoftwareIdentification;
import java.io.IOException;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 8, 2014
 * Time: 11:52:26 AM
 * Class: RunMethod
 * IDE: NetBeans 6.9.1
 */
public abstract class RunMethod {

	private final Authentication authentication;
	private final SoftwareIdentification softwareIdentification;
	protected DatabaseAccessor databaseAccessor;
	
	public RunMethod(Authentication authentication, SoftwareIdentification softwareIdentification) {
		this.authentication = authentication;
		this.softwareIdentification = softwareIdentification;
	}

	protected static final boolean isRunFailed(BigInteger runId) throws ApolloDatabaseException {
		MethodCallStatus status = GetRunStatusMethod.getRunStatus(runId);
		MethodCallStatusEnum statusEnum = status.getStatus();

		return statusEnum.equals(MethodCallStatusEnum.FAILED);
	}

	protected final BigInteger userAuthenticated(DatabaseAccessor dbAccessor) throws ApolloDatabaseException {
		boolean userAuthenticated = dbAccessor.authenticateUser(authentication);
		if (!userAuthenticated) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId("You are not registered to use Apollo Web Services.");
				return new BigInteger(Long.toString(runId));
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				return ApolloServiceErrorHandler.FATAL_ERROR_CODE;
			}
		}

		return BigInteger.ZERO;
	}

	protected final BigInteger userAuthorizedForCachedResults(DatabaseAccessor dbAccessor) throws ApolloDatabaseException {
		boolean userAuthorized = dbAccessor.authorizeUserForSoftwareCacheData(authentication, softwareIdentification);
		if (!userAuthorized) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId("You are not authorized to use the specified software.");
				return new BigInteger(Long.toString(runId));
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				return ApolloServiceErrorHandler.FATAL_ERROR_CODE;
			}
		}

		return BigInteger.ZERO;
	}

	protected final BigInteger userAuthorizedToRunSoftware(DatabaseAccessor dbAccessor) throws ApolloDatabaseException {
		boolean userAuthorized = dbAccessor.authorizeUserForRunningSoftware(authentication, softwareIdentification);
		if (!userAuthorized) {
			try {
				long runId = ApolloServiceErrorHandler.writeErrorWithErrorId("You are not authorized to run the specified software, only view cached results.");
				return new BigInteger(Long.toString(runId));
			} catch (IOException e) {
				System.err.println("IOException writing error file: "
					+ e.getMessage());
				return ApolloServiceErrorHandler.FATAL_ERROR_CODE;
			}
		}

		return BigInteger.ZERO;
	}
}
