package com.data.foody.configuration

import com.data.foody.domain.Recipe
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.RedisTemplate

@Profile("{local, server}")
@Configuration
class DataLoaderConfiguration {

    @Bean
    fun dataLoader(redisTemplate: RedisTemplate<String, Recipe>): CommandLineRunner {
        return CommandLineRunner {
            val resource = ClassPathResource("data/preprocessing/recipe_information.csv")

            csvReader().open(resource.inputStream) {
                readNext() // 헤더 읽기
                var row = readNext()
                while (row != null) {
                    val recipe = Recipe(
                            recipe_id = row[0].toLong(),
                            ingredients = row[1],
                            ingredients_concat = row[2],
                            ingredient_count = row[3].toInt(),
                            energy = row[4].toFloat(),
                            carbohydrates = row[5].toFloat(),
                            protein = row[6].toFloat(),
                            fats = row[7].toFloat(),
                            dietaryFiber = row[8].toFloat(),
                            calcium = row[9].toFloat(),
                            sodium = row[10].toFloat(),
                            iron = row[11].toFloat(),
                            vitaminA = row[12].toFloat(),
                            vitaminC = row[13].toFloat(),
                            difficulty = row[14],
                            servers = row[15].toFloat().toInt(),
                            food_preparation_methods = row[16],
                            food_situations = row[17],
                            food_ingredients = row[18],
                            food_types = row[19]
                    )

                    redisTemplate.opsForValue().set(recipe.recipe_id.toString(), recipe)
                    row = readNext()
                }
            }
        }
    }
}