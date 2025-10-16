package com.khanhdd.fanout_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.common_service.dto.FollowPost;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
  private final ObjectMapper objectMapper;

  private final StringRedisTemplate redisTemplate;

  public KafkaConsumer(ObjectMapper objectMapper, StringRedisTemplate redisTemplate) {
    this.objectMapper = objectMapper;
    this.redisTemplate = redisTemplate;
  }

  @KafkaListener(topics = "post-created-topic", groupId = "my-group")
  public void listen(String message) {
    try {
      // parse JSON string -> object
      FollowPost followPost = objectMapper.readValue(message, FollowPost.class);

      System.out.println(
          "Received: userId=" + followPost.getAuthorId() + ", postId=" + followPost.getPostId());

      this.addPostToNewsfeed(followPost);

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void addPostToNewsfeed(FollowPost followPost) {
    String key = "newsfeed::" + followPost.getFollowerId();
    try {
      FeedItem feedItem = new FeedItem();
      feedItem.setPostId(followPost.getPostId());
      feedItem.setAuthorId(followPost.getAuthorId());

      String value = objectMapper.writeValueAsString(feedItem);

      redisTemplate.opsForList().leftPush(key, value);
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
