package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 7, 2014
 * Time: 2:26:02 PM
 * Class: DatabaseAccessor
 * IDE: NetBeans 6.9.1
 */
public abstract class DatabaseAccessor {

	protected ApolloDbUtils dbUtils;
	protected final Authentication authentication;

	public DatabaseAccessor(Authentication authentication) {
		this.dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
		this.authentication = authentication;
	}

	public void removeAllDataAssociatedWithRunId(BigInteger runId) throws ApolloDatabaseException {
		dbUtils.removeRunData(runId);
	}

	public boolean authenticateUser(Authentication authentication) throws ApolloDatabaseException {
		return dbUtils.authenticateUser(authentication);
	}

	public boolean authorizeUserForSoftwareCacheData(Authentication authentication, SoftwareIdentification softwareIdentification) throws ApolloDatabaseException {
		boolean requestRunSimulator = false;
		return dbUtils.authorizeUser(authentication, softwareIdentification, requestRunSimulator);
	}

	public boolean authorizeUserForRunningSoftware(Authentication authentication, SoftwareIdentification softwareIdentification) throws ApolloDatabaseException {
		boolean requestRunSimulator = true;
		return dbUtils.authorizeUser(authentication, softwareIdentification, requestRunSimulator);
	}

	protected final boolean isRunIdAssociatedWithMatchingRunMessage(String targetRunMessageAsJson,
			BigInteger runIdAssociatedWithRunMessageHash) throws ApolloDatabaseException {

		String runSimulationMessageAssociatedWithRunIdAsJson
				= getRunMessageAssociatedWithRunIdAsJsonOrNull(runIdAssociatedWithRunMessageHash);

		if (runSimulationMessageAssociatedWithRunIdAsJson == null) {
			throw new ApolloDatabaseException(
					"There was no run_simulation_message.json content for run ID "
					+ runIdAssociatedWithRunMessageHash);
		}

		return targetRunMessageAsJson.equals(runSimulationMessageAssociatedWithRunIdAsJson);
	}

	public abstract BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException;

	protected abstract String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws ApolloDatabaseException;

	public abstract BigInteger insertRunIntoDatabase() throws ApolloDatabaseException;
}
