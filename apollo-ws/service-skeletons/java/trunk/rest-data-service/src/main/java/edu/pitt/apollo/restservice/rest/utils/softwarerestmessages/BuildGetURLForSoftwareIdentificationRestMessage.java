package edu.pitt.apollo.restservice.rest.utils.softwarerestmessages;

import edu.pitt.apollo.restservice.rest.responsemessage.GetURLForSoftwareIdentificationRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

/**
 * Created by dcs27 on 5/28/15.
 */
public class BuildGetURLForSoftwareIdentificationRestMessage {
    public static GetURLForSoftwareIdentificationRestMessage buildFailedGetURLForSoftwareIdentificationMessage(String errorMessage)
    {
        GetURLForSoftwareIdentificationRestMessage returnMessage = new GetURLForSoftwareIdentificationRestMessage();
        Meta meta = new Meta();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        meta.setNumberOfReturnedResults(0);
         meta.setStatusMessage(errorMessage);
        meta.setStatus(adem.getStatus());
        returnMessage.setMeta(meta);
        return returnMessage;
    }

    public static GetURLForSoftwareIdentificationRestMessage buildSuccessfulGetURLForSoftwareIdentificationMessage(String wsdlURL)
    {
        GetURLForSoftwareIdentificationRestMessage returnMessage = new GetURLForSoftwareIdentificationRestMessage();
        returnMessage.setWsdlURL(wsdlURL);
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(1);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        return returnMessage;
    }
}
