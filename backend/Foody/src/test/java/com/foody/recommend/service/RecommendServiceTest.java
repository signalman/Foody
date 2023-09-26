package com.foody.recommend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.foody.util.ServiceTest;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecommendServiceTest extends ServiceTest {

    @Test
    @DisplayName("재료 String으로 조립된다")
    void t1() throws Exception {

        List<String> ingredients = List.of("가", "나", "다", "라");

        String result = ingredients.stream().collect(Collectors.joining(" "));

        assertEquals(result, "가 나 다 라");
    }

}