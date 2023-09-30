package com.foody.global.util;

import com.foody.food.entity.FoodSearch;
import com.foody.food.repository.FoodSearchRepository;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.repository.RecipeCustomRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataLoader {

    private final RecipeCustomRepository recipeCustomRepository;
    private final FoodSearchRepository foodSearchRepository;

    public List<Recipe> readRecipesFromCSV(Path filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            ColumnPositionMappingStrategy<Recipe> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Recipe.class);
            String[] memberFieldsToBindTo = {
                    "id", "name", "ingredient", "description", "url", "difficulty", "servers",
                    "foodMethod", "foodSituation", "foodIngredients", "foodTypes", "energy", "carbohydrates",
                "protein", "dietaryFiber", "calcium", "sodium", "iron", "fats", "vitaminA", "vitaminC"
            };
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<Recipe> csvToBean = new CsvToBeanBuilder<Recipe>(reader)
                    .withMappingStrategy(strategy)
                    .withSkipLines(1)
                    .withType(Recipe.class)
                    .build();

            return csvToBean.parse();
        }
    }

    @Bean
    public CommandLineRunner dataLoad() {
        return (args) -> {
            boolean exists = recipeCustomRepository.isExistsData();
            ClassPathResource resource = new ClassPathResource("recipe/foody_recipe.csv");
            Path path = resource.getFile().toPath();
            if(!exists) {
                List<Recipe> recipes = readRecipesFromCSV(path);
                recipeCustomRepository.bulkInsert(recipes);
            }
        };
    }

    public List<FoodSearch> readFoodsFromCSV(Path filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            ColumnPositionMappingStrategy<FoodSearch> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(FoodSearch.class);
            String[] memberFieldsToBindTo = {
                "id", "name", "energy", "carbohydrates",
                "protein", "dietaryFiber", "calcium", "sodium", "iron", "fats", "vitaminA", "vitaminC"
            };
            strategy.setColumnMapping(memberFieldsToBindTo);

            CsvToBean<FoodSearch> csvToBean = new CsvToBeanBuilder<FoodSearch>(reader)
                .withMappingStrategy(strategy)
                .withSkipLines(1)
                .withType(FoodSearch.class)
                .build();

            return csvToBean.parse();
        }
    }

    @Bean
    public CommandLineRunner foodDataLoad() {
        return (args) -> {
            boolean exists = foodSearchRepository.isExistsData();
            ClassPathResource resource = new ClassPathResource("food/foody_food.csv");
            Path path = resource.getFile().toPath();
            if(!exists) {
                List<FoodSearch> foodSearchList = readFoodsFromCSV(path);
                foodSearchRepository.bulkInsert(foodSearchList);
            }
        };
    }

}
