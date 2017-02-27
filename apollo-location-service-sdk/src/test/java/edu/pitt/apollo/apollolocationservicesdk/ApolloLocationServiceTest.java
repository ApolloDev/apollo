package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mas400 on 7/11/16.
 */
public class ApolloLocationServiceTest {
    private ApolloLocationService implementation;
    @Before
    public void setUp() throws Exception {
        implementation = new ApolloLocationService(new ApolloLocationServiceWebConnector());

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isLocation() throws Exception {
        assertEquals(false, implementation.isLocation("Malaria"));
        //returns false, need geojson to be fixed
//        assertEquals(true, implementation.isLocation("Pittsburgh"));

    }

    @Test
    public void getFeatureFromLocationName() throws Exception {
        //doesnt work until geojson is fixed
    }

    @Test
    public void getFeatureFromLocationCode() throws Exception {
        assertEquals("Allegheny", implementation.getFeatureFromLocationCode("1169").getLocationName());
    }

}