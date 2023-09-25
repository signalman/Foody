package com.data.foody.domain

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.test.context.ActiveProfiles
import kotlin.system.measureTimeMillis

@SpringBootTest
@ActiveProfiles("test")
class CsvReaderTest {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Recipe>
    private lateinit var sampleRecipe: Recipe

    @BeforeEach
    fun setUp() {
        val resource = ClassPathResource("data/preprocessing/recipe_information.csv")
        var target: List<String>? = listOf()

        csvReader().open(resource.inputStream) {
            readNext()
            target = readNext()
        }
        print(target)
        if(target != null) {
            sampleRecipe = Recipe(
                    recipe_id = target!![0].toLong(),
                    ingredients = target!![1],
                    ingredients_concat = target!![2],
                    ingredient_count = target!![3].toInt(),
                    energy = target!![4].toFloat(),
                    carbohydrates = target!![5].toFloat(),
                    protein = target!![6].toFloat(),
                    fats = target!![7].toFloat(),
                    dietaryFiber = target!![8].toFloat(),
                    calcium = target!![9].toFloat(),
                    sodium = target!![10].toFloat(),
                    iron = target!![11].toFloat(),
                    vitaminA = target!![12].toFloat(),
                    vitaminC = target!![13].toFloat(),
                    difficulty = target!![14],
                    servers = target!![15].toFloat().toInt(),
                    food_preparation_methods = target!![16],
                    food_situations = target!![17],
                    food_ingredients = target!![18],
                    food_types = target!![19]
            )
        }

    }

    @Test
    @DisplayName("CSV 파일 읽기 테스트")
    fun read_csv_test() {

        val resource = ClassPathResource("data/preprocessing/recipe_information.csv")
        val recipeIndex: List<String> = listOf(
                "recipe_id", "ingredients", "ingredients_concat", "ingredient_count",
                "energy", "carbohydrates", "protein", "fats", "dietaryFiber", "calcium",
                "sodium", "iron", "vitaminA", "vitaminC", "difficulty", "servers",
                "food_preparation_methods", "food_situations", "food_ingredients", "food_types"
        )

        var target: List<String> = listOf()
        val elapsedTime = measureTimeMillis {
            val rows: List<List<String>> = csvReader().readAll(resource.inputStream)
            target = rows[0]
        }

        println("Elapsed time: $elapsedTime ms")
        assertEquals(target, recipeIndex)

    }

    @Test
    @DisplayName("redis에 데이터 삽입된다")
    fun redis_insertion_and_retrieval_test() {

         // 데이터 삽입
         redisTemplate.opsForValue().set(sampleRecipe.recipe_id.toString(), sampleRecipe)

         // 데이터 검색
        val retrievedRecipe = redisTemplate.opsForValue().get(sampleRecipe.recipe_id.toString())

        println(retrievedRecipe)
        print(retrievedRecipe!!.ingredients)

        assertThat(retrievedRecipe).isNotNull
        assertThat(retrievedRecipe).isEqualTo(sampleRecipe)



    }
}