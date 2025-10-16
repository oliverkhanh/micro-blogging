package com.khanhdd.fanout_service.feign_client;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.fanout_service.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "followService", url = "${follow.service.url}")
public interface FollowServiceClient {
  @GetMapping("/api/follow/followers")
  ApiResponse<List<User>> getFollowers(@RequestParam("userId") Long userId);

  @GetMapping("/api/follow/followers/count")
  ApiResponse<Long> getFollowersCount(@RequestParam("userId") Long userId);
}
