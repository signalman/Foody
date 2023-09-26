package com.foody.recipe.util;

import java.util.Arrays;
import java.util.List;
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

}
