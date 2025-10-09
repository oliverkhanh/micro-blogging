package com.khanhdd.follow_service.service;

import java.time.LocalDateTime;
import java.util.List;

import com.khanhdd.follow_service.entity.Follow;
import com.khanhdd.follow_service.entity.User;
import com.khanhdd.follow_service.repository.UserRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {
  private final UserRepository userRepository;

  public FollowService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  @CachePut(value = "followersCache", key = "#followeeId")
  public List<User> followUser(String followerId, String followeeId) {
    User follower = userRepository.findById(followerId).orElseGet(() -> new User(followerId));
    User followee = userRepository.findById(followeeId).orElseGet(() -> new User(followeeId));
    Follow follow = new Follow(followee, LocalDateTime.now());
    follower.getFollowees().add(follow);
    userRepository.save(follower);

    return userRepository.findFollowersByUserId(followeeId);
  }

  @Transactional
  @CachePut(value = "followersCache", key = "#followeeId")
  public List<User> unfollowUser(String followerId, String followeeId) {
    userRepository.deleteFollowRelationship(followerId, followeeId);

    return userRepository.findFollowersByUserId(followeeId);
  }

  @Cacheable(value = "followersCache", key = "#userId")
  public List<User> getFollowers(String userId) {
    return userRepository.findFollowersByUserId(userId);
  }

  public long getFollowersCount(String userId) {
    return userRepository.countFollowersByUserId(userId);
  }
}
