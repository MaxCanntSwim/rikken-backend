package com.MaxEradus.rikken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/player/**").permitAll()  // Allow access to /api/player
                        .anyRequest().authenticated()  // Require authentication for other endpoints
                )
                .csrf().disable();  // Disable CSRF for testing (enable in production)

        return http.build();
    }
}
