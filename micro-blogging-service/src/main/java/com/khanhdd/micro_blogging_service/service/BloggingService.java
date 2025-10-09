package com.khanhdd.micro_blogging_service.service;

import com.khanhdd.common_service.dto.request.CreatePostDto;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.micro_blogging_service.feign_client.FanoutServiceClient;
import com.khanhdd.micro_blogging_service.feign_client.PostServiceClient;
import feign.FeignException;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.stereotype.Service;

@Service
public class BloggingService {
  private final PostServiceClient postClient;
  private final NotificationService notificationService;
  private final FanoutService fanoutService;

  public BloggingService(
      PostServiceClient postClient,
      NotificationService notificationService,
      FanoutService fanoutService) {
    this.postClient = postClient;
    this.notificationService = notificationService;
    this.fanoutService = fanoutService;
  }

  public PostResponse publish(CreatePostDto request) {
    try {
      PostResponse postResponse = postClient.createPost(request);

      // Call async
      notificationService.callApiToNotificationService(postResponse);
      fanoutService.callApiToFanoutService(postResponse);

      return postResponse;
    } catch (FeignException e) {
      // xử lý lỗi: log, chuyển đổi exception, retry tùy cần
      throw new ApiException("Failed to call post-service: " + e.getMessage(), e);
    }
  }
}
