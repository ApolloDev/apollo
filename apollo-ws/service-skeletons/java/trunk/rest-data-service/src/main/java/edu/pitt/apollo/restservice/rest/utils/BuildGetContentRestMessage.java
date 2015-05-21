package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndLabel;
import edu.pitt.apollo.restservice.rest.responsemessage.GetContentRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

/**
 * Created by dcs27 on 5/20/15.
 */
public class BuildGetContentRestMessage {
    public static GetContentRestMessage buildFailedGetListOfFilesAssociatedToRunRestMessage(String errorMessage)
    {
        GetContentRestMessage returnMessage = new GetContentRestMessage();
        Meta meta = new Meta();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        meta.setNumberOfReturnedResults(0);

        if(errorMessage.contains("No entry found"))
        {
            meta.setStatusMessage("No run was found in the database.");
        }
        else{
            meta.setStatusMessage(errorMessage);
        }
        meta.setStatus(adem.getStatus());
        returnMessage.setMeta(meta);
        return returnMessage;
    }

    public static GetContentRestMessage buildSuccessfulGetListOfFilesAssociatedToRunRestMessage(String fileContentOrUrl)
    {
        GetContentRestMessage returnMessage = new GetContentRestMessage();
        returnMessage.setContent(fileContentOrUrl);
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(1);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        return returnMessage;
    }
}
