package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.data_service_types.v3_0_0.GetRunIdsAssociatedWithSimulationGroupResult;
import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationResult;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.responsemessage.SimulationGroupRestMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/27/15.
 */
public class BuildSimulationGroupRestMessage {
    public static SimulationGroupRestMessage buildFailedSimulationGroupRestMessage(String errorMessage)
    {
        SimulationGroupRestMessage returnMessage = new SimulationGroupRestMessage();
        Meta meta = new Meta();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        meta.setNumberOfReturnedResults(0);

        if(errorMessage.contains("No entry found"))
        {
            meta.setStatusMessage("No run was found in the .");
        }
        else{
            meta.setStatusMessage(errorMessage);
        }
        meta.setStatus(adem.getStatus());
        returnMessage.setMeta(meta);
        return returnMessage;
    }

    public static SimulationGroupRestMessage buildSuccessfulSimulationGroupRestMessage(GetRunIdsAssociatedWithSimulationGroupResult result)
    {
        SimulationGroupRestMessage returnMessage = new SimulationGroupRestMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(result.getAssociatedRunIds().size());
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        returnMessage.setListOfRunIds(result.getAssociatedRunIds());
        return returnMessage;
    }
}
