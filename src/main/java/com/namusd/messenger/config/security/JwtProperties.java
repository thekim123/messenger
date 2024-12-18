package com.namusd.messenger.config.security;


/**
 * @apiNote interface -> class 로 변경
 * @since 2024.01.05
 */
public class JwtProperties {
    public static final String SECRET = "your-secure-secret-key-here-should-be-at-least-256-bits";
    public static final Long EXPIRATION_TIME = 15 * 60 * 1000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
