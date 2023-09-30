package com.foody.recommend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.foody.mbti.dto.response.MbtiResponse;
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
    @DisplayName("재료 기반으로 유사한 레시피 도출한다")
    void t2() throws Exception {
        String ingredients = "대파 고추가루 돼지고기 고추 파프리카 피망 동태 황태";
        List<Long> recommendItemList = recommendService.ingredientSendToServer(ingredients);

        System.out.println(recommendItemList.get(0));

        assertEquals(5, recommendItemList.size());
    }

    @Test
    @DisplayName("하이브리드 추천 시스템으로 레시피 추천한다")
    void t3() throws Exception {
        MbtiResponse mbtiResponse = new MbtiResponse(1, 2 ,3 ,4 ,5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

        List<Long> hybridRecommendList = recommendService.preferenceSendToServer(mbtiResponse);

        assertEquals(4, hybridRecommendList.size());
    }

}