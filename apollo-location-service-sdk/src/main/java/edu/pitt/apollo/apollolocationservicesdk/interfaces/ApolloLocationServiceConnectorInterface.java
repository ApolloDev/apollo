package edu.pitt.apollo.apollolocationservicesdk.interfaces;

import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationCacheException;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationServicesUnreachableException;
import org.codehaus.jettison.json.JSONException;

import java.io.IOException;

/**
 * Created by mas400 on 7/7/16.
 */
public interface ApolloLocationServiceConnectorInterface {
    /**
     * Returns location geojson from a location code
     */
    public String getLocationByID(String locationCode) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException;

    /**
     * Returns location geojson from a location name
     */
    public String getLocationByName(String locationName, String encompassingRegion) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException;

    /**
     * Returns location geojson from a lat and long
     */
    public String getLocationByCoordinate(String latitude, String longitude) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException;

    /**
     * Returns unique location names from a given location name
     */
    public String getUniqueLocationNames(String locationName) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException;

    /**
     * Returns location name and location code from a location type id
     */
    public String getLocationsByType(String locationTypeID) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException;

    public ApolloLocationServiceConnectorInterface getNextConnector();
}
