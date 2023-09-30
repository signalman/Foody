package com.foody.food.repository;

import com.foody.food.entity.FoodSearch;
import java.util.List;

public interface FoodSearchRepository {

    void bulkInsert(List<FoodSearch> foodSearchList);

    boolean isExistsData();
}
