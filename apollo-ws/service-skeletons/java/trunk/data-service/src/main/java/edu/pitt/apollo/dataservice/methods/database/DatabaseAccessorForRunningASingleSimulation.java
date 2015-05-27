package edu.pitt.apollo.dataservice.methods.database;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import edu.pitt.apollo.Md5UtilsException;
//import edu.pitt.apollo.apolloservice.translatorservice.TranslatorServiceRecordContainer;
import edu.pitt.apollo.db.ApolloDbUtils;
import edu.pitt.apollo.db.RunIdAndCollisionId;
import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
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
			throws ApolloDatabaseException, Md5UtilsException {

		RunIdAndCollisionId runIdAndHighestMD5CollisionIdForRun = dbUtils.getRunIdAndHighestMD5CollisionIdForRun(runMessage);

		boolean needToAddRun = true;
		if (runIdAndHighestMD5CollisionIdForRun.getCollisionId() > 0) {
			//just assume they are the same for now...it's not like MD5 will collide, but we should verify
			needToAddRun = false;
		}
		BigInteger[] runIdSimulationGroupId;
		if (needToAddRun) {
			runIdSimulationGroupId =null;
//			runIdSimulationGroupId = dbUtils.addSimulationRun(
//					runMessage,
//					memberOfSimulationGroupIdOrNull, runIdAndHighestMD5CollisionIdForRun.getCollisionId() + 1,
//					((RunSimulationMessage) runMessage)
//							.getSimulatorIdentification(),
//					TranslatorServiceRecordContainer
//							.getTranslatorSoftwareIdentification(), authentication);
		} else {
			runIdSimulationGroupId = new BigInteger[2];
			runIdSimulationGroupId[0] = runIdAndHighestMD5CollisionIdForRun.getRunId();
			runIdSimulationGroupId[1] = dbUtils.getSimulationGroupIdForRun(runIdAndHighestMD5CollisionIdForRun.getRunId());
		}
		return runIdSimulationGroupId;
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
	public BigInteger insertRun(BigInteger runId, Authentication authentication) throws DataServiceException {
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
	public HashMap<BigInteger, String> getListOfFilesForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
		return null;
	}

	@Override
	public HashMap<BigInteger, String> getListOfURLsForRunId(BigInteger runId, Authentication authentication) throws DataServiceException {
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


		/* int md5CollisionId = dbUtils.getHighestMD5CollisionIdForRun(runMessage) + 1;

		BigInteger[] runIdsimulationGroupId = dbUtils.addSimulationRun(
				runMessage,
				memberOfSimulationGroupIdOrNull, md5CollisionId,
				((RunSimulationMessage) runMessage)
						.getSimulatorIdentification(),
				TranslatorServiceRecordContainer
						.getTranslatorSoftwareIdentification(), authentication);
		return runIdsimulationGroupId;
		*/

}
