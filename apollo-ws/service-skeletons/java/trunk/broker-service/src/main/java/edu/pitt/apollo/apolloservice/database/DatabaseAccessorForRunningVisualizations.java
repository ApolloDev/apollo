package edu.pitt.apollo.apolloservice.database;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.db.ApolloDatabaseException;
import edu.pitt.apollo.types.v2_0_2.RunVisualizationMessage;

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

	private final RunVisualizationMessage runVisualizationMessage;

	public DatabaseAccessorForRunningVisualizations(RunVisualizationMessage runVisualizationMessage) {
		super();
		this.runVisualizationMessage = runVisualizationMessage;
	}

	@Override
	protected String getRunMessageAssociatedWithRunIdAsJsonOrNull(BigInteger runId) throws ApolloDatabaseException {
		Map<String, ByteArrayOutputStream> currentRunVisualizationMessageAsJsonMap
			= dbUtils.getDataContentForSoftware(
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

	@Override
	public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException {

		List<BigInteger> runIds = dbUtils.getVisualizationRunIdsAssociatedWithRunVisualizationMessageHash(runVisualizationMessage);

		if (runIds.size() > 0) {
			String targetRunVisualizationMessageAsJson = dbUtils.getJSONString(runVisualizationMessage, RunVisualizationMessage.class);
			for (BigInteger runIdAssociatedWithRunVisualizationMessageHash : runIds) {
				if (isRunIdAssociatedWithMatchingRunMessage(targetRunVisualizationMessageAsJson,
					runIdAssociatedWithRunVisualizationMessageHash)) {
					return runIdAssociatedWithRunVisualizationMessageHash;
				}
			}

			return null;
		} else {
			return null;
		}
	}

	@Override
	public BigInteger insertRunIntoDatabase() throws ApolloDatabaseException {
		int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runVisualizationMessage) + 1;
		BigInteger runId = new BigInteger(Integer.toString(dbUtils.addVisualizationRun(runVisualizationMessage, md5CollisionId)));
		return runId;
	}
}
