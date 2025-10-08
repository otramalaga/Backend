package com.finalproject.Backend.config;

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
        
        config.addAllowedOriginPattern("http://localhost:5173");
        config.addAllowedOriginPattern("http://localhost:3000");
        config.addAllowedOriginPattern("https://otramalaga.com");
        config.addAllowedOriginPattern("https://www.otramalaga.com");
        
        config.addAllowedOriginPattern("https://*.onrender.com");
        config.addAllowedOriginPattern("https://*.netlify.app");
        
        config.addAllowedMethod("*");
        
        config.addAllowedHeader("*");
        
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}