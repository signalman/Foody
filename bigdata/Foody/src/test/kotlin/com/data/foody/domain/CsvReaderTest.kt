package com.data.foody.domain

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import kotlin.system.measureTimeMillis

@SpringBootTest
class CsvReaderTest {

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
    @DisplayName("redis 연결된다")
    fun connected_redis_test() {

    }
}