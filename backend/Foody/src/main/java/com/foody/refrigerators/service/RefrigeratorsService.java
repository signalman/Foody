package com.foody.refrigerators.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.dto.request.CustomIngredientRequest;
import com.foody.refrigerators.dto.response.*;
import com.foody.refrigerators.entity.*;
import com.foody.refrigerators.exception.RefrigeratorsException;
import com.foody.refrigerators.repository.IngredientCategoryRepository;
import com.foody.refrigerators.repository.IngredientRepository;
import com.foody.refrigerators.repository.RefrigeratorIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefrigeratorsService {

    private final IngredientRepository ingredientRepository;
    private final RefrigeratorIngredientRepository refrigeratorIngredientRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final MemberService memberService;
    private final WebClient webClient;

    @Value("${X_OCR_SECRET}")
    private String OCRSecret;

    public IngredientCategory findIngredientCategory(Long ingredientCategoryId) {
        return ingredientCategoryRepository.findById(ingredientCategoryId)
                .orElseThrow(
                        () -> new RefrigeratorsException(ErrorCode.INGREDIENT_CATEGORY_NOT_FOUND));

    }

    public Ingredient findIngredient(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(
                        () -> new RefrigeratorsException(ErrorCode.INGREDIENT_NOT_FOUND));
    }

    public RefrigeratorIngredient findRefrigeratorIngredient(Member member, Ingredient ingredient) {
        return refrigeratorIngredientRepository.findByMemberAndIngredient(member, ingredient)
                .orElseThrow(
                        () -> new RefrigeratorsException(ErrorCode.USER_INGREDIENT_NOT_FOUND));
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
                        () -> new RefrigeratorsException(ErrorCode.INGREDIENT_NOT_FOUND)
                );
    }

    public boolean existsIngredient(String keyword) {
        return ingredientRepository.existsByIngredientNameAndIngredientType(keyword, IngredientType.INITIAL);
    }

    public void insertIngredient(String email,List<Long> ingredients) {

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        for(long ingredientId : ingredients) {
            Member member = memberService.findByEmail(email);
            Ingredient ingredient = findIngredient(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if(!existsRefrigeratorIngredient(member, ingredient)) {
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

            if(!existsRefrigeratorIngredient(member, ingredient)) {
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

    public List<SearchIngredientResponse> getReceiptIngredient(String imgData) {

        Map<String, Object> receiptData = WebClient.builder()
                .baseUrl("https://h06yrkfqdl.apigw.ntruss.com/custom/v1/24865/a3c7f37381726a3c8db098b0146f830eeabd9de71d2b8322576ce264d9730c8f/document/receipt")
                .defaultHeader("X-OCR-SECRET", OCRSecret)
                .defaultHeader("Content-Type", "application/json")
                .build()
                .post()
                .body(BodyInserters.fromValue(imgData))
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse ->
                            Mono.error(new RefrigeratorsException(ErrorCode.INVALID_RECEIPT_OCR))
                )
                .bodyToMono(Map.class)
                .block();

        if(receiptData.isEmpty()) {
            throw new RefrigeratorsException(ErrorCode.INGREDIENT_NOT_FOUND);
        }

        log.debug("영수증 데이터 : {}", receiptData);

        Map<String, Object> receipt = (Map<String, Object>) ((List<Map>) receiptData.get("images")).get(0).get("receipt");
        List<Map> items = (List<Map>) ((List<Map>) ((Map<String, Object>) receipt.get("result")).get("subResults")).get(0).get("items");

        List<SearchIngredientResponse> list = new ArrayList<>();

        for (Map item : items) {
            Map<String, Object> name = (Map<String, Object>) ((Map<String, Object>) item).get("name");
            String itemName = (String) ((Map<String, Object>) name.get("formatted")).get("value");

            log.debug("상품명 : {}", itemName);

            Map itemCategorys = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", itemName)
                            .queryParam("display", "4")
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map itemCategory = ((List<Map>) itemCategorys.get("items")).get(3);

            String itemMappingResult = ((String) itemCategory.get("category4")).isBlank()
                    ? ((String) itemCategory.get("category3")) : ((String) itemCategory.get("category4"));

            log.debug("네이버 쇼핑 API 호출 결과 : {}", itemMappingResult);

            log.debug("재료 DB에 존재 여부 : {}", existsIngredient(itemMappingResult));
            if (existsIngredient(itemMappingResult)) {
                list.add(new SearchIngredientResponse(searchIngredient(itemMappingResult)));
            }
        }

        return list;

    }

}
