package edu.pitt.apollo.apolloservice.methods.run;

import java.math.BigInteger;

import edu.pitt.apollo.services_common.v3_0_0.RunResult;

public class RunResultAndSimulationGroupId {
	RunResult runResult;
	BigInteger simulationGroupId;

	public RunResult getRunResult() {
		return runResult;
	}

	public void setRunResult(RunResult runResult) {
		this.runResult = runResult;
	}

	public BigInteger getSimulationGroupId() {
		return simulationGroupId;
	}

	public void setSimulationGroupId(BigInteger simulationGroupId) {
		this.simulationGroupId = simulationGroupId;
	}
}
