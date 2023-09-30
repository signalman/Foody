package com.foody.mbti.dto.response;

import com.foody.mbti.entity.Mbti;
import com.foody.nutrient.entity.Nutrient;

public record MbtiResponse(
    // 종류별
        int koreanMainDish, // 한식 메인디쉬
        int westernMainDish, // 양식 메인디쉬
        int sideDish, // 밑반찬
        int dessert, // 디저트

        // 상황별
        int dailyFood, // 일상
        int festivalFood, // 접대
        int convenienceFood, // 간편식
        int snackFood, // 술(안주)
        int etcFood, // 기타

        // 재료별
        int meat, // 고기류
        int vegetableSeafood, // 채소/해물류
        int processedFood, // 가공식품류
        int healthFood, // 건강류
        int grain, // 주식류

        // 방법별
        int lowCook, // 저온조리
        int highCook, // 고온조리
        int waterCook, // 수분조리
        int rawCook, // 날것
        int etcCook // 기타
) {
    public MbtiResponse(Mbti mbti) {
        this(
            mbti.getKoreanMainDish(),
            mbti.getWesternMainDish(),
            mbti.getSideDish(),
            mbti.getDessert(),

            mbti.getDailyFood(),
            mbti.getFestivalFood(),
            mbti.getConvenienceFood(),
            mbti.getSnackFood(),
            mbti.getEtcFood(),

            mbti.getMeat(),
            mbti.getVegetableSeafood(),
            mbti.getProcessedFood(),
            mbti.getHealthFood(),
            mbti.getGrain(),

            mbti.getLowCook(),
            mbti.getHighCook(),
            mbti.getWaterCook(),
            mbti.getRawCook(),
            mbti.getEtcCook()
        );
    }
}
