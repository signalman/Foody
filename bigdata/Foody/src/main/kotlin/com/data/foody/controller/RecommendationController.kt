package com.data.foody.controller

import com.data.foody.recommendation.RecommendationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/data")
@RestController
class RecommendationController(private val recommendationService: RecommendationService) {

    @PostMapping("/ingredient")
    suspend fun getRecommendationByIngredient(@RequestBody ingredients: String): ResponseEntity<String> {

        return ResponseEntity.ok().body(recommendationService.getRecommendationByIngredientsWithCoroutine(ingredients))
    }
}