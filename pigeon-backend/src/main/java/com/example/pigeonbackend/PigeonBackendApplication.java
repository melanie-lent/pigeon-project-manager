package com.example.pigeonbackend;

import com.example.pigeonbackend.controller.AuthController;
import com.example.pigeonbackend.controller.ProjectController;
import com.example.pigeonbackend.controller.TaskController;
import com.example.pigeonbackend.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PigeonBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(PigeonBackendApplication.class, args);
	}

	private UserController userController;
	private ProjectController projectController;
	private TaskController taskController;
	private AuthController authController;
	private DataConfig dataConfig;

}
