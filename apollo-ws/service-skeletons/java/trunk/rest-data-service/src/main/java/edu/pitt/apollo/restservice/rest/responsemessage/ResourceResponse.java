package edu.pitt.apollo.restservice.rest.responsemessage;

/**
 * Created by dcs27 on 4/20/15.
 */
public class ResourceResponse {
    Meta meta;
    Object resource;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Object getResource() {
        return resource;
    }

    public void setResource(Object resource) {
        this.resource = resource;
    }
}
