package com.foody.recipe.util;

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
    }

}