package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.services_common.v3_0_0.ServiceRegistrationRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/19/15.
 */
public class GetListOfRegisteredSoftwareRestMessage {
    Meta meta;
    List<ServiceRegistrationRecord> listOfServiceRecords = new ArrayList<ServiceRegistrationRecord>();

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ServiceRegistrationRecord> getListOfServiceRecords()
    {
        return listOfServiceRecords;
    }
    public void setListOfServiceRecords(List<ServiceRegistrationRecord> listOfServiceRecords)
    {
        this.listOfServiceRecords=listOfServiceRecords;
    }

}
