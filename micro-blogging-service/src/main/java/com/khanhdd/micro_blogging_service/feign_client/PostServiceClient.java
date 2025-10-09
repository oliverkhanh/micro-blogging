package com.khanhdd.micro_blogging_service.feign_client;

import com.khanhdd.common_service.dto.request.CreatePostDto;
import com.khanhdd.common_service.dto.request.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "postService", url = "${post.service.url}")
public interface PostServiceClient {

  @PostMapping("/api/posts")
  PostResponse createPost(@RequestBody CreatePostDto request);
}
