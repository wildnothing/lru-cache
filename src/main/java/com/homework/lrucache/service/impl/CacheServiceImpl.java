package com.homework.lrucache.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.homework.lrucache.exception.LruCacheException;
import com.homework.lrucache.repository.CacheRepository;
import com.homework.lrucache.repository.entity.CalcEntity;
import com.homework.lrucache.service.CacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * LRU cache implementation. Production ready, backed by a LinkedHashMap.
 * Typically I'd use Hazelcast with a write-through/write-behind mechanism here instead.
 *
 * @param <K> CacheService entry key
 * @param <V> CacheService entry value
 */
@Service
public class CacheServiceImpl<K, V> extends LinkedHashMap<K, V> implements CacheService<K, V> {

    private final CacheRepository cacheRepository;
    private final Gson gson;
    private final int capacity;

    private Function<K, V> calculation = null;
    private final Type keyType = new TypeToken<K>(){}.getType();
    private final Type valueType = new TypeToken<V>(){}.getType();

    CacheServiceImpl(CacheRepository cacheRepository, Gson gson, @Value("${capacity}") int capacity) {
        super(capacity, 0.75f, true);
        this.cacheRepository = cacheRepository;
        this.gson = gson;
        this.capacity = capacity;
    }

    @Override
    public synchronized V getCacheEntry(K key) {
        V entry = get(key);

        if (entry == null) {
            Optional<CalcEntity> calcEntity = cacheRepository.findById(gson.toJson(key));

            if (calcEntity.isPresent()) {
                super.put(gson.fromJson(calcEntity.get().getKey(), keyType),
                        gson.fromJson(calcEntity.get().getValue(), valueType));
                entry = gson.fromJson(calcEntity.get().getValue(), valueType);
            }

        }

        return entry;
    }

    @Override
    public synchronized void putCacheEntry(K key) {
        if (calculation == null) {
            throw new LruCacheException("Calculation not set");
        }

        // This is not atomic behaviour. There might be cases where writing to the cache
        // succeeds but writing to the persistent store fails
        V value = calculation.apply(key);
        super.put(key, value);

        cacheRepository.save(new CalcEntity(gson.toJson(key), gson.toJson(value)));
    }

    @Override
    public void setCalculation(Function<K, V> calculation) {
        this.calculation = calculation;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > capacity;
    }
}
