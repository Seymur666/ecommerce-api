package com.ecommerce.api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class TokenManager {
//    private final SecretKey secretKey;
//
//    @Value("${jwt.access.expiration}")
//    private int accessTokenValidatePeriod;
//
//    @Value("${jwt.refresh.expiration}")
//    private int refreshTokenValidatePeriod;
//
//    public TokenManager(@Value("${jwt.secret}") String jwtSecret) {
//        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
//        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
//    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private int accessTokenValidatePeriod;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenValidatePeriod;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public Map<String, String> generateToken(String userEmail, String roles) {
        Map<String, String> tokens = new HashMap<>();

        String accessToken = buildToken(userEmail, accessTokenValidatePeriod, roles);
        String refreshToken = buildToken(userEmail, refreshTokenValidatePeriod, roles);

        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    private String buildToken(String userEmail, int expirationTime, String roles) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

//    private String buildToken(String userEmail, int expirationTime, String roles) {
//        return Jwts.builder()
//                .setSubject(userEmail)
//                .claim("roles", roles)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(SignatureAlgorithm.HS256, jwtSecret)
//                .compact();
//    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    public Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
}
