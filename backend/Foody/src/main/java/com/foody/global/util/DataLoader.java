package com.foody.global.util;

import com.foody.recipe.entity.Recipe;
import com.foody.recipe.repository.RecipeCustomRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataLoader {

    public List<Recipe> readRecipesFromCSV(Path filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            ColumnPositionMappingStrategy<Recipe> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Recipe.class);
            String[] memberFieldsToBindTo = {
                    "id", "name", "ingredient", "description", "url", "difficulty", "servers",
                    "foodMethod", "foodSituation", "foodIngredients", "foodTypes"
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
    public CommandLineRunner dataLoad(RecipeCustomRepository recipeCustomRepository) {
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
}
