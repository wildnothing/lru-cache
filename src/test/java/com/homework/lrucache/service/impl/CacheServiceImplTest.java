package com.homework.lrucache.service.impl;

import com.google.gson.Gson;
import com.homework.lrucache.exception.LruCacheException;
import com.homework.lrucache.repository.CacheRepository;
import com.homework.lrucache.repository.entity.CalcEntity;
import com.homework.lrucache.service.CacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheServiceImplTest {

    @Autowired
    private CacheService<Integer, String> cacheService;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private Gson gson;

    @Before
    public void setup() {
        cacheRepository.save(new CalcEntity(gson.toJson(1), gson.toJson("Hello, world!")));
    }

    @Test
    public void shouldFetchCacheEntryFromDbIfNotPresent() {
        cacheService.setCalculation((k) -> "Hello, world!");
        assertThat(cacheService.getCacheEntry(1)).isEqualTo("Hello, world!");
    }

    @Test
    public void shouldGetCacheEntry() {
        cacheService.putCacheEntry(1);
        assertThat(cacheService.getCacheEntry(1)).isEqualTo("Hello, world!");
    }

    @Test(expected = LruCacheException.class)
    public void shouldThrowExceptionIfCalculationNotSet() {
        cacheService.setCalculation(null);
        cacheService.putCacheEntry(1);
    }

}