package com.namusd.messenger.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.namusd.messenger.config.security.JwtProperties;
import com.namusd.messenger.config.security.JwtService;
import com.namusd.messenger.facade.JwtFacade;
import com.namusd.messenger.model.dto.JwtDto;
import com.namusd.messenger.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtFacade jwtFacade;

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> refreshToken(@RequestBody JwtDto.RefreshRequest refreshToken, HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        JwtDto.Refresh tokens = jwtFacade.refreshJwt(refreshToken.getSendRefreshToken(), requestUrl);
        return ResponseEntity.ok(tokens);
    }

}
