package com.khanhdd.fanout_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FanoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanoutServiceApplication.class, args);
	}

}
