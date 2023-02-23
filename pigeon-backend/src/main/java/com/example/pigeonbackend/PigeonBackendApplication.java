package com.example.pigeonbackend;

import com.example.pigeonbackend.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PigeonBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigeonBackendApplication.class, args);
	}

	private UserController userController;
	private DataConfig dataConfig;
}
