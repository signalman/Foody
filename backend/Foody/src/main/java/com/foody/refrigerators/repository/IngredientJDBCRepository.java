package com.foody.refrigerators.repository;

import com.foody.refrigerators.dto.request.IngredientCSV;

import java.util.List;

public interface IngredientJDBCRepository {
    void bulkInsert(List<IngredientCSV> ingredients);

    boolean isExistsData();
}
