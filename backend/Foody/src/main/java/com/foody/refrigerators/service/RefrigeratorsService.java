package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.dto.request.CustomIngredientRequest;
import com.foody.refrigerators.dto.response.ReceiptIngredientResponse;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.entity.*;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientCategoryRepository;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefrigeratorsService {

    private final IngredientRepository ingredientRepository;
    private final RefrigeratorIngredientRepository refrigeratorIngredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final MemberService memberService;
    private final WebClient webClient;

    public IngredientCategory findIngredientCategory(Long ingredientCategoryId) {
        return ingredientCategoryRepository.findById(ingredientCategoryId)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND));

    }

    public Ingredient findIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_NOT_FOUND));
    }

    public RefrigeratorIngredient findRefrigeratorIngredient(Member member, Ingredient ingredient) {
        return refrigeratorIngredientRepository.findByMemberAndIngredient(member, ingredient)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.USER_INGREDIENT_NOT_FOUND));
    }

    public Optional<Ingredient> findCustomIngredient(CustomIngredientRequest ingredient) {
        return ingredientRepository.findIngredientByIngredientNameAndIngredientCategory_Id(ingredient.ingredientName(), ingredient.ingredientCategoryId());
    }

    public Ingredient saveCustomIngredient(CustomIngredientRequest customIngredient) {
        IngredientCategory ingredientCategory = findIngredientCategory(customIngredient.ingredientCategoryId());
        return ingredientRepository.save(Ingredient.from(customIngredient.ingredientName(), ingredientCategory));
    }

    public List<SearchIngredientResponse> searchIngredient(String keyword) {
        List<SearchIngredientResponse> searchResult = ingredientRepository.findIngredientsByIngredientNameContainingAndIngredientType(keyword, IngredientType.INITIAL)
                .stream().map(SearchIngredientResponse::new).toList();

//        log.debug("first ingredient name : " + searchResult.get(0).getIngredientName());
        return searchResult;
    }

    public void insertIngredient(String email,List<Long> ingredients) {

        // TODO : 사용자의 냉장고에 이미 재료가 존재하면 다시 저장하지 않음

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        for(long ingredientId : ingredients) {
            Member member = memberService.findByEmail(email);
            Ingredient ingredient = findIngredient(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);
            insertIngredients.add(refrigeratorIngredient);
        }

        refrigeratorIngredientRepository.saveAll(insertIngredients);
    }

    public void insertCustomIngredient(String email, List<CustomIngredientRequest> customIngredients){

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        List<Ingredient> ingredients = customIngredients.stream().map(customIngredient -> findCustomIngredient(customIngredient)
                .orElseGet(() -> saveCustomIngredient(customIngredient))).toList();

        for(Ingredient ingredient : ingredients) {
            Member member = memberService.findByEmail(email);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);
            insertIngredients.add(refrigeratorIngredient);
        }

        refrigeratorIngredientRepository.saveAll(insertIngredients);

    }

    public List<UserRefrigeratorResponse> getUserRefrigerator(String email) {
        Member member = memberService.findByEmail(email);
        List<RefrigeratorIngredient> ingredients = refrigeratorIngredientRepository.findAllByMember(member);
        return ingredients.stream().map(ingredient -> new UserRefrigeratorResponse(ingredient)).toList();
    }

    public void resetRefrigerator(String email) {
        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> ingredients = refrigeratorIngredientRepository.findAllByMember(member);
        refrigeratorIngredientRepository.deleteAllInBatch(ingredients);

    }

    public void deleteUserIngredient(String email, Long ingredientId) {
        Member member = memberService.findByEmail(email);
        Ingredient ingredient = findIngredient(ingredientId);

        RefrigeratorIngredient refrigeratorIngredient = findRefrigeratorIngredient(member, ingredient);

        refrigeratorIngredientRepository.delete(refrigeratorIngredient);
    }

    public List<ReceiptIngredientResponse> getReceiptIngredient(List<String> items) {

        for(String item : items) {
            ResponseEntity<String> test = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", item)
                            .queryParam("display","1")
                            .build())
                    .retrieve()
                    .toEntity(String.class)
                    .block();

            log.debug(test.toString());
        }
        return null;

    }

}
