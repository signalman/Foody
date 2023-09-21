package com.foody.security.util;

import com.foody.global.exception.ErrorCode;
import com.foody.security.exception.SecurityException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final Long accessTokenExpireTimeMs = 3600000L; // 1시간
    private final Long refreshTokenExpireTimeMs = 1209600000L; // 2주일
    private final RedisTemplate<String, String> redisTemplate;

    public String getEmail(String token, String secretKey){
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("email", String.class);
    }

    public boolean isExpired(String token, String secretkey) {
        return Jwts.parserBuilder()
            .setSigningKey(secretkey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration()
            .before(new Date());
    }

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

        // refreshToken은 생성시 reids에 저장
        redisTemplate.opsForValue().set(email,refreshToken,refreshTokenExpireTimeMs, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    public String recreateAccessToken(String refreshToken, String secretKey) {
        try{
            // 전달 받은 RefreshToken에서 정보 추출
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

            String email = claims.get("email", String.class);
            Long id = claims.get("id",Long.class);
            Boolean isJoined = claims.get("isJoined", Boolean.class);

            // Redis에 존재하는지 확인
            String redisRefreshToken = redisTemplate.opsForValue().get(email);

            // 유효하지 않은 경우
            if(redisRefreshToken == null || !redisRefreshToken.equals(redisRefreshToken)) {
                // 에러 던지기
                throw new SecurityException(ErrorCode.INVALID_REFRESH_TOKEN);
            }
            // 유효한 경우
            Date now = new Date();
            Date accessTokenExpiration = new Date(now.getTime() + accessTokenExpireTimeMs);

            Claims newClaims = Jwts.claims();
            newClaims.put("id",id);
            newClaims.put("email", email);
            newClaims.put("isJoined", isJoined);

            String accessToken = Jwts.builder()
                                     .setClaims(claims)
                                     .setIssuedAt(now)
                                     .setExpiration(accessTokenExpiration)
                                     .signWith(
                                         Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)),
                                         SignatureAlgorithm.HS256)
                                     .compact();

            return accessToken;

        } catch (Exception e) {
            // RefreshToken 검증 실패
            // 에러 던지기 -> 새로운 AccessToken 만들기
            throw new SecurityException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

}
