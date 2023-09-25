package com.data.foody.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true) //  JSON 직렬화/역직렬화 중에 알려지지 않은 속성이 있을 경우 무시
data class Recipe(
    @JsonProperty("recipe_id") val recipe_id: Long,
    @JsonProperty("ingredients") val ingredients: String,
    @JsonProperty("ingredients_concat") val ingredients_concat: String,
    @JsonProperty("ingredient_count") val ingredient_count: Int,
    @JsonProperty("energy") val energy: Float,
    @JsonProperty("carbohydrates") val carbohydrates: Float,
    @JsonProperty("protein") val protein: Float,
    @JsonProperty("fats") val fats: Float,
    @JsonProperty("dietaryFiber") val dietaryFiber: Float,
    @JsonProperty("calcium") val calcium: Float,
    @JsonProperty("sodium") val sodium: Float,
    @JsonProperty("iron") val iron: Float,
    @JsonProperty("vitaminA") val vitaminA: Float,
    @JsonProperty("vitaminC") val vitaminC: Float,
    @JsonProperty("difficulty") val difficulty: String,
    @JsonProperty("servers") val servers: Int,
    @JsonProperty("food_preparation_methods") val food_preparation_methods: String,
    @JsonProperty("food_situations") val food_situations: String,
    @JsonProperty("food_ingredients") val food_ingredients: String,
    @JsonProperty("food_types") val food_types: String
)
