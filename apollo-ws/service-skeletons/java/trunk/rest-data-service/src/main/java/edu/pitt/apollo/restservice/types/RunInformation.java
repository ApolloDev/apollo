package edu.pitt.apollo.restservice.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcs27 on 5/21/15.
 */
public class RunInformation {
    List<BigInteger> groupIds = new ArrayList<BigInteger>();
    String serviceType;

    public List<BigInteger> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<BigInteger> groupIds) {
        this.groupIds = groupIds;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
