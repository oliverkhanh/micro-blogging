package com.khanhdd.common_service.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
  private Long id;

  private Long userId;

  private String content;

  private LocalDateTime createdAt;
}
