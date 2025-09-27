package com.khanhdd.follow_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node("User")
public class User {
  @Id @GeneratedValue private Long id;

  @Relationship(type = "FOLLOWS")
  private Set<Follow> followings = new HashSet<>();
}
