package com.instimaster.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.instimaster.security.config.JwtConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtIssuerTest {

    @InjectMocks
    JwtIssuer jwtIssuer;

    @Mock
    JwtConfig properties;

    @Test
    void issue() {
        when(properties.getSecretKey()).thenReturn("mySecret");
        List<String> roles = Arrays.asList(new String[]{"role"});
        String sign = JWT.create()
                .withSubject(String.valueOf(1))
                .withExpiresAt(Instant.now().plus(Duration.of(5, ChronoUnit.MINUTES)))
                .withClaim("e", "admin")
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256("mySecret"));
        assertEquals(sign, jwtIssuer.issue(1, "admin", roles));
    }
}