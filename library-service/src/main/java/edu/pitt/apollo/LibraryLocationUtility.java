package edu.pitt.apollo;

import edu.pitt.apollo.apollolocationservicesdk.ApolloLocationService;
import edu.pitt.apollo.apollolocationservicesdk.FileBackedApolloLocationServiceCache;
import edu.pitt.apollo.apollolocationservicesdk.interfaces.ApolloLocationServiceInterface;
import edu.pitt.apollo.apollolocationservicesdk.LeastRecentlyUsedCache;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceCachedConnector;
import edu.pitt.apollo.apollolocationservicesdk.connectors.ApolloLocationServiceWebConnector;
import edu.pitt.apollo.apollolocationservicesdk.utilities.LocationNameUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nem41 on 1/24/17.
 */
public class LibraryLocationUtility {

    private static ApolloLocationServiceInterface implementation;
    private static FileBackedApolloLocationServiceCache fileBackedApolloLocationServiceCache;

    static {
        ApolloLocationServiceWebConnector webConnector = new ApolloLocationServiceWebConnector();
        fileBackedApolloLocationServiceCache = new FileBackedApolloLocationServiceCache(new LeastRecentlyUsedCache(100));
        ApolloLocationServiceCachedConnector cachedConnector = new ApolloLocationServiceCachedConnector(fileBackedApolloLocationServiceCache, webConnector);
        implementation = new ApolloLocationService(cachedConnector);
    }

    public static String getLocationNameFromCodes(List<String> apolloLocationCodes) {
        return LocationNameUtility.getLocationDescription(apolloLocationCodes, implementation);
    }

    public static void main(String[] args) {
        List<String> locations = new ArrayList<>();
        locations.add("7468");
        System.out.println("");
        System.out.println(getLocationNameFromCodes(locations));
    }
}
