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


    /* 자동완성 기능 v1 /v2*/
    @GetMapping("/autocomplete/v1")
    public ResponseEntity<Set<String>> getFoodSuggestionV1andV2(@RequestParam String query){
        Set<String> suggestion = foodService.getFoodSuggestionsV1andV2(query, 10);
        return ResponseEntity.ok()
                             .body(suggestion);
    }

    /* 자동완성 기능 v3 */
    @GetMapping("/autocomplete/v3")
    public ResponseEntity<Set<String>> getFoodSuggestionV3(@RequestParam String query){
        Set<String> suggestion = foodService.getFoodSuggestionsV3(query, 10);
        return ResponseEntity.ok()
                             .body(suggestion);
    }

    /* 자동완성 기능 v4 */
    @GetMapping("/autocomplete/v4")
    public ResponseEntity<Set<String>> getFoodSuggestionV4(@RequestParam String query){
        Set<String> suggestion = foodService.getFoodSuggestionsV4(query, 10);
        return ResponseEntity.ok()
                             .body(suggestion);
    }


    /* 음식 이름으로 dto 반환 */
    @GetMapping
    public ResponseEntity<FoodSearchResponse> getFoodByName(@RequestParam String name){
        FoodSearchResponse foodResponse = foodService.getFoodByName(name);
        return ResponseEntity.ok()
                             .body(foodResponse);
    }

    @GetMapping("/hotkeyword")
    public ResponseEntity<Set<String>> getHotKeyword(){
        Set<String> hotKeywords = foodService.getHotKeywords();
        return ResponseEntity.ok()
                             .body(hotKeywords);
    }


}
