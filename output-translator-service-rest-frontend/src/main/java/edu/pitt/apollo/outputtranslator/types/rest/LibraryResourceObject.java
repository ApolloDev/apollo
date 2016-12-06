package edu.pitt.apollo.outputtranslator.types.rest;

import edu.pitt.apollo.types.v4_0_1.Epidemic;

/**
 * Created by dcs27 on 4/22/15.
 */
public class LibraryResourceObject {
    String name;
    Epidemic item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Epidemic getItem() {
        return item;
    }

    public void setItem(Epidemic item) {
        this.item = item;
    }
}
