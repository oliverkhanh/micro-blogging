package com.khanhdd.fanout_service.controller;

import com.khanhdd.common_service.dto.FeedItem;
import com.khanhdd.fanout_service.service.FanoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fanout")
public class FanoutController {
  private final FanoutService fanoutService;

  public FanoutController(FanoutService fanoutService) {
    this.fanoutService = fanoutService;
  }

  @PostMapping
  public ResponseEntity<Void> fanout(@RequestBody FeedItem request) {
    fanoutService.fanout(request);
    return ResponseEntity.noContent().build();
  }
}
