package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.refrigerators.dto.request.InsertIngredientRequest;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.entity.Ingredient;
import com.foody.refrigerators.entity.RefrigeratorIngredient;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import com.foody.user.entity.User;
import com.foody.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefrigeratorsService {

    private final IngredientRepository ingredientRepository;
    private final RefrigeratorIngredientRepository refrigeratorIngredientRepository;
    private final UserService userService;

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

    public void insertIngredient(String email, InsertIngredientRequest insertIngredientRequest) {

        List<RefrigeratorIngredient> ingredients = new ArrayList<>();

        for(long ingredientId : insertIngredientRequest.IngredientId()) {
            User user = userService.findByEmail(email);
            Ingredient ingredient = findByIngredientId(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(user, ingredient);
            ingredients.add(refrigeratorIngredient);
        }

        refrigeratorIngredientRepository.saveAll(ingredients);
    }



}
