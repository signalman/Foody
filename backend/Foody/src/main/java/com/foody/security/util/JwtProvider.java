package com.foody.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final Long accessTokenExpireTimeMs = 3600000L; // 1시간
    private final Long refreshTokenExpireTimeMs = 1209600000L; // 2주일

    public String createAccessToken(Long id, String email, String secretKey, Boolean isJoined) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        claims.put("email", email);
        claims.put("isJoined", isJoined);

        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessTokenExpireTimeMs);

        String accessToken = Jwts.builder()
                                 .setClaims(claims)
                                 .setIssuedAt(now)
                                 .setExpiration(accessTokenExpiration)
                                 .signWith(
                                     Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                                     SignatureAlgorithm.HS256)
                                 .compact();

        return accessToken;
    }

    public String createRefreshToken(String email, String secretKey) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date();
        Date refreshTokenExpiration = new Date(now.getTime() + refreshTokenExpireTimeMs);

        String refreshToken = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(refreshTokenExpiration)
            .signWith(
                Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact();

        return refreshToken;
    }
}
