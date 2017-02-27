package edu.pitt.apollo.apollolocationservicesdk.types;

import java.util.Map;
import java.util.Set;

/**
 * Created by mas400 on 7/12/16.
 */
public interface ApolloLocationServiceCacheContainer<K, V> {

    public void setMaxEntries(int size);

    public int getMaxEntries();

    public void addToCache(K key, V value);

    public V getFromCache(K key);

    public boolean isInCache(K key);

    public Set<K> keySet();

}
