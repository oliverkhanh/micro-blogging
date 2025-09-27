package com.khanhdd.user_service.service;

import com.khanhdd.user_service.entity.User;
import com.khanhdd.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return repository.findById(id);
  }

  public User createUser(User user) {
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    return repository.save(user);
  }

  public User updateUser(Long id, User updatedUser) {
    return repository
        .findById(id)
        .map(
            user -> {
              user.setUserName(updatedUser.getUserName());
              user.setPassword(updatedUser.getPassword());
              user.setFirstName(updatedUser.getFirstName());
              user.setLastName(updatedUser.getLastName());
              user.setAge(updatedUser.getAge());
              user.setSex(updatedUser.getSex());
              user.setIsActive(updatedUser.getIsActive());
              user.setUpdatedAt(LocalDateTime.now());
              return repository.save(user);
            })
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public void deleteUser(Long id) {
    repository.deleteById(id);
  }
}
