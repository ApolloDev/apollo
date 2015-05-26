package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.data_service_types.v3_0_0.GetRunInformationResult;
import edu.pitt.apollo.restservice.rest.responsemessage.GetRunInformationRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;
import edu.pitt.apollo.restservice.types.RunInformation;

import java.util.List;

/**
 * Created by dcs27 on 5/21/15.
 */
public class BuildGetRunInformationRestMessage {
    public static GetRunInformationRestMessage buildFailedGetRunInformationRestMessage(String errorMessage) {
        GetRunInformationRestMessage returnMessage = new GetRunInformationRestMessage();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        Meta meta = new Meta();
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
    public static GetRunInformationRestMessage buildSuccessfulGetRunInformationRestMessage(GetRunInformationResult result) {
        GetRunInformationRestMessage returnMessage = new GetRunInformationRestMessage();
        Meta meta = new Meta();
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);

        RunInformation information = new RunInformation();
        information.setServiceType(result.getServiceType());
        information.setGroupIds(result.getAssociatedGroupIds());
        returnMessage.setRunInformation(information);
        return returnMessage;

    }
}
