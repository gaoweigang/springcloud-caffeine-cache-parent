package com.gwg.springcloud.config.cafeinecache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * CacheConfig
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    private CaffeineCacheProperties properties;

    @Bean
    public CacheManager caffeineCacheManager() {
        List<CaffeineCache> caches = Lists.newArrayList();
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        if (!CollectionUtils.isEmpty(properties.getItem())) {
            properties.getItem().forEach(i -> {
                CaffeineCache cache;
                if (i.getRecordStats()) {
                    cache = new CaffeineCache(i.getCacheName(), Caffeine.newBuilder()
                            .recordStats()
                            .expireAfterWrite(i.getExpireSeconds(), TimeUnit.SECONDS)
                            .initialCapacity(i.getInitSize())
                            .maximumSize(i.getMaxSize())
                            .build());
                } else {
                    cache = new CaffeineCache(i.getCacheName(), Caffeine.newBuilder()
                            .expireAfterWrite(i.getExpireSeconds(), TimeUnit.SECONDS)
                            .initialCapacity(i.getInitSize())
                            .maximumSize(i.getMaxSize())
                            .build());
                }
                caches.add(cache);
            });
            cacheManager.setCaches(caches);
        }
        return cacheManager;
    }

}
