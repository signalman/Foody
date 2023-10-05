package com.foody.mealplan.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.food.dto.request.FoodRequest;
import com.foody.food.dto.response.FoodResponse;
import com.foody.mealplan.dto.request.MealPlanRequest;
import com.foody.mealplan.dto.response.MealPlanResponse;
import com.foody.mealplan.dto.response.MealResponse;
import com.foody.mealplan.entity.MealType;
import com.foody.nutrient.dto.request.NutrientRequest;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class MealPlanControllerTest extends ControllerTest {

    private final String baseUrl = "/api/v1/mealplan";

    @Test
    @DisplayName("최근 식단 기록 날짜를 보여준다.")
    public void getLast20DaysRecords() throws Exception {

        List<LocalDate> dates = Arrays.asList(LocalDate.parse("2023-09-11"), LocalDate.parse("2023-09-14"));

        LoginInfo loginInfo = new LoginInfo("hoin1234@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        when(mealPlanService.findLast20DaysRecords(loginInfo)).thenReturn(dates);
        mockMvc.perform(
                   get(baseUrl + "/recent")
                       .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
               )
               .andExpect(status().isOk())
               .andDo(
                   document("mealplan/getLast20DaysRecords",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint()),
//                       requestFields(
//
//                           fieldWithPath()
//                       )
                       responseFields(
                           fieldWithPath("[]").description("식단 날짜 기록 목록")
                       )
                   )
               );
    }
    @Test
    @DisplayName("해당 날짜에 기록된 식단을 보여준다.")
    public void getMealPlanByDate() throws Exception {

        NutrientResponse nutrientResponse = new NutrientResponse(20.1, 2.1, 3.3, 4.5, 2.3, 0.0, 2.1,
            2.1, 0.0, 0.0);
        FoodResponse foodResponse1 = new FoodResponse(null, "김치찌개", nutrientResponse);
        FoodResponse foodResponse2 = new FoodResponse(null, "공기밥", nutrientResponse);
        FoodResponse foodResponse3 = new FoodResponse(null, "동그랑땡", nutrientResponse);

        MealResponse breakFastResponse = new MealResponse(nutrientResponse,
            Arrays.asList(foodResponse1, foodResponse2, foodResponse3), "eernfiasdf-jfe.jpg",
            LocalTime.now());
        MealResponse lunchResponse = new MealResponse(nutrientResponse,
            Arrays.asList(foodResponse1, foodResponse2, foodResponse3), "eernfiasdf-jfe.jpg",
            LocalTime.now());
        MealResponse dinnerResponse = new MealResponse(nutrientResponse,
            Arrays.asList(foodResponse1, foodResponse2, foodResponse3), "eernfiasdf-jfe.jpg",
            LocalTime.now());
        MealResponse snackResponse = new MealResponse(nutrientResponse,
            Arrays.asList(foodResponse1, foodResponse2, foodResponse3), "eernfiasdf-jfe.jpg",
            LocalTime.now());
        MealPlanResponse mealPlanResponse = new MealPlanResponse(
            new NutrientResponse(20.1, 2.1, 3.3, 4.5, 2.3, 0.0, 2.1, 2.1, 0.0, 0.0),
            breakFastResponse, lunchResponse, dinnerResponse, snackResponse, LocalDate.now());

        LoginInfo loginInfo = new LoginInfo("hoin1234@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        when(mealPlanService.getMealPlanByDate("2023-10-01", loginInfo)).thenReturn(mealPlanResponse);

        mockMvc.perform(
            get(baseUrl)
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
                .param("date", "2023-10-01")
               )
               .andExpect(status().isOk())
               .andDo(
                   document("/mealplan/getMealPlanByDate",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint()),
                       responseFields(
                           fieldWithPath("total.energy").description("데일리 영양소 - 열량"),
                           fieldWithPath("total.carbohydrates").description("데일리 영양소 - 탄수화물"),
                           fieldWithPath("total.protein").description("데일리 영양소 - 단백질"),
                           fieldWithPath("total.dietaryFiber").description("데일리 영양소 - 식이섬유"),
                           fieldWithPath("total.calcium").description("데일리 영양소 - 칼슘"),
                           fieldWithPath("total.sodium").description("데일리 영양소 - 나트륨"),
                           fieldWithPath("total.iron").description("데일리 영양소 - 철분"),
                           fieldWithPath("total.fats").description("데일리 영양소 - 지방"),
                           fieldWithPath("total.vitaminA").description("데일리 영양소 - 비타민A"),
                           fieldWithPath("total.vitaminC").description("데일리 영양소 - 비타민C"),

                           fieldWithPath("breakfast.total.energy").description("아침 총합 열량"),
                           fieldWithPath("breakfast.total.carbohydrates").description("아침 총합 탄수화물"),
                           fieldWithPath("breakfast.total.protein").description("아침 총합 단백질"),
                           fieldWithPath("breakfast.total.dietaryFiber").description("아침 총합 식이섬유"),
                           fieldWithPath("breakfast.total.calcium").description("아침 총합 칼슘"),
                           fieldWithPath("breakfast.total.sodium").description("아침 총합 나트륨"),
                           fieldWithPath("breakfast.total.iron").description("아침 총합 철분"),
                           fieldWithPath("breakfast.total.fats").description("아침 총합 지방"),
                           fieldWithPath("breakfast.total.vitaminA").description("아침 총합 비타민A"),
                           fieldWithPath("breakfast.total.vitaminC").description("아침 총합 비타민C"),

                           fieldWithPath("breakfast.foods.[].name").description("아침 음식 이름"),
                           fieldWithPath("breakfast.foods.[].nutrient.energy").description("아침 총합 열량"),
                           fieldWithPath("breakfast.foods.[].nutrient.carbohydrates").description("아침 총합 탄수화물"),
                           fieldWithPath("breakfast.foods.[].nutrient.protein").description("아침 총합 단백질"),
                           fieldWithPath("breakfast.foods.[].nutrient.dietaryFiber").description("아침 총합 식이섬유"),
                           fieldWithPath("breakfast.foods.[].nutrient.calcium").description("아침 총합 칼슘"),
                           fieldWithPath("breakfast.foods.[].nutrient.sodium").description("아침 총합 나트륨"),
                           fieldWithPath("breakfast.foods.[].nutrient.iron").description("아침 총합 철분"),
                           fieldWithPath("breakfast.foods.[].nutrient.fats").description("아침 총합 지방"),
                           fieldWithPath("breakfast.foods.[].nutrient.vitaminA").description("아침 총합 비타민A"),
                           fieldWithPath("breakfast.foods.[].nutrient.vitaminC").description("아침 총합 비타민C"),
                           fieldWithPath("breakfast.imgUrl").description("아침 대표 사진"),
                           fieldWithPath("breakfast.time").description("아침 입력 시각"),

                           fieldWithPath("lunch.total.energy").description("점심 총합 열량"),
                           fieldWithPath("lunch.total.carbohydrates").description("점심 총합 탄수화물"),
                           fieldWithPath("lunch.total.protein").description("점심 총합 단백질"),
                           fieldWithPath("lunch.total.dietaryFiber").description("점심 총합 식이섬유"),
                           fieldWithPath("lunch.total.calcium").description("점심 총합 칼슘"),
                           fieldWithPath("lunch.total.sodium").description("점심 총합 나트륨"),
                           fieldWithPath("lunch.total.iron").description("점심 총합 철분"),
                           fieldWithPath("lunch.total.fats").description("점심 총합 지방"),
                           fieldWithPath("lunch.total.vitaminA").description("점심 총합 비타민A"),
                           fieldWithPath("lunch.total.vitaminC").description("점심 총합 비타민C"),

                           fieldWithPath("lunch.foods.[].name").description("점심 음식 이름"),
                           fieldWithPath("lunch.foods.[].nutrient.energy").description("점심 음식 열량"),
                           fieldWithPath("lunch.foods.[].nutrient.carbohydrates").description("점심 음식 탄수화물"),
                           fieldWithPath("lunch.foods.[].nutrient.protein").description("점심 음식 단백질"),
                           fieldWithPath("lunch.foods.[].nutrient.dietaryFiber").description("점심 음식 식이섬유"),
                           fieldWithPath("lunch.foods.[].nutrient.calcium").description("점심 음식 칼슘"),
                           fieldWithPath("lunch.foods.[].nutrient.sodium").description("점심 음식 나트륨"),
                           fieldWithPath("lunch.foods.[].nutrient.iron").description("점심 음식 철분"),
                           fieldWithPath("lunch.foods.[].nutrient.fats").description("점심 음식 지방"),
                           fieldWithPath("lunch.foods.[].nutrient.vitaminA").description("점심 음식 비타민A"),
                           fieldWithPath("lunch.foods.[].nutrient.vitaminC").description("점심 음식 비타민C"),
                           fieldWithPath("lunch.imgUrl").description("점심 대표 사진"),
                           fieldWithPath("lunch.time").description("점심 입력 시각"),

                           fieldWithPath("dinner.total.energy").description("저녁 총합 열량"),
                           fieldWithPath("dinner.total.carbohydrates").description("저녁 총합 탄수화물"),
                           fieldWithPath("dinner.total.protein").description("저녁 총합 단백질"),
                           fieldWithPath("dinner.total.dietaryFiber").description("저녁 총합 식이섬유"),
                           fieldWithPath("dinner.total.calcium").description("저녁 총합 칼슘"),
                           fieldWithPath("dinner.total.sodium").description("저녁 총합 나트륨"),
                           fieldWithPath("dinner.total.iron").description("저녁 총합 철분"),
                           fieldWithPath("dinner.total.fats").description("저녁 총합 지방"),
                           fieldWithPath("dinner.total.vitaminA").description("저녁 총합 비타민A"),
                           fieldWithPath("dinner.total.vitaminC").description("저녁 총합 비타민C"),

                           fieldWithPath("dinner.foods.[].name").description("저녁 음식 이름"),
                           fieldWithPath("dinner.foods.[].nutrient.energy").description("저녁 음식 열량"),
                           fieldWithPath("dinner.foods.[].nutrient.carbohydrates").description("저녁 음식 탄수화물"),
                           fieldWithPath("dinner.foods.[].nutrient.protein").description("저녁 음식 단백질"),
                           fieldWithPath("dinner.foods.[].nutrient.dietaryFiber").description("저녁 음식 식이섬유"),
                           fieldWithPath("dinner.foods.[].nutrient.calcium").description("저녁 음식 칼슘"),
                           fieldWithPath("dinner.foods.[].nutrient.sodium").description("저녁 음식 나트륨"),
                           fieldWithPath("dinner.foods.[].nutrient.iron").description("저녁 음식 철분"),
                           fieldWithPath("dinner.foods.[].nutrient.fats").description("저녁 음식 지방"),
                           fieldWithPath("dinner.foods.[].nutrient.vitaminA").description("저녁 음식 비타민A"),
                           fieldWithPath("dinner.foods.[].nutrient.vitaminC").description("저녁 음식 비타민C"),
                           fieldWithPath("dinner.imgUrl").description("저녁 대표 사진"),
                           fieldWithPath("dinner.time").description("저녁 입력 시각"),
                           
                           fieldWithPath("snack.total.energy").description("간식 총합 열량"),
                           fieldWithPath("snack.total.carbohydrates").description("간식 총합 탄수화물"),
                           fieldWithPath("snack.total.protein").description("간식 총합 단백질"),
                           fieldWithPath("snack.total.dietaryFiber").description("간식 총합 식이섬유"),
                           fieldWithPath("snack.total.calcium").description("간식 총합 칼슘"),
                           fieldWithPath("snack.total.sodium").description("간식 총합 나트륨"),
                           fieldWithPath("snack.total.iron").description("간식 총합 철분"),
                           fieldWithPath("snack.total.fats").description("간식 총합 지방"),
                           fieldWithPath("snack.total.vitaminA").description("간식 총합 비타민A"),
                           fieldWithPath("snack.total.vitaminC").description("간식 총합 비타민C"),

                           fieldWithPath("snack.foods.[].name").description("간식 음식 이름"),
                           fieldWithPath("snack.foods.[].nutrient.energy").description("간식 음식 열량"),
                           fieldWithPath("snack.foods.[].nutrient.carbohydrates").description("간식 음식 탄수화물"),
                           fieldWithPath("snack.foods.[].nutrient.protein").description("간식 음식 단백질"),
                           fieldWithPath("snack.foods.[].nutrient.dietaryFiber").description("간식 음식 식이섬유"),
                           fieldWithPath("snack.foods.[].nutrient.calcium").description("간식 음식 칼슘"),
                           fieldWithPath("snack.foods.[].nutrient.sodium").description("간식 음식 나트륨"),
                           fieldWithPath("snack.foods.[].nutrient.iron").description("간식 음식 철분"),
                           fieldWithPath("snack.foods.[].nutrient.fats").description("간식 음식 지방"),
                           fieldWithPath("snack.foods.[].nutrient.vitaminA").description("간식 음식 비타민A"),
                           fieldWithPath("snack.foods.[].nutrient.vitaminC").description("간식 음식 비타민C"),
                           fieldWithPath("snack.imgUrl").description("간식 대표 사진"),
                           fieldWithPath("snack.time").description("간식 입력 시각"),
                           
                           fieldWithPath("lunch").description("점심"),
                           fieldWithPath("dinner").description("저녁"),
                           fieldWithPath("snack").description("간식"),
                           fieldWithPath("date").description("날짜")
                       )
                       )
               );
    }

    @Test
    @DisplayName("식단을 해당 날짜에 등록한다.")
    public void registMealPlan() throws Exception {
        LoginInfo loginInfo = new LoginInfo("hoin1234@gmail.com");
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new TestingAuthenticationToken(loginInfo, null));
        SecurityContextHolder.setContext(securityContext);

        NutrientRequest nutrientRequest = new NutrientRequest(20.1, 2.1, 3.3, 4.5, 2.3, 0.0, 2.1,
            2.1, 0.0, 0.0);
        FoodRequest foodRequest1 = new FoodRequest("김치", nutrientRequest);
        FoodRequest foodRequest2 = new FoodRequest("공기밥", nutrientRequest);
        FoodRequest foodRequest3 = new FoodRequest("된장찌개", nutrientRequest);

        MealPlanRequest mealPlanRequest = new MealPlanRequest(MealType.BREAKFAST, "2023-10-01",
            Arrays.asList(foodRequest1, foodRequest2, foodRequest3));

        MockMultipartFile multipartFile = new MockMultipartFile("mealImage", new byte[13]);

        doNothing().when(mealPlanService).registMealPlan(loginInfo, mealPlanRequest, multipartFile, null);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("mealPlanRequest", "",
            "application/json", objectMapper.writeValueAsBytes(mealPlanRequest));
        mockMvc.perform(
            multipart(baseUrl + "/food")
                .file("mealImage", multipartFile.getBytes())
                .file(mockMultipartFile)
                .with(authentication(new TestingAuthenticationToken(loginInfo, null)))
        ).andExpect(status().isNoContent())
               .andDo(
                   document("mealplan/registMealPlan",
                       preprocessRequest(prettyPrint()),
                       preprocessResponse(prettyPrint()),
                       requestParts(
                           partWithName("mealImage").description("식단 이미지"),
                           partWithName("mealPlanRequest").description("식단 요청 정보")),
                       requestPartFields(
                           "mealPlanRequest",
                           fieldWithPath("type").description("식사 유형"),
                           fieldWithPath("date").description("식단 날짜"),
                           fieldWithPath("foodRequestList[].name").description("음식 이름"),
                           fieldWithPath("foodRequestList[].nutrientRequest.*").description("영양소 정보")
                       )
                   )
               );
    }
}
