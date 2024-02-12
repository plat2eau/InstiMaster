package com.instimaster.security.jwt;

import com.instimaster.security.principal.UserPrincipalAuthToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalMapper jwtMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getTokenFromRequest(request)
                .map(jwtDecoder::decode)
                .map(jwtMapper::convert)
                .map(UserPrincipalAuthToken::new)
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
