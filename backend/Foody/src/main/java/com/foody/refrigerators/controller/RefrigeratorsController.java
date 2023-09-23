package com.foody.refrigerators.controller;

import com.foody.refrigerators.dto.request.InsertIngredientRequest;
import com.foody.refrigerators.dto.response.SearchIngredientResponse;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/refrigerators")
@RequiredArgsConstructor
public class RefrigeratorsController {

    private final RefrigeratorsService refrigeratorsService;

    @GetMapping("/ingredient")
    public ResponseEntity<List<SearchIngredientResponse>> searchIngredient(String keyword) {
        log.debug("keyword : " + keyword);
        List<SearchIngredientResponse> ingredients = refrigeratorsService.searchIngredientList(keyword);
        return ResponseEntity.ok().body(ingredients);
    }

    @PostMapping
    public ResponseEntity<String> registerIngredient(
            @RequestBody InsertIngredientRequest insertIngredients,
            @AuthenticationPrincipal LoginInfo loginInfo
    ) {
        refrigeratorsService.insertIngredient(loginInfo.email(), insertIngredients.ingredients());
        refrigeratorsService.insertCustomIngredient(loginInfo.email(), insertIngredients.customIngredients());
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<UserRefrigeratorResponse>> getUserRefrigerator(@AuthenticationPrincipal LoginInfo loginInfo) {
        return ResponseEntity.ok().body(refrigeratorsService.getUserRefrigerator(loginInfo.email()));
    }

    @DeleteMapping
    public ResponseEntity<String> resetRefrigerator(@AuthenticationPrincipal LoginInfo loginInfo) {
        refrigeratorsService.resetRefrigerator(loginInfo.email());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ingredientId}")
    public ResponseEntity<String> deleteIngredient(@AuthenticationPrincipal LoginInfo loginInfo, @PathVariable Long ingredientId) {
        refrigeratorsService.deleteUserIngredient(loginInfo.email(), ingredientId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/receipt")
    public ResponseEntity<List<SearchIngredientResponse>> getReceiptIngredient(@RequestBody List<String> items) {
        List<SearchIngredientResponse> ingredientResponses = refrigeratorsService.getReceiptIngredient(items);
        return ResponseEntity.ok().body(ingredientResponses);
    }

}
