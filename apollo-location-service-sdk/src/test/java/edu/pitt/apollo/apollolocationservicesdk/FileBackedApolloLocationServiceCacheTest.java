package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.Interfaces.ApolloLocationServiceInterface;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceCachedConnector;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mas400 on 7/14/16.
 */
public class FileBackedApolloLocationServiceCacheTest {
    private ApolloLocationServiceInterface implementation;
    private FileBackedApolloLocationServiceCache fileBackedApolloLocationServiceCache;

    @Before
    public void setUp() throws Exception {
        ApolloLocationServiceWebConnector webConnector = new ApolloLocationServiceWebConnector();
        fileBackedApolloLocationServiceCache = new FileBackedApolloLocationServiceCache(new LeastRecentlyUsedCache(100));
        ApolloLocationServiceCachedConnector cachedConnector = new ApolloLocationServiceCachedConnector(fileBackedApolloLocationServiceCache, webConnector);
        implementation = new ApolloLocationService(cachedConnector);
        ApolloLocationServiceFeature apolloLocationServiceFeature = implementation.getFeatureFromLocationCode("1169");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void writeCachedFile() throws Exception {
        fileBackedApolloLocationServiceCache.writeCachedFile();
        readCacheFile();
    }

    @Test
    public void readCacheFile() throws Exception {
        FileBackedApolloLocationServiceCache  newFileBackedCache = new FileBackedApolloLocationServiceCache(new LeastRecentlyUsedCache(100));
        assertEquals(fileBackedApolloLocationServiceCache.getGeoJsonFromLocationCode("1169"), newFileBackedCache.getGeoJsonFromLocationCode("1169"));

    }

}