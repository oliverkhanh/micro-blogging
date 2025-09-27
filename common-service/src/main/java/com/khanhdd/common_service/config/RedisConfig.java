package com.khanhdd.common_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

  public static final String USER_CACHE = "user";
  public static final String POST_CACHE = "post";
  public static final long USER_CACHE_TTL_MINUTES = 60;
  public static final long POST_CACHE_TTL_MINUTES = 60;
  public static final long DEFAULT_CACHE_TTL_MINUTES = 10;

  @Bean
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);

    // Key -> String
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());

    // Value -> JSON (dùng ObjectMapper của Spring Boot, không bị deprecated)
    GenericJackson2JsonRedisSerializer serializer =
        new GenericJackson2JsonRedisSerializer(objectMapper);
    template.setValueSerializer(serializer);
    template.setHashValueSerializer(serializer);

    template.setEnableTransactionSupport(true);
    template.afterPropertiesSet();
    return template;
  }

  @Bean
  public RedisCacheManager cacheManager(
      RedisConnectionFactory connectionFactory, ObjectMapper objectMapper) {
    // Default cache config
    RedisCacheConfiguration defaultCacheConfig =
        createCacheConfig(objectMapper, Duration.ofMinutes(DEFAULT_CACHE_TTL_MINUTES));

    // Config riêng cho từng cache name
    Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
    cacheConfigs.put(
        USER_CACHE, createCacheConfig(objectMapper, Duration.ofMinutes(USER_CACHE_TTL_MINUTES)));
    cacheConfigs.put(
        POST_CACHE, createCacheConfig(objectMapper, Duration.ofMinutes(POST_CACHE_TTL_MINUTES)));

    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultCacheConfig)
        .withInitialCacheConfigurations(cacheConfigs)
        .transactionAware()
        .build();
  }

  private RedisCacheConfiguration createCacheConfig(ObjectMapper objectMapper, Duration ttl) {
    GenericJackson2JsonRedisSerializer serializer =
        new GenericJackson2JsonRedisSerializer(objectMapper);

    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(ttl)
        .disableCachingNullValues()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(serializer));
  }
}
