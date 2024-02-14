package com.instimaster.security.principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserPrincipalAuthTokenTest {

    @InjectMocks
    UserPrincipalAuthToken authToken;

    @Mock
    UserPrincipal principal;

    @Test
    void getPrincipal() {
        assertEquals(principal, authToken.getPrincipal());
    }
}