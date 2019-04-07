package com.homework.lrucache.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CacheConfigTest {

    @Autowired
    private CacheConfig cacheConfig;

    @Test
    public void shouldGetPersistenceExceptionTranslationPostProcessor() {
        assertThat(cacheConfig.persistenceExceptionTranslationPostProcessor()).isNotNull();
    }

    @Test
    public void shouldGetGson() {
        assertThat(cacheConfig.gson()).isNotNull();
    }
}