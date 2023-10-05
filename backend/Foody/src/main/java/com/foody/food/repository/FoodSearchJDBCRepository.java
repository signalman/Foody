package com.foody.food.repository;

import com.foody.food.entity.FoodSearch;
import java.util.List;
import org.springframework.stereotype.Repository;

public interface FoodSearchJDBCRepository {

    void bulkInsert(List<FoodSearch> foodSearchList);

    boolean isExistsData();

}
