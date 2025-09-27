package com.khanhdd.newsfeed_service.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewsfeedResponse {
  private Long id;

  private String userName;

  private String content;

  private LocalDateTime createdAt;
}
