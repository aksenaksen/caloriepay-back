package com.pknu.caloriepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CaloriepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaloriepayApplication.class, args);
	}

}
