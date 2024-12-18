package com.namusd.messenger.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class JwtService {

    public static String verifyJwtAndGetUsername(String token) {
        String removePrefix = token.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(removePrefix)
                .getClaim("username").asString();

        if (username == null) {
            throw new TokenExpiredException("비정상적인 토큰입니다. 다시 로그인해주십시오.");
        }

        return username;
    }
}
