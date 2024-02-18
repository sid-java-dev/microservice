package com.microservice.comment3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Comment3Application {

	public static void main(String[] args) {
		SpringApplication.run(Comment3Application.class, args);
	}

}
