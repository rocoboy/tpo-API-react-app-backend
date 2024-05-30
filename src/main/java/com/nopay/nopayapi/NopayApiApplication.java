package com.nopay.nopayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.nopay")
public class NopayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NopayApiApplication.class, args);
	}

}
