package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.services_common.v3_0_0.SoftwareIdentification;

/**
 * Created by dcs27 on 5/12/15.
 */
public class GetSoftwareIdentificationForRunRestMessage {
    Meta meta;
    SoftwareIdentification resource;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public SoftwareIdentification getResource() {
        return resource;
    }

    public void setResource(SoftwareIdentification resource) {
        this.resource = resource;
    }

}
