package com.XAUS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class XausApplication {

	public static void main(String[] args) {
		SpringApplication.run(XausApplication.class, args);
	}

}
