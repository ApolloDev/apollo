package edu.pitt.apollo.restservice.rest.responsemessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/6/15.
 */
public class AddLibraryItemResponseMessage {

        Meta meta;
        List<Object> resource = new ArrayList<Object>();

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public List<Object> getResource() {
            return resource;
        }

        public void setcollectionItems(List<Object> resource) {
            this.resource = resource;
        }


}
