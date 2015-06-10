package edu.pitt.apollo.syntheticpopulationserviceclient;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0.KillRunRequest;
import edu.pitt.apollo.service.syntheticpopulationservice.v3_0_0.KillRunResponse;
import edu.pitt.apollo.synthetic_population_service_types.v3_0_0.SyntheticPopulationRunStatusMessage;

import edu.pitt.apollo.syntheticpopulationserviceclient.WSClient;

public class ClientTest {
	@Test
	public void testGenerateKill() throws Exception {
		//below is an example usage of generateSyntheticPopulation() and getRunStatus()
		List<String> boundaryIDs = new ArrayList<String>();
		List<String> variableIDs = new ArrayList<String>();
		BigInteger year = BigInteger.valueOf(2015);
		BigInteger month = BigInteger.valueOf(4);
		BigInteger day = BigInteger.valueOf(13);
		
		boundaryIDs.add("42001");
		boundaryIDs.add("42002");
		boundaryIDs.add("42003");
		variableIDs.add("HEATING");
		variableIDs.add("HOUSEHOLD_SIZE");
		variableIDs.add("HOUSEHOLD_INCOME");
		
		BigInteger runID = WSClient.generateSyntheticPopulation(boundaryIDs, variableIDs, year, month, day);
		
		SyntheticPopulationRunStatusMessage runStatus = WSClient.getRunStatus(runID);
		assert(runStatus.getRunStatus().toString().equals("QUEUED"));
		
		KillRunRequest killRequest = new KillRunRequest();
		killRequest.setRunId(runID);
		KillRunResponse killRunResponse = WSClient.killRun(killRequest);
		//assert(killRunResponse.getRunStatus().getRunStatus().toString().equals("RUN_TERMINATED"));
		
		return;
	}
	
	@Test
	public void testCompleted() throws Exception {
		BigInteger runID = BigInteger.valueOf(968);
		
		SyntheticPopulationRunStatusMessage runStatus = WSClient.getRunStatus(runID);
		assert(runStatus.getCompletedRunUrl() != null);
		
		return;
	}
}
