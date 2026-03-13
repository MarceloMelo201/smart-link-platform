package com.mm.smart_link_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SmartLinkPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLinkPlatformApplication.class, args);
	}

}
