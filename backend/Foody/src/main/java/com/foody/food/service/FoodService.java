package com.foody.food.service;

import com.foody.food.dto.response.FoodResponse;
import com.foody.food.entity.Food;
import com.foody.food.exception.FoodException;
import com.foody.food.repository.FoodRepository;
import com.foody.global.exception.ErrorCode;
import com.foody.mealplan.exception.MealPlanException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    public Food findByName(String name){
        return foodRepository.findByName(name)
                             .orElseThrow(() -> new FoodException(ErrorCode.FOOD_NOT_FOUND));
    }

    public FoodResponse getFoodByName(String name) {
        return new FoodResponse(findByName(name));
    }
}
