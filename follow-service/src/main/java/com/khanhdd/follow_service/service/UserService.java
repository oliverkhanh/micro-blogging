package com.khanhdd.follow_service.service;

import com.khanhdd.follow_service.entity.Follow;
import com.khanhdd.follow_service.entity.User;
import com.khanhdd.follow_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

  private final UserRepository repo;

  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public void follow(Long followerId, Long followeeId) {
    repo.createFollow(followerId, followeeId);
  }

  @Transactional
  public void unfollow(Long followerId, Long followeeId) {
    repo.deleteFollow(followerId, followeeId);
  }

  public List<Long> getFollowingIds(Long id) {
    return repo.getFollowingIds(id);
  }

  public List<Long> getFollowerIds(Long id) {
    return repo.getFollowerIds(id);
  }
}
