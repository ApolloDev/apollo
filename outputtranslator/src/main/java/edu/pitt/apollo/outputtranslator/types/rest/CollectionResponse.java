package edu.pitt.apollo.outputtranslator.types.rest;

import java.util.ArrayList;
import java.util.List;


public class CollectionResponse {
    Meta meta;
    List<CollectionResource> data = new ArrayList<CollectionResource>();


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CollectionResource> getData() {
        return data;
    }

    public void setcollectionItems(List<CollectionResource> data) {
        this.data = data;
    }
}
