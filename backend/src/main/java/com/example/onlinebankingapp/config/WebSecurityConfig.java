package com.example.onlinebankingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf( csrf -> csrf.disable() )
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/**").permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

}
