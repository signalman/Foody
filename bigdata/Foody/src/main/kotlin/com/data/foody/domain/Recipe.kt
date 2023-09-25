package com.data.foody.domain

data class Recipe(
        val recipe_id: Long,
        val ingredients: String,
        val ingredients_concat: String,
        val ingredient_count: Int,
        val energy: Float,
        val carbohydrates: Float,
        val protein: Float,
        val fats: Float,
        val dietaryFiber: Float,
        val calcium: Float,
        val sodium: Float,
        val iron: Float,
        val vitaminA: Float,
        val vitaminC: Float,
        val difficulty: String,
        val servers: Int,
        val food_preparation_methods: String,
        val food_situations: String,
        val food_ingredients: String,
        val food_types: String
)
