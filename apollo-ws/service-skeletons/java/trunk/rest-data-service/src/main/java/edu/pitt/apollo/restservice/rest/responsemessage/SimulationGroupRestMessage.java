package edu.pitt.apollo.restservice.rest.responsemessage;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/27/15.
 */
public class SimulationGroupRestMessage {
    Meta meta;
    List<BigInteger> listOfGroupIds;

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public List<BigInteger> getListOfGroupIds() {
        return listOfGroupIds;
    }
    public void setListOfGroupIds(List<BigInteger> listOfGroupIds) {
        this.listOfGroupIds = listOfGroupIds;
    }
}
