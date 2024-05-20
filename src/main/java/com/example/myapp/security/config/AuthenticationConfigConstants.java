package com.example.myapp.security.config;

public class AuthenticationConfigConstants {
    public static final String SECRET = "Java_to_Dev_Secret";
    public static final long EXPIRATION_TIME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/register";
}