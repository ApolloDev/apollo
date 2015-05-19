package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.restservice.rest.responsemessage.Meta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/6/15.
 */
public class ApolloXsdVersionResponseMessage {
    Meta meta;
    List<String> xsdVersions = new ArrayList<String>();

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<String> getXsdVersions() {
        return xsdVersions;
    }

    public void setXsdVersions(List<String> xsdVersions) {
        this.xsdVersions = xsdVersions;
    }
}
