package com.instimaster.security;

import com.instimaster.entity.User;
import com.instimaster.security.principal.UserPrincipal;
import com.instimaster.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailService service;

    private String email = "testEmail";
    private String password = "testPassword";
    private String role = "testRole";
    private long userId = 123L;

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setId(userId);
        when(userService.getUserByEmail(anyString())).thenReturn(Optional.of(user));

        UserPrincipal userResult = UserPrincipal.builder()
                .userId(userId)
                .email(email)
                .authorities(Arrays.asList(new SimpleGrantedAuthority(role)))
                .password(user.getPassword())
                .build();
        UserDetails userDetails = service.loadUserByUsername(email);
        assertThat(userDetails, samePropertyValuesAs(userResult));
    }
}