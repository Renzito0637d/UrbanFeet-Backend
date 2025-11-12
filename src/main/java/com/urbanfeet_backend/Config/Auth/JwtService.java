package com.urbanfeet_backend.Config.Auth;

import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    @Value("${security.jwt.secret:}")
    private String secret; // clave secreta para firmar los tokens

    @Value("${security.jwt.exp-seconds:3600}")
    private long accessExp;

    @Value("${security.jwt.refresh-exp-seconds:604800}")
    private long refreshExp;

    private SecretKey key() {
        try {
            byte[] b64 = io.jsonwebtoken.io.Decoders.BASE64.decode(secret);
            return io.jsonwebtoken.security.Keys.hmacShaKeyFor(b64);
        } catch (IllegalArgumentException e) {
            return io.jsonwebtoken.security.Keys
                    .hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }
    }

    private String buildToken(Map<String, Object> claims, String subject, long seconds) {
        var now = new java.util.Date();
        var exp = new java.util.Date(now.getTime() + seconds * 1000);
        return io.jsonwebtoken.Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(exp)
                .signWith(key())
                .compact();
    }

    public String generateAccessToken(org.springframework.security.core.userdetails.UserDetails u) {
        return buildToken(Map.of("typ", "access"), u.getUsername(), accessExp);
    }

    public String generateRefreshToken(org.springframework.security.core.userdetails.UserDetails u) {
        return buildToken(Map.of("typ", "refresh"), u.getUsername(), refreshExp);
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails user) {
        String username = getUsername(token);
        return username != null && username.equals(user.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new java.util.Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key()).build()
                .parseSignedClaims(token).getPayload();
    }

    public String getType(String token) {
        Object t = getClaims(token).get("typ");
        return t == null ? null : t.toString();
    }

}