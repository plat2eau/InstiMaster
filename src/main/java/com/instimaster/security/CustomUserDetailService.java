package com.instimaster.security;

import com.instimaster.entity.User;
import com.instimaster.security.principal.UserPrincipal;
import com.instimaster.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.getUserByEmail(email);
        if(!userOptional.isPresent()) throw new UsernameNotFoundException("User with email " + email +" not found");

        User user = userOptional.get();
        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .authorities(Arrays.asList(new SimpleGrantedAuthority(user.getRole())))
                .password(user.getPassword())
                .build();
    }
}
