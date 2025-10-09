package com.khanhdd.follow_service.controller;

import java.util.List;

import com.khanhdd.follow_service.entity.User;
import com.khanhdd.follow_service.service.FollowService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
  private final FollowService followService;

  public FollowController(FollowService followService) {
    this.followService = followService;
  }

  @PostMapping
  public void follow(@RequestParam String followerId, @RequestParam String followeeId) {
    followService.followUser(followerId, followeeId);
  }

  @PostMapping("/unfollow")
  public void unfollow(@RequestParam String followerId, @RequestParam String followeeId) {
    followService.unfollowUser(followerId, followeeId);
  }

  @GetMapping("/followers")
  public List<User> getFollowers(@RequestParam String userId) {
    return followService.getFollowers(userId);
  }

  @GetMapping("/followers/count")
  public long getFollowersCount(@RequestParam String userId) {
    return followService.getFollowersCount(userId);
  }
}
