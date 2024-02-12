package com.instimaster;

import com.instimaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class InstiMasterApplication implements CommandLineRunner {

	private final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(InstiMasterApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		userService.createDefaultAdmin();
	}
}
