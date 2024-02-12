package com.instimaster.security.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.instimaster.security.principal.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtToPrincipalMapper {
    public UserPrincipal convert(DecodedJWT jwt) {
        return UserPrincipal
                .builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .email(jwt.getClaim("e").asString())
                .authorities(getGrantedAuthoritiesFromClaim(jwt.getClaim("a")))
                .build();
    }

    private List<SimpleGrantedAuthority> getGrantedAuthoritiesFromClaim(Claim claim) {
        if(claim.isNull() || claim.isMissing()) return Arrays.asList(new SimpleGrantedAuthority[] {});
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
