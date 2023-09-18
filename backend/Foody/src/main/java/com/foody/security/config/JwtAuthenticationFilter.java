package com.foody.security.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("authorization : '{}'", authorization);

        if(authorization == null || !(authorization.startsWith("Bearer ") || authorization.startsWith("Refresh "))) {
            // 토큰 없는 경우 Filter 타지 않도록 함
            log.debug("Authorization is NULL");
            filterChain.doFilter(request, response);
        }

        // 나머지 경우
    }
}
