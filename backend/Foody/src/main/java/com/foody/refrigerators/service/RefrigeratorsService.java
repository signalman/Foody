package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.entity.Ingredient;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefrigeratorsService {

    private final IngredientRepository ingredientRepository;

    public Ingredient findByIngredientId(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_NOT_FOUND));
    }

    public List<SearchIngredientResponse> searchIngredient(String keyword) {
        List<SearchIngredientResponse> searchResult = ingredientRepository.findIngredientsByIngredientNameContaining(keyword)
                .stream().map(SearchIngredientResponse::new).toList();

        log.debug("first ingredient name : " + searchResult.get(0).getIngredientName());
        return searchResult;
    }


}
