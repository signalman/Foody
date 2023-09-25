package com.data.foody.recommendation

import com.data.foody.domain.Recipe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import kotlin.system.measureTimeMillis

@ActiveProfiles("test")
@SpringBootTest
class RecommendationServiceTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Recipe>

    @Autowired
    private lateinit var recommendationService: RecommendationService

    @Test
    @DisplayName("redis의 모든 keys 불러와진다")
    fun shouldFetchAllKeysFromRedis() {

        val elapsedTime = measureTimeMillis {
        val keys: Set<String> = redisTemplate.keys("*")
            assertEquals(keys.size, 120738)
        }

        println("Elapsed time: $elapsedTime ms")
    }
}