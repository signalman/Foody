package com.foody.recommend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.recipe.dto.response.RecipeListResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;

class RecommendControllerTest extends ControllerTest {

    private final String BASEURL = "/api/v1/recommend";

    @Test
    @DisplayName("재료 기반 추천 조회된다")
    void recommendByIngredientShouldReserved() throws Exception {

        List<RecipeListResponse> responseList = new ArrayList<>();
        RecipeListResponse mockResponse = new RecipeListResponse(123456L, "맛있는 김치찌개", "ssafy.com");
        responseList.add(mockResponse);

        when(recommendService.findRecommendItemByIngredients(any())).thenReturn(responseList);
        setAuthentication();

        mockMvc.perform(
            get(BASEURL + "/ingredients")
                .with(authentication(new TestingAuthenticationToken(new LoginInfo("lkm454545@gmail.com"), null)))
        ).andExpect(status().isOk())
            .andDo(
                document("/recommend/ingredients",
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("[]").description("추천 레시피 목록"),
                        fieldWithPath("[].id").description("추천 레시피 id"),
                        fieldWithPath("[].name").description("추천 레시피 제목"),
                        fieldWithPath("[].url").description("추천 레시피 사진 url")
                    ))
            );

    }

    @Test
    @DisplayName("하이브리드 추천 조회된다")
    void hybridRecommendShouldReserved() throws Exception {

        List<RecipeListResponse> responseList = new ArrayList<>();
        RecipeListResponse mockResponse = new RecipeListResponse(123456L, "맛있는 김치찌개", "ssafy.com");
        responseList.add(mockResponse);

        when(recommendService.findHybridItemByPreference(any())).thenReturn(responseList);
        setAuthentication();

        mockMvc.perform(
                   get(BASEURL + "/hybrid")
                       .with(authentication(new TestingAuthenticationToken(new LoginInfo("lkm454545@gmail.com"), null)))
               ).andExpect(status().isOk())
               .andDo(
                   document("/recommend/hybrid",
                       preprocessResponse(prettyPrint()),
                       responseFields(
                           fieldWithPath("[]").description("추천 레시피 목록"),
                           fieldWithPath("[].id").description("추천 레시피 id"),
                           fieldWithPath("[].name").description("추천 레시피 제목"),
                           fieldWithPath("[].url").description("추천 레시피 사진 url")
                       ))
               );

    }
}