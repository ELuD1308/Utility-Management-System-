package com.utilityinternational.utility_backend.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
@Slf4j
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private int jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration-ms}")
    private int jwtRefreshExpirationMs;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String generateJwtToken(Authentication authentication) {

        UserDetails userPrincipal =
                (UserDetails) authentication.getPrincipal();

        return buildToken(
                userPrincipal.getUsername(),
                jwtExpirationMs
        );
    }

    public String generateTokenFromUsername(String username) {

        return buildToken(username, jwtExpirationMs);
    }

    public String generateRefreshToken(String username) {

        return buildToken(username, jwtRefreshExpirationMs);
    }

    private String buildToken(String subject, long expirationMs) {

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(key())
            .compact();
    }

    public String getUsernameFromJwtToken(String token) {

        return Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateJwtToken(String authToken) {

        try {

                Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken);

            return true;

        } catch (MalformedJwtException e) {

            log.error("Invalid JWT token: {}", e.getMessage());

        } catch (ExpiredJwtException e) {

            log.error("JWT token is expired: {}", e.getMessage());

        } catch (UnsupportedJwtException e) {

            log.error("JWT token is unsupported: {}", e.getMessage());

        } catch (IllegalArgumentException e) {

            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}