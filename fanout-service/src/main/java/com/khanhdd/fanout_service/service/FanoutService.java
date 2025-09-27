package com.khanhdd.fanout_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhdd.common_service.dto.FollowPost;
import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.fanout_service.kafka.KafkaProducer;

import java.util.ArrayList;
import java.util.List;

public class FanoutService {
  private final KafkaProducer producer;
  private final ObjectMapper objectMapper;

  public FanoutService(KafkaProducer producer, ObjectMapper objectMapper) {
    this.producer = producer;
    this.objectMapper = objectMapper;
  }

  public void fanout(FeedItem feedItem) {
    if (!isFanoutOnWrite()) {
      return;
    }
    // get follower ids

    // get follower data: filter follower, blacklist

    // Result is follower id list
    List<Long> followerIds = new ArrayList<>();
    followerIds.add(2L);
    // publish to message queue
    for (Long followerId : followerIds) {
      try {
        FollowPost followPost = new FollowPost();
        followPost.setAuthorId(feedItem.getAuthorId());
        followPost.setPostId(feedItem.getPostId());
        followPost.setFollowerId(followerId);

        // Convert object -> JSON string
        String message = objectMapper.writeValueAsString(followPost);

        producer.sendMessage("post-created-topic", message);

      } catch (JsonProcessingException e) {
        System.out.println("json processing exception " + e.getMessage());
      }
    }
  }

  private boolean isFanoutOnWrite() {
    return true;
  }
}
