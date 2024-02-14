package com.instimaster.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.instimaster.security.principal.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtToPrincipalMapperTest {

    @Mock
    DecodedJWT decode;

    @Mock
    Claim claim;

    @InjectMocks
    JwtToPrincipalMapper jwtToPrincipalMapper;

    @Test
    void convert() {
        when(decode.getSubject()).thenReturn("1");
        when(decode.getClaim("e")).thenReturn(claim);
        when(claim.asString()).thenReturn("admin@admin");
        when(decode.getClaim("a")).thenReturn(claim);
        when(claim.isNull()).thenReturn(false);
        when(claim.isMissing()).thenReturn(false);
        when(claim.asList(any())).thenReturn(List.of("ROLE_ADMIN"));
        UserPrincipal convert = jwtToPrincipalMapper.convert(decode);
        assertTrue(convert.getAuthorities().contains("ROLE_ADMIN"));
        assertEquals(convert.getEmail(), "admin@admin");
        assertEquals(convert.getUserId(), 1);
    }
}