package com.khanhdd.fanout_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khanhdd.common_service.dto.FollowPost;
import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.fanout_service.dto.User;
import com.khanhdd.fanout_service.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FanoutService {
  private final KafkaProducer producer;
  private final ObjectMapper objectMapper;
  private final FollowService followService;

  public FanoutService(
      KafkaProducer producer, ObjectMapper objectMapper, FollowService followService) {
    this.producer = producer;
    this.objectMapper = objectMapper;
    this.followService = followService;
  }

  public void fanout(FeedItem feedItem) {
    if (!isFanoutOnWrite(feedItem.getAuthorId())) {
      return;
    }
    // get follower ids
    List<User> followers = followService.getFollowers(feedItem.getAuthorId());

    // TODO
    // get follower data: filter follower, blacklist

    // Result is follower id list
    List<Long> followerIds = followers.stream().map(User::getId).collect(Collectors.toList());
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

  private boolean isFanoutOnWrite(Long authorId) {
    // If user has more than 5 followers => celebrity
//    Long followersCount = followService.getFollowerCount(authorId);
//    return followersCount > 5;
    return true;
  }
}
