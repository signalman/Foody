package com.foody.recipe.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.foody.recipe.dto.IngredientUnit;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeUtilsTest {

    @Test
    @DisplayName("레시피 분리 테스트")
    void t1() throws Exception{
        String description = "['1. 먼저 체친 박력분과 베이킹 파우더, 소금, 슈가파우더를 큰볼에 넣고 찬 버터조각을 넣어 주세요.', '2. 스크레퍼나 포크등으로 소보루형태로 만들어 주시다가 중간쯤 시나몬파우더와 아몬드가루를 넣어 줍니다. 시나몬가루를 취향에 맞게 조금 더 넣으셔도 되요. 너무 많이 넣으시지는 말구요. 소보루형태가 될때즘 바닐라오일을 넣고 마무리 해주세요.', '3. 소로부형태를 한덩어리로 뭉쳐서 비닐팩에 넣고 두깨가 1.5~2센티 정도로 될때까지 밀대로 밀어서 냉장고에 3시간정도 보관해 줍니다. 휴지가 끝난 반죽은 비닐을 벗기고 큐브모양으로 잘라서 준비해 주세요.', '4. 팬에 적당한 간격을 두고 팬닝후 170도로 예열한 오븐에 넣고 18~20분 사이로 구워 주세요. 많이 부풀진 않으니까 간격을 너무 멀리 두고 팬닝하실 필요 없어요. 다 구워지면 식힘망이나 바구니 같은데 두고 좀 식혀 주세요.', '5. 거의 다 식어서 약간의 따뜻함이 남았을때 준비해둔 슈가파우더를 볼에 넣고 슈가 파우더를 듬뿍 묻혀 주세요.', '6. 맛있는 시나몬큐브 완성 입니다. 첫맛은 슈가파우더의 달콤함이 샤르르 입안에서 소프트하게 부서지는 식감. 향긋한 시나몬향이 입안 가득 퍼지면서 고소하고 달콤하고 시나몬향이 화악 퍼지는게 정말 너무 맛있어요. 선물용으로도 티푸드로도 강추입니다.']";

        List<String> steps = RecipeUtils.getDescriptionFromString(description);

        System.out.println(steps.size());

        for (String step : steps) {
            System.out.println(step);
        }

        assertEquals(steps.size(), 6);
    }

    @Test
    @DisplayName("재료 목록 분리 테스트")
    void t2() throws Exception {
        String ingredientString = "[{'ingre_name': '식빵', 'ingre_count': '6', 'ingre_unit': '장'}, {'ingre_name': '양상추', 'ingre_count': '4', 'ingre_unit': '쪽'}, {'ingre_name': '슬라이스햄', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '토마토', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '양겨자', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '마요네즈', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '후추', 'ingre_count': '', 'ingre_unit': '약간'}, {'ingre_name': '레몬즙', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '크림치즈', 'ingre_count': '', 'ingre_unit': ''}, {'ingre_name': '계란', 'ingre_count': '6', 'ingre_unit': '개'}, {'ingre_name': '양파', 'ingre_count': '1', 'ingre_unit': '개분'}]";

        List<IngredientUnit> recipeUnits = RecipeUtils.formatIngredients(ingredientString);

        assert recipeUnits != null;
        for(IngredientUnit ingredientUnit : recipeUnits) {
            System.out.println(ingredientUnit.name() + " " + ingredientUnit.unit());
        }

        assertNotNull(recipeUnits);
    }

}