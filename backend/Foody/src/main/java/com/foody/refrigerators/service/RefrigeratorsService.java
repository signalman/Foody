package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.dto.request.InsertIngredientRequest;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.entity.Ingredient;
import com.foody.refrigerators.entity.RefrigeratorIngredient;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
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
    private final MemberService memberService;

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
            Member member = memberService.findByEmail(email);
            Ingredient ingredient = findByIngredientId(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);
            ingredients.add(refrigeratorIngredient);
        }

        refrigeratorIngredientRepository.saveAll(ingredients);
    }



}
