package com.example.add_spring_boot.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSession httpSession) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)                  // Approve all requests that match to this
                .authorizeHttpRequests(req -> req.requestMatchers("api/v1/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider()
                .addFilterBefore();
        return http.build();
    }

    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dap =
                new DaoAuthenticationProvider();
        dap.setUserDetailsService();
    }
}
