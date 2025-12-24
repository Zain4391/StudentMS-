package com.StudentMS.StudentMS.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Public endpoints (authentication)
                .requestMatchers("/api/auth/**").permitAll()

                // Student endpoints
                .requestMatchers(HttpMethod.POST, "/api/students").permitAll() // Allow registration
                .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/students/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasRole("ADMIN")

                // Teacher endpoints
                .requestMatchers(HttpMethod.POST, "/api/teachers").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/teachers/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/teachers/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/teachers/**").hasRole("ADMIN")

                // Course endpoints
                .requestMatchers(HttpMethod.POST, "/api/courses").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.GET, "/api/courses/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/courses/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.DELETE, "/api/courses/**").hasAnyRole("ADMIN", "TEACHER")

                // Enrollment endpoints
                .requestMatchers(HttpMethod.POST, "/api/enrollments").hasRole("STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/enrollments/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/enrollments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/enrollments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/enrollments/*/grade").hasRole("TEACHER")

                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
