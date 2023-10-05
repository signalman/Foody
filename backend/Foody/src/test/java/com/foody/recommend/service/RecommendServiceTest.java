package com.foody.recommend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.foody.mbti.dto.response.MbtiResponse;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.recommend.dto.resquest.CombineInfoForPreference;
import com.foody.recommend.dto.resquest.CombineInfoForRefrigerator;
import com.foody.util.ServiceTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RecommendServiceTest extends ServiceTest {

    @Autowired RecommendService recommendService;
    @Test
    @DisplayName("재료 String으로 조립된다")
    void t1() throws Exception {

        List<String> ingredients = List.of("가", "나", "다", "라");

        String result = ingredients.stream().collect(Collectors.joining(" "));

        assertEquals(result, "가 나 다 라");
    }

    @Test
    @DisplayName("재료 기반, 영양소 기반으로 유사한 레시피 추천한다")
    void t2() throws Exception {
        String ingredients = "대파 고추가루 돼지고기 고추 파프리카 피망 동태 황태";
        NutrientResponse nutrientResponse = new NutrientResponse(600.5, 50.2, 15.3, 20.4, 10.1, 75.6, 200.7, 8.3, 55.9, 30.2);
        CombineInfoForRefrigerator combineInfoForRefrigerator = new CombineInfoForRefrigerator(ingredients, nutrientResponse);
        List<Long> recommendItemList = recommendService.ingredientSendToServer(combineInfoForRefrigerator);

        System.out.println(recommendItemList.get(0));
        recommendItemList.forEach(System.out::println);
        assertEquals(10, recommendItemList.size());
    }

    @Test
    @DisplayName("하이브리드 추천 시스템으로 레시피 추천한다")
    void t3() throws Exception {
        MbtiResponse mbtiResponse = new MbtiResponse(1, 2 ,3 ,4 ,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        List<Long> hybridRecommendList = recommendService.preferenceSendToServer(mbtiResponse);

        assertEquals(10, hybridRecommendList.size());
    }

    @Test
    @DisplayName("취향 + 결핍영양소 레시피 추천된다")
    void t4() throws Exception {

        NutrientResponse nutrientResponse = new NutrientResponse(600.5, 50.2, 15.3, 20.4, 10.1, 75.6, 200.7, 8.3, 55.9, 30.2);
        MbtiResponse mbtiResponse = new MbtiResponse(1, 2 ,3 ,4 ,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        CombineInfoForPreference combineInfoForPreference = new CombineInfoForPreference(nutrientResponse, mbtiResponse);

        List<Long> preferenceRecommendList = recommendService.preferenceAndNutrientSendToServer(
            combineInfoForPreference);

        assertEquals(10, preferenceRecommendList.size());

    }

}