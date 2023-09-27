package com.foody.recommend.dto.response;

public record RecommendItem(
        long id,
        double energy,
        double protein,
        double dietaryFiber,
        double calcium,
        double sodium,
        double iron,
        double fats,
        double vitaminA,
        double vitaminC
        ) {
}
