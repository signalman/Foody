package com.foody.recommend.service;

import com.foody.recommend.dto.response.RecommendItem;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RefrigeratorsService refrigeratorsService;

    @Transactional
    public List<RecommendItem> findRecommendItemByIngredients(String email) {

        List<UserRefrigeratorResponse> refrigerator = refrigeratorsService.getUserRefrigerator(email);
        String ingredients = getIngredientsString(refrigerator);

        return null;
    }

    private String getIngredientsString(List<UserRefrigeratorResponse> refrigerator) {

        return refrigerator.stream()
                .map(UserRefrigeratorResponse::ingredientName)
                .collect(Collectors.joining(" "));
    }
}
