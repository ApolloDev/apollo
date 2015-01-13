package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 7, 2014
 * Time: 11:56:26 AM
 * Class: DatabaseAccessorForRunningSimulations
 * IDE: NetBeans 6.9.1
 */
public class DatabaseAccessorForRunningSimulations extends DatabaseAccessor {

    private final RunSimulationMessage runSimulationMessage;

    public DatabaseAccessorForRunningSimulations(RunSimulationMessage runSimulationMessage, Authentication authentication) {
        super(authentication);
        this.runSimulationMessage = runSimulationMessage;
    }

	@Override
    protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws ApolloDatabaseException {
        Map<String, ByteArrayOutputStream> currentRunSimulationMessageAsJsonMap =
                dbUtils.getDataContentForSoftware(
                runId,
                ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
                dbUtils.getSoftwareIdentificationKey(TranslatorServiceRecordContainer.getTranslatorSoftwareIdentification()));
        for (String label : currentRunSimulationMessageAsJsonMap.keySet()) {
            if (label.equals("run_simulation_message.json")) {
                return currentRunSimulationMessageAsJsonMap.get(label).toString();
            }
        }
        return null;
    }

	@Override
    public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException {
        List<BigInteger> runIds = dbUtils.getSimulationRunIdsAssociatedWithRunSimulationMessageHash(runSimulationMessage);

        if (runIds.size() > 0) {
            String targetRunSimulationMessageAsJson = dbUtils.getJSONString(runSimulationMessage, RunSimulationMessage.class);
            for (BigInteger runIdAssociatedWithRunSimulationMessageHash : runIds) {
                if (isRunIdAssociatedWithMatchingRunMessage(targetRunSimulationMessageAsJson, 
                        runIdAssociatedWithRunSimulationMessageHash)) {
                    return runIdAssociatedWithRunSimulationMessageHash;
                }
            }
            return null;
        } else {
            return null;
        }
    }

	@Override
    public BigInteger insertRunIntoDatabase() throws ApolloDatabaseException {
        int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runSimulationMessage) + 1;
        BigInteger runId = new BigInteger(Integer.toString(dbUtils.addSimulationRun(runSimulationMessage, md5CollisionId,
                TranslatorServiceRecordContainer.getTranslatorSoftwareIdentification(), authentication)));
        return runId;
    }
}
