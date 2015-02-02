package edu.pitt.apollo.apolloservice.database;

//delete one of these!!!!
import java.math.BigInteger;

import edu.pitt.apollo.apollo_service_types.v3_0_0.RunSimulationsMessage;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;

public class DatabaseAccessorForRunningMultipleSimulations extends
		DatabaseAccessorForRunningSimulations {

	public DatabaseAccessorForRunningMultipleSimulations(
			RunSimulationsMessage runSimulationsMessage,
			Authentication authentication) {
		super(authentication, runSimulationsMessage
				.getSimulatorIdentification(), RunSimulationsMessage.class);
		this.runMessage = runSimulationsMessage;
	}

	@Override
	public BigInteger[] insertRunIntoDatabase(
			BigInteger memberOfSimulationGroupIdOrNull)
			throws ApolloDatabaseException {
		int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runMessage) + 1;

		BigInteger[] runIdSimulationGroupId = dbUtils.addSimulationRun(
				runMessage,
				memberOfSimulationGroupIdOrNull, md5CollisionId,
				((RunSimulationsMessage) runMessage)
						.getSimulatorIdentification(),
				TranslatorServiceRecordContainer
						.getTranslatorSoftwareIdentification(), authentication);
		return runIdSimulationGroupId;
	}
}
