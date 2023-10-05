package com.foody.mbti.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.foody.global.service.AmazonS3Service;
import com.foody.mbti.dto.request.MbtiRequest;
import com.foody.mbti.dto.response.MbtiImgResponse;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.security.service.CustomUserDetailService;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class MbtiControllerTest extends ControllerTest {

    private final String baseUrl = "/api/v1/mbti";

    @Test
    @DisplayName("먹BTI용_사진_반환한다.")
    void initializeMbtiTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        String[][] arr = new String[28][2];
        arr[0][0] = "음식 이름";
        arr[0][1] = "url";

        when(mbtiService.initailizeMbtiImage())
            .thenReturn(arr);

        mockMvc.perform(
            get(baseUrl + "/")
                .with(authentication(new TestingAuthenticationToken(loginInfo,null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("mbti/image",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("images[].[]").description("음식 이름과 URL의 배열")
                    )
                )
            );
    }

    @Test
    @DisplayName("회원_먹BTI_계산한다.")
    void createMbtiTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        List<Integer> arr = new ArrayList<>();
        for(int i=0;i<28;i++){
            arr.add(i%3);
        }

        MbtiRequest mbtiRequest = new MbtiRequest(arr);

        mockMvc.perform(
            post(baseUrl + "/create")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(mbtiRequest))
        ).andExpect(status().isNoContent())
            .andDo(
                document("mbti/create",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("results").description("결과값 배열 - 0->별로에요, 1->보통이에요, 2->좋아요")
                    )
                )
            );
    }

}
