package com.hanium.diARy.auth;

public interface JwtProperties {
    String SECRET = "diary1212";
    int EXPIRATION_TIME = 60000*10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
