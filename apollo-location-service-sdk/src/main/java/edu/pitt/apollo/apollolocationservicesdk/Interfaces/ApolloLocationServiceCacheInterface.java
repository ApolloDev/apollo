package edu.pitt.apollo.apollolocationservicesdk.Interfaces;

/**
 * Created by mas400 on 7/12/16.
 */
public interface ApolloLocationServiceCacheInterface {
    public String getGeoJsonFromLocationCode(String locationCode);

    public String getGeoJsonFromLocationName(String locationName);

    public void addLocationCodeToCache(String locationCode, String geoJson);

    public void addLocationNameToCache(String locationName, String geoJson);
}
