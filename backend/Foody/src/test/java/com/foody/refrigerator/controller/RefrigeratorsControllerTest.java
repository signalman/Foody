package com.foody.refrigerator.controller;

import com.foody.refrigerators.dto.request.CustomIngredientRequest;
import com.foody.refrigerators.dto.request.InsertIngredientRequest;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefrigeratorsControllerTest extends ControllerTest {

    private final String baseUrl = "/api/v1/refrigerators";

    @Test
    @DisplayName("재료를_검색한다.")
    public void searchIngredientTest() throws Exception {

        List<SearchIngredientResponse> list = new ArrayList<>();
        SearchIngredientResponse mockResponse1 = new SearchIngredientResponse(1L, "토마토", 1L);
        SearchIngredientResponse mockResponse2 = new SearchIngredientResponse(2L, "토마토 퓨레", 12L);

        list.add(mockResponse1);
        list.add(mockResponse2);

        when(refrigeratorsService.searchIngredientList(any(String.class)))
               .thenReturn(list);

        mockMvc.perform(
                get(baseUrl + "/ingredient")
                        .param("keyword", URLEncoder.encode("토마토", StandardCharsets.UTF_8.toString()))
                        .characterEncoding(StandardCharsets.UTF_8)
                )
                .andExpect(status().isOk())
                .andDo(
                        document("refrigerators/searchIngredient",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("keyword").description("검색어")
                                ),
                                responseFields(
                                        fieldWithPath("[]").description("재료 목록"),
                                        fieldWithPath("[].ingredientId").type(JsonFieldType.NUMBER).description("ingredient PK"),
                                        fieldWithPath("[].ingredientName").type(JsonFieldType.STRING).description("재료 이름"),
                                        fieldWithPath("[].ingredientCategoryId").type(JsonFieldType.NUMBER).description("ingredientCategory PK")
                                )
                        )
                );
    }

    @Test
    @DisplayName("냉장고에_재료를_등록한다.")
    public void registerIngredient() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        List<Long> ingredientIds = new ArrayList<>();
        ingredientIds.add(1L);
        List<CustomIngredientRequest> customIngredientRequests = new ArrayList<>();
        customIngredientRequests.add(new CustomIngredientRequest(1L, "토마토마"));
        InsertIngredientRequest insertIngredientRequest = new InsertIngredientRequest(ingredientIds, customIngredientRequests);

//        doNothing().when(refrigeratorsService.insertIngredient())

        mockMvc.perform(post(baseUrl)
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(insertIngredientRequest))
        ).andExpect(status().isNoContent())
                .andDo(
                        document("refrigerators/registerIngredient",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("ingredients").description("ingredient PK"),
                                        fieldWithPath("customIngredients").description("사용자 입력 재료"),
                                        fieldWithPath("customIngredients[].ingredientCategoryId").description("재료 카테고리 PK"),
                                        fieldWithPath("customIngredients[].ingredientName").description("재료 이름")
                                ))
                );
    }

    @Test
    @DisplayName("냉장고의_재료를_조회한다.")
    public void getUserRefrigerator() throws Exception {

        List<UserRefrigeratorResponse> list = new ArrayList<>();

        list.add(new UserRefrigeratorResponse(1L,"마늘",1L, "2023.09.20"));
        list.add(new UserRefrigeratorResponse(6L,"버터",1L, "2023.09.20"));

        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        when(refrigeratorsService.getUserRefrigerator(loginInfo.email())).thenReturn(list);

        mockMvc.perform(get(baseUrl)
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                ).andExpect(status().isOk())
                .andDo(
                        document("refrigerators/getUserRefrigerator",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("[]").description("냉장고 재료 목록"),
                                        fieldWithPath("[].ingredientId").type(JsonFieldType.NUMBER).description("ingredient PK"),
                                        fieldWithPath("[].ingredientName").type(JsonFieldType.STRING).description("재료 이름"),
                                        fieldWithPath("[].ingredientCategoryId").type(JsonFieldType.NUMBER).description("ingredientCategory Id"),
                                        fieldWithPath("[].createDate").type(JsonFieldType.STRING).description("생성 날짜")
                                ))
                );
    }


}
