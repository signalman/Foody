package com.data.foody.controller

import com.data.foody.recommendation.DataService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/data")
@RestController
class DataController(private val dataService: DataService) {

    @PostMapping("/ingredient")
    fun getRecommendationByIngredient(@RequestBody ingredients: String) {

    }
}