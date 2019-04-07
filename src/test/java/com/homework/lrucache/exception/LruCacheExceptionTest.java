package com.homework.lrucache.exception;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LruCacheExceptionTest {

    @Test
    public void shouldReturnLruCacheException() {
        assertThat(new LruCacheException("Something broke")).hasMessage("Something broke");
    }
}