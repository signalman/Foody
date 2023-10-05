package com.foody.refrigerator.service;

import com.foody.refrigerators.repository.IngredientCategoryRepository;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import com.foody.util.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;

public class RefrigeratorServiceTest extends ServiceTest {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    RefrigeratorIngredientRepository refrigeratorIngredientRepository;


}
