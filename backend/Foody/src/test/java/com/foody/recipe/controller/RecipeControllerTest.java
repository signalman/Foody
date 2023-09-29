package com.foody.recipe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.foody.recipe.dto.IngredientUnit;
import com.foody.recipe.dto.response.RecipeResponse;
import com.foody.security.util.LoginInfo;
import com.foody.util.ControllerTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;

class RecipeControllerTest extends ControllerTest {

    private final String BASEURL = "/api/v1/recipe";

    @Test
    @DisplayName("레시피 상세 조회 성공한다")
    void recipeInformationReserved() throws Exception {
        List<String> steps = List.of("김치를 꺼내줍니다", "김치를 먹어줍니다.");
        List<IngredientUnit> ingredientUnitList = List.of(new IngredientUnit("김치", "2쪽"), new IngredientUnit("물", "1컵"));
        RecipeResponse recipeResponse = new RecipeResponse(123456L, "김치찜", steps, ingredientUnitList, "testUrl", "어려움", 2, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, true);

        when(recipeService.findById(anyLong(), any())).thenReturn(recipeResponse);

        setAuthentication();

        mockMvc.perform(
            get(BASEURL + "/{id}", 123456L)
                .with(authentication(new TestingAuthenticationToken(new LoginInfo("lkm454545@gmail.com"), null)))
        ).andExpect(status().isOk())
            .andDo(
                document("/recipe/id",
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("id").description("recipe ID")
                    ),
                    responseFields(
                        fieldWithPath("id").description("recipe ID"),
                        fieldWithPath("name").description("레시피 제목"),
                        fieldWithPath("steps").description("레시피 순서(list에 순서대로 저장되있음)"),
                        fieldWithPath("ingredient").description("레시피 재료 목록"),
                        fieldWithPath("ingredient[].name").description("레시피 재료 이름"),
                        fieldWithPath("ingredient[].unit").description("재료의 양"),
                        fieldWithPath("url").description("레시피 사진 주소"),
                        fieldWithPath("difficulty").description("레시피 난이도"),
                        fieldWithPath("servers").description("몇 인분 기준"),
                        fieldWithPath("energy").description("칼로리"),
                        fieldWithPath("carbohydrates").description("탄수화물"),
                        fieldWithPath("protein").description("단백질"),
                        fieldWithPath("dietaryFiber").description("식이섬유"),
                        fieldWithPath("calcium").description("칼슘"),
                        fieldWithPath("sodium").description("나트륨"),
                        fieldWithPath("iron").description("철분"),
                        fieldWithPath("fats").description("지방"),
                        fieldWithPath("vitaminA").description("비타민A"),
                        fieldWithPath("vitaminC").description("비타민C"),
                        fieldWithPath("isBookmarked").description("북마크 여부")
                    )
                )
            );

    }
}