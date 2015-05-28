package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.data_service_types.v3_0_0.ContentIdAndDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/20/15.
 */
public class GetListOfContentAssociatedToRunRestMessage {
    Meta meta;
    List<ContentIdAndDescription> listOfContentIdAndDescriptions = new ArrayList<ContentIdAndDescription>();

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ContentIdAndDescription> getListOfContentIdAndDescriptions()
    {
        return listOfContentIdAndDescriptions;
    }
    public void setListOfContentIdAndDescriptions(List<ContentIdAndDescription> listOfContentIdAndDescriptions)
    {
        this.listOfContentIdAndDescriptions=listOfContentIdAndDescriptions;
    }
}
