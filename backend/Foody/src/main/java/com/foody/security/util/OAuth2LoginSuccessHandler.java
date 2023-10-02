package com.foody.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.member.dto.response.TokenResponse;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
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
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

//    private String redirectUrl = "https://j9c106.p.ssafy.io/login";
    private String redirectUrl = "http://localhost:3000/login";

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    private final ObjectMapper objectMapper = new ObjectMapper(); // jSon문자열로의 변환을 위해

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = token.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");


//         회원 가입 여부 판별
        if (memberRepository.existsByEmail(email)) {
            // 로그인 진행
            log.info("=========={} login start ==========", email);
            TokenResponse tokenResponse = memberService.loginMember(response, authentication,
                oAuth2User);

            String accessToken = tokenResponse.accessToken();
            String refreshToken = tokenResponse.refreshToken();
            String user;

            if(memberRepository.checkInfoIsNUll(email) == null){
                user = "1";
            }
            else {
                user = "0";
            }

            String formattedRedirectUrl = String.format("%s?accessToken=%s&refreshToken=%s&user=%s",
                redirectUrl,
                URLEncoder.encode(accessToken, "UTF-8"),
                URLEncoder.encode(refreshToken, "UTF-8"),
                URLEncoder.encode(user, "UTF-8"));  // user 값을 인코딩하여 추가


//            response.sendRedirect(redirectUrl);
            log.info("{} redirectUrl",formattedRedirectUrl);
            response.sendRedirect(formattedRedirectUrl);

        } else {
            // 회원 가입 추가 정보 입력
            log.info("=========={} join start ==========", email);

            TokenResponse tokenResponse = memberService.signUpMember(response, authentication,
                oAuth2User);

            String accessToken = tokenResponse.accessToken();
            String refreshToken = tokenResponse.refreshToken();
            String user = "0";

            String formattedRedirectUrl = String.format("%s?accessToken=%s&refreshToken=%s&user=%s",
                redirectUrl,
                URLEncoder.encode(accessToken, "UTF-8"),
                URLEncoder.encode(refreshToken, "UTF-8"),
                URLEncoder.encode(user, "UTF-8"));

//            response.sendRedirect(redirectUrl);
            log.info("{} redirectUrl",formattedRedirectUrl);
            response.sendRedirect(formattedRedirectUrl);
        }

    }

}
