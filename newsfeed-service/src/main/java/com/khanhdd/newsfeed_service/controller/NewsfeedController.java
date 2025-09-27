package com.khanhdd.newsfeed_service.controller;

import com.khanhdd.newsfeed_service.service.NewsfeedService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/newsfeed")
public class NewsfeedController {

  private final NewsfeedService newsfeedService;

  public NewsfeedController(NewsfeedService newsfeedService) {
    this.newsfeedService = newsfeedService;
  }

  @GetMapping("/{userId}")
  public List<String> getNewsfeed(
      @PathVariable Long userId, @RequestParam(defaultValue = "20") int limit) {
    return newsfeedService.getNewsfeed(userId, limit);
  }
}
