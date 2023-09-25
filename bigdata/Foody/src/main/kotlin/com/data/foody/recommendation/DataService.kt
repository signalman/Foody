package com.data.foody.recommendation

import com.data.foody.domain.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class DataService (private val redisTemplate: RedisTemplate<String, Recipe>) {

    fun findAllKeys() : Set<String>? {
        return redisTemplate.keys("*")
    }

    fun findAllValues() : List<Recipe>? {
        val keys = findAllKeys()
        val recipes: MutableList<Recipe> = mutableListOf()

        keys?.forEach { x ->
            redisTemplate.opsForValue().get(x)?.let {
                recipes.add(it)
            }
        }

        return recipes
    }

    suspend fun findAllValuesWithCoroutine(): List<Recipe>? = withContext(Dispatchers.IO) {
        val keys = findAllKeys()
        val recipes: MutableList<Recipe> = mutableListOf()

        keys?.let {
            coroutineScope {
                val deferredRecipes = keys.map { key ->
                    async {
                        redisTemplate.opsForValue().get(key)
                    }
                }
                deferredRecipes.forEach { deferredRecipe ->
                    deferredRecipe.await()?.let { recipe ->
                        recipes.add(recipe)
                    }
                }
            }
        }

        recipes
    }



}