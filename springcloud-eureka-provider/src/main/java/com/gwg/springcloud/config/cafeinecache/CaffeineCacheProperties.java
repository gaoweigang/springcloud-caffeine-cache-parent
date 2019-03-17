package com.gwg.springcloud.config.cafeinecache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * CaffeineCacheProperties
 */
@Component
@ConfigurationProperties(prefix = "caffeine-cache")
@Data
public class CaffeineCacheProperties {

    public static final int DEFAULT_MAX_SIZE = 2000;
    public static final int DEFAULT_EXPIRE_SECONDS = 180;
    public static final int DEFAULT_INIT_SIZE = 100;
    public static final boolean DEFAULT_RECORD_STATS = Boolean.TRUE;

    private List<Cache> item = new ArrayList<>();

    @Data
    public static class Cache {
        // 缓存名称
        private String cacheName;
        // 初始缓存容量 initialCapacity
        private int initSize = DEFAULT_INIT_SIZE;
        // 缓存最大条数 maximumSize
        private int maxSize = DEFAULT_MAX_SIZE;
        // 最后一次写入后的过期时间 expireAfterWrite
        private int expireSeconds = DEFAULT_EXPIRE_SECONDS;
        // 是否统计 recordStats
        private Boolean recordStats = DEFAULT_RECORD_STATS;

    }

}
