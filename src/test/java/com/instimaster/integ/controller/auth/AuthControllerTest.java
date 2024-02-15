package com.instimaster.integ.controller.auth;

import com.instimaster.InstiMasterApplication;
import com.instimaster.controller.AuthController;
import com.instimaster.model.request.auth.LoginRequest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InstiMasterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.0");

    @LocalServerPort
    private int port;

    @Autowired
    private AuthController authController;

    @Autowired
    RestTemplate restTemplate;

    @Value("${users.default.admin.username}")
    private String defaultAdminEmail;

    private final String defaultAdminPassword = "admin123";

    private final String LOGIN_PATH = "/login";

    @Test
    public void successfulLogin() throws JSONException {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        LoginRequest loginRequest = LoginRequest.builder()
                .email(defaultAdminEmail)
                .password(defaultAdminPassword)
                .build();

        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(LOGIN_PATH),
                HttpMethod.POST, loginRequestEntity, String.class
        );
        assertNotNull(Objects.requireNonNull(response.getBody()));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void unSuccessfulLogin() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();

        LoginRequest loginRequest = LoginRequest.builder()
                .email("randomUserName")
                .password("randomPassword")
                .build();

        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest);

        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(
                    createURLWithPort(LOGIN_PATH),
                    HttpMethod.POST, loginRequestEntity, String.class
            );
        } catch (RestClientException e) {
            assertTrue(e.getMessage().contains(BadCredentialsException.class.getSimpleName()));
        }
    }

    private String createURLWithPort(String uri) {
        return "https://localhost:" + port + uri;
    }
}
