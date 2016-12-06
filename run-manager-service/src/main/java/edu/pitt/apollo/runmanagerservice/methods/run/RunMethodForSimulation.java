package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.apollo_service_types.v4_0_1.RunInfectiousDiseaseTransmissionExperimentMessage;
import edu.pitt.apollo.apollo_service_types.v4_0_1.RunSimulationsMessage;
import edu.pitt.apollo.exception.DatastoreException;
import edu.pitt.apollo.exception.FilestoreException;
import edu.pitt.apollo.services_common.v4_0_1.Authentication;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatus;
import edu.pitt.apollo.services_common.v4_0_1.MethodCallStatusEnum;
import edu.pitt.apollo.services_common.v4_0_1.RunMessage;
import edu.pitt.apollo.simulator_service_types.v4_0_1.RunSimulationMessage;
import edu.pitt.apollo.utilities.ApolloClassList;
import edu.pitt.isg.objectserializer.JsonUtils;
import edu.pitt.isg.objectserializer.exceptions.JsonUtilsException;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Author: Nick Millett Email: nick.millett@gmail.com Date: Jan 22, 2015 Time: 12:17:43 PM Class: RunMethodForSimulationAndVisualization
 */
public class RunMethodForSimulation extends AbstractRunMethod {

    private Class runMessageClass = null;

    public RunMethodForSimulation(BigInteger stagedRunId, Authentication authentication) throws JsonUtilsException, DatastoreException, FilestoreException {
        super(stagedRunId, authentication, "run_message.json");
    }

    @Override
    protected String getRunMessageJson(String runMessageFilename) throws DatastoreException, FilestoreException {
        String json = dataServiceDao.getRunMessageAssociatedWithRunIdAsJsonOrNull(runId, authentication, "run_message.json");

        if (json.contains("\"type\" : \"RunSimulationMessage\"")) {
            runMessageClass = RunSimulationMessage.class;
        } else if (json.contains("\"type\" : \"RunSimulationsMessage\"")) {
            runMessageClass = RunSimulationsMessage.class;
        } else if (json.contains("\"type\" : \"RunInfectiousDiseaseTransmissionExperimentMessage\"")) {
            runMessageClass = RunInfectiousDiseaseTransmissionExperimentMessage.class;
        } else {
            throw new DatastoreException("Unsupported run message type when getting run message json");
        }

        return json;
    }

    @Override
    protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
        JsonUtils jsonUtils = new JsonUtils(Arrays.asList(ApolloClassList.classList));
        if (runMessageClass.equals(RunSimulationMessage.class)) {
            return (RunSimulationMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
        } else if (runMessageClass.equals(RunSimulationsMessage.class)) {
            return (RunSimulationsMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
        } else {
            return (RunInfectiousDiseaseTransmissionExperimentMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, runMessageClass);
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
