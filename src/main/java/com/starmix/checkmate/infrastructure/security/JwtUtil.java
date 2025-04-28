package com.starmix.checkmate.infrastructure.security;

import com.starmix.checkmate.global.exception.CustomException;
import com.starmix.checkmate.infrastructure.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String TOKEN_PREFIX = "Bearer ";

    private final JwtConfig jwtConfig;

    public String getToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new CustomException("현재 요청 정보를 가져올 수 없습니다.", HttpStatus.FORBIDDEN);
        }

        HttpServletRequest request = attributes.getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new CustomException("토큰 정보가 없습니다.", HttpStatus.FORBIDDEN);
        }

        String[] parts = authorizationHeader.trim().split("\\s+");
        if (parts.length != 2 || !parts[0].equalsIgnoreCase("Bearer")) {
            throw new CustomException("토큰 형식이 올바르지 않습니다.", HttpStatus.FORBIDDEN);
        }

        return parts[1];
    }

    public String generateToken(String email) {
        String jti = UUID.randomUUID().toString();
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiration()))
                .setId(jti)
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String extractEmail() {
        return extractEmail(getToken());
    }

    private String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new CustomException("JWT 인증 정보가 없습니다.", HttpStatus.FORBIDDEN);
        }
    }
}