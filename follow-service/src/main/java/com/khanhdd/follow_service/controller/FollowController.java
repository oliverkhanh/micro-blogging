package com.khanhdd.follow_service.controller;

import java.util.List;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.follow_service.entity.User;
import com.khanhdd.follow_service.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {
  private final FollowService followService;

  public FollowController(FollowService followService) {
    this.followService = followService;
  }

  @PostMapping
  public ResponseEntity<Void> follow(@RequestParam Long followerId, @RequestParam Long followeeId) {
    followService.followUser(followerId, followeeId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/unfollow")
  public ResponseEntity<Void> unfollow(
      @RequestParam Long followerId, @RequestParam Long followeeId) {
    followService.unfollowUser(followerId, followeeId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/followers")
  public ApiResponse<List<User>> getFollowers(@RequestParam Long userId) {
    return ApiResponse.success(followService.getFollowers(userId));
  }

  @GetMapping("/followers/count")
  public ApiResponse<Long> getFollowersCount(@RequestParam Long userId) {
    return ApiResponse.success(followService.getFollowersCount(userId));
  }
}
