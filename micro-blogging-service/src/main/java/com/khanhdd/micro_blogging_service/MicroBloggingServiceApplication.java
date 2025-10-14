package com.khanhdd.micro_blogging_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class MicroBloggingServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(MicroBloggingServiceApplication.class, args);
  }
}
