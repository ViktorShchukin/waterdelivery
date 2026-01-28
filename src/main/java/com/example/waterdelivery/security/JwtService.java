package com.example.waterdelivery.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    @Value("${wd.security.jwt.secret}")
    private String jwtSecret;

    @Value("${wd.security.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        String userLoing = authentication.getName();
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
                .subject(userLoing)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();


    }

    public Optional<String> getEmail(String token) {
        try {
            String email = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Optional.ofNullable(email);
        } catch (JwtException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
