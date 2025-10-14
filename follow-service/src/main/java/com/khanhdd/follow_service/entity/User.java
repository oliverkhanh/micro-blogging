package com.khanhdd.follow_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node("User")
@JsonIgnoreProperties({"followees"})
public class User {
  @Id private Long id;

  @Relationship(type = "FOLLOWS", direction = Relationship.Direction.OUTGOING)
  private Set<Follow> followees = new HashSet<>();

  public User() {}

  public User(Long id) {
    this.id = id;
  }
}
