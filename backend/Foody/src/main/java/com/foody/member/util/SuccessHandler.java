package com.foody.member.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.member.dto.response.TokenResponse;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final ObjectMapper objectMapper = new ObjectMapper(); // jSon문자열로의 변환을 위해

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + ": " + value);
        }

        // 회원 가입 여부 판별
        if (memberRepository.existsByEmail(email)) { // 이미 존재, 로그인 진행
            log.info("{} need ======login======", email);
            TokenResponse tokenResponse = memberService.loginMember(response, authentication, oAuth2User);
            // 응답 보내기
            String jsonTokenResponse = objectMapper.writeValueAsString(tokenResponse);

            System.out.println("asdfasdfasdfasddf");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonTokenResponse);
            out.flush();
            log.info("done?");

        } else { // 나머지 회원 가입진행(DB에 저장) 이후 추가 정보 받아야함
            log.info("{} need ======join======", email);
            TokenResponse tokenResponse = memberService.signUpMember(response, authentication, oAuth2User);
            // 응답 보내기
            String jsonTokenResponse = objectMapper.writeValueAsString(tokenResponse);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonTokenResponse);
            out.flush();
        }
    }
}
