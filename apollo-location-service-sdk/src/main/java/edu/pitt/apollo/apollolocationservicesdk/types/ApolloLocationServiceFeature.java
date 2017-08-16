package edu.pitt.apollo.apollolocationservicesdk.types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.pitt.apollo.types.v4_0_1.SpatialGranularityEnum;
import org.geojson.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static edu.pitt.apollo.apollolocationservicesdk.utilities.LocationNameUtility.adminTypeOnes;

/**
 * Created by mas400 on 7/6/16.
 */
public class ApolloLocationServiceFeature extends Feature {
    protected static final SimpleDateFormat formatter = new SimpleDateFormat(ApolloLocationServiceFeatureConstants.APOLLO_LS_DATE_FORMAT);
    protected static final ObjectMapper om = new ObjectMapper();

    public ApolloLocationServiceFeature(Feature feature) {
        setId(feature.getId());
        setProperties(feature.getProperties());
        setGeometry(feature.getGeometry());
        setBbox(feature.getBbox());
    }

    public ApolloLocationServiceFeature(FeatureCollection featureCollection) {
        this(featureCollection.getFeatures().get(0));
    }

    public String getLocationTypeName() {
        return getProperty(ApolloLocationServiceFeatureConstants.LOCATION_TYPE_NAME);
    }

    public String getLocationCode() {
        return getProperty(ApolloLocationServiceFeatureConstants.APOLLO_LOCATION_CODE);
    }

    public String getLocationName() {
        return getProperty(ApolloLocationServiceFeatureConstants.LOCATION_NAME);
    }

    public String getEncompassingRegionCode() {
        return getProperty(ApolloLocationServiceFeatureConstants.ENCOMPASSING_REGION);
    }

    public Date getStartDate() throws ParseException {
        return formatter.parse((String) getProperty(ApolloLocationServiceFeatureConstants.START_DATE));
    }

    public String getStartDateString() {
        return getProperty(ApolloLocationServiceFeatureConstants.START_DATE);
    }

    public SpatialGranularityEnum getAdminLevel() {
        int adminLevel = 0;
        List<LinkedHashMap> encompassingRegions = getProperty(ApolloLocationServiceFeatureConstants.LINEAGE);
        for(LinkedHashMap encompassingRegion : encompassingRegions) {
            if(adminTypeOnes.contains(encompassingRegion.get("locationTypeName").toString()) || adminLevel > 0){
                adminLevel++;

            }
        }
        return SpatialGranularityEnum.fromValue("admin" + Integer.toString(adminLevel + 1));
    }

    public ArrayList getEncompassingRegions() {
        return getProperty(ApolloLocationServiceFeatureConstants.LINEAGE);
    }

    public String generateGeoJson() throws JsonProcessingException{
        FeatureCollection featureCollection = new FeatureCollection();
        Feature feature = new Feature();
        feature.setProperties(this.getProperties());
        feature.setGeometry(this.getGeometry());
        feature.setBbox(this.getBbox());
        feature.setId(this.getId());
        featureCollection.add(feature);
        featureCollection.setBbox(this.getBbox());
        String geoJson = om.writeValueAsString(featureCollection);
        return geoJson;
    }

}
