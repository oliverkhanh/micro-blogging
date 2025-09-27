package com.khanhdd.follow_service.controller;

import com.khanhdd.follow_service.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @PostMapping("/{followerId}/follow/{followeeId}")
  public String follow(@PathVariable Long followerId, @PathVariable Long followeeId) {
    service.follow(followerId, followeeId);
    return followerId + " followed " + followeeId;
  }

  @GetMapping("/{id}/followings")
  public List<Long> followings(@PathVariable Long id) {
    return service.getFollowingIds(id);
  }

  @GetMapping("/{id}/followers")
  public List<Long> followers(@PathVariable Long id) {
    return service.getFollowerIds(id);
  }

  @DeleteMapping("/{followerId}/unfollow/{followeeId}")
  public String unfollow(@PathVariable Long followerId, @PathVariable Long followeeId) {
    service.unfollow(followerId, followeeId);
    return followerId + " unfollowed " + followeeId;
  }
}
