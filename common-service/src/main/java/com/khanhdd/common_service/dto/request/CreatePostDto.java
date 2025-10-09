package com.khanhdd.common_service.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostDto {
  private Long authorId;

  private String content;
}
