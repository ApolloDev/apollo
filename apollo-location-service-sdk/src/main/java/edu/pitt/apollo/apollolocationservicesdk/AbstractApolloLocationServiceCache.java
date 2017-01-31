package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceCacheInterface;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceCacheContainer;

/**
 * Created by mas400 on 7/12/16.
 */
public class AbstractApolloLocationServiceCache implements ApolloLocationServiceCacheInterface {
    protected ApolloLocationServiceCacheContainer<String, String> codeToJsonMap;
    protected ApolloLocationServiceCacheContainer<String, String> nameToJsonMap;
    protected int maxEntries;

    public AbstractApolloLocationServiceCache(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    @Override
    public String getGeoJsonFromLocationCode(String locationCode) {
        if (codeToJsonMap.isInCache(locationCode))
            return codeToJsonMap.getFromCache(locationCode);
        return null;
    }

    @Override
    public String getGeoJsonFromLocationName(String locationName) {
        if (nameToJsonMap.isInCache(locationName))
            return nameToJsonMap.getFromCache(locationName);
        return null;
    }

    @Override
    public void addLocationCodeToCache(String locationCode, String geoJson) {
        codeToJsonMap.addToCache(locationCode, geoJson);
    }

    @Override
    public void addLocationNameToCache(String locationName, String geoJson) {
        nameToJsonMap.addToCache(locationName, geoJson);
    }
}
