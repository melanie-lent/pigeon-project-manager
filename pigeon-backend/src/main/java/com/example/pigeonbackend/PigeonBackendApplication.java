package com.example.pigeonbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
@RestController
public class PigeonBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigeonBackendApplication.class, args);
	}
	@GetMapping("/root")
	public String apiRoot() {
		return "hello world";
	}
}
