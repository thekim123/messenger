package com.namusd.messenger.util;

import com.auth0.jwt.exceptions.JWTDecodeException;

public class JwtUtil {

    public static void isValidJwt(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return;
        }
        throw new JWTDecodeException("토큰 형식이 올바르지 않습니다.");
    }
}
