package com.data.foody.recommendation

import com.data.foody.algorithm.Similarity
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
            Pair(recipe.recipe_id, score)
        }

        // 유사도에 따라 레시피 정렬 및 상위 5개 추출
        val topRecipesWithScores = similarityScores?.sortedByDescending { it.second }?.take(5)

        // Recipe IDs와 유사도를 하나의 문자열로 반환
        return topRecipesWithScores?.joinToString(", ") { "${it.first}: ${it.second}" } ?: ""
    }

    suspend fun getRecommendationByIngredientsWithCoroutine(ingredients: String): String {
        val allRecipes = dataService.findAllValuesWithCoroutine()

        // 재료의 수가 5개 이상인 레시피만 필터링
        val filteredRecipes = allRecipes?.filter { it.ingredient_count >= 5 }

        // 각 레시피에 대해 유사도 계산 (병렬 처리)
        val similarityScores = coroutineScope {
            filteredRecipes?.map { recipe ->
                async {
                    val score = similarity.cosineSimilarity(ingredients, recipe.ingredients_concat)
                    Pair(recipe.recipe_id, score)
                }
            }?.map { it.await() }
        }

        // 유사도에 따라 레시피 정렬 및 상위 5개 추출
        val topRecipesWithScores = similarityScores?.sortedByDescending { it.second }?.take(5)
        // Recipe IDs와 유사도를 하나의 문자열로 반환
        return topRecipesWithScores?.joinToString(", ") { "${it.first}: ${it.second}" } ?: ""
    }

    suspend fun getRecommendationByJaccard(ingredients: String): String {
        val allRecipes = dataService.findAllValuesWithCoroutine()

        // 재료의 수가 5개 이상인 레시피만 필터링
        val filteredRecipes = allRecipes?.filter { it.ingredient_count >= 5 }

        // 각 레시피에 대해 유사도 계산 (자카드 유사도 활용)
        val similarityScores = filteredRecipes?.map { recipe ->
            val score = similarity.jaccardSimilarity(ingredients, recipe.ingredients_concat)
            Pair(recipe.recipe_id, score)
        }

        // 유사도에 따라 레시피 정렬 및 상위 5개 추출
        val topRecipesWithScores = similarityScores?.sortedByDescending { it.second }?.take(5)

        // Recipe IDs와 유사도를 하나의 문자열로 반환
        return topRecipesWithScores?.joinToString(", ") { "${it.first}: ${it.second}" } ?: ""
    }

    suspend fun getRecommendationByTFIDFWithCoroutine(ingredients: String): String {
        val allRecipes = dataService.findAllValuesWithCoroutine()

        // 재료의 수가 5개 이상인 레시피만 필터링
        val filteredRecipes = allRecipes?.filter { it.ingredient_count >= 5 }

        // 각 레시피에 대해 유사도 계산
        val similarityScores = filteredRecipes?.map { recipe ->
            val score = similarity.tfidfSimilarity(ingredients, recipe.ingredients_concat)
            Pair(recipe.recipe_id, score)
        }

        // 유사도에 따라 레시피 정렬 및 상위 5개 추출
        val topRecipesWithScores = similarityScores?.sortedByDescending { it.second }?.take(5)

        // Recipe IDs와 유사도를 하나의 문자열로 반환
        return topRecipesWithScores?.joinToString(", ") { "${it.first}: ${it.second}" } ?: ""
    }
}