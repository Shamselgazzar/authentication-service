package com.sumerge.authservice.security;

import com.sumerge.authservice.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtGeneratorService {

    @Value("${jwt.secretKeyEncoded}")
    private String secret_key;

    @Value("${jwt.expiration.day}")
    private long expirationPeriod;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    public String createToken(String email) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(User user) {
        return createToken(user.getEmail());
    }






}
