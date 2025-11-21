package com.example.spring_introduce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 1. Rejestracja - dostępna dla każdego
                        .requestMatchers(HttpMethod.POST, "/v1/users").permitAll()

                        // 2. Wyszukiwanie po emailu - dostępne dla każdego (wg Twojego ostatniego kodu)
                        .requestMatchers(HttpMethod.GET, "/v1/users/email").permitAll()

                        // 3. Aktualizacja danych i zmiana roli użytkownika - TYLKO DLA ADMINA
                        .requestMatchers(HttpMethod.PUT, "/v1/users/*", "/v1/users/*/role").hasRole("ADMIN")

                        // 4. Lista użytkowników - TYLKO DLA ADMINA
                        .requestMatchers(HttpMethod.GET, "/v1/users").hasRole("ADMIN")

                        // 5. Reszta wymaga zalogowania
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                // ZMIANA: Usuwamy SessionCreationPolicy.STATELESS lub zmieniamy na IF_REQUIRED
                // Dzięki temu Spring utworzy sesję (ciasteczko) po pierwszym logowaniu
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}