package edu.pitt.apollo.apollolocationservicesdk.Interfaces;

import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceEntry;

/**
 * Created by mas400 on 8/23/16.
 */
public interface ApolloLocationServiceAsFileInterface {
    public String getGeoJsonFromLocationCode(int locationCode);

    public String getGeoJsonFromLocationName(String locationName, String encompassingRegion);

    public void addLocationCode(int locationCode, ApolloLocationServiceEntry entry);

    public void addLocationName(String locationName, ApolloLocationServiceEntry entry);
}
