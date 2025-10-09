package com.khanhdd.micro_blogging_service.feign_client;

import com.khanhdd.common_service.dto.FeedItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "fanoutService", url = "${fanout.service.url}")
public interface FanoutServiceClient {
  @PostMapping("/api/fanout")
  ResponseEntity<Void> fanout(@RequestBody FeedItem request);
}
