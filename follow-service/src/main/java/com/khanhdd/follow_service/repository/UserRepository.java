package com.khanhdd.follow_service.repository;

import com.khanhdd.follow_service.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {
  @Query("MATCH (follower:User)-[r:FOLLOWS]->(followee:User {id: $userId}) RETURN follower")
  List<User> findFollowersByUserId(String userId);

  @Query("MATCH (follower:User)-[:FOLLOWS]->(followee:User {id: $userId}) RETURN count(follower)")
  long countFollowersByUserId(String userId);

  @Transactional
  @Query(
      "MATCH (follower:User {id: $followerId})-[r:FOLLOWS]->(followee:User {id: $followeeId}) DELETE r")
  void deleteFollowRelationship(String followerId, String followeeId);
}
