package edu.pitt.apollo.outputtranslator.types.rest;

/**
 * Created by dcs27 on 4/20/15.
 */
public class ResourceResponse {
    Meta meta;
    LibraryResourceObject data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public LibraryResourceObject getData() {
        return data;
    }

    public void setData(LibraryResourceObject data) {
        this.data = data;
    }
}
