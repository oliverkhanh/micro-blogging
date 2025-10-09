package com.khanhdd.micro_blogging_service.controller;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.common_service.dto.request.CreatePostDto;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.micro_blogging_service.service.BloggingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blogging")
public class BloggingController {
  private final BloggingService bloggingService;

  public BloggingController(BloggingService bloggingService) {
    this.bloggingService = bloggingService;
  }

  @PostMapping("/v1/me/feed")
  public ApiResponse<PostResponse> publish(@RequestBody CreatePostDto request) {
    return ApiResponse.success(bloggingService.publish(request));
  }
}
