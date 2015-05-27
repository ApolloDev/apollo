package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.JsonUtils;
import edu.pitt.apollo.JsonUtilsException;
import edu.pitt.apollo.runmanagerservice.exception.DataServiceException;
import edu.pitt.apollo.runmanagerservice.exception.RunManagerServiceException;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_0.RunResult;
import edu.pitt.apollo.visualizer_service_types.v3_0_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:17:43 PM Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForVisualization extends AbstractRunMethod {

	public RunMethodForVisualization(BigInteger runId, BigInteger associatedSimulationGroupId, Object message) throws JsonUtilsException, DataServiceException {
		super(runId);
	}

	@Override
	protected Object convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
		JsonUtils jsonUtils = new JsonUtils();
		return (RunVisualizationMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, RunVisualizationMessage.class);
	}

	@Override
	protected Object getObjectToReturn(BigInteger runId) throws RunManagerServiceException {
		RunResult runResult = new RunResult();
		runResult.setRunId(runId);
		runResult.setMethodCallStatus(getDefaultSuccessfulMethodCallStatus());
		return runResult;
	}

	@Override
	protected MethodCallStatus getDefaultSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.CALLED_VISUALIZER);
		status.setMessage("The run request has been sent to the visualizer");
		return status;
	}

}
