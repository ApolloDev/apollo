package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.types.LeastRecentlyUsedLinkedHashMap;

/**
 * Created by mas400 on 7/12/16.
 */
public class LeastRecentlyUsedCache extends AbstractApolloLocationServiceCache{
    public LeastRecentlyUsedCache(int maxEntries) {
        super(maxEntries);
        codeToJsonMap = new LeastRecentlyUsedLinkedHashMap<>();
        codeToJsonMap.setMaxEntries(maxEntries);
        nameToJsonMap = new LeastRecentlyUsedLinkedHashMap<>();
        nameToJsonMap.setMaxEntries(maxEntries);
    }
}
