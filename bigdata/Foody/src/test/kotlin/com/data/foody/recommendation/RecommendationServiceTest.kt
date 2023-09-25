package com.data.foody.recommendation

import com.data.foody.domain.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

    @Test
    @DisplayName("코루틴으로 redis의 모든 keys 불러와진다")
    fun shouldFetchAllKeysFromRedisWithRedis() = runBlocking {
        val elapsedTime = measureTimeMillis {
            val keys: Set<String> = withContext(Dispatchers.IO) {
                redisTemplate.keys("*")
            }
            assertEquals(keys.size, 120738)
        }

        println("Elapsed time: $elapsedTime ms")
    }


    @Test
    @DisplayName("service에서 keys 호출된다")
    fun keysCalledByRecService() {
        val keys: Set<String>? = recommendationService.findAllKeys()

        assertEquals(keys?.size, 120738)

    }

    @Test
    @DisplayName("redis에서 모든 value 불러와진다")
    fun shouldFetchAllValuesFromRedis() {

        val elapsedTime = measureTimeMillis {
            val recipes: List<Recipe>? = recommendationService.findAllValues()
            assertEquals(recipes?.size, 120738)
        }
        println("Elapsed time: $elapsedTime ms")

    }

    @Test
    @DisplayName("코루틴으로 모든 value 불러와진다")
    fun shouldFetchAllValuesFromRedisWithCoroutine() = runBlocking {
        val elapsedTime = measureTimeMillis {
            val recipes: List<Recipe>? = recommendationService.findAllValuesWithCoroutine()
            assertEquals(recipes?.size, 120738)
        }
        println("Elapsed time: $elapsedTime ms")
    }
}