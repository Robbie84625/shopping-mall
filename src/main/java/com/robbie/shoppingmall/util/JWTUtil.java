package com.robbie.shoppingmall.util;

import com.robbie.shoppingmall.config.SecurityConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    @Autowired
    private SecurityConfig securityConfig;


    public  String generateToken(Map<String,Object> claims,long expiration){
        long expirationTime = System.currentTimeMillis() + expiration;

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.ES512,securityConfig.getSecretKey())
                .compact();
    }

}
