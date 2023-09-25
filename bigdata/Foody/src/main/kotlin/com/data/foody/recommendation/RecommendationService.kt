package com.data.foody.recommendation

import com.data.foody.domain.Recipe
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RecommendationService (private val redisTemplate: RedisTemplate<String, Recipe>) {

    fun findAllKeys() : Set<String>? {
        return redisTemplate.keys("*")
    }

}