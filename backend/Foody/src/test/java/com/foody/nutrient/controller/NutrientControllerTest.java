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
import com.foody.nutrient.dto.response.NutrientByTypeResponse;
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

        String date = "2023-09-20";
        String type = "LUNCH";


        NutrientResponse nutrientResponse = new NutrientResponse(1000,50,30,20,10,10,10,10,10,10);

        when(nutrientService.calcMealNutrient(loginInfo.email(), date, MealType.valueOf(type))).thenReturn(nutrientResponse);

        mockMvc.perform(
            get(baseUrl + "/nutrientInfo")
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("nutrient/nutrientInfo",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestFields(
                        fieldWithPath("time").description("먹은 날짜"),
                        fieldWithPath("mealType").description("식사 타입(BREAKFAST, LUNCH, DINNER, SNACK)")
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
    @DisplayName("회원의_끼니별_권장_영양소_총집합_반환")
    void getAllTypeNutrientTest() throws Exception {
        LoginInfo loginInfo = new LoginInfo("lkm454545@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        NutrientResponse nutrientResponse = new NutrientResponse(1000,50,30,20,10,10,10,10,10,10);

        NutrientByTypeResponse nutrientByTypeResponse = new NutrientByTypeResponse(nutrientResponse,nutrientResponse,nutrientResponse,nutrientResponse);

        when(nutrientService.getAllNutrient(loginInfo.email())).thenReturn(nutrientByTypeResponse);

        mockMvc.perform(
            get(baseUrl + "/alltype")
                .with(authentication(new TestingAuthenticationToken(loginInfo,null)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("/nutrient/allTypeNutrient",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("breakfast").description("아침 권장 영양정보"),
                        fieldWithPath("breakfast.energy").description("칼로리, Kcal"),
                        fieldWithPath("breakfast.carbohydrates").description("탄수화물, g"),
                        fieldWithPath("breakfast.protein").description("단백질, g"),
                        fieldWithPath("breakfast.dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("breakfast.calcium").description("칼슘, mg"),
                        fieldWithPath("breakfast.sodium").description("나트륨, mg"),
                        fieldWithPath("breakfast.iron").description("철분, mg"),
                        fieldWithPath("breakfast.fats").description("지방, g"),
                        fieldWithPath("breakfast.vitaminA").description("비타민A, μg"),
                        fieldWithPath("breakfast.vitaminC").description("비타민C, mg"),

                        fieldWithPath("lunch").description("점심 권장 영양정보"),
                        fieldWithPath("lunch.energy").description("칼로리, Kcal"),
                        fieldWithPath("lunch.carbohydrates").description("탄수화물, g"),
                        fieldWithPath("lunch.protein").description("단백질, g"),
                        fieldWithPath("lunch.dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("lunch.calcium").description("칼슘, mg"),
                        fieldWithPath("lunch.sodium").description("나트륨, mg"),
                        fieldWithPath("lunch.iron").description("철분, mg"),
                        fieldWithPath("lunch.fats").description("지방, g"),
                        fieldWithPath("lunch.vitaminA").description("비타민A, μg"),
                        fieldWithPath("lunch.vitaminC").description("비타민C, mg"),

                        fieldWithPath("dinner").description("저녁 권장 영양정보"),
                        fieldWithPath("dinner.energy").description("칼로리, Kcal"),
                        fieldWithPath("dinner.carbohydrates").description("탄수화물, g"),
                        fieldWithPath("dinner.protein").description("단백질, g"),
                        fieldWithPath("dinner.dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("dinner.calcium").description("칼슘, mg"),
                        fieldWithPath("dinner.sodium").description("나트륨, mg"),
                        fieldWithPath("dinner.iron").description("철분, mg"),
                        fieldWithPath("dinner.fats").description("지방, g"),
                        fieldWithPath("dinner.vitaminA").description("비타민A, μg"),
                        fieldWithPath("dinner.vitaminC").description("비타민C, mg"),

                        fieldWithPath("snack").description("야식 권장 영양정보"),
                        fieldWithPath("snack.energy").description("칼로리, Kcal"),
                        fieldWithPath("snack.carbohydrates").description("탄수화물, g"),
                        fieldWithPath("snack.protein").description("단백질, g"),
                        fieldWithPath("snack.dietaryFiber").description("식이섬유, g"),
                        fieldWithPath("snack.calcium").description("칼슘, mg"),
                        fieldWithPath("snack.sodium").description("나트륨, mg"),
                        fieldWithPath("snack.iron").description("철분, mg"),
                        fieldWithPath("snack.fats").description("지방, g"),
                        fieldWithPath("snack.vitaminA").description("비타민A, μg"),
                        fieldWithPath("snack.vitaminC").description("비타민C, mg")
                    )
                    )
            );
    }
}
