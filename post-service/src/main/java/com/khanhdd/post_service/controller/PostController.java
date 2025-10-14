package com.khanhdd.post_service.controller;

import com.khanhdd.common_service.dto.request.CreatePostDto;
import com.khanhdd.common_service.dto.request.PostResponse;
import com.khanhdd.post_service.entity.Post;
import com.khanhdd.post_service.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  @GetMapping
  public List<Post> getAll() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getById(@PathVariable Long id) {
    return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public PostResponse create(@RequestBody CreatePostDto post) {
    return service.create(post);
  }

  @PutMapping("/{id}")
  public Post update(@PathVariable Long id, @RequestBody Post post) {
    return service.update(id, post);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
