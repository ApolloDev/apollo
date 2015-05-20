package edu.pitt.apollo.restservice.rest.responsemessage;

import edu.pitt.apollo.restservice.rest.responsemessage.Meta;

/**
 * Created by dcs27 on 5/20/15.
 */
public class GetContentRestMessage {
    Meta meta;
    String content;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
