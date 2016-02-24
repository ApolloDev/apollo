package edu.pitt.apollo.outputtranslator.types.rest;

/**
 * Created by dcs27 on 4/20/15.
 */
public class CollectionResource {
    ItemType type;
    String id;
    String name;
   // String url;

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }

}
