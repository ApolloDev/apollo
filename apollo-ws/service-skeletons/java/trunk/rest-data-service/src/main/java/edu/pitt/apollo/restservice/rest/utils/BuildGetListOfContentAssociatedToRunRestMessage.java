package edu.pitt.apollo.restservice.rest.utils;


import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndLabel;
import edu.pitt.apollo.restservice.rest.responsemessage.GetListOfContentAssociatedToRunRestMessage;
import edu.pitt.apollo.restservice.rest.responsemessage.Meta;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.ApolloDatabaseExceptionMessage;
import edu.pitt.apollo.restservice.rest.statuscodesandmessages.RequestSuccessfulMessage;

import java.util.List;

/**
 * Created by dcs27 on 5/20/15.
 */
public class BuildGetListOfContentAssociatedToRunRestMessage {
    public static GetListOfContentAssociatedToRunRestMessage buildFailedGetListOfFilesAssociatedToRunRestMessage(String errorMessage)
    {
        GetListOfContentAssociatedToRunRestMessage returnMessage = new GetListOfContentAssociatedToRunRestMessage();
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

    public static GetListOfContentAssociatedToRunRestMessage buildSuccessfulGetListOfFilesAssociatedToRunRestMessage(List<ContentIdAndLabel> listOfContentIdAndLabels)
    {
        GetListOfContentAssociatedToRunRestMessage returnMessage = new GetListOfContentAssociatedToRunRestMessage();
        int returnedItems = 0;
        for (ContentIdAndLabel cil : listOfContentIdAndLabels) {
            returnMessage.getListOfContentIdAndLabels().add(cil);
            returnedItems++;
        }
        Meta meta = new Meta();
        meta.setNumberOfReturnedResults(returnedItems);
        meta.setStatus(RequestSuccessfulMessage.getStatus());
        meta.setStatusMessage(RequestSuccessfulMessage.getMessage());
        returnMessage.setMeta(meta);

        return returnMessage;
    }
}
