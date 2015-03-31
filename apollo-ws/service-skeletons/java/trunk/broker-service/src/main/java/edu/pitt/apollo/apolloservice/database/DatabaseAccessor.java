package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.RunIdentificationAndLabel;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

import java.math.BigInteger;
import java.util.List;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time:
 * 2:26:02 PM Class: DatabaseAccessor IDE: NetBeans 6.9.1
 */
public abstract class DatabaseAccessor {

	protected static final ApolloDbUtils dbUtils;
	protected final Authentication authentication;

	static {
		dbUtils = ApolloDbUtilsContainer.getApolloDbUtils();
	}

	public DatabaseAccessor(Authentication authentication) {

		this.authentication = authentication;
	}

	public void removeAllDataAssociatedWithRunId(BigInteger runId)
			throws ApolloDatabaseException {
		dbUtils.removeRunData(runId);
	}

	public boolean authenticateUser(Authentication authentication)
			throws ApolloDatabaseException {
		return dbUtils.authenticateUser(authentication);
	}

	public boolean authorizeUserForSoftwareCacheData(
			Authentication authentication,
			SoftwareIdentification softwareIdentification)
			throws ApolloDatabaseException {
		boolean requestRunSimulator = false;
		return dbUtils.authorizeUser(authentication, softwareIdentification,
				requestRunSimulator);
	}

	public boolean authorizeUserForRunningSoftware(
			Authentication authentication,
			SoftwareIdentification softwareIdentification)
			throws ApolloDatabaseException {
		boolean requestRunSimulator = true;
		return dbUtils.authorizeUser(authentication, softwareIdentification,
				requestRunSimulator);
	}

	protected final boolean isRunIdAssociatedWithMatchingRunMessage(
			String targetRunMessageAsJson,
			BigInteger runIdAssociatedWithRunMessageHash)
			throws ApolloDatabaseException {

		String runSimulationMessageAssociatedWithRunIdAsJson = getRunMessageAssociatedWithRunIdAsJsonOrNull(runIdAssociatedWithRunMessageHash);

		if (runSimulationMessageAssociatedWithRunIdAsJson == null) {
			throw new ApolloDatabaseException(
					"There was no run_simulation_message.json content for run ID "
							+ runIdAssociatedWithRunMessageHash);
		}

		return targetRunMessageAsJson
				.equals(runSimulationMessageAssociatedWithRunIdAsJson);
	}

	public List<BigInteger> getRunIdsAssociatedWithRun(BigInteger runId)
			throws ApolloDatabaseException {
		return dbUtils.getRunIdsForBatch(runId);
	}

	public void addRunIdsToSimulationGroup(
			BigInteger simulationGroupId,
			List<RunIdentificationAndLabel> simulationRunIdentificationsAndLabels) throws ApolloDatabaseException {
		dbUtils.addRunIdsToSimulationGroup(simulationGroupId,
				simulationRunIdentificationsAndLabels);
	}

	public abstract BigInteger getCachedRunIdFromDatabaseOrNull()
			throws ApolloDatabaseException;

	protected abstract String getRunMessageAssociatedWithRunIdAsJsonOrNull(
			BigInteger runId) throws ApolloDatabaseException;

	public abstract BigInteger[] insertRunIntoDatabase(
			BigInteger memberOfSimulationGroupIdOrNull)
			throws ApolloDatabaseException;

	public BigInteger getSimulationGroupIdForRun(BigInteger runId)
			throws ApolloDatabaseException {
		return dbUtils.getSimulationGroupIdForRun(runId);
	}
}
