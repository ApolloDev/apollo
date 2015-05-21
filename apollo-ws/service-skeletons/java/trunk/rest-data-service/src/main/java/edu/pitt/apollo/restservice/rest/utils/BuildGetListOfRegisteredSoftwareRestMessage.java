package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.restservice.rest.responsemessage.GetListOfRegisteredSoftwareRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;
import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

import java.util.List;

/**
 * Created by dcs27 on 5/19/15.
 */
public class BuildGetListOfRegisteredSoftwareRestMessage {
    public static GetListOfRegisteredSoftwareRestMessage buildFailedGetIdentificationKeyRestMessage(String errorMessage)
    {
        GetListOfRegisteredSoftwareRestMessage returnMessage = new GetListOfRegisteredSoftwareRestMessage();
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

    public static GetListOfRegisteredSoftwareRestMessage buildSuccessfulGetIdentificationKeyRestMessage(List<ServiceRegistrationRecord> listOfServiceRecords)
    {
        GetListOfRegisteredSoftwareRestMessage returnMessage = new GetListOfRegisteredSoftwareRestMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(1);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        for (ServiceRegistrationRecord srr : listOfServiceRecords) {
            returnMessage.getListOfServiceRecords().add(srr);
        }
        return returnMessage;
    }
}
