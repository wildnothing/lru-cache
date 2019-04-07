package com.homework.lrucache.service;

import java.util.function.Function;

public interface CacheService<K, V> {

    V getCacheEntry(K key);

    void putCacheEntry(K key);

    void setCalculation(Function<K, V> calculation);
}
