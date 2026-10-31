package com.portmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.portmanagement.security.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(csrf->csrf.disable())
			.authorizeHttpRequests(auth->auth
					.requestMatchers("/api/auth/**",
							"/swagger-ui/**",
			                "/swagger-ui.html",
			                "/v3/api-docs/**"
							).permitAll()
					
					.requestMatchers("/api/users/me").authenticated()
					
					.requestMatchers("/api/users/**")
	                .hasRole("ADMIN")
	                
	                .requestMatchers(HttpMethod.GET, "/api/ports/**")
	                .hasAnyRole("ADMIN", "PORT_OPERATOR", "SHIPPING_COMPANY")
	                
	                .requestMatchers("/api/ports/**")
	                .hasRole("ADMIN")
	                
	                .requestMatchers(HttpMethod.GET, "/api/vessels/**")
	                .hasAnyRole("ADMIN", "PORT_OPERATOR", "SHIPPING_COMPANY")
	                
	                .requestMatchers("/api/vessels/**")
	                .hasRole("ADMIN")
	                
					.anyRequest().authenticated()
					)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);   //run my jwtAuthentivationFilter before spring security's UsernamePasswordAuthenticationFilter.
		return httpSecurity.build();
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
