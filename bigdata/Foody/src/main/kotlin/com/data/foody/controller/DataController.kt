package com.data.foody.controller

import com.data.foody.recommendation.RecommendationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/data")
@RestController
class DataController(private val recommendationService: RecommendationService) {

    @PostMapping("/ingredient")
    fun getRecommendationByIngredient
}