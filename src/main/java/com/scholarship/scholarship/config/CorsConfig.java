package com.scholarship.scholarship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Use direct URLs instead of placeholders
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("https://accounts.google.com");

        // Allow all methods
        config.addAllowedMethod("*");
        // Allow all headers
        config.addAllowedHeader("*");

        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}