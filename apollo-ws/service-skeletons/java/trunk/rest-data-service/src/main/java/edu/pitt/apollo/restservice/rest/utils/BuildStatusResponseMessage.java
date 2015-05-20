package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.responsemessage.StatusOnlyResponseMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

/**
 * Created by dcs27 on 5/13/15.
 */
public class BuildStatusResponseMessage {

    public static StatusOnlyResponseMessage buildFailedStatusResponseMessage(String errorMessage)
    {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        Meta meta = new Meta();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        if(errorMessage.contains("No entry found"))
        {
            meta.setStatusMessage("No run was found in the database.");
        }
        else{
            meta.setStatusMessage(errorMessage);
        }
        meta.setNumberOfReturnedResults(0);
        meta.setStatus(adem.getStatus());
        returnMessage.setMeta(meta);
        return returnMessage;
    }

    public static StatusOnlyResponseMessage buildSuccessfulStatusResponseMessage()
    {
        StatusOnlyResponseMessage returnMessage = new StatusOnlyResponseMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(0);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        return returnMessage;
    }
}
