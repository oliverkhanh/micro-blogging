package com.khanhdd.fanout_service.service;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.fanout_service.dto.User;
import com.khanhdd.fanout_service.feign_client.FollowServiceClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
  private final RedisTemplate<String, List<User>> listFollowersRedisTemplate;
//  private final RedisTemplate<String, Long> followerCountRedisTemplate;

  private static final String FOLLOWERS_CACHE_KEY_PREFIX = "followersCache::";
  private static final String FOLLOWERS_COUNT_CACHE_KEY_PREFIX = "followersCountCache::";

  private final FollowServiceClient followServiceClient;

  public FollowService(
      RedisTemplate<String, List<User>> listFollowersRedisTemplate,
//      RedisTemplate<String, Long> followerCountRedisTemplate,
      FollowServiceClient followServiceClient) {
    this.listFollowersRedisTemplate = listFollowersRedisTemplate;
//    this.followerCountRedisTemplate = followerCountRedisTemplate;
    this.followServiceClient = followServiceClient;
  }

//  public Long getFollowerCount(Long userId) {
//    String cacheKey = FOLLOWERS_COUNT_CACHE_KEY_PREFIX + userId;
//    Long cachedValue = followerCountRedisTemplate.opsForValue().get(cacheKey);
//    if (cachedValue != null) {
//      return cachedValue;
//    }
//    ApiResponse<Long> response = followServiceClient.getFollowersCount(userId);
//    return response.getData();
//  }

  public List<User> getFollowers(Long userId) {
    String cacheKey = FOLLOWERS_CACHE_KEY_PREFIX + userId;
    List<User> cachedFollowers = listFollowersRedisTemplate.opsForValue().get(cacheKey);
    if (cachedFollowers != null) {
      return cachedFollowers;
    }

    return followServiceClient.getFollowers(userId).getData();
  }
}
