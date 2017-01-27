package edu.pitt.apollo.apollolocationservicesdk.Interfaces;

import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;

/**
 * Created by mas400 on 6/30/16.
 */
public interface ApolloLocationServiceInterface {
    /**
     * Checks if String is a location by checking existence in Apollo LS
     */
    public Boolean isLocation(String possibleLocation);

    /**
     * Returns an ApolloLocationServiceFeature object for a location, use "" if the encompassingRegion is not known
     */
    public ApolloLocationServiceFeature getFeatureFromLocationName(String location, String encompassingRegion);

    /**
     * Returns an ApolloLocationServiceFeature object for a location code
     */
    public ApolloLocationServiceFeature getFeatureFromLocationCode(String locationCode);
}
