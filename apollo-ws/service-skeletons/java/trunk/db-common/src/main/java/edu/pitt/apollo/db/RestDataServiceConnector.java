/*
 * Copyright 2015 nem41.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.pitt.apollo.db;

import edu.pitt.apollo.data_service_types.v3_0_0.GetAllOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLAsZipMessage;
import edu.pitt.apollo.data_service_types.v3_0_0.GetOutputFilesURLsMessage;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;
import edu.pitt.apollo.simulator_service_types.v3_0_0.RunSimulationMessage;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author nem41
 */
public class RestDataServiceConnector implements DataServiceConnector {

	@Override
	public boolean isRunBatch(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger getSimulationGroupIdForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<BigInteger> getRunIdsForBatch(BigInteger batchRunId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public RunSimulationMessage getRunSimulationMessageForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public GetOutputFilesURLsMessage getGetOutputFilesURLsMessageForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public GetOutputFilesURLAsZipMessage getGetOutputFilesURLAsZipMessageForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public GetAllOutputFilesURLAsZipMessage getGetAllOutputFilesURLAsZipMessageForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean authorizeUser(Authentication authentication, SoftwareIdentification softwareIdentification, boolean requestToRunSoftware) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getRunKey(RunSimulationMessage runSimulationMessage) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean authenticateUser(Authentication authentication) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int addTextDataContent(String content) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int associateContentWithRunId(BigInteger runKey, int dataContentKey, int runDataDescriptionId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SoftwareIdentification getSoftwareIdentificationForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int getSoftwareIdentificationKeyForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger[] addDataServiceRun(GetAllOutputFilesURLAsZipMessage message, Authentication authentication, SoftwareIdentification dataServiceSoftwareId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger[] addDataServiceRun(GetOutputFilesURLsMessage message, Authentication authentication, SoftwareIdentification dataServiceSoftwareId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger[] addDataServiceRun(GetOutputFilesURLAsZipMessage message, Authentication authentication, SoftwareIdentification dataServiceSoftwareId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger[] addSimulationRun(Object runMessage, BigInteger memberOfSimulationGroupIdOrNull, SoftwareIdentification sourceSoftwareForRunSimulationMessage, SoftwareIdentification destinationSoftwareForRunSimulationMessage, Authentication authentication) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void updateStatusOfRun(BigInteger runId, MethodCallStatusEnum statusEnum, String message) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int updateLastServiceToBeCalledForRun(BigInteger runId, SoftwareIdentification softwareIdentification) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public SoftwareIdentification getLastServiceToBeCalledForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public MethodCallStatus getStatusOfLastServiceToBeCalledForRun(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger getNewSimulationGroupId() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void addRunIdsToSimulationGroup(BigInteger simulationGroupId, List<BigInteger> runIds) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BigInteger[] addVisualizationRun(RunVisualizationMessage runVisualizationMessage, Authentication authentication) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void removeRunData(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public HashMap<BigInteger, String> getListOfFilesForRunId(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public HashMap<BigInteger, String> getListOfURLsForRunId(BigInteger runId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getFileContentForFileId(BigInteger fileId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getURLForURLId(BigInteger urlId) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
