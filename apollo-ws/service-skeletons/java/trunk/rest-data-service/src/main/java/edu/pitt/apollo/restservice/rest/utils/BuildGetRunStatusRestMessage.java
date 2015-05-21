package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.restservice.rest.responsemessage.GetRunStatusRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;
import edu.pitt.apollo.services_common.v3_0_0.RunStatus;

/**
 * Created by dcs27 on 5/15/15.
 */
public class BuildGetRunStatusRestMessage {

    public static GetRunStatusRestMessage buildFailedGetIdentificationKeyRestMessage(String errorMessage)
    {
        GetRunStatusRestMessage returnMessage = new GetRunStatusRestMessage();
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

    public static GetRunStatusRestMessage buildSuccessfulGetIdentificationKeyRestMessage(RunStatus runStatus)
    {
        GetRunStatusRestMessage returnMessage = new GetRunStatusRestMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(1);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        returnMessage.setResource(runStatus);
        return returnMessage;
    }
}
