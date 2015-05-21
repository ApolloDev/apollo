package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndLabel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/20/15.
 */
public class GetListOfContentAssociatedToRunRestMessage {
    Meta meta;
    List<ContentIdAndLabel> listOfContentIdAndLabels = new ArrayList<ContentIdAndLabel>();

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ContentIdAndLabel> getListOfContentIdAndLabels()
    {
        return listOfContentIdAndLabels;
    }
    public void setListOfContentIdAndLabels(List<ContentIdAndLabel> listOfContentIdAndLabels)
    {
        this.listOfContentIdAndLabels=listOfContentIdAndLabels;
    }
}
