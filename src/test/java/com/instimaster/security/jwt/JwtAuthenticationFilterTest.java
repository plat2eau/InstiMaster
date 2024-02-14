package com.instimaster.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.instimaster.security.config.JwtConfig;
import com.instimaster.security.principal.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    JwtDecoder jwtDecoder;

    @Mock
    DecodedJWT decodedJWT;

    @Mock
    UserPrincipal userPrincipal;

    @Mock
    JwtToPrincipalMapper jwtMapper;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @Test
    void doFilterInternal() throws ServletException, IOException {
        when(request.getHeader(anyString())).thenReturn("Bearer testToken");
        when(jwtDecoder.decode(anyString())).thenReturn(decodedJWT);
        when(jwtMapper.convert(any())).thenReturn(userPrincipal);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(jwtDecoder, times(1)).decode(any());
        verify(jwtMapper, times(1)).convert(any());
    }
}