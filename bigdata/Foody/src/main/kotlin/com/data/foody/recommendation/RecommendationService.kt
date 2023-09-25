package com.data.foody.recommendation

import com.data.foody.domain.Recipe
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RecommendationService (private val redisTemplate: RedisTemplate<String, Recipe>) {

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

}