package edu.pitt.apollo.apollolocationservicesdk.types;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mas400 on 7/13/16.
 */
public class LeastFrequentlyUsedLinkedHashMap<K, V> extends LinkedHashMap<K, LeastFrequentlyUsedLinkedHashMap.CacheEntry> implements ApolloLocationServiceCacheContainer<K, V> {
    private int maxEntries = 100;

    @Override
    public boolean isInCache(K key) {
        return super.containsKey(key);
    }

    @Override
    public Set<K> keySet() {
        return super.keySet();
    }

    @Override
    public void addToCache(K code, V geoJson) {
        if (!isFull()) {
            CacheEntry<V> temp = new CacheEntry<V>();
            temp.setData(geoJson);
            temp.setFrequency(0);

            super.put(code, temp);
        } else {
            while(isFull()) {
                K entryKeyToBeRemoved = getLFUKey();
                super.remove(entryKeyToBeRemoved);

                CacheEntry<V> temp = new CacheEntry<V>();
                temp.setData(geoJson);
                temp.setFrequency(0);

                super.put(code, temp);
            }
        }
    }

    public K getLFUKey() {
        K key = null;
        int minFreq = Integer.MAX_VALUE;

        for (Map.Entry<K, CacheEntry> entry : this.entrySet()) {
            if (minFreq > entry.getValue().frequency) {
                key = entry.getKey();
                minFreq = entry.getValue().frequency;
            }
        }

        return key;
    }

    @Override
    public V getFromCache(K key) {
//        if(((String)key).equals("Tolu Viejo")) {
//            super.remove("Tolu Viejo");
//        }

        if (this.containsKey(key))  // cache hit
        {
            CacheEntry<V> temp = super.get(key);
            temp.frequency++;
            return temp.data;
        }
        return null; // cache miss
    }

    public boolean isFull() {
        if (this.size() >= maxEntries)
            return true;

        return false;
    }

    public int getMaxEntries() {
        return maxEntries;
    }

    public void setMaxEntries(int maxEntries) {
        this.maxEntries = maxEntries;
    }


    class CacheEntry<V> implements Serializable{
        private V data;
        private int frequency;

        private CacheEntry() {
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

    }
}