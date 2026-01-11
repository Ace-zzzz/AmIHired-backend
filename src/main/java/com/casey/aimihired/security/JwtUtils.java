package com.casey.aimihired.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    private String jwtSecretKey;

    @Value("${app.jwt.expiration-ms}")
    private Long jwtExpiration;

    // ALOGIRTHM USED
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // GENERATES TOKEN
    public String generateToken(String username) {
        Instant now = Instant.now();

        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(Date.from(now))
                   .setExpiration(Date.from(now.plusMillis(jwtExpiration)))
                   .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                   .compact();
    } 

    // GETS THE USER CLAIMS
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    // VALIDATES THE TOKEN
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);

            return true;
        }
        catch(JwtException | IllegalArgumentException error) {

            return false;
        }
    }
}
