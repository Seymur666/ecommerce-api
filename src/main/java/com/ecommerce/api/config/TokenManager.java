package com.ecommerce.api.config;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenManager {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access.expiration}")
    private int accessTokenValidatePeriod;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenValidatePeriod;

    public Map<String, String> generateToken(String userEmail, String roles) {
        Map<String, String> tokens = new HashMap<>();

        String accessToken = buildToken(userEmail, accessTokenValidatePeriod, roles);
        String refreshToken = buildToken(userEmail, refreshTokenValidatePeriod, roles);

        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    private String buildToken(String userEmail, int expirationTime, String roles) {
        return Jwts.builder()
                .setSubject(userEmail)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

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
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }
}
