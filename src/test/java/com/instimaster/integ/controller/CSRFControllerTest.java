package com.instimaster.integ.controller;

import com.instimaster.InstiMasterApplication;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@EnabledIfSystemProperty(named = "docker.tests.enabled", matches = "true")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InstiMasterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CSRFControllerTest {
}
