package com.khanhdd.newsfeed_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhdd.common_service.dto.FeedItem;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NewsfeedService {
  private final StringRedisTemplate redisTemplate;
  private final ObjectMapper objectMapper;

  public NewsfeedService(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
    this.redisTemplate = redisTemplate;
    this.objectMapper = objectMapper;
  }

  public List<String> getNewsfeed(Long userId, int limit) {
    // Get newsfeed from cache
    List<FeedItem> cacheFeedItems =
        this.getNewsfeedFromCache(userId, limit); // Query to get newsfeed

    // Get newsfeed from database
    List<FeedItem> dbFeedItems = new ArrayList<>();

    // Merge by createdAt sort
    List<FeedItem> newsfeedItems = this.mergeAndSortFeeds(cacheFeedItems, dbFeedItems);
    if (CollectionUtils.isEmpty(newsfeedItems)) {
      return Collections.emptyList();
    }
    // Get userInfo
    // Get from cache -> Get from db

    // Get post Info
    // Get from cache -> get from db

    return Collections.emptyList();
  }

  public List<FeedItem> mergeAndSortFeeds(
      List<FeedItem> cacheFeedItems, List<FeedItem> dbFeedItems) {
    // Gộp 2 list lại
    List<FeedItem> merged = new ArrayList<>();
    merged.addAll(cacheFeedItems);
    merged.addAll(dbFeedItems);

    List<FeedItem> distinct =
        new ArrayList<>(
            merged.stream()
                .collect(
                    Collectors.toMap(
                        FeedItem::getPostId, item -> item, (existing, replacement) -> existing))
                .values());

    // Sắp xếp theo createdAt (mới nhất trước)
    distinct.sort(Comparator.comparing(FeedItem::getCreatedAt).reversed());

    return distinct;
  }

  private void getUserInfo(Long userId) {
    // Get from cache

    // If not exist in cache, call API to get from DB
  }

  private void getPostInfo(Long postId) {
    // Get from cache

    // If not exist in cache, call API to get from DB
  }

  private List<FeedItem> getNewsfeedFromCache(Long userId, int limit) {
    String key = "newsfeed:" + userId;

    // Lấy limit item đầu tiên từ Redis list
    List<String> items = redisTemplate.opsForList().range(key, 0, limit - 1);

    if (items == null) {
      return List.of();
    }

    return items.stream()
        .map(
            json -> {
              try {
                return objectMapper.readValue(json, FeedItem.class);
              } catch (Exception e) {
                return null;
              }
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
