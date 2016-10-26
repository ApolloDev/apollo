package edu.pitt.apollo.translatorservice.types;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett
 * Email: nick.millett@gmail.com
 * Date: Apr 8, 2015
 * Time: 4:28:51 PM
 * Class: BatchResultSetRow
 */
public class BatchResultSetRow {

	private BigInteger simulationRunId;
	private String runSimulationMessageJson;

	/**
	 * @return the simulationRunId
	 */
	public BigInteger getSimulationRunId() {
		return simulationRunId;
	}

	/**
	 * @param simulationRunId the simulationRunId to set
	 */
	public void setSimulationRunId(BigInteger simulationRunId) {
		this.simulationRunId = simulationRunId;
	}

	/**
	 * @return the runSimulationMessageJson
	 */
	public String getRunSimulationMessageJson() {
		return runSimulationMessageJson;
	}

	/**
	 * @param runSimulationMessageJson the runSimulationMessageJson to set
	 */
	public void setRunSimulationMessageJson(String runSimulationMessageJson) {
		this.runSimulationMessageJson = runSimulationMessageJson;
	}
	
}
