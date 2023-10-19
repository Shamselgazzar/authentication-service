package com.sumerge.authservice.security;

import com.sumerge.authservice.entity.User;

import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.security.Key;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class JwtGeneratorServiceTest {


    private JwtGeneratorService jwtGeneratorService;

    private String secret_key = "zhQtXw29SYwaXRnICaoEBdhttcEzEtN90IvhHTbb02hDluGWI7jeSXyPRxXjqGs1Nb9tSagqRu3LYV4BKkNXVA==";
    @BeforeEach
    public void setUp() {
        jwtGeneratorService = new JwtGeneratorService();
        ReflectionTestUtils.setField(jwtGeneratorService, "expirationPeriod", 3600000L);
        ReflectionTestUtils.setField(jwtGeneratorService,"secret_key", secret_key);
    }
    @Test
    public void testCreateToken() {
        User user = new User();
        user.setPassword("123");
        user.setEmail("testuser@example.com");

        String token = jwtGeneratorService.createToken(user);

       Claims claims = Jwts.parserBuilder()
                .setSigningKey(getTestSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(user.getEmail(), claims.getSubject());
    }

    private Key getTestSigningKey() {
        String encodedKey = secret_key;
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

