package edu.pitt.apollo.apollolocationservicesdk;

import edu.pitt.apollo.apollolocationservicesdk.types.LeastFrequentlyUsedLinkedHashMap;

/**
 * Created by mas400 on 7/13/16.
 */
public class LeastFrequentlyUsedCache extends AbstractApolloLocationServiceCache{
    public LeastFrequentlyUsedCache(int maxEntries) {
        super(maxEntries);
        codeToJsonMap = new LeastFrequentlyUsedLinkedHashMap<>();
        codeToJsonMap.setMaxEntries(maxEntries);
        nameToJsonMap = new LeastFrequentlyUsedLinkedHashMap<>();
        nameToJsonMap.setMaxEntries(maxEntries);
    }
}
