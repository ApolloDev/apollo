package edu.pitt.apollo.connector;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;


/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public abstract class DataServiceConnector {

	protected final String serviceUrl;
	
	public DataServiceConnector(String url) {
		this.serviceUrl = url;
	}
	
	public abstract boolean isRunBatch(BigInteger runId);

	public abstract BigInteger getSimulationGroupIdForRun(BigInteger runId);

	public abstract List<BigInteger> getRunIdsForBatch(BigInteger batchRunId);

	public abstract RunSimulationMessage getRunSimulationMessageForRun(BigInteger runId);

	public abstract GetOutputFilesURLsMessage getGetOutputFilesURLsMessageForRun(BigInteger runId);

	public abstract GetOutputFilesURLAsZipMessage getGetOutputFilesURLAsZipMessageForRun(BigInteger runId);

	public abstract GetAllOutputFilesURLAsZipMessage getGetAllOutputFilesURLAsZipMessageForRun(BigInteger runId);

	public abstract boolean authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware);

	public abstract int getRunKey(RunSimulationMessage runSimulationMessage);

	public abstract boolean authenticateUser(Authentication authentication);

	public abstract int addTextDataContent(String content);

	public abstract int associateContentWithRunId(BigInteger runKey, int dataContentKey, int runDataDescriptionId);

	public abstract SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId);

	public abstract int getSoftwareIdentificationKeyForRun(BigInteger runId);

	public abstract BigInteger[] addDataServiceRun(GetAllOutputFilesURLAsZipMessage message);

	public abstract BigInteger[] addDataServiceRun(GetOutputFilesURLsMessage message);

	public abstract BigInteger[] addDataServiceRun(GetOutputFilesURLAsZipMessage message);

	public abstract BigInteger[] addSimulationRun(RunSimulationMessage runMessage, BigInteger memberOfSimulationGroupIdOrNull);

	public abstract void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnum, String message);

	public abstract int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification);

	public abstract SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId);

	public abstract MethodCallStatus getStatusOfLastServiceToBeCalledForRun(BigInteger runId);

	public abstract BigInteger getNewSimulationGroupId();

	public abstract void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds);

	public abstract BigInteger[] addVisualizationRun(RunVisualizationMessage runVisualizationMessage);
	
	public abstract void removeRunData(BigInteger runId);
	
	public abstract MethodCallStatus getRunStatus(BigInteger runId);

    /*---DAN'S ADDITIONS FOR REST INTERFACE--*/
    public abstract HashMap<BigInteger,String> getListOfFilesForRunId(BigInteger runId);

    public abstract HashMap<BigInteger,String> getListOfURLsForRunId(BigInteger runId);

    public abstract String getFileContentForFileId(BigInteger fileId);

    public abstract String getURLForSoftwareId(SoftwareIdentification softwareId);

	public abstract RunResult getOutputFilesURLs(BigInteger runId);
	
	public abstract RunResult getOutputFilesURLAsZip(BigInteger runId);
	
	public abstract RunResult getAllOutputFilesURLAsZip(BigInteger runId);
}
