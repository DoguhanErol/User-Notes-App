package com.example.UserNotesApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
            auth.requestMatchers("/api/v1/**").permitAll()  // /public/** yolundaki isteklere izin verir
                    .anyRequest().authenticated();  // Diğer tüm istekler kimlik doğrulama gerektirir
        }).httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
