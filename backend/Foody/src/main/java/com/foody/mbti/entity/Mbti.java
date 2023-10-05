package com.foody.mbti.entity;

import com.foody.global.entity.BaseEntity;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mbti extends BaseEntity {

    // 종류별
    private int koreanMainDish; // 한식 메인디쉬
    private int westernMainDish; // 양식 메인디쉬
    private int sideDish; // 밑반찬
    private int dessert; // 디저트

    // 상황별
    private int dailyFood; // 일상
    private int festivalFood; // 접대
    private int convenienceFood; // 간편식
    private int snackFood; // 술(안주)
    private int etcFood; // 기타

    // 재료별
    private int meat; // 고기류
    private int vegetableSeafood; // 채소/해물류
    private int processedFood; // 가공식품류
    private int healthFood; // 건강류
    private int grain; // 주식류

    // 방법별
    private int lowCook; // 저온조리
    private int highCook; // 고온조리
    private int waterCook; // 수분조리
    private int rawCook; // 날것
    private int etcCook; // 기타

    public void updateKoreanMainDish(int value) {
        this.koreanMainDish += value;
    }

    public void updateWesternDish(int value) {
        this.westernMainDish += value;
    }

    public void updateSideDish(int value) {
        this.sideDish += value;
    }

    public void updateDessert(int value) {
        this.dessert += value;
    }

    // 상황별
    public void updateDailyFood(int value) {
        this.dailyFood += value;
    }

    public void updateFestivalFood(int value) {
        this.festivalFood += value;
    }

    public void updateConvenienceFood(int value) {
        this.convenienceFood += value;
    }

    public void updateSnackFood(int value) {
        this.snackFood += value;
    }

    public void updateEtcFood(int value) {
        this.etcFood += value;
    }

    // 재료별
    public void updateMeat(int value) {
        this.meat += value;
    }

    public void updateVegetableSeafood(int value) {
        this.vegetableSeafood += value;
    }

    public void updateProcessedFood(int value) {
        this.processedFood += value;
    }

    public void updateHealthFood(int value) {
        this.healthFood += value;
    }

    public void updateGrain(int value) {
        this.grain += value;
    }

    // 방법별
    public void updateLowCook(int value) {
        this.lowCook += value;
    }

    public void updateHighCook(int value) {
        this.highCook += value;
    }

    public void updateWaterCook(int value) {
        this.waterCook += value;
    }

    public void updateRawCook(int value) {
        this.rawCook += value;
    }

    public void updateEtcCook(int value) {
        this.etcCook += value;
    }


}
