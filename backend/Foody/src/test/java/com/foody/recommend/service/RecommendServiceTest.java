package com.foody.recommend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.foody.recommend.dto.response.RecommendItem;
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
        List<RecommendItem> recommendItemList = recommendService.ingredientSendToServer(ingredients);

        System.out.println(recommendItemList.get(0));

        assertEquals(5, recommendItemList.size());
    }

}