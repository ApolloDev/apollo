package edu.pitt.apollo.apollolocationservicesdk;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceCachedConnector;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import org.geojson.FeatureCollection;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

/**
 * Created by mas400 on 7/8/16.
 */
public class ApolloLocationServiceFeatureTest {
    private ApolloLocationService implementation = new ApolloLocationService(new ApolloLocationServiceCachedConnector(new LeastRecentlyUsedCache(100), new ApolloLocationServiceWebConnector()));
    private ApolloLocationServiceFeature apolloLocationServiceFeature;
    @org.junit.Before
    public void setUp() throws Exception {
        apolloLocationServiceFeature = implementation.getFeatureFromLocationCode("1169");
    }

    @org.junit.Test
    public void getLocationTypeName() throws Exception {
        assertEquals("County", apolloLocationServiceFeature.getLocationTypeName());
    }

    @org.junit.Test
    public void getLocationCode() throws Exception {
        assertEquals("1169", apolloLocationServiceFeature.getLocationCode());
    }

    @org.junit.Test
    public void getLocationName() throws Exception {
        assertEquals("Allegheny", apolloLocationServiceFeature.getLocationName());
    }

    @org.junit.Test
    public void getEncompassingRegionCode() throws Exception {
        assertEquals("1213", apolloLocationServiceFeature.getEncompassingRegionCode());
    }

    @org.junit.Test
    public void getStartDate() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(simpleDateFormat.parse("1787-12-12"), apolloLocationServiceFeature.getStartDate());
    }

    @org.junit.Test
    public void getStartDateString() throws Exception {
        assertEquals("1787-12-12", apolloLocationServiceFeature.getStartDateString());
    }

    @org.junit.Test
    public void getAdminLevel() throws Exception {
        assertEquals("1169", apolloLocationServiceFeature.getLocationCode());
    }

    @org.junit.Test
    public void getEncompassingRegions() throws Exception {
        assertEquals(2, apolloLocationServiceFeature.getEncompassingRegions().size());
    }

    @org.junit.Test
    public void getGeoJson() throws Exception {
        String locationJson = apolloLocationServiceFeature.generateGeoJson();
        FeatureCollection featureCollection = implementation.createFeatureCollectionFromJSON(locationJson);
        ApolloLocationServiceFeature newApolloLocationServiceFeature = new ApolloLocationServiceFeature(featureCollection);
        assertEquals(apolloLocationServiceFeature.getAdminLevel(), newApolloLocationServiceFeature.getAdminLevel());
        assertEquals(apolloLocationServiceFeature.getEncompassingRegionCode(), newApolloLocationServiceFeature.getEncompassingRegionCode());
        assertEquals(apolloLocationServiceFeature.getEncompassingRegions(), newApolloLocationServiceFeature.getEncompassingRegions());
        assertEquals(apolloLocationServiceFeature.getLocationCode(), newApolloLocationServiceFeature.getLocationCode());
        assertEquals(apolloLocationServiceFeature.getLocationName(), newApolloLocationServiceFeature.getLocationName());
        assertEquals(apolloLocationServiceFeature.getLocationTypeName(), newApolloLocationServiceFeature.getLocationTypeName());
        assertEquals(apolloLocationServiceFeature.getStartDate(), newApolloLocationServiceFeature.getStartDate());
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }
}