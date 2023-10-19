package com.sumerge.authservice.security;

import com.sumerge.authservice.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Service
public class JwtService {

    @Value("${jwt.secretKeyEncoded}")
    private String secret_key; //= "zhQtXw29SYwaXRnICaoEBdhttcEzEtN90IvhHTbb02hDluGWI7jeSXyPRxXjqGs1Nb9tSagqRu3LYV4BKkNXVA==";
    @Value("${jwt.secretKey}")
    private  String simpleSecretKey;// = "helloFromTheOtherSide";
    @Value("${jwt.expiration}")
    private long expirationPeriod;

    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        Claims claims= extractAllClaims(token);

        return claimsResolver.apply(claims);
    }
    public String simpleExtractEmail(String jwtToken) {
        Claims claims= extractAllClaims(jwtToken);
        return claims.getSubject();
    }

    public Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationPeriod);
        Key key = Keys.hmacShaKeyFor(simpleSecretKey.getBytes());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(
            User userDetails,
            Map<String, Object> extraClaims
    ) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(now.getTime() + expirationPeriod);

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(User userDetails) {
        return createToken(userDetails, new HashMap<>());
    }


    public boolean validateToken(String token, User userDetails) {
        String email = extractEmail(token);
        return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public boolean isTokenValid(String token) {
        try {
            // Parse the token and verify its signature
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (SignatureException  | MalformedJwtException | ExpiredJwtException e) {
            // JWT signature is invalid, token is malformed, or it has expired
            return false;
        }
    }


}
