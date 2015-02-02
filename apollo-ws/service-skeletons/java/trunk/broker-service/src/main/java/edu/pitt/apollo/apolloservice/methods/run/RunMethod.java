package edu.pitt.apollo.apolloservice.methods.run;

import java.math.BigInteger;

import edu.pitt.apollo.apolloservice.error.ApolloServiceErrorHandler;
import edu.pitt.apollo.apolloservice.exception.UnrecognizedMessageTypeException;
import edu.pitt.apollo.apolloservice.thread.RunApolloServiceThreadFactory;
import edu.pitt.apollo.services_common.v3_0_0.Authentication;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * 
 * Author: Nick Millett Email: nick.millett@gmail.com Date: May 8, 2014 Time:
 * 11:52:26 AM Class: RunMethod IDE: NetBeans 6.9.1
 */
public class RunMethod extends AbstractRunMethod {

	private Object message;

	public RunMethod(Authentication authentication,
			SoftwareIdentification softwareIdentification, Object message) {
		super(authentication, softwareIdentification);
		this.message = message;
	}

	public final RunResultAndSimulationGroupId stageInDatabase(
			BigInteger simulationGroupId) {
		return stageRun(message, simulationGroupId);

	}

	public final RunResult run() {
		RunResultAndSimulationGroupId runResultAndSimulationGroupId = stageInDatabase(null);
		RunResult runResult = runResultAndSimulationGroupId.getRunResult();
		if ((runResult.getMethodCallStatus().getStatus() == MethodCallStatusEnum.LOADING_RUN_CONFIG_INTO_DATABASE) ||
				(runResult.getMethodCallStatus().getStatus() == MethodCallStatusEnum.LOADED_RUN_CONFIG_INTO_DATABASE)) {
			try {
				runApolloServiceThread = RunApolloServiceThreadFactory
						.getRunApolloServiceThread(authentication, message,
								runResult.getRunId(), runResultAndSimulationGroupId.getSimulationGroupId());

				runApolloServiceThread.setAuthenticationPasswordFieldToBlank();
				runApolloServiceThread.start();
			} catch (UnrecognizedMessageTypeException e) {
				return createRunResult(
						ApolloServiceErrorHandler.JOB_ID_FOR_FATAL_ERROR,
						MethodCallStatusEnum.FAILED,
						"Error of type + " + e.getClass().toString() + ":"
								+ e.getMessage());
			}
		}
		return runResult;
	}
}
