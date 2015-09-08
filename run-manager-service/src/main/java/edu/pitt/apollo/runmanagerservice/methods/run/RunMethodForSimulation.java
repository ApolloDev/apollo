package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.apollo_service_types.v3_0_2.RunSimulationsMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.runmanagerservice.exception.RunMessageFileNotFoundException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatus;
import edu.pitt.apollo.services_common.v3_0_2.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v3_0_2.RunMessage;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.utilities.JsonUtils;

import java.math.BigInteger;

/**
 *
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:17:43 PM Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForSimulation extends AbstractRunMethod {

    private Class runMessageClass = null;
	public RunMethodForSimulation(BigInteger stagedRunId, Authentication authentication) throws JsonUtilsException, DataServiceException {
		super(stagedRunId, authentication, "run_simulation_message.json");
	}

    @Override
    protected String getRunMessageJson(String runMessageFilename) throws DataServiceException {
        String json = dataServiceDao.getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, "run_message.json");

        if (json.contains("\"type\" : \"RunSimulationMessage\"")) {
            runMessageClass = RunSimulationMessage.class;
        } else if (json.contains("\"type\" : \"RunSimulationsMessage\"")) {
            runMessageClass = RunSimulationsMessage.class;
        } else {
            throw new DataServiceException("Unsupported run message type when getting run message json");
        }

        return json;
    }

    @Override
	protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
		JsonUtils jsonUtils = new JsonUtils();
        if (runMessageClass.equals(RunSimulationMessage.class)) {
            return (RunSimulationMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
        } else {
            return (RunSimulationsMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
        }
	}

	@Override
	protected MethodCallStatus getDefaultSuccessfulMethodCallStatus() {
		MethodCallStatus status = new MethodCallStatus();
		status.setStatus(MethodCallStatusEnum.CALLED_SIMULATOR);
		status.setMessage("The run request has been sent to the simulator");
		return status;
	}
}
