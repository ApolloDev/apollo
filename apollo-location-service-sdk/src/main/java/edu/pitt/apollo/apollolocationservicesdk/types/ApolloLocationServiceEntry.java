package edu.pitt.apollo.apollolocationservicesdk.types;

import java.io.Serializable;

/**
 * Created by mas400 on 8/23/16.
 */
public class ApolloLocationServiceEntry implements Serializable{
    String locationName;
    int apolloLocationCode;
    int encompassingRegionCode;
    int locationTypeId;

    public ApolloLocationServiceEntry(String locationName, int apolloLocationCode, int encompassingRegionCode, int locationTypeId) {
        this.locationName = locationName;
        this.apolloLocationCode = apolloLocationCode;
        this.encompassingRegionCode = encompassingRegionCode;
        this.locationTypeId = locationTypeId;
    }

    public int getApolloLocationCode() {
        return apolloLocationCode;
    }

    public void setApolloLocationCode(int apolloLocationCode) {
        this.apolloLocationCode = apolloLocationCode;
    }

    public int getEncompassingRegionCode() {
        return encompassingRegionCode;
    }

    public void setEncompassingRegionCode(int encompassingRegionCode) {
        this.encompassingRegionCode = encompassingRegionCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationTypeId() {
        return locationTypeId;
    }

    public void setLocationTypeId(int locationTypeId) {
        this.locationTypeId = locationTypeId;
    }
}
