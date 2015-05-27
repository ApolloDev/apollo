package edu.pitt.apollo.restservice.rest.responsemessage;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by dcs27 on 5/27/15.
 */
public class SimulationGroupRestMessage {
    Meta meta;
    List<BigInteger> listOfRunIds;

    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public List<BigInteger> getListOfRunIds() {
        return listOfRunIds;
    }
    public void setListOfRunIds(List<BigInteger> listOfRunIds) {
        this.listOfRunIds = listOfRunIds;
    }
}
