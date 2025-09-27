package com.khanhdd.follow_service.repository;

import com.khanhdd.follow_service.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface UserRepository extends Neo4jRepository<User, Long> {
  @Query("MATCH (a:User), (b:User) " +
          "WHERE id(a) = $followerId AND id(b) = $followeeId " +
          "CREATE (a)-[:FOLLOWS {createdAt: datetime()}]->(b)")
  void createFollow(Long followerId, Long followeeId);

  @Query("MATCH (a:User)-[f:FOLLOWS]->(b:User) " +
          "WHERE id(a) = $followerId AND id(b) = $followeeId " +
          "DELETE f")
  void deleteFollow(Long followerId, Long followeeId);

  @Query("MATCH (u:User)-[:FOLLOWS]->(v:User) WHERE id(u) = $id RETURN id(v)")
  List<Long> getFollowingIds(Long id);

  @Query("MATCH (u:User)<-[:FOLLOWS]-(v:User) WHERE id(u) = $id RETURN id(v)")
  List<Long> getFollowerIds(Long id);
}