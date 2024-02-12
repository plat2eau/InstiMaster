package com.instimaster.service;

import com.instimaster.model.request.auth.LoginRequest;
import com.instimaster.model.response.auth.LoginResponse;
import com.instimaster.security.jwt.JwtIssuer;
import com.instimaster.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {


    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<LoginResponse> attemptLogin(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtIssuer.issue(1L, principal.getEmail(), roles);
        return new ResponseEntity<>(LoginResponse.builder().accessToken(token).build(), HttpStatus.OK);
    }
}
