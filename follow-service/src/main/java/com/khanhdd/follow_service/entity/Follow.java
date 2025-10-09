package com.khanhdd.follow_service.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

@Getter
@Setter
@RelationshipProperties
public class Follow {
  @Id @GeneratedValue private Long id;
  @TargetNode private User followee;
  private LocalDateTime createdAt;

  public Follow() {}

  public Follow(User followee, LocalDateTime createdAt) {
    this.followee = followee;
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Follow)) return false;
    Follow that = (Follow) o;
    return followee != null && followee.getId().equals(that.followee.getId());
  }

  @Override
  public int hashCode() {
    return followee != null ? followee.getId().hashCode() : 0;
  }
}
