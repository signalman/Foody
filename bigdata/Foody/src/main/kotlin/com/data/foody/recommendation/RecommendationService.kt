package com.data.foody.recommendation

import com.data.foody.algorithm.Similarity
import org.springframework.stereotype.Service

@Service
class RecommendationService(private val dataService: DataService) {

    private val similarity = Similarity()

    suspend fun getRecommendationByIngredients(ingredients: String): String {
        val allRecipes = dataService.findAllValuesWithCoroutine()

        // 재료의 수가 5개 이상인 레시피만 필터링
        val filteredRecipes = allRecipes?.filter { it.ingredient_count >= 5 }

        // 각 레시피에 대해 유사도 계산
        val similarityScores = filteredRecipes?.map { recipe ->
            val score = similarity.cosineSimilarity(ingredients, recipe.ingredients_concat)
            Pair(recipe, score)
        }

        // 유사도에 따라 레시피 정렬 및 상위 5개 추출 후, 해당 레시피들의 recipe_id만 추출
        val topRecipeIds = similarityScores?.sortedByDescending { it.second }?.take(5)?.map { it.first.recipe_id }

        // Recipe IDs를 하나의 문자열로 반환
        return topRecipeIds?.joinToString(", ") ?: ""
    }
}