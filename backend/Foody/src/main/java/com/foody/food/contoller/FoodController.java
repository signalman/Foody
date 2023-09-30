package com.foody.food.contoller;

import com.foody.food.dto.response.FoodResponse;
import com.foody.food.service.FoodService;
import java.util.List;
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


    @GetMapping("/search-suggest")
    public ResponseEntity<List<FoodResponse>> getFoodSuggestion(@RequestParam String query){
        List<FoodResponse> foodResponseList = foodService.getFoodSuggestion(query);
        return ResponseEntity.ok()
                             .body(foodResponseList);
    }


}
