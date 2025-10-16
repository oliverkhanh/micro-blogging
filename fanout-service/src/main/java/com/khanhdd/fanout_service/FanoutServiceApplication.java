package com.khanhdd.fanout_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
		scanBasePackages = {
				"com.khanhdd.fanout_service",
				"com.khanhdd.common_service"
		}
)
@EnableFeignClients(basePackages = "com.khanhdd.fanout_service")
public class FanoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FanoutServiceApplication.class, args);
	}

}
