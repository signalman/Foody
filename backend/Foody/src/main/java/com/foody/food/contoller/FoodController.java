package com.foody.food.contoller;

import com.foody.food.dto.response.FoodSearchResponse;
import com.foody.food.service.FoodService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/food")
public class FoodController {

    private final FoodService foodService;


    /* 자동완성 기능 */
    @GetMapping("/autocomplete")
    public ResponseEntity<Set<String>> getFoodSuggestion(@RequestParam String query){
        Set<String> suggestion = foodService.getFoodSuggestionsV1(query, 10);
        return ResponseEntity.ok()
                             .body(suggestion);
    }

    /* 음식 이름으로 dto 반환 */
    @GetMapping("")
    public ResponseEntity<FoodSearchResponse> getFoodByName(@RequestParam String name){
        FoodSearchResponse foodResponse = foodService.getFoodNameByName(name);
        return ResponseEntity.ok()
                             .body(foodResponse);
    }



}
