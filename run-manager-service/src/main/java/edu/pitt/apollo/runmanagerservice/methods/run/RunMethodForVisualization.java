package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.db.exceptions.ApolloDatabaseException;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.services_common.v4_0.Authentication;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0.RunMessage;
import edu.pitt.apollo.utilities.JsonUtils;
import edu.pitt.apollo.visualizer_service_types.v4_0.RunVisualizationMessage;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:17:43 PM Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForVisualization extends AbstractRunMethod {

	public RunMethodForVisualization(BigInteger runId, Authentication authentication) throws JsonUtilsException, DatastoreException, FilestoreException {
		super(runId, authentication, "run_message.json");
	}

	@Override
	protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
		JsonUtils jsonUtils = new JsonUtils();
		return (RunVisualizationMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, RunVisualizationMessage.class);
	}

//	@Override
//	protected Object getObjectToReturn(BigInteger runId) throws RunManagerServiceException {
//		RunResult runResult = new RunResult();
//		runResult.setRunId(runId);
//		runResult.setMethodCallStatus(getDefaultSuccessfulMethodCallStatus());
//		return runResult;
//	}

	@Override
	protected MethodCallStatus getDefaultSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.CALLED_VISUALIZER);
		status.setMessage("The run request has been sent to the visualizer");
		return status;
	}

}
