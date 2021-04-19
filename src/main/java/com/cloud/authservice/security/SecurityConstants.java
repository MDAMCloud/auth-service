package com.cloud.authservice.security;

public class SecurityConstants {
  public static final String SECRET = "URLSHORTENER";
  public static final long EXPIRATION_TIME = 900_000; // 15 DAKİKA
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/api/login";
}
