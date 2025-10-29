package com.pknu.caloriepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class CaloriepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaloriepayApplication.class, args);
	}

}
