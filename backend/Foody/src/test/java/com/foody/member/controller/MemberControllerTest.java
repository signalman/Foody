package com.foody.member.controller;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.foody.global.service.AmazonS3Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.member.dto.request.CheckNicknameRequest;
import com.foody.member.dto.request.MemberInfoModifyRequest;
import com.foody.member.dto.request.MemberJoinRequest;
import com.foody.member.dto.request.RefreshTokenRequest;
import com.foody.member.dto.response.RefreshTokenResponse;
import com.foody.member.dto.response.TokenResponse;
import com.foody.security.service.CustomUserDetailService;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class MemberControllerTest extends ControllerTest {


    private final String baseUrl = "/api/v1/member";

    @Test
    @DisplayName("회원_추가정보_입력한다.")
    void joinMemberTest() throws  Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        MemberJoinRequest memberJoinRequest = new MemberJoinRequest("lkm454545@gmail.com","코카콜라",187.46,70,1,26,1);

        mockMvc.perform(
            post(baseUrl + "/join")
                .with(authentication(new TestingAuthenticationToken(loginInfo,null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(memberJoinRequest))
        ).andExpect(status().isNoContent())
        .andDo(
            document("/member/join",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").description("이메일"),
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("height").description("키"),
                    fieldWithPath("weight").description("몸무게"),
                    fieldWithPath("gender").description("성별(남자-0, 여자-1)"),
                    fieldWithPath("age").description("나이"),
                    fieldWithPath("activityLevel").description("활동량(1(적다),2(보통),3(많다))")
                )
            )
        );

    }

    @Test
    @DisplayName("닉네임_중복검사_한다")
    void checkNickname() throws Exception {
        CheckNicknameRequest checkNicknameRequest = new CheckNicknameRequest("코카롤라");

        mockMvc.perform(
            get(baseUrl + "/check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(checkNicknameRequest))
        ).andExpect(status().isNoContent())
            .andDo(document("/member/check",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("nickname").description("닉네임")
                )
            ));
    }

    @Test
    @DisplayName("회원_정보_수정한다.")
    void modifyMemberInfoTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        MemberInfoModifyRequest memberInfoModifyRequest = new MemberInfoModifyRequest("닉네임",123.45,67.89,0,27,2);

        mockMvc.perform(
            put(baseUrl +  "/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(memberInfoModifyRequest))
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
        ).andExpect(status().isNoContent())
            .andDo(document("/member/info",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("nickname").description("닉네임"),
                    fieldWithPath("height").description("키"),
                    fieldWithPath("weight").description("몸무게"),
                    fieldWithPath("gender").description("성별"),
                    fieldWithPath("age").description("나이"),
                    fieldWithPath("activityLevel").description("활동량")
                )
                )
            );
    }

    @Test
    @DisplayName("회원_로그아웃_한다.")
    void logoutTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        doNothing().when(memberService).logout(loginInfo);

        mockMvc.perform(
            get(baseUrl + "/logout")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
               ).andExpect(status().isNoContent())
               .andDo(
                   document("/member/logout",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint())
                   ));
    }

    @Test
    @DisplayName("accessToken_갱신한다.")
    void refreshTokenTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm@naver.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        when(memberService.refreshToken(any())).thenReturn(new RefreshTokenResponse("new accessToken"));

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("refreshToken");

        mockMvc.perform(
            post(baseUrl + "/refresh")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(refreshTokenRequest))

        ).andExpect(status().isOk())
            .andDo(
                document("/member/refresh",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("저장된 refreshToken")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("새로 발급된 accessToken")
                    )
                ));
    }


}
