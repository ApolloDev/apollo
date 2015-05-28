package edu.pitt.apollo.restservice.rest.responsemessage;

/**
 * Created by dcs27 on 5/28/15.
 */
public class GetURLForSoftwareIdentificationRestMessage {
    Meta meta;
    String wsdlURL;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getWsdlURL() {
        return wsdlURL;
    }

    public void setWsdlURL(String wsdlURL) {
        this.wsdlURL = wsdlURL;
    }
}
