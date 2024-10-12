package com.example.add_spring_boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${secure.username}")
    private String userName;
    @Value("${secure.password}")
    private String password;
    @Value("${secure.role}")
    private String role;

    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .anyRequest().authenticated() // Authenticating all requests
                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager manager() {
        UserDetails principleUser = User.withDefaultPasswordEncoder() // Principle User
                .username(userName)
                .password(password)
                .roles(role) // Define the User Role
                .build();
        return new InMemoryUserDetailsManager(principleUser);
    }
}
