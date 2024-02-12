package com.instimaster.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.instimaster.security.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtConfig properties;

    public DecodedJWT decode(String token) {
        return JWT
                .require(
                        Algorithm.HMAC256(properties.getSecretKey()))
                .build()
                .verify(token);
    }
}
