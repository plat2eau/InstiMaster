package com.instimaster.integ.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.instimaster.InstiMasterApplication;
import com.instimaster.exceptions.user.UserAlreadyExistsException;
import com.instimaster.model.request.auth.CreateUserRequest;
import com.instimaster.model.request.auth.LoginRequest;
import com.instimaster.model.response.auth.CreateUserResponse;
import com.instimaster.model.response.auth.LoginResponse;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InstiMasterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16.0");


    @LocalServerPort
    private int port;

    @Autowired
    RestTemplate restTemplate;

    @Value("${users.default.admin.username}")
    private String defaultAdminEmail;

    private final Gson gson = new Gson();

    private final String defaultAdminPassword = "admin123";

    private final String LOGIN_PATH = "/login";

    private final String USER_PATH = "/admin/user";

    @Test
    public void createUser() throws JSONException, JsonProcessingException {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();

        String accessToken = getAccessToken(defaultAdminEmail, defaultAdminPassword);

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .email("test")
                .password("pass")
                .role("ROLE_USER")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<CreateUserRequest> createUserRequestHttpEntity = new HttpEntity<>(createUserRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(USER_PATH),
                HttpMethod.POST, createUserRequestHttpEntity, String.class
        );
        CreateUserResponse createUserResponse = gson.fromJson(response.getBody(), CreateUserResponse.class);
        assertNotNull(createUserResponse.getId());
        try {
            restTemplate.exchange(
                    createURLWithPort(USER_PATH),
                    HttpMethod.POST, createUserRequestHttpEntity, String.class
            );
        } catch (RestClientException e) {
            assertTrue(e.getMessage().contains(UserAlreadyExistsException.class.getSimpleName()));
        }
    }

    private String getAccessToken(String email, String pass) throws JSONException {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(pass)
                .build();
        HttpEntity<LoginRequest> loginRequestEntity = new HttpEntity<>(loginRequest);

        //login as admin
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(LOGIN_PATH),
                HttpMethod.POST, loginRequestEntity, String.class
        );

        LoginResponse loginResponse = gson.fromJson(response.getBody(), LoginResponse.class);
        return loginResponse.getAccessToken();
    }

    private String createURLWithPort(String uri) {
        return "https://localhost:" + port + uri;
    }
}
