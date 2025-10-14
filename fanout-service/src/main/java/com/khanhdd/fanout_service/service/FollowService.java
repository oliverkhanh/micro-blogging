package com.khanhdd.fanout_service.service;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.fanout_service.dto.User;
import com.khanhdd.fanout_service.feign_client.FollowServiceClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {
  private final RedisTemplate<String, List<User>> redisTemplate;

  private static final String CACHE_KEY_PREFIX = "followers:";

  private final FollowServiceClient followServiceClient;

  public FollowService(
      RedisTemplate<String, List<User>> redisTemplate, FollowServiceClient followServiceClient) {
    this.redisTemplate = redisTemplate;
    this.followServiceClient = followServiceClient;
  }

  public Long getFollowerCount(Long userId) {
    ApiResponse<Long> response = followServiceClient.getFollowersCount(userId);
    return response.getData();
  }

  public List<User> getFollowers(Long userId) {
    String cacheKey = CACHE_KEY_PREFIX + userId;
    List<User> cachedFollowers = redisTemplate.opsForValue().get(cacheKey);
    if (cachedFollowers != null) {
      return cachedFollowers;
    }

    return followServiceClient.getFollowers(userId).getData();
  }
}
