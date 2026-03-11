package com.igitan.springboothome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringboothomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringboothomeApplication.class, args);
	}

}
