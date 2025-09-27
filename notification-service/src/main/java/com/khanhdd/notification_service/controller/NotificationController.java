package com.khanhdd.notification_service.controller;

import com.khanhdd.common_service.dto.FeedItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
  @PostMapping
  public ResponseEntity<Void> createUser(@RequestBody FeedItem request) {
    System.out.println(
        "Send notification to user : "
            + request.getAuthorId()
            + " with post: "
            + request.getPostId());
    return ResponseEntity.noContent().build();
  }
}
