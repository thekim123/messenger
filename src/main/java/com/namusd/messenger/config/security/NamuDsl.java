package com.namusd.messenger.config.security;

import com.namusd.messenger.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class NamuDsl extends AbstractHttpConfigurer<NamuDsl, HttpSecurity> {
    private final UserRepository userRepository;
    private final CorsConfig corsConfig;

    public NamuDsl(UserRepository userRepository, CorsConfig corsConfig) {
        this.userRepository = userRepository;
        this.corsConfig = corsConfig;
    }

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(corsConfig.corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
    }

}
