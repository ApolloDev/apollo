package edu.pitt.apollo.apollolocationservicesdk.types;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mas400 on 7/12/16.
 */
//Removes the least recently used
public class LeastRecentlyUsedLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements ApolloLocationServiceCacheContainer<K, V> {
    private int maxEntries = 100;

    public LeastRecentlyUsedLinkedHashMap() {
        //default initial capacity (16), default load factor (0.75), accessOrder - the ordering mode - true for access-order
        //by default the linked list order is the insertion order, not access order
        super(16, (float) 0.75, true);
    }

    @Override
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public boolean isInCache(K key) {
        return super.containsKey(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() >= maxEntries;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }


    @Override
    public void addToCache(K key, V value) {
        super.put(key, value);
    }

    @Override
    public V getFromCache(K key) {
        return super.get(key);
    }
}
