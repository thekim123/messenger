package com.namusd.messenger.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.namusd.messenger.config.security.auth.PrincipalDetails;
import com.namusd.messenger.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public String verifyJwtAndGetUsername(String token) {
        String removePrefix = token.replace(JwtProperties.TOKEN_PREFIX, "");
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(removePrefix)
                .getClaim("username").asString();

        if (username == null) {
            throw new TokenExpiredException("비정상적인 토큰입니다. 다시 로그인해주십시오.");
        }

        return username;
    }

    public DecodedJWT decodeRefreshToken(String refreshToken) {
        String refresh = refreshToken.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(refresh);
    }


    public String generateAccessToken(User loginUser, String requestUrl) {
        return JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15분
                .withClaim("id", loginUser.getId())
                .withClaim("username", loginUser.getUsername())
                .withClaim("roles", loginUser.getRoles().toString())
                .withIssuer(requestUrl)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

    public String generateRefreshToken(User loginUser, String requestUrl) {
        return JWT.create()
                .withSubject(loginUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000L)) // 2주
                .withIssuer(requestUrl)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }
}
