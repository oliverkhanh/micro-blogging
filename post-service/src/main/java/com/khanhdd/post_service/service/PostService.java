package com.khanhdd.post_service.service;

import com.khanhdd.common_service.dto.request.CreatePostDto;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.post_service.entity.Post;
import com.khanhdd.post_service.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public List<Post> getAll() {
    return repository.findAll();
  }

  public Optional<Post> getById(Long id) {
    return repository.findById(id);
  }

  public PostResponse create(CreatePostDto post) {
    Post postEntity = new Post();
    postEntity.setUserId(post.getAuthorId());
    postEntity.setContent(post.getContent());
    postEntity.setCreatedAt(LocalDateTime.now());
    postEntity = repository.save(postEntity);

    PostResponse postResponse = new PostResponse();
    postResponse.setId(postEntity.getId());
    postResponse.setCreatedAt(postEntity.getCreatedAt());
    postResponse.setUserId(postEntity.getUserId());
    postResponse.setContent(postEntity.getContent());

    return postResponse;
  }

  public Post update(Long id, Post postData) {
    return repository
        .findById(id)
        .map(
            post -> {
              post.setContent(postData.getContent());
              post.setUserId(postData.getUserId());
              return repository.save(post);
            })
        .orElseThrow(() -> new RuntimeException("Post not found"));
  }

  public void delete(Long id) {
    repository.deleteById(id);
  }
}
