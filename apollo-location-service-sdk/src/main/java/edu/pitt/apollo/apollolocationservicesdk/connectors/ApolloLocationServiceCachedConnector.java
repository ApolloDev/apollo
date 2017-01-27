package edu.pitt.apollo.apollolocationservicesdk.connectors;

import edu.pitt.apollo.apollolocationservicesdk.Interfaces.ApolloLocationServiceCacheInterface;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationCacheException;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationServicesUnreachableException;
import edu.pitt.apollo.apollolocationservicesdk.Interfaces.ApolloLocationServiceConnectorInterface;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by mas400 on 7/12/16.
 */
public class ApolloLocationServiceCachedConnector implements ApolloLocationServiceConnectorInterface {
    private ApolloLocationServiceConnectorInterface nextConnector;
    private ApolloLocationServiceCacheInterface cache;

    //create 2nd constructor to preload locations
    public ApolloLocationServiceCachedConnector(List<String> codes, ApolloLocationServiceCacheInterface cache, ApolloLocationServiceConnectorInterface nextConnector) {
        this.nextConnector = nextConnector;
        this.cache = cache;
        for (String code : codes) {
            try {
                getLocationByID(code);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ApolloLocationServiceCachedConnector(ApolloLocationServiceCacheInterface cache, ApolloLocationServiceConnectorInterface nextConnector) {
        this.cache = cache;
        this.nextConnector = nextConnector;
    }

    @Override
    public String getLocationByID(String locationCode) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        String geoJson = cache.getGeoJsonFromLocationCode(locationCode);
        if (geoJson != null)
            return geoJson;
        else {
            //cache missed
            geoJson = nextConnector.getLocationByID(locationCode);
            cache.addLocationCodeToCache(locationCode, geoJson);
            return geoJson;
        }
    }

    @Override
    public String getLocationByName(String locationName, String encompassingRegion) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        String geoJson = cache.getGeoJsonFromLocationName(locationName);
        if(geoJson != null) {
            return geoJson;
        }
        else {
            geoJson = nextConnector.getLocationByName(locationName, encompassingRegion);
            if(!geoJson.contains("Bad request") && !geoJson.contains("\"resultSize\":\"0\"") && geoJson != null)
                cache.addLocationNameToCache(locationName, geoJson);
            return geoJson;
        }
    }

    @Override
    public String getLocationByCoordinate(String latitude, String longitude) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        return null;
    }

    @Override
    public String getUniqueLocationNames(String locationName) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        return null;
    }

    @Override
    public String getLocationsByType(String locationTypeID) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        return null;
    }

    @Override
    public ApolloLocationServiceConnectorInterface getNextConnector() {
        return null;
    }
}
