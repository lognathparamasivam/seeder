package com.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class SeederApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeederApplication.class, args);
	}

}
