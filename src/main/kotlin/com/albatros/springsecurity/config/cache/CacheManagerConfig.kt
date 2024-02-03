package com.albatros.springsecurity.config.cache

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import java.time.Duration
import java.util.Collections

@Configuration
class CacheManagerConfig {

    companion object {
        val timeToLiveDuration: Duration = Duration.ofSeconds(40_000)
    }

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {

        val cacheConfig = RedisCacheConfiguration
            .defaultCacheConfig()
            .disableCachingNullValues()

        val cacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory)

        val cacheDefaults = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(timeToLiveDuration)
            .disableCachingNullValues()

        return RedisCacheManager
            .builder(cacheWriter)
            .cacheDefaults(cacheDefaults)
            .transactionAware()
            .withInitialCacheConfigurations(Collections.singletonMap("predefined", cacheConfig))
            .build()
    }
}
