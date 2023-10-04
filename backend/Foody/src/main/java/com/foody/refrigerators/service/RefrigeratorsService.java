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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
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

    @Transactional
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

    @Transactional
    public void insertIngredient(String email, List<Long> ingredients) {
        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        for (long ingredientId : ingredients) {
            Ingredient ingredient = findIngredient(ingredientId);

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if (!existsRefrigeratorIngredient(member, ingredient)) {
                insertIngredients.add(refrigeratorIngredient);
            }
        }

        refrigeratorIngredientRepository.saveAll(insertIngredients);
    }

    @Transactional
    public void insertCustomIngredient(String email, List<CustomIngredientRequest> customIngredients) {

        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> insertIngredients = new ArrayList<>();

        List<Ingredient> ingredients = customIngredients.stream().map(customIngredient -> findCustomIngredient(customIngredient)
                .orElseGet(() -> saveCustomIngredient(customIngredient))).toList();

        for (Ingredient ingredient : ingredients) {

            RefrigeratorIngredient refrigeratorIngredient =
                    RefrigeratorIngredient.from(member, ingredient);

            if (!existsRefrigeratorIngredient(member, ingredient)) {
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

    @Transactional
    public void resetRefrigerator(String email) {
        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> ingredients = refrigeratorIngredientRepository.findAllByMember(member);
        refrigeratorIngredientRepository.deleteAllInBatch(ingredients);

    }

    @Transactional
    public void deleteUserIngredient(String email, Long ingredientId) {
        Member member = memberService.findByEmail(email);
        Ingredient ingredient = findIngredient(ingredientId);

        RefrigeratorIngredient refrigeratorIngredient = findRefrigeratorIngredient(member, ingredient);

        refrigeratorIngredientRepository.delete(refrigeratorIngredient);
    }

    @Transactional
    public void deleteIngredientList(String email, Long[] ingredientIds) {
        Member member = memberService.findByEmail(email);

        List<RefrigeratorIngredient> ingredients = new ArrayList<>();

        for(Long ingredientId : ingredientIds) {
            Ingredient ingredient = findIngredient(ingredientId);
            RefrigeratorIngredient refrigeratorIngredient = findRefrigeratorIngredient(member, ingredient);
            ingredients.add(refrigeratorIngredient);
        }

        refrigeratorIngredientRepository.deleteAllInBatch(ingredients);
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


        if (receiptData.isEmpty()) {
            throw new RefrigeratorsException(ErrorCode.INGREDIENT_NOT_FOUND);
        }

        log.debug("영수증 데이터 : {}", receiptData);

        List<Map<String, Object>> images = (List<Map<String, Object>>) receiptData.get("images");
        Map<String, Object> image = images.get(0);

        String message = (String) image.get("message");

        if (!message.equals("SUCCESS")) {
            throw new RefrigeratorsException(ErrorCode.INVALID_RECEIPT_OCR);
        }

        Map<String, Object> receipt = (Map<String, Object>) image.get("receipt");
        Map<String, Object> result = (Map<String, Object>) receipt.get("result");
        List<Map<String, Object>> subResults = (List<Map<String, java.lang.Object>>) result.get("subResults");
        List<Map<String, Object>> items = (List<Map<String, Object>>) subResults.get(0).get("items");

        Set<SearchIngredientResponse> set = new HashSet<>();

        for (Map<String, Object> item : items) {
            Map<String, Object> name = (Map<String, Object>) item.get("name");
            String itemName = (String) name.get("text");

            if (existsIngredient(itemName)) {
                set.add(new SearchIngredientResponse(searchIngredient(itemName)));
                continue;
            }

//            log.debug("상품명 : {}", itemName);

            Map<String, Object> categoryResult = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("query", itemName)
                            .queryParam("display", "2")
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            List<Map<String, Object>> itemCategorys = (List<Map<String, Object>>) categoryResult.get("items");

            if (itemCategorys.isEmpty()) {
                continue;
            }

            for(Map<String, Object> itemCategory : itemCategorys) {
                String itemMappingResult = ((String) itemCategory.get("category4")).isBlank()
                        ? ((String) itemCategory.get("category3")) : ((String) itemCategory.get("category4"));

                log.debug("네이버 쇼핑 API 호출 결과 : {}", itemMappingResult);

                log.debug("재료 DB에 존재 여부 : {}", existsIngredient(itemMappingResult));
                if (existsIngredient(itemMappingResult)) {
                    set.add(new SearchIngredientResponse(searchIngredient(itemMappingResult)));
                    break;
                }
            }
        }

        return set.stream().toList();

    }

}
