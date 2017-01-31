package edu.pitt.apollo.apollolocationservicesdk.connectors;

import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationCacheException;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationServicesUnreachableException;
import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceConnectorInterface;
import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceAsFileInterface;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

/**
 * Created by mas400 on 8/23/16.
 */
public class ApolloLocationServiceAsFileConnector implements ApolloLocationServiceConnectorInterface {
    private ApolloLocationServiceConnectorInterface nextConnector;
    private ApolloLocationServiceAsFileInterface cache;

    public ApolloLocationServiceAsFileConnector(ApolloLocationServiceAsFileInterface cache, ApolloLocationServiceConnectorInterface nextConnector) {
        this.cache = cache;
        this.nextConnector = nextConnector;
    }

    @Override
    public String getLocationByID(String locationCode) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        int code = Integer.parseInt(locationCode);
        String geoJson = cache.getGeoJsonFromLocationCode(code);
        if(geoJson != null)
            return geoJson;
        else {
            geoJson = nextConnector.getLocationByID(locationCode);
            return geoJson;
        }
    }

    @Override
    public String getLocationByName(String locationName, String encompassingRegion) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        String geoJson = cache.getGeoJsonFromLocationName(locationName, encompassingRegion);
        if(geoJson != null)
            return geoJson;
        else {
            return nextConnector.getLocationByName(locationName, encompassingRegion);
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
