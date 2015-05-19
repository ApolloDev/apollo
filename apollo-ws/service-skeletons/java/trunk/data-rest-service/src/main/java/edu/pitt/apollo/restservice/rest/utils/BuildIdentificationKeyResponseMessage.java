package edu.pitt.apollo.restservice.rest.utils;

import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.responsemessage.GetIdentificationKeyRestMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/13/15.
 */
public class BuildIdentificationKeyResponseMessage {

    public static GetIdentificationKeyRestMessage buildFailedGetIdentificationKeyRestMessage(String errorMessage)
    {
        GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();
        Meta meta = new Meta();
        ApolloDatabaseExceptionMessage adem = new ApolloDatabaseExceptionMessage();
        meta.setNumberOfReturnedResults(0);
        meta.setStatusMessage(errorMessage);
        meta.setStatus(adem.getStatus());
        returnMessage.setMeta(meta);
        return returnMessage;
    }

    public static GetIdentificationKeyRestMessage buildSuccessfulGetIdentificationKeyRestMessage(BigInteger indentificationKey)
    {
        GetIdentificationKeyRestMessage returnMessage = new GetIdentificationKeyRestMessage();
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(1);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);
        returnMessage.setResource(indentificationKey);
        return returnMessage;
    }
}
