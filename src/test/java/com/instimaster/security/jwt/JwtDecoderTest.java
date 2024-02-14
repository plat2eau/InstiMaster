package com.instimaster.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.instimaster.security.config.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtDecoderTest {

    @Mock
    JwtConfig properties;

    @InjectMocks
    JwtDecoder jwtDecoder;

    @Test
    void decode() {
        String TEST_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.8wL_WHr6GuJgsxhbJ5WIZwqpEbNWvL6_fCk0RG02TTQ";
        when(properties.getSecretKey()).thenReturn("mySecret");
        DecodedJWT decode = jwtDecoder.decode(TEST_JWT);
        assertEquals("eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ", decode.getPayload());
        assertEquals("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", decode.getHeader());
        assertEquals("8wL_WHr6GuJgsxhbJ5WIZwqpEbNWvL6_fCk0RG02TTQ", decode.getSignature());
    }

}