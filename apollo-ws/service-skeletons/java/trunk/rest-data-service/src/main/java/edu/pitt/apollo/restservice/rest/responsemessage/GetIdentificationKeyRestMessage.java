package edu.pitt.apollo.restservice.rest.responsemessage;

import java.math.BigInteger;

/**
 * Created by dcs27 on 5/12/15.
 */
public class GetIdentificationKeyRestMessage {
    Meta meta;
    BigInteger resource;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public BigInteger getResource() {
        return resource;
    }

    public void setResource(BigInteger resource) {
        this.resource = resource;
    }
}
