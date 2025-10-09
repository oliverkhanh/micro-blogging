package com.khanhdd.common_service.exception;

import com.khanhdd.common_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {

  // Xử lý lỗi chung (Exception)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("Internal server error: " + ex.getMessage()));
  }

  // Xử lý lỗi bad request
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Object>> handleBadRequest(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ex.getMessage()));
  }

  // Xử lý lỗi validation (nếu dùng @Valid)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
    String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(errorMessage));
  }
}
