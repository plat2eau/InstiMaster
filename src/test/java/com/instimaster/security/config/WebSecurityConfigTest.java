package com.instimaster.security.config;

import com.instimaster.security.CustomUserDetailService;
import com.instimaster.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSecurityConfigTest {

    @InjectMocks
    WebSecurityConfig webSecurityConfig;

    @Mock
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Mock
    CustomUserDetailService customUserDetailService;
    @Mock
    AuthenticationManagerBuilder builder;
    @Mock
    private DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsService> daoAuthenticationConfigurer;

    @Mock
    HttpSecurity httpSecurity;

    @Captor
    ArgumentCaptor<PasswordEncoder> passwordEncoderArgumentCaptor;

    @Test
    void securityFilterChain() throws Exception {
        when(httpSecurity.addFilterBefore(any(), any())).thenReturn(httpSecurity);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.securityMatcher(anyString())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.formLogin(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        webSecurityConfig.securityFilterChain(httpSecurity);
        verify(httpSecurity, times(1)).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        verify(httpSecurity, times(1)).securityMatcher("/**");
        verify(httpSecurity, times(1)).sessionManagement(any());
        verify(httpSecurity, times(1)).formLogin(any());
        verify(httpSecurity, times(1)).authorizeHttpRequests(any());
    }

    @Test
    void passwordEncoder() {
        assertInstanceOf(BCryptPasswordEncoder.class, webSecurityConfig.passwordEncoder());
    }

    @Test
    void authenticationManager() throws Exception {
        when(httpSecurity.getSharedObject(any())).thenReturn(builder);
        when(builder.userDetailsService(any())).thenReturn(daoAuthenticationConfigurer);
        webSecurityConfig.authenticationManager(httpSecurity);
        verify(httpSecurity, times(1)).getSharedObject(AuthenticationManagerBuilder.class);
        verify(builder, times(1)).userDetailsService(customUserDetailService);
        verify(daoAuthenticationConfigurer, times(1)).passwordEncoder(any());
        verify(daoAuthenticationConfigurer).passwordEncoder(passwordEncoderArgumentCaptor.capture());
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoderArgumentCaptor.getValue());
    }
}