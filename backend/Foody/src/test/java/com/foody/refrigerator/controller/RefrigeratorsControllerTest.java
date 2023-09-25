package com.foody.refrigerator.controller;

import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

//    @Test
//    @DisplayName("냉장고에_재료를_등록한다.")
//    public void registerIngredient() throws Exception {
//        mockMvc.perform(post(baseUrl))
//    }


}
