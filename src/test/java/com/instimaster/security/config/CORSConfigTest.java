package com.instimaster.security.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CORSConfigTest {

    @InjectMocks
    private static CORSConfig corsConfig;

    private final String[] allowedOrigins = new String[]{"https://example.com", "https://test.com"};
    private final String[] allowedMethods =new String[]{"GET","POST","PUT","DELETE"};
    @Test
    void corsConfigurationSource() {
        CorsConfigurationSource corsConfigSource = corsConfig.corsConfigurationSource(allowedOrigins, allowedMethods);
        assertThat(corsConfigSource).isNotNull();

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/any-path");
        CorsConfiguration corsConfiguration = corsConfigSource.getCorsConfiguration(request);
        assertThat(corsConfiguration).isNotNull();

        assertThat(corsConfiguration.getAllowedOrigins()).contains("https://example.com");
        assertThat(corsConfiguration.getAllowedMethods()).contains("GET", "POST", "PUT", "DELETE");
        assertThat(corsConfiguration.getAllowedHeaders()).contains(HttpHeaders.CONTENT_TYPE, "X-CSRF-TOKEN");
    }
}