package com.ivsucic.shorting.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ivsucic"})
public class ShortingUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortingUrlApplication.class, args);
	}

}
