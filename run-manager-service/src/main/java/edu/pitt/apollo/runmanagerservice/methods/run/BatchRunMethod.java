package edu.pitt.apollo.runmanagerservice.methods.run;

import edu.pitt.apollo.apollo_service_types.v3_0_2.RunSimulationsMessage;
import edu.pitt.apollo.exception.DataServiceException;
import edu.pitt.apollo.exception.JsonUtilsException;
import edu.pitt.apollo.services_common.v3_0_2.Authentication;
import edu.pitt.apollo.services_common.v3_0_2.RunMessage;
import edu.pitt.apollo.simulator_service_types.v3_0_2.RunSimulationMessage;
import edu.pitt.apollo.utilities.JsonUtils;

import java.math.BigInteger;

/**
 * Created by jdl50 on 5/6/15.
 */
public class BatchRunMethod extends RunMethodForSimulation {


    public BatchRunMethod(BigInteger stagedRunId, Authentication authentication) throws JsonUtilsException, DataServiceException {
        super(stagedRunId, authentication);
    }

    @Override
    protected RunMessage convertRunMessageJson(String jsonForRunMessage) throws JsonUtilsException {
        JsonUtils jsonUtils = new JsonUtils();
        return (RunSimulationMessage) jsonUtils.getObjectFromJson(jsonForRunMessage, RunSimulationsMessage.class);
    }

}