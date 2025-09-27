package com.khanhdd.common_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowPost {
  private Long authorId;

  private Long postId;

  private Long followerId;
}
