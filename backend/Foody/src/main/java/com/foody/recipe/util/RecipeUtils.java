package com.foody.recipe.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecipeUtils {


    public static List<String> getDescriptionFromString(String description) {
        // 문자열의 시작과 끝의 '['와 ']' 제거
        description = description.trim();
        if (description.startsWith("[")) {
            description = description.substring(1);
        }
        if (description.endsWith("]")) {
            description = description.substring(0, description.length() - 1);
        }

        String[] steps = description.split("', '");

        return Arrays.stream(steps)
                     .map(RecipeUtils::cleanUpDescription)
                     .collect(Collectors.toList());
    }

    // 불필요한 문자나 패턴 제거
    private static String cleanUpDescription(String description) {
        description = description.replace(".'", "."); // .' 와 같은 문자 제거
        description = description.replace("'", ""); // ' 문자 제거
        return description.trim();
    }

    public static String formatIngredients(String jsonInput) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 싱글 쿼트를 더블 쿼트로 변환
        jsonInput = jsonInput.replace("'", "\"");
        try {
            List<Map<String, String>> ingredients = objectMapper.readValue(jsonInput, List.class);

            List<String> formattedIngredients = new ArrayList<>();
            for (Map<String, String> ingredient : ingredients) {
                StringBuilder sb = new StringBuilder();

                sb.append(ingredient.get("ingre_name"));
                if (!ingredient.get("ingre_count").isEmpty()) {
                    sb.append(" ").append(ingredient.get("ingre_count"));
                }
                if (!ingredient.get("ingre_unit").isEmpty()) {
                    sb.append(ingredient.get("ingre_unit"));
                }
                formattedIngredients.add(sb.toString());
            }
            return String.join(", ", formattedIngredients);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing JSON";
        }
    }

}
