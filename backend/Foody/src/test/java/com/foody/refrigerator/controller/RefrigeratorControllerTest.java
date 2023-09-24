package com.foody.refrigerator.controller;

import com.foody.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RefrigeratorControllerTest extends ControllerTest {

    private final String baseUrl = "/api/v1/refrigerator";

    @Test
    @DisplayName("재료_검색")
    public void searchIngredientTest() throws Exception {


        mockMvc.perform(
                get(baseUrl + "/ingredient")
                        .param("keyword", "토마토")
        ).andExpect(status().isOk())
                .andDo(
                        document("refrigerator/searchIngredient")
                );
    }
}
