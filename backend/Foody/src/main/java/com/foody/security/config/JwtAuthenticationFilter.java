package com.foody.security.config;

import com.foody.global.entity.UserInfo;
import com.foody.security.service.CustomUserDetailService;
import com.foody.security.util.JwtProvider;
import com.foody.security.util.LoginInfo;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService customUserDetailService;
    private final JwtProvider jwtProvider;

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("authorization : '{}'", authorization);

        if(authorization == null || !(authorization.startsWith("Bearer ") || authorization.startsWith("Refresh "))) {
            // 토큰 없는 경우 Filter 타지 않도록 함
            log.debug("Authorization is NULL");
            filterChain.doFilter(request, response);
            return;
        }

        // accessToken 유효한 경우 SecurityContextHolder에 등록
        if(authorization.startsWith("Bearer")) {
            String accesstoken;
            try {
                accesstoken = authorization.split(" ")[1];
            } catch (Exception e) {
                log.debug("토큰을 분해할 수 없습니다.");
                filterChain.doFilter(request,response);
                return;
            }
            // token 정보

            // 토큰이 유효한지
            if(jwtProvider.isExpired(accesstoken, secretKey)) {
                log.error("acc Token expired");
                filterChain.doFilter(request,response);
                return;
            }

            // 이메일 추출
            String email = jwtProvider.getEmail(accesstoken, secretKey);

            UserInfo userDetails = (UserInfo) customUserDetailService.loadUserByUsername(email);
            LoginInfo loginInfo = new LoginInfo(email);

            // LoginInfo에 등록
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginInfo, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                                 .setAuthentication(usernamePasswordAuthenticationToken);

        }
        else if(authorization.startsWith("Refresh ")) {
            /// Refresh Token 처리
            String refreshToken = authorization.substring(8); // "Refresh " prefix 제거
            try {
                String newAccessToken = jwtProvider.recreateAccessToken(refreshToken, secretKey);
                response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
            } catch (Exception e) {
                // Refresh Token 검증 실패 또는 새로운 Access Token 발급 실패
                log.error("Failed to refresh access token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}
