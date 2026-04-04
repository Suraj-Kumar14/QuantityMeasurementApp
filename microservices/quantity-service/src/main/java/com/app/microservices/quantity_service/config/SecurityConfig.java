package com.app.microservices.quantity_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable()) // - THIS IS KEY!
			.authorizeHttpRequests(authz -> authz
					.anyRequest().permitAll());
		// For H2 Console
		http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
		return http.build();
	}
}