package com.namusd.messenger.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.namusd.messenger.config.security.auth.PrincipalDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        ObjectMapper om = new ObjectMapper();
        LoginRequestDto dto;
        try {
            dto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (IOException e) {
            log.error("Failed to parse login request", e);
            throw new UsernameNotFoundException("Failed to parse login request", e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 15분
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        String refresh = JWT.create()
                .withSubject(principalDetails.getUser().getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000L)) // 2주
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access", jwtToken);
        tokens.put("refresh", refresh);
        response.setContentType(APPLICATION_JSON_VALUE);

        //response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
        new ObjectMapper().writeValue(response.getOutputStream(), ResponseEntity.status(HttpStatus.OK).body(tokens));
    }


    @Getter
    @AllArgsConstructor
    private class LoginRequestDto {
        private String username;
        private String password;
    }


}
