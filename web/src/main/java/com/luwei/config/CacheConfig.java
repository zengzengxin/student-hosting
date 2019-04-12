package com.luwei.config;

import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public ConcurrentMapCacheManager chageCacheToConcurrentMapCacheManager(){
        return new ConcurrentMapCacheManager();
    }
}
