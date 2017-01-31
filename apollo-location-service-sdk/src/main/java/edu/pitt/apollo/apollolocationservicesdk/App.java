package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceInterface;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceAsFileConnector;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import edu.pitt.apollo.apollolocationservicesdk.types.ApolloLocationServiceFeature;

/**
 * Created by mas400 on 7/7/16.
 */
public class App {
    private static int maxEntries = 2000;

    public static void main(String[] args) {
        ApolloLocationServiceWebConnector webConnector = new ApolloLocationServiceWebConnector();
        ApolloLocationServiceAsFile apolloLocationServiceAsFile = new ApolloLocationServiceAsFile(new ApolloLocationServiceAsFileImplementation());
        ApolloLocationServiceAsFileConnector fileConnector = new ApolloLocationServiceAsFileConnector(apolloLocationServiceAsFile, webConnector);
        ApolloLocationServiceInterface implementation = new ApolloLocationService(fileConnector);

//
//        FileBackedApolloLocationServiceCache fileBackedApolloLocationServiceCache = new FileBackedApolloLocationServiceCache(new LeastFrequentlyUsedCache(maxEntries));
//        ApolloLocationServiceCachedConnector cachedConnector = new ApolloLocationServiceCachedConnector(fileBackedApolloLocationServiceCache, webConnector);
//        ApolloLocationServiceInterface implementation = new ApolloLocationService(cachedConnector);

        boolean test = implementation.isLocation("Canada");
        ApolloLocationServiceFeature apolloLocationServiceFeature = implementation.getFeatureFromLocationName("United States of America", "");
        try {
            apolloLocationServiceAsFile.serializeApolloLocationServiceAsFile();
//            fileBackedApolloLocationServiceCache.writeCachedFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Boolean isLocation = implementation.isLocation("Colima");

    }
}
