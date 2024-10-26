package com.rusyn.test_twitter.config


import com.rusyn.test_twitter.security.SessionSecurityFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig {

    private final SessionSecurityFilter securityFilter;

    SecurityConfig(SessionSecurityFilter securityFilter) {
        this.securityFilter = securityFilter
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(configurer -> configurer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                        .anyRequest().authenticated())
                .addFilterAt(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
