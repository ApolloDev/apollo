package edu.pitt.apollo.dataservice.methods.database;

import edu.pitt.apollo.ApolloServiceConstants;
import edu.pitt.apollo.Md5UtilsException;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.*;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
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

	private final RunVisualizationMessage runVisualizationMessage;

	public DatabaseAccessorForRunningVisualizations(RunVisualizationMessage runVisualizationMessage, Authentication authentication, ApolloDbUtils dbUitls) throws ApolloDatabaseException {
		super(authentication, dbUitls);
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
	public BigInteger getCachedRunIdFromDatabaseOrNull() throws ApolloDatabaseException, Md5UtilsException {

		List<BigInteger> runIds = dbUtils.getVisualizationRunIdsAssociatedWithRunVisualizationMessageHash(runVisualizationMessage);

		if (runIds.size() > 0) {
			String targetRunVisualizationMessageAsJson = jsonUtils.getJSONString(runVisualizationMessage);
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
	public BigInteger[] insertRunIntoDatabase(BigInteger memberOfSimulationGroupIdOrNull) throws ApolloDatabaseException, Md5UtilsException {
		int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runVisualizationMessage) + 1;
		BigInteger[] runIdsimulationGroupId = dbUtils.addVisualizationRun(runVisualizationMessage, md5CollisionId, authentication);
		return runIdsimulationGroupId;
	}

	@Override
	public List<BigInteger> getRunIdsAssociatedWithSimulationGroupForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}


	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnumToSet, String messageToSet, Authentication authentication) throws DataServiceException {

	}

	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification, Authentication authentication) throws DataServiceException {
		return 0;
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public void addRunIdsToSimulationGroupForRun(BigInteger simulationGroupId, List<BigInteger> runIds, Authentication authentication) throws DataServiceException {

	}

	@Override
	public void removeRunData(BigInteger runId, Authentication authentication) throws DataServiceException {

	}

	@Override
	public MethodCallStatus getRunStatus(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public HashMap<BigInteger, FileAndURLDescription> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public String getFileContentForFileId(BigInteger fileId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public String getURLForURLId(BigInteger urlId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public String getURLForSoftwareIdentification(SoftwareIdentification softwareId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public void runDataServiceToGetOutputFilesURLs(BigInteger runId, Authentication authentication) throws DataServiceException {

	}

	@Override
	public void runDataServiceToGetOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {

	}

	@Override
	public void runDataServiceToGetAllOutputFilesURLAsZip(BigInteger runId, Authentication authentication) throws DataServiceException {

	}
}
