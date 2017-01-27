package edu.pitt.apollo.apollolocationservicesdk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pitt.apollo.apollolocationservicesdk.Interfaces.ApolloLocationServiceInterface;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationCacheException;
import edu.pitt.apollo.apollolocationservicesdk.exception.ApolloLocationServicesUnreachableException;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeatureConstants;
import edu.pitt.apollo.apollolocationservicesdk.Interfaces.ApolloLocationServiceConnectorInterface;
import org.codehaus.jettison.json.JSONException;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by mas400 on 6/30/16.
 */
public class ApolloLocationService implements ApolloLocationServiceInterface {
    protected ApolloLocationServiceConnectorInterface apolloLSConnectorInterface;

    public ApolloLocationService(ApolloLocationServiceConnectorInterface apolloLSConnectorInterface) {
        this.apolloLSConnectorInterface = apolloLSConnectorInterface;
    }

    public Boolean isLocation(String possibleLocation) {
        String locationJSON = "";
        try {
            locationJSON = apolloLSConnectorInterface.getLocationByName(possibleLocation, "");

            FeatureCollection featureCollection = createFeatureCollectionFromJSON(locationJSON);
            if (featureCollection.getFeatures().size() > 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ApolloLocationServiceFeature getFeatureFromLocationName(String location, String encompassingRegion) {
        String locationJSON = "";
        try {
            location = stripAccents(location);
            locationJSON = apolloLSConnectorInterface.getLocationByName(location, encompassingRegion);
            FeatureCollection featureCollection = createFeatureCollectionFromJSON(locationJSON);
            return new ApolloLocationServiceFeature(getFeatureWithHighestAdminLevel(featureCollection, encompassingRegion));
        } catch (Exception e) {
            System.out.println("location: " + location + ", encompassing region: " + encompassingRegion + " could not be found.");
            return null;
        }
    }

    @Override
    public ApolloLocationServiceFeature getFeatureFromLocationCode(String locationCode) {
        String locationJSON = "";
        try {
            locationJSON = apolloLSConnectorInterface.getLocationByID(locationCode);
            FeatureCollection featureCollection = createFeatureCollectionFromJSON(locationJSON);
            return new ApolloLocationServiceFeature(featureCollection.getFeatures().get(0));
        } catch (Exception e) {
            return null;
        }
    }

    protected FeatureCollection createFeatureCollectionFromJSON(String json) throws IOException {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om.readValue(json, FeatureCollection.class);
    }

    private Feature getFeatureWithHighestAdminLevel(FeatureCollection featureCollection, String encompassingRegion) throws JSONException, IOException, ApolloLocationServicesUnreachableException, ApolloLocationCacheException {
        String featureCode = "";
        Integer lineageSize = null;

        if (featureCollection.getFeatures().size() == 1) {
            return featureCollection.getFeatures().get(0);
        } else {
            for (int i = 0; i < featureCollection.getFeatures().size(); i++) {
                Feature feature = featureCollection.getFeatures().get(i);

                if (!encompassingRegion.equals("")) {
                    ArrayList lineage = (ArrayList) feature.getProperties().get(ApolloLocationServiceFeatureConstants.LINEAGE);
                    if (lineage.size() > 0) {
                        String encompassingRegionName = stripAccents((String) ((LinkedHashMap) lineage.get(lineage.size() - 1)).get(ApolloLocationServiceFeatureConstants.LOCATION_NAME));

                        if (encompassingRegionName.toLowerCase().contains(encompassingRegion.toLowerCase())) {
                            featureCode = (String) feature.getProperties().get(ApolloLocationServiceFeatureConstants.APOLLO_LOCATION_CODE);
                            break;
                        }
                    }

                } else {
                    if (lineageSize == null || ((ArrayList) feature.getProperties().get(ApolloLocationServiceFeatureConstants.LINEAGE)).size() < lineageSize) {
                        lineageSize = ((ArrayList) feature.getProperties().get(ApolloLocationServiceFeatureConstants.LINEAGE)).size();
                        featureCode = (String) feature.getProperties().get(ApolloLocationServiceFeatureConstants.APOLLO_LOCATION_CODE);
                        if (lineageSize < 2)
                            break;
                    }
                }
            }
            if (!featureCode.equals("")) {
                FeatureCollection newFeatureCollection = createFeatureCollectionFromJSON(apolloLSConnectorInterface.getLocationByID(featureCode));
                return newFeatureCollection.getFeatures().get(0);
            }
        }
        return null;
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
