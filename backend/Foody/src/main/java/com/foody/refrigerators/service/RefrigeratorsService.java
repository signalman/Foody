package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.dto.request.CustomIngredientRequest;
import com.foody.refrigerators.dto.response.*;
import com.foody.refrigerators.entity.*;
import com.foody.refrigerators.exception.IngredientException;
import com.foody.refrigerators.repository.IngredientCategoryRepository;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public boolean existsRefrigeratorIngredient(Member member, Ingredient ingredient) {
        return refrigeratorIngredientRepository.existsByMemberAndIngredient(member, ingredient);
    }

    public Optional<Ingredient> findCustomIngredient(CustomIngredientRequest ingredient) {
        return ingredientRepository.findIngredientByIngredientNameAndIngredientCategory_Id(ingredient.ingredientName(), ingredient.ingredientCategoryId());
    }

    public Ingredient saveCustomIngredient(CustomIngredientRequest customIngredient) {
        IngredientCategory ingredientCategory = findIngredientCategory(customIngredient.ingredientCategoryId());
        return ingredientRepository.save(Ingredient.from(customIngredient.ingredientName(), ingredientCategory));
    }

    public List<SearchIngredientResponse> searchIngredientList(String keyword) {
        return ingredientRepository.findIngredientsByIngredientNameContainingAndIngredientType(keyword, IngredientType.INITIAL)
                .stream().map(SearchIngredientResponse::new).toList();
    }

    public Ingredient searchIngredient(String keyword) {
        return ingredientRepository.findIngredientByIngredientNameAndIngredientType(keyword, IngredientType.INITIAL)
                .orElseThrow(
                        () -> new IngredientException(ErrorCode.INGREDIENT_NOT_FOUND)
                );
    }

    public boolean existsIngredient(String keyword) {
        return ingredientRepository.existsByIngredientName(keyword);
    }

    public void insertIngredient(String email,List<Long> ingredients) {

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        for(long ingredientId : ingredients) {
            Member member = memberService.findByEmail(email);
            Ingredient ingredient = findIngredient(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if(existsRefrigeratorIngredient(member, ingredient)) {
                insertIngredients.add(refrigeratorIngredient);
            }
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

            if(existsRefrigeratorIngredient(member, ingredient)) {
                insertIngredients.add(refrigeratorIngredient);
            }
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

    public List<SearchIngredientResponse> getReceiptIngredient(List<String> itemNames) {

        List<SearchIngredientResponse> list = new ArrayList<>();

        for(String itemName : itemNames) {
            Items items = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", itemName)
                            .queryParam("display","1")
                            .build())
                    .retrieve()
                    .bodyToMono(Items.class)
                    .block();

            log.debug(items.items().toString());

            String itemCategory = items.items().get(0).category4();
            if(existsIngredient(itemCategory)) {
                list.add(new SearchIngredientResponse(searchIngredient(itemCategory)));
            } else {
                list.add(new SearchIngredientResponse(itemName));
            }

        }
        return list;

    }

}
