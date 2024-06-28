package com.robbie.shoppingmall.util;

import com.robbie.shoppingmall.config.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

  @Autowired private SecurityConfig securityConfig;

  public String generateToken(Map<String, Object> claims, long expiration) {
    long expirationTime = System.currentTimeMillis() + expiration;

    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(new Date(expirationTime))
        .signWith(
            SignatureAlgorithm.HS256,
            Base64.getEncoder().encodeToString(securityConfig.getSecretKey().getBytes()))
        .compact();
  }
}
