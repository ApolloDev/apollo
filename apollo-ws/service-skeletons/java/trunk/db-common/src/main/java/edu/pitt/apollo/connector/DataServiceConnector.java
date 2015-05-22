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
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;


/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 17, 2013 Time: 4:35:10 PM Class: DbUtils IDE: NetBeans 6.9.1
 */
public interface DataServiceConnector {

	public boolean isRunBatch(BigInteger runId);

	public BigInteger getSimulationGroupIdForRun(BigInteger runId);
	

	public List<BigInteger> getRunIdsForBatch(BigInteger batchRunId);

	public RunSimulationMessage getRunSimulationMessageForRun(BigInteger runId);

	public GetOutputFilesURLsMessage getGetOutputFilesURLsMessageForRun(BigInteger runId);

	public GetOutputFilesURLAsZipMessage getGetOutputFilesURLAsZipMessageForRun(BigInteger runId);

	public GetAllOutputFilesURLAsZipMessage getGetAllOutputFilesURLAsZipMessageForRun(BigInteger runId);

	public boolean authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware);

	public int getRunKey(RunSimulationMessage runSimulationMessage);

	public boolean authenticateUser(Authentication authentication);

	public int addTextDataContent(String content);

	public int associateContentWithRunId(BigInteger runKey, int dataContentKey, int runDataDescriptionId);

	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId);

	public int getSoftwareIdentificationKeyForRun(BigInteger runId);

	public BigInteger[] addDataServiceRun(GetAllOutputFilesURLAsZipMessage message);

	public BigInteger[] addDataServiceRun(GetOutputFilesURLsMessage message);

	public BigInteger[] addDataServiceRun(GetOutputFilesURLAsZipMessage message);

	public BigInteger[] addSimulationRun(RunSimulationMessage runMessage, BigInteger memberOfSimulationGroupIdOrNull);

	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnum, String message);

	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification);

	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId);

	public MethodCallStatus getStatusOfLastServiceToBeCalledForRun(BigInteger runId);

	public BigInteger getNewSimulationGroupId();

	public void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds);

	public BigInteger[] addVisualizationRun(RunVisualizationMessage runVisualizationMessage);
	
	public void removeRunData(BigInteger runId);
	
	public MethodCallStatus getRunStatus(BigInteger runId);

    /*---DAN'S ADDITIONS FOR REST INTERFACE--*/
    public HashMap<BigInteger,String> getListOfFilesForRunId(BigInteger runId);

    public HashMap<BigInteger,String> getListOfURLsForRunId(BigInteger runId);

    public String getFileContentForFileId(BigInteger fileId);

    public String getURLForURLId(BigInteger urlId);

}
