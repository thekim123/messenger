package com.namusd.messenger.facade;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.namusd.messenger.config.security.JwtService;
import com.namusd.messenger.model.dto.JwtDto;
import com.namusd.messenger.model.entity.User;
import com.namusd.messenger.service.UserService;
import com.namusd.messenger.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class JwtFacade {

    private final JwtService jwtService;
    private final UserService userService;


    public JwtDto.Refresh refreshJwt(String refreshToken, String requestUrl) {
        JwtUtil.isValidJwt(refreshToken);
        DecodedJWT decodedJWT = jwtService.decodeRefreshToken(refreshToken);
        String username = decodedJWT.getSubject();
        User user = userService.findByUsername(username);

        String access = jwtService.generateAccessToken(user, requestUrl);
        String refresh = jwtService.generateRefreshToken(user, requestUrl);

        return JwtDto.Refresh.builder()
                .access(access)
                .refresh(refresh)
                .build();
    }


}
