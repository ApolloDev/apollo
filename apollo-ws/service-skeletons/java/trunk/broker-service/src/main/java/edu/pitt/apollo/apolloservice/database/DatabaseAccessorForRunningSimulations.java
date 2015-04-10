package edu.pitt.apollo.apolloservice.database;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 7, 2014 Time:
 * 11:56:26 AM Class: DatabaseAccessorForRunningSimulations IDE: NetBeans 6.9.1
 */
public abstract class DatabaseAccessorForRunningSimulations extends
		DatabaseAccessor {

	protected Object runMessage;
	private SoftwareIdentification softwareIdentification;
	private Class runMessageClass;

	public DatabaseAccessorForRunningSimulations(Authentication authentication,
			SoftwareIdentification softwareIdentification,
			ApolloDbUtils dbUtils, Class clazz) throws ApolloDatabaseException {
		super(authentication, dbUtils);
		this.runMessageClass = clazz;
		this.softwareIdentification = softwareIdentification;
	}

	@Override
	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(
			BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> currentRunSimulationMessageAsJsonMap = dbUtils
				.getDataContentForSoftware(
						runId,
						ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
						dbUtils.getSoftwareIdentificationKey(TranslatorServiceRecordContainer
								.getTranslatorSoftwareIdentification()));
		for (String label : currentRunSimulationMessageAsJsonMap.keySet()) {
			if (label.equals("run_simulation_message.json")) {
				return currentRunSimulationMessageAsJsonMap.get(label)
						.toString();
			}
		}
		return null;
	}

	@Override
	public BigInteger getCachedRunIdFromDatabaseOrNull()
			throws ApolloDatabaseException {
		List<BigInteger> runIds = dbUtils
				.getSimulationRunIdsAssociatedWithRunSimulationMessageHash(
						softwareIdentification, runMessage);
		if (runIds.size() > 0) {
			// String targetRunSimulationMessageAsJson =
			// dbUtils.getJSONString(runSimulationMessage,
			// RunSimulationMessage.class);
			for (BigInteger runIdAssociatedWithRunSimulationMessageHash : runIds) {
				if (isRunIdAssociatedWithMatchingRunMessage(
						ApolloDbUtils.getJSONString(runMessage), 
						runIdAssociatedWithRunSimulationMessageHash)) {
					return runIdAssociatedWithRunSimulationMessageHash;
				}
			}
			return null;
		} else {
			return null;
		}
	}

}
