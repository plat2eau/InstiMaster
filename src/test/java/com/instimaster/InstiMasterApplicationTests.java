package com.instimaster;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest
class InstiMasterApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl="http://localhost";

	private static TestRestTemplate template;


	@BeforeAll
	public static void init() {
		template = new TestRestTemplate();
	}

	@BeforeEach
	public void setUp() {
		baseUrl = baseUrl.concat(":").concat(port+"");
	}


}
