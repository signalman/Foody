package com.foody.global.util;

import com.foody.recipe.entity.Recipe;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CsvUtil {

    public List<Recipe> readRecipesFromCSV(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
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
}
