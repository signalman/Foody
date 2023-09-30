package com.foody.nutrient.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

import com.foody.mealplan.entity.MealType;
import com.foody.nutrient.dto.request.AteFoodNutrientInfoRequest;
import com.foody.nutrient.dto.request.NutrientTypeRequest;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class NutrientControllerTest extends ControllerTest {

    private final String baseUrl = "/api/v1/nutrient";

    @Test
    @DisplayName("회원_1일_총_권장_영양소를_반환한다.")
    void getNutrientTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        NutrientResponse nutrientResponse = new NutrientResponse(1000,50,30,20,10,10,10,10,10,10);
        String email = "lkm454545@gmail.com";

        when(nutrientService.getNutrient(email)).thenReturn(new NutrientResponse(1000,50,30,20,10,10,10,10,10,10));

        mockMvc.perform(
            get(baseUrl + "/")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("nutrient/getNutrient",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("energy").description("칼로리, Kcal"),
                        fieldWithPath("carbohydrates").description("탄수화물, g"),
                        fieldWithPath("protein").description("단백질, g"),
                        fieldWithPath("dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("calcium").description("칼슘, mg"),
                        fieldWithPath("sodium").description("나트륨, mg"),
                        fieldWithPath("iron").description("철분, mg"),
                        fieldWithPath("fats").description("지방, g"),
                        fieldWithPath("vitaminA").description("비타민A, μg"),
                        fieldWithPath("vitaminC").description("비타민C, mg")
                    )
                    )
            );
    }

    @Test
    @DisplayName("회원_끼니별_권장_영양소를_반환한다.")
    void getTypeNutrientTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        NutrientTypeRequest nutrientTypeRequest = new NutrientTypeRequest(MealType.BREAKFAST);

        NutrientResponse nutrientResponse = new NutrientResponse(1000,50,30,20,10,10,10,10,10,10);

        when(nutrientService.getMealNutrient(loginInfo.email(), MealType.BREAKFAST)).thenReturn(nutrientResponse);

        mockMvc.perform(
            get(baseUrl + "/type")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(nutrientTypeRequest))
        ).andExpect(status().isOk())
            .andDo(
                document("nutrient/getTypeNutrient",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("mealType").description("식사 타입(MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK)")
                    ),
                    responseFields(
                        fieldWithPath("energy").description("칼로리, Kcal"),
                        fieldWithPath("carbohydrates").description("탄수화물, g"),
                        fieldWithPath("protein").description("단백질, g"),
                        fieldWithPath("dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("calcium").description("칼슘, mg"),
                        fieldWithPath("sodium").description("나트륨, mg"),
                        fieldWithPath("iron").description("철분, mg"),
                        fieldWithPath("fats").description("지방, g"),
                        fieldWithPath("vitaminA").description("비타민A, μg"),
                        fieldWithPath("vitaminC").description("비타민C, mg")
                    )
                )
            );
    }

    @Test
    @DisplayName("회원의_끼니별_먹은_영양소_반환한다.")
    void getNutrientInfoTest() throws Exception{
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        AteFoodNutrientInfoRequest ateFoodNutrientInfoRequest =
            new AteFoodNutrientInfoRequest( LocalDateTime.now(),MealType.BREAKFAST);

        NutrientResponse nutrientResponse = new NutrientResponse(1000,50,30,20,10,10,10,10,10,10);

        when(nutrientService.calcMealNutrient(loginInfo.email(), ateFoodNutrientInfoRequest.time(), ateFoodNutrientInfoRequest.mealType())).thenReturn(nutrientResponse);

        mockMvc.perform(
            get(baseUrl + "/nutrientInfo")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(ateFoodNutrientInfoRequest))
        ).andExpect(status().isOk())
            .andDo(
                document("nutrient/nutrientInfo",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("time").description("먹은 날짜"),
                        fieldWithPath("mealType").description("식사 타입(MealType.BREAKFAST, MealType.LUNCH, MealType.DINNER, MealType.SNACK)")
                        ),
                    responseFields(
                        fieldWithPath("energy").description("칼로리, Kcal"),
                        fieldWithPath("carbohydrates").description("탄수화물, g"),
                        fieldWithPath("protein").description("단백질, g"),
                        fieldWithPath("dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("calcium").description("칼슘, mg"),
                        fieldWithPath("sodium").description("나트륨, mg"),
                        fieldWithPath("iron").description("철분, mg"),
                        fieldWithPath("fats").description("지방, g"),
                        fieldWithPath("vitaminA").description("비타민A, μg"),
                        fieldWithPath("vitaminC").description("비타민C, mg")
                    )
                )
            );
    }
}
