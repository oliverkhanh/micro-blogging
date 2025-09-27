package com.khanhdd.user_service.controller;

import com.khanhdd.common_service.dto.ApiResponse;
import com.khanhdd.user_service.entity.User;
import com.khanhdd.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public ApiResponse<List<User>> getAllUsers() {
    return ApiResponse.success(service.getAllUsers());
  }

  @GetMapping("/{id}")
  public ApiResponse<User> getUserById(@PathVariable Long id) {
    return ApiResponse.success(service.getUserById(id).orElse(null));
  }

  @PostMapping
  public User createUser(@RequestBody User user) {
    return service.createUser(user);
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return service.updateUser(id, user);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
