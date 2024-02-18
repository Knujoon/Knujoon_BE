package com.example.Knujoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//스케줄링 가능하게
public class KnujoonApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnujoonApplication.class, args);
	}

}
