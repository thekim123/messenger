package com.namusd.messenger.config.security;

import com.namusd.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final CorsConfig corsConfig;
    private final JwtService jwtService;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new NamuDsl(userRepository, corsConfig, jwtService))
                .and()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/api/admin/**").hasRole("ADMIN")
                        .antMatchers("/api/board/**", "/swagger-ui.html").hasAnyRole("USER", "ADMIN")
                        .antMatchers("/api/bookmark/**").hasAnyRole("USER", "GUEST", "ADMIN")
                        .antMatchers("/api/user/join", "/chat/**", "/api/region/**", "/api/gati/**").permitAll()
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
