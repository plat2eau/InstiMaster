package com.instimaster.security.principal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserPrincipalTest {

    UserPrincipal userPrincipal;

    @BeforeAll
    void setup() {
        userPrincipal = new UserPrincipal(1, "admin@admin", "password", List.of());
    }

    @Test
    public void getAuthorities() {
        assertEquals(List.of(), userPrincipal.getAuthorities());
    }

    @Test
    public void getPassword() {

        assertEquals("password", userPrincipal.getPassword());
    }

    @Test
    public void getUsername() {
        assertEquals("admin@admin", userPrincipal.getEmail());
    }

    @Test
    void isAccountNonExpired() {
        assertTrue(userPrincipal.isAccountNonExpired());
    }

    @Test
    void isAccountNonLocked() {
        assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    void isCredentialsNonExpired() {
        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    void isEnabled() {
        assertTrue(userPrincipal.isEnabled());
    }
}