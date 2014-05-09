package edu.pitt.apollo.apolloservice.database;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.types.v2_0_1.RunVisualizationMessage;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: May 7, 2014
 * Time: 2:06:14 PM
 * Class: DatabaseAccessorForRunningVisualizations
 * IDE: NetBeans 6.9.1
 */
public class DatabaseAccessorForRunningVisualizations extends DatabaseAccessor {

    private RunVisualizationMessage runVisualizationMessage;

    public DatabaseAccessorForRunningVisualizations(RunVisualizationMessage runVisualizationMessage) {
        super();
        this.runVisualizationMessage = runVisualizationMessage;
    }

    private String getRunVisualizationMessageAsJsonForRunIdOrNull(int runId) throws ApolloDatabaseException {
        Map<String, ByteArrayOutputStream> currentRunVisualizationMessageAsJsonMap =
                dbUtils.getDataContentForSoftware(
                runId,
                ApolloServiceConstants.END_USER_APPLICATION_SOURCE_ID,
                dbUtils.getSoftwareIdentificationKey(runVisualizationMessage.getVisualizerIdentification()));
        for (String label : currentRunVisualizationMessageAsJsonMap.keySet()) {
            if (label.equals("run_visualization_message.json")) {
                return currentRunVisualizationMessageAsJsonMap.get(label).toString();
            }
        }
        return null;
    }

    private boolean runIdIsAssociatedWithMatchingRunVisualizationMessage(String targetRunVisualizationMessageAsJson,
            int runIdAssociatedWithRunVisualizationMessageHash) throws ApolloDatabaseException {

        String runVisualizationMessageAssociatedWithRunIdAsJson =
                getRunVisualizationMessageAsJsonForRunIdOrNull(runIdAssociatedWithRunVisualizationMessageHash);

        if (runVisualizationMessageAssociatedWithRunIdAsJson == null) {
            throw new ApolloDatabaseException(
                    "There was no run_visualization_message.json content for run ID "
                    + runIdAssociatedWithRunVisualizationMessageHash);
        }

        if (targetRunVisualizationMessageAsJson.equals(runVisualizationMessageAssociatedWithRunIdAsJson)) {
            return true;
        } else {
            return false;
        }

    }

    public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException {

        List<Integer> runIds = dbUtils.getVisualizationRunIdsAssociatedWithRunVisualizationMessageHash(runVisualizationMessage);

        if (runIds.size() > 0) {
            String targetRunVisualizationMessageAsJson = dbUtils.getJSONString(runVisualizationMessage);
            for (int runIdAssociatedWithRunVisualizationMessageHash : runIds) {
                if (runIdIsAssociatedWithMatchingRunVisualizationMessage(targetRunVisualizationMessageAsJson,
                        runIdAssociatedWithRunVisualizationMessageHash)) {
                    return new BigInteger(Integer.toString(runIdAssociatedWithRunVisualizationMessageHash));
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public BigInteger insertRunIntoDatabase(
            RunVisualizationMessage runVisualizationMessage)
            throws ApolloDatabaseException {
        int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runVisualizationMessage) + 1;
        BigInteger runId = new BigInteger(Integer.toString(dbUtils.addVisualizationRun(runVisualizationMessage, md5CollisionId)));
        return runId;
    }
}
