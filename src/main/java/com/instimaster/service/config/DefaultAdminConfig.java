package com.instimaster.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("users.default.admin")
public class DefaultAdminConfig {
    private String username;
    private String password;
}
