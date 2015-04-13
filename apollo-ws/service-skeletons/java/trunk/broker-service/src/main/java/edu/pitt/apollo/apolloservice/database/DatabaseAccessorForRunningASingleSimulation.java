package edu.pitt.apollo.apolloservice.database;

import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;

public class DatabaseAccessorForRunningASingleSimulation extends
		DatabaseAccessorForRunningSimulations {

	public DatabaseAccessorForRunningASingleSimulation(
			RunSimulationMessage runSimulationMessage,
			Authentication authentication, ApolloDbUtils dbUtils) throws ApolloDatabaseException {
		super(authentication,
				runSimulationMessage.getSimulatorIdentification(),
				dbUtils,
				RunSimulationMessage.class);
		this.runMessage = runSimulationMessage;
	}

	@Override
	public BigInteger[] insertRunIntoDatabase(
			BigInteger memberOfSimulationGroupIdOrNull)
			throws ApolloDatabaseException {
		int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runMessage) + 1;

		BigInteger[] runIdsimulationGroupId = dbUtils.addSimulationRun(
				runMessage,
				memberOfSimulationGroupIdOrNull, md5CollisionId,
				((RunSimulationMessage) runMessage)
						.getSimulatorIdentification(),
				TranslatorServiceRecordContainer
						.getTranslatorSoftwareIdentification(), authentication);
		return runIdsimulationGroupId;
	}
}
