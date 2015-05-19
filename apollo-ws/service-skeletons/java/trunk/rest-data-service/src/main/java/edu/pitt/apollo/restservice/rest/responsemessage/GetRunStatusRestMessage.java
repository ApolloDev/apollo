package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.services_common.v3_0_0.RunStatus;
/**
 * Created by dcs27 on 5/15/15.
 */
public class GetRunStatusRestMessage {
    Meta meta;
    RunStatus resource;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public RunStatus getResource() {
        return resource;
    }

    public void setResource(RunStatus resource) {
        this.resource = resource;
    }
}
