package com.starmix.checkmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CheckmateApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckmateApplication.class, args);
	}

}
