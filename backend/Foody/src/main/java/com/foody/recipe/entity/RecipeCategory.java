package com.foody.recipe.entity;

import com.foody.mbti.entity.Mbti;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecipeCategory {
    KOREAN_MAIN_DISH("한식메인디시") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateKoreanMainDish(value);
        }
    },
    WESTERN_MAIN_DISH("양식메인디시") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateWesternDish(value);
        }
    },
    SIDE_DISH("밑반찬") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateSideDish(value);
        }
    },
    DESSERT("디저트") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateDessert(value);
        }
    },
    DAILY_FOOD("일상") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateDailyFood(value);
        }
    },
    FESTIVAL_FOOD("접대") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateFestivalFood(value);
        }
    },
    CONVENIENCE_FOOD("간편식") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateConvenienceFood(value);
        }
    },
    SNACK_FOOD("술") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateSnackFood(value);
        }
    },
    ETC_FOOD("기타") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateEtcFood(value);
        }
    },
    MEAT("고기류") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateMeat(value);
        }
    },
    VEGETABLE_SEA_FOOD("채소/해물류") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateVegetableSeafood(value);
        }
    },
    PROCESSED_FOOD("가공식품류") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateProcessedFood(value);
        }
    },
    HEALTH_FOOD("건강류") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateHealthFood(value);
        }
    },
    GRAIN("주식류") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateGrain(value);
        }
    },
    LOW_COOK("저온조리") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateLowCook(value);
        }
    },
    HIGH_COOK("고온조리") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateHighCook(value);
        }
    },
    WATER_COOK("수분조리") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateWaterCook(value);
        }
    },
    RAW_COOK("날것") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateRawCook(value);
        }
    },
    ETC_COOK("기타조리") {
        @Override
        public void applyPreference(Mbti mbti, int value) {
            mbti.updateEtcCook(value);
        }
    };

    private final String koreanName;

    public abstract void applyPreference(Mbti mbti, int value);

    public static RecipeCategory fromKoreanName(String koreanName) {
        for (RecipeCategory category : values()) {
            if (category.getKoreanName().equals(koreanName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown korean name: " + koreanName);
    }

}
