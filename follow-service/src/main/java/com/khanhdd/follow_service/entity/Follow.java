package com.khanhdd.follow_service.entity;

import lombok.Getter;
import org.springframework.data.neo4j.core.schema.*;
import java.time.LocalDateTime;

@Getter
@RelationshipProperties
public class Follow {

  @Id @GeneratedValue private Long id;

  @TargetNode private User followee;

  private LocalDateTime createdAt;

  public Follow() {}

  public Follow(User followee) {
    this.followee = followee;
    this.createdAt = LocalDateTime.now();
  }
}
