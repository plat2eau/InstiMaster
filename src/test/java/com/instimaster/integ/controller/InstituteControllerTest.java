package com.instimaster.integ.controller;

import com.google.gson.Gson;
import com.instimaster.InstiMasterApplication;
import com.instimaster.model.request.CreateInstituteRequest;
import com.instimaster.model.request.GetInstituteByIdRequest;
import com.instimaster.model.request.UpdateInstituteRequest;
import com.instimaster.model.request.auth.LoginRequest;
import com.instimaster.model.response.CreateInstituteResponse;
import com.instimaster.model.response.GetAllInstitutesResponse;
import com.instimaster.model.response.GetInstituteByIdResponse;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InstiMasterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InstituteControllerTest {

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

    private final String INSTITUTES_PATH = "/institutes";

    @Test
    public void happyTest() throws JSONException {

        // createInstitute Test
        String accessToken = getAccessToken(defaultAdminEmail, defaultAdminPassword);
        CreateInstituteRequest createInstituteRequest = CreateInstituteRequest.builder()
                .name("name")
                .head("head")
                .contact("+914123121")
                .location("location")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<CreateInstituteRequest> createInstituteRequestHttpEntity = new HttpEntity<>(createInstituteRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH),
                HttpMethod.POST, createInstituteRequestHttpEntity, String.class
        );
        CreateInstituteResponse createInstituteResponse = gson.fromJson(response.getBody(), CreateInstituteResponse.class);
        assertNotNull(createInstituteResponse.getId());

        // getInstituteById Test
        HttpEntity<GetInstituteByIdRequest> getInstituteByIdRequestHttpEntity = new HttpEntity<>(headers);
        response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH) + "/" + createInstituteResponse.getId(),
                HttpMethod.GET, getInstituteByIdRequestHttpEntity, String.class
        );
        GetInstituteByIdResponse getInstituteByIdResponse = gson.fromJson(response.getBody(), GetInstituteByIdResponse.class);
        assertEquals(getInstituteByIdResponse.getInstitute().getName(), "name");
        assertEquals(getInstituteByIdResponse.getInstitute().getHead(), "head");
        assertEquals(getInstituteByIdResponse.getInstitute().getContact(), "+914123121");
        assertEquals(getInstituteByIdResponse.getInstitute().getLocation(), "location");

        // Create one more institute
        CreateInstituteRequest createInstituteRequest2 = CreateInstituteRequest.builder()
                .name("nameII")
                .head("headII")
                .contact("+913213123")
                .location("location2")
                .build();

        createInstituteRequestHttpEntity = new HttpEntity<>(createInstituteRequest2, headers);
        restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH),
                HttpMethod.POST, createInstituteRequestHttpEntity, String.class
        );

        // getAllInstitutes Test
        HttpEntity<Object> getAllInstitutesHttpEntity = new HttpEntity<>(headers);
        response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH),
                HttpMethod.GET, getInstituteByIdRequestHttpEntity, String.class
        );
        GetAllInstitutesResponse getAllInstitutesResponse = gson.fromJson(response.getBody(), GetAllInstitutesResponse.class);
        assertEquals(getAllInstitutesResponse.getInstitutes().size(), 2);

        // updateInstitute Test
        UpdateInstituteRequest updateInstituteRequest = UpdateInstituteRequest.builder()
                .id(createInstituteResponse.getId())
                .name("updatedName")
                .build();
        HttpEntity<Object> updateInstituteHttpEntity = new HttpEntity<>(updateInstituteRequest, headers);
        response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH),
                HttpMethod.PUT, updateInstituteHttpEntity, String.class
        );
        response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH) + "/" + createInstituteResponse.getId(),
                HttpMethod.GET, getInstituteByIdRequestHttpEntity, String.class
        );
        getInstituteByIdResponse = gson.fromJson(response.getBody(), GetInstituteByIdResponse.class);
        assertEquals(getInstituteByIdResponse.getInstitute().getName(), "updatedName");

        // deleteInstitute Test
        HttpEntity<Object> deleteInstituteRequestHttpEntity = new HttpEntity<>(headers);
        response = restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH) + "/" + createInstituteResponse.getId(),
                HttpMethod.DELETE, deleteInstituteRequestHttpEntity, String.class
        );
        assertThrows(HttpClientErrorException.class, () -> restTemplate.exchange(
                createURLWithPort(INSTITUTES_PATH) + "/" + createInstituteResponse.getId(),
                HttpMethod.GET, getInstituteByIdRequestHttpEntity, String.class
        ));
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
